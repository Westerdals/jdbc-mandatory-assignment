package no.group37;

import org.flywaydb.core.Flyway;
import org.h2.jdbcx.JdbcDataSource;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.assertj.core.api.Assertions.assertThat;


public class MemberTest {

    @Test
    void shouldRetrieveMemberList() throws SQLException {
        JdbcDataSource dataSource = new JdbcDataSource();
        dataSource.setUrl("jdbc:h2:mem:test;DB_CLOSE_DELAY=-1");

        Flyway.configure().baselineOnMigrate(true).dataSource(dataSource).load().migrate();

        Member member = new Member();
        member.setMemberName("Ingrid");
        member.setMail("ingrid@gmail.com");
        member.setId(1);
        MemberDao memberDao = new MemberDao(dataSource);
        memberDao.insert(member);
        assertThat(memberDao.listAll()).contains(member);
    }
}
