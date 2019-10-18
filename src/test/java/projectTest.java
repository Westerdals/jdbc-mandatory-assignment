import org.junit.jupiter.api.Test;
import java.util.Random;
import static org.assertj.core.api.Assertions.*;



public class projectTest {

    @Test
    void shouldRetrieveStoredProjects() {
        ProjectDao dao = new ProjectDao();
        String projectName = pickOne(new String[] {"Design", "Java", "JavaScript", "Informasjonssikkerhet", "Smidig"});
        dao.insertProject(projectName);
        assertThat(dao.listAll()).contains(projectName);
    }

    private String pickOne(String[] strings) {
        return strings[new Random().nextInt(strings.length)];
    }
}
