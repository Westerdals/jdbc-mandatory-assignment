package no.group37;

import no.group37.Member;
import no.group37.MemberDao;
import no.group37.ProjectDao;
import org.flywaydb.core.Flyway;
import org.h2.jdbcx.JdbcDataSource;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

import static org.assertj.core.api.Assertions.*;


public class MemberToProjectTest {

    @Test
    void shouldPutMemberInProject() throws SQLException {

        /*JdbcDataSource dataSource = new JdbcDataSource();
        dataSource.setUrl("jdbc:h2:mem:test;DB_CLOSE_DELAY=-1");

        Flyway.configure().baselineOnMigrate(true).dataSource(dataSource).load().migrate();

        MemberToProject memberToProject = new MemberToProject();
        memberToProject.setProjectId(1);
        memberToProject.setMemberId(1);
        MemberToProjectDao memberToProjectDao = new MemberToProjectDao(dataSource);
        memberToProjectDao.insert(memberToProject);

        assertThat(memberToProjectDao.listAll()).contains(memberToProject); */

        /*String memberName = "Frøya";
        String memberMail = "frøya@mail.com";


        Member member = new Member();
        member.setMemberName(memberName);
        member.setMail(memberMail);
        member.setId(1);
        Project project = new Project();
        project.setProjectName("Ridekurs");
        project.setId(1);
        Project project1 = new Project();
        project1.setProjectName("hulaballo");
        project.setId(2);




        ProjectDao projectDao = new ProjectDao(dataSource);
        MemberDao memberDao = new MemberDao(dataSource);
        projectDao.insert(project);
        projectDao.insert(project1);
        memberDao.insert(member); */

    }
}
