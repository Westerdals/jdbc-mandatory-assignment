package no.group37;

import javax.sql.DataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;



public class ProjectDao extends AbstractDao<Project> {


    public ProjectDao(DataSource dataSource) {
        super(dataSource);
    }

    @Override
    public void insertObject(Project project, PreparedStatement statement) throws SQLException {
        statement.setString(1, project.getName());
    }


    @Override
    protected Project readObject(ResultSet rs) throws SQLException {
        Project project = new Project();
        project.setId(rs.getLong("id"));
        project.setProjectName(rs.getString("name"));
        return project;
    }

    public long insert(Project project) throws SQLException {
        return insert(project,
                "insert into projects (name) values (?)"
        );
    }

    public List<Project> listAll() throws SQLException {
        return listAll(
                "select * from projects"
        );
    }

    public List<Project> listSelectedProjects(long id) throws SQLException {
        return listAll(
                "select * from projects where id=" + id
        );
    }

    public Project retrieve(long id) throws SQLException {
        try (Connection connection = dataSource.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement("select * from projects where id= ?")) {
                statement.setLong(1, id);
                try (ResultSet rs = statement.executeQuery()) {
                    if (rs.next()) {
                        return readObject(rs);
                    } else {
                    return null;
                    }
                }
            }
        }
    }

    public String listToString(List <Project> project) {
        return Arrays.toString((project).toArray())
                .replace("[", " ")
                .replace("]", "")
                .replace(",", "");
    }

}
