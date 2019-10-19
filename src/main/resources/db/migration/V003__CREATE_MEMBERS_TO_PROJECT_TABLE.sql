CREATE TABLE member_to_project (
    project_id int  not null,
    member_id int  not null,
    primary key (project_id, member_id)
);