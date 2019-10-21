package no.group37;

import org.h2.jdbcx.JdbcDataSource;
import org.junit.jupiter.api.Test;
import java.sql.SQLException;
import static org.assertj.core.api.Assertions.*;



public class ProjectTest {

    private JdbcDataSource dataSource = DatabaseTest.testDataSource();


    @Test
    void shouldFindStoredProjects() throws SQLException {
        ProjectDao projectDao = new ProjectDao(dataSource);
        Project project = sampleProject();
        long id = projectDao.insert(project);
        project.setId(id);
        assertThat(projectDao.listAll()).contains(project);
    }

    @Test
    void shouldSaveAllProjectFields() throws SQLException {
        ProjectDao projectDao = new ProjectDao(dataSource);
        Project project = sampleProject();
        long id = projectDao.insert(project);
        project.setId(id);
        assertThat(project).hasNoNullFieldsOrProperties();
        assertThat(projectDao.retrieve(id))
                .isEqualToComparingFieldByField(project);
    }

    private Project sampleProject(){
        Project project = new Project();
        String projectName = "Java Project";
        project.setProjectName(projectName);
        return project;
    }

}
