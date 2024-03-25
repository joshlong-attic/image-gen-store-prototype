create table if not exists ai_image_prompt
(
    prompt text not null,
    uri    text not null,
    id     serial primary key
);