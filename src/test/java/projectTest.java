import org.junit.jupiter.api.Test;

import javax.sql.DataSource;

import static org.assertj.core.api.Assertions.*;

public class projectTest {


    @Test
    void shouldRetrieveStoredProjects() {
        ProjectDao dao = new ProjectDao();
        // String memberName = pickOne(new String[] {"Java", "JavaScript", "Informasjonssikkerhet", "Smidig"});
        dao.insertProject("Design");
        assertThat(dao.listAll()).contains("Design");
    }
}
