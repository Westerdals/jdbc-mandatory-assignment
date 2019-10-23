package no.group37;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
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

    //we had to use seperate method different than insert from AbstractDao,  cause we didnt have automatic primary key in member_to_project table
    public void insertMemberToProject(MemberToProject memberToProject) throws SQLException {
        try (Connection conn = dataSource.getConnection()) {
            try (PreparedStatement statement = conn.prepareStatement(
                    "insert into member_to_project (project_id, member_id) values (?, ?)"
                )) {
                insertObject(memberToProject, statement);
                statement.executeUpdate();
            }
        }
    }

    public List<MemberToProject> listAll() throws SQLException {
        return listAll(
                "select * from member_to_project"
        );
    }

    public List<MemberToProject> selectUnique(long project_id, long member_id) throws SQLException {
        return listAll(
                "select * from member_to_project where project_id = " + project_id
                        + " and member_id = " + member_id
        );
    }

    public String listToString(List <MemberToProject> memberToProject) {
        return Arrays.toString((memberToProject).toArray())
                .replace("[", " ")
                .replace("]", "")
                .replace(",", "");
    }
}
