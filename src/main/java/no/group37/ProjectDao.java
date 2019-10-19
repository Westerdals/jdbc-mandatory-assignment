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




    public static void main(String[] args) throws SQLException, IOException {
        System.out.println("Add a new project");
        String projectName = new Scanner(System.in).nextLine();

        Properties properties = new Properties();
        properties.load(new FileReader("projectdb.properties"));

        PGSimpleDataSource dataSource = new PGSimpleDataSource();
        dataSource.setUrl(properties.getProperty("dataSource.url"));
        dataSource.setPassword(properties.getProperty("dataSource.password"));
        dataSource.setUser(properties.getProperty("dataSource.user"));

        Flyway.configure().dataSource(dataSource).load().migrate();

        Project project = new Project();
        project.setProjectName(projectName);
        ProjectDao tidyProject = new ProjectDao(dataSource);
        tidyProject.insert(project);

        System.out.println(tidyProject.listAll());
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
