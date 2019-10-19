CREATE TABLE member_to_project (
    id SERIAL PRIMARY KEY,
    project_id INT REFERENCES projects(id),
    member_id INT REFERENCES members(id)
);