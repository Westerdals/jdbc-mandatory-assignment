package no.group37;

import java.util.Objects;

public class MemberToProject {
    private long projectId;
    private long memberId;

    public long getProjectId() {
        return projectId;
    }

    public void setProjectId(long projectId) {
        this.projectId = projectId;
    }

    public long getMemberId() {
        return memberId;
    }

    public void setMemberId(long memberId) {
        this.memberId = memberId;
    }

    @Override
    public String toString() {
        return "ProjectID : + " + projectId
                + "Name : " + memberId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass())
            return false;

        MemberToProject that = (MemberToProject) o;
        return projectId == that.projectId &&
                memberId == that.memberId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(projectId, memberId);
    }
}
