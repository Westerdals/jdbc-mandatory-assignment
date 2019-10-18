
import java.util.ArrayList;
import java.util.List;


public class ProjectDao {

    private List<String> projects = new ArrayList<>();

    public void insertProject(String projectName) {
        projects.add(projectName);
    }

    public List<String> listAll() {
        return projects;
    }

}
