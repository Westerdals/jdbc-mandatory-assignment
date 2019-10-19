package no.group37;

import no.group37.Member;
import no.group37.MemberDao;
import no.group37.ProjectDao;
import org.flywaydb.core.Flyway;
import org.h2.jdbcx.JdbcDataSource;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.assertj.core.api.Assertions.*;


public class MemberToProjectTest {

    @Test
    void shouldPutMemberInProject() throws SQLException {
        Long controlId;


        JdbcDataSource dataSource = new JdbcDataSource();
        dataSource.setUrl("jdbc:h2:mem:test;DB_CLOSE_DELAY=-1");

        Flyway.configure().baselineOnMigrate(true).dataSource(dataSource).load().migrate();

        String memberName = "Frøya";
        String memberMail = "frøya@mail.com";


        Member member = new Member();
        member.setMemberName(memberName);
        member.setMail(memberMail);
        member.setId(1);



        ProjectDao projectDao = new ProjectDao(dataSource);
        MemberDao memberDao = new MemberDao(dataSource);
        String projectName = "Ridekurs";
        //projectDao.insert(projectName);
        controlId = memberDao.insert(member);

        assertThat(memberDao.listAll()).contains(member);
       //not finished!
        //chose project you wanto to assign member to
        //chose member you want to assign
        //projectDao.assignProject(projectId); //chose id of project and insert into MemberToProject table
        //memberDao.assignMember(memberId); //insert into MemberToProject table where id of project as in assignProject



        //assertThat(member).hasNoNullFieldsOrProperties();

    }
}
