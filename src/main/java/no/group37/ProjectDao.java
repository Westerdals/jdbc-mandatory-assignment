package no.group37;

import org.flywaydb.core.Flyway;
import org.postgresql.ds.PGSimpleDataSource;

import javax.sql.DataSource;
import java.io.FileReader;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;
import java.util.Scanner;


public class ProjectDao extends AbstractDao<String> {


    public ProjectDao(DataSource dataSource) {
        super(dataSource);
    }

    @Override
    public void insertObject(String projectName, PreparedStatement statement) throws SQLException {

        statement.setString(1, projectName);
    }


    @Override
    protected String readObject(ResultSet rs) throws SQLException {
        return rs.getString("name");
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
        tidyProject.insert(projectName);

        System.out.println(tidyProject.listAll());
    }

    public void insert(String projectName) throws SQLException {
        insert(projectName, "insert into projects (name) values (?)");
    }

    public List<String> listAll() throws SQLException {
        return listAll("select * from projects");
    }
}
