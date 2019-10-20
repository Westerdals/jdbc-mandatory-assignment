package no.group37;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class MemberToProjectDao extends AbstractDao<MemberToProject> {

    public MemberToProjectDao(DataSource dataSource) {
        super(dataSource);
    }

    @Override
    public void insertObject(MemberToProject memberToProject, PreparedStatement statement) throws SQLException {
        statement.setLong(1, memberToProject.getProjectId());
        statement.setLong(2, memberToProject.getMemberId());
    }

    @Override
    protected MemberToProject readObject(ResultSet rs) throws SQLException {
        MemberToProject memberToProject = new MemberToProject();
        memberToProject.setProjectId(rs.getLong("project_id"));
        memberToProject.setMemberId(rs.getLong("member_id"));
        return memberToProject;
    }

    public void insert(MemberToProject memberToProject) throws SQLException {
         insert(memberToProject,
                "insert into member_to_project (project_id, member_id) values (?, ?)"
        );
    }

    public List<MemberToProject> listAll() throws SQLException {
        return listAll(
                "select * from member_to_project"
        );
    }

    public List<MemberToProject> selectWhereProjectId(long project_id) throws SQLException {
        return listAll(
                "select * from member_to_project where project_id = " + project_id );
    }

    public List<MemberToProject> selectUnique(long project_id, long member_id) throws SQLException {
        return listAll(
                "select * from member_to_project where project_id = " + project_id + " and member_id = " + member_id);
    }

    // select * from  members join  projectmembers on members.id = projectmembers.member_id join projects on projects.id = projectmembers.project_id where projectmembers.project_id =




}
