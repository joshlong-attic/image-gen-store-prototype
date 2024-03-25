package com.example.aiimages;

import org.springframework.ai.image.ImageClient;
import org.springframework.ai.image.ImagePrompt;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.annotation.Id;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.function.RouterFunction;
import org.springframework.web.servlet.function.ServerResponse;

import java.util.Collection;
import java.util.Map;

import static org.springframework.web.servlet.function.RouterFunctions.route;
import static org.springframework.web.servlet.function.ServerResponse.ok;

@SpringBootApplication
public class AiImagesApplication {

    public static void main(String[] args) {
        SpringApplication.run(AiImagesApplication.class, args);
    }

    @Bean
    RouterFunction<ServerResponse> routes(ImageClient openAiImageClient, AiImagePromptRepository repository) {
        return route()
                .POST("/prompts", request -> {
                    var prompt = request.param("prompt").orElseThrow();
                    var response = openAiImageClient.call(new ImagePrompt(prompt));
                    var url = response.getResult().getOutput().getUrl();
                    repository.save(new AiImagePrompt(null, prompt, url));
                    return ok().build();
                })
                .GET("/prompts", (request) -> ok().body(repository.findAll()))
                .GET("/prompts.html", (request) -> ok().render("prompts", Map.of("prompts", repository.findAll())))
                .build();
    }
}

/*
@Controller
class AiImageTableController {

    private final AiImagePromptRepository repository;
    private final ImageClient openAiImageClient;

    AiImageTableController(AiImagePromptRepository repository, ImageClient openAiImageClient) {
        this.repository = repository;
        this.openAiImageClient = openAiImageClient;
    }


    // prompts?prompt=...
    @PostMapping("/prompts")
    void add(@RequestParam String prompt) {
        var response = openAiImageClient.call(new ImagePrompt(prompt));
        var url = response.getResult().getOutput().getUrl();
        repository.save(new AiImagePrompt(null, prompt, url));
    }


    // model-view-controller
    @GetMapping({"/", "/prompts.html"})
    ModelAndView allHtml() {
        // src/main/resources/templates/ + STRING + .html
        var map = Map.of("prompts", this.repository.findAll());
        return new ModelAndView("prompts", map);
    }

    @ResponseBody
    @GetMapping("/prompts")
    Collection<AiImagePrompt> all() {
        return this.repository.findAll();
    }
}
*/

interface AiImagePromptRepository extends ListCrudRepository<AiImagePrompt, Integer> {
}

record AiImagePrompt(@Id Integer id, String prompt, String uri) {
}