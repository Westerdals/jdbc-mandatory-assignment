package no.group37;

import org.assertj.core.api.Assertions;
import org.h2.jdbcx.JdbcDataSource;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.Random;
import static org.assertj.core.api.Assertions.*;



public class projectTest {

    @Test
    void shouldRetrieveStoredProjects() throws SQLException {
        JdbcDataSource dataSource = new JdbcDataSource();
        dataSource.setUrl("jdbc:h2:mem:test");

        dataSource.getConnection().createStatement().execute(
                "create table projects (name varchar(100) not null)"
        );

        ProjectDao dao = new ProjectDao(dataSource);
        String projectName = "Java Project";
        dao.insertProject(projectName);
        Assertions.assertThat(dao.listAll()).contains(projectName);
    }

    private String pickOne(String[] strings) {
        return strings[new Random().nextInt(strings.length)];
    }
}
