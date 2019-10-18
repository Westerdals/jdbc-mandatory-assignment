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

        JdbcDataSource dataSource = new JdbcDataSource();
        dataSource.setUrl("jdbc:h2:mem:test;DB_CLOSE_DELAY=-1");

        Flyway.configure().baselineOnMigrate(true).dataSource(dataSource).load().migrate();

        Member member = new Member();
        member.setMemberName("Frøya");
        member.setMail("froya@mail.no");
        member.setId(1);

        ProjectDao projectDao = new ProjectDao(dataSource);
        MemberDao memberDao = new MemberDao(dataSource);
        String projectName = "Ridekurs";
        projectDao.insert(projectName);
       //not finished!



        //assertThat(member).hasNoNullFieldsOrProperties();

    }
}
