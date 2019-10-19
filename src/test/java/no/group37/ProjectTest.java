package no.group37;

import org.flywaydb.core.Flyway;
import org.h2.jdbcx.JdbcDataSource;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.Random;
import static org.assertj.core.api.Assertions.*;



public class ProjectTest {

    @Test
    void shouldRetrieveStoredProjects() throws SQLException {
        JdbcDataSource dataSource = new JdbcDataSource();
        dataSource.setUrl("jdbc:h2:mem:test;DB_CLOSE_DELAY=-1");

        Flyway.configure().baselineOnMigrate(true).dataSource(dataSource).load().migrate();

        ProjectDao dao = new ProjectDao(dataSource);
        String projectName = "Java Project";
        dao.insert(projectName);
        assertThat(dao.listAll()).contains(projectName);
    }

    private String pickOne(String[] strings) {
        return strings[new Random().nextInt(strings.length)];
    }
}
