package no.group37;

import org.flywaydb.core.Flyway;
import org.postgresql.ds.PGSimpleDataSource;

import javax.sql.DataSource;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;
import java.util.Scanner;


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
}
