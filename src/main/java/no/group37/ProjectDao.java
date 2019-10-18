package no.group37;

import org.flywaydb.core.Flyway;
import org.postgresql.ds.PGSimpleDataSource;

import javax.sql.DataSource;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Scanner;


public class ProjectDao {

    private DataSource dataSource;


    public ProjectDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void insertProject(String projectName) throws SQLException {

        try (Connection conn = dataSource.getConnection()) {
             PreparedStatement statement = conn.prepareStatement(
                    "insert into projects (name) values (?)"
            );
            statement.setString(1,projectName);
            statement.executeUpdate();
        }
    }


    public List<String> listAll() throws SQLException {
        try (Connection connection = dataSource.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(
                    "select * from projects"
            )) {
                try (ResultSet rs = statement.executeQuery()) {
                    List<String> result = new ArrayList<>();

                    while (rs.next()) {
                        result.add(rs.getString("name"));
                    }

                    return result;
                }
            }
        }
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

        ProjectDao tidyProject = new ProjectDao(dataSource);
        tidyProject.insertProject(projectName);

        System.out.println(tidyProject.listAll());
    }

}
