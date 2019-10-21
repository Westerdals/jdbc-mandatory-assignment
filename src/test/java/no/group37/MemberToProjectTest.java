package no.group37;

import org.h2.jdbcx.JdbcDataSource;
import org.junit.jupiter.api.Test;
import java.sql.SQLException;
import static org.assertj.core.api.Assertions.*;


public class MemberToProjectTest {

    private JdbcDataSource dataSource = DatabaseTest.testDataSource();


     @Test
    void shouldPutMemberInProject() throws SQLException {

        String memberName = "Frøya";
        String memberMail = "frøya@mail.com";
        Member member = new Member();
        member.setMemberName(memberName);
        member.setMail(memberMail);
        member.setId(1);
        Project project = new Project();
        project.setProjectName("Ridekurs");
        project.setId(1);

        MemberDao memberDao = new MemberDao(dataSource);
        memberDao.insert(member);
        ProjectDao projectDao = new ProjectDao(dataSource);
        projectDao.insert(project);

        MemberToProject memberToProject = new MemberToProject();
        memberToProject.setProjectId(1);
        memberToProject.setMemberId(1);
        MemberToProjectDao memberToProjectDao = new MemberToProjectDao(dataSource);
        memberToProjectDao.insertMemberToProject(memberToProject);



        assertThat(memberToProjectDao.listAll()).contains(memberToProject);

    }
}
