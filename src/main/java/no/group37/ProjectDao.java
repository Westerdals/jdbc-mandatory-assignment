package no.group37;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class ProjectDao {

    private List<String> projects = new ArrayList<>();
    private DataSource dataSource;

    public ProjectDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void insertProject(String projectName) throws SQLException {
        projects.add(projectName);

        Connection conn = dataSource.getConnection();
        PreparedStatement statement = conn.prepareStatement(
                "insert into projects (name) values (?)"
        );
        statement.setString(1,projectName);
        statement.executeUpdate();
    }


    public List<String> listAll() {
        return projects;
    }

}
