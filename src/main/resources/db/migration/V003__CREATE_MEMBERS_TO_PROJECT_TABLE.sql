CREATE TABLE member_to_project (
    id serial primary key,
    project_id int  not null,
    member_id int  not null
);