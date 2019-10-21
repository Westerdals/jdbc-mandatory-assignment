CREATE TABLE member_to_project (
    project_id INT REFERENCES projects(id),
    member_id INT REFERENCES members(id),
    primary key (project_id, member_id)
);

