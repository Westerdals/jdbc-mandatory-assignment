package no.group37;

import java.util.Objects;

public class Project {
    private long id;
    private String name;


    public void setId(long id) {
        this.id = id;
    }

    public void setProjectName(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass())
            return false;

        Project project = (Project) o;
        return id == project.id &&
                Objects.equals(name, project.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }

    @Override
    public String toString() {
        return   id + "  |  " + name +  "\n";
    }
}
