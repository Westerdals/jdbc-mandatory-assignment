package no.group37;

import org.flywaydb.core.Flyway;
import org.h2.jdbcx.JdbcDataSource;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.assertj.core.api.Assertions.*;

public class MemberTest {

    @Test
    void shouldRetrieveMemberList() throws SQLException {
        JdbcDataSource dataSource = new JdbcDataSource();
        dataSource.setUrl("jdbc:h2:mem:test;DB_CLOSE_DELAY=-1");

        Flyway.configure().baselineOnMigrate(true).dataSource(dataSource).load().migrate();

        MemberDao memberDao = new MemberDao(dataSource);
        String memberName = "Ingrid";
        String email = "ingrid@mail.no";
        memberDao.insertMember(memberName, email);
        assertThat(memberDao.listAll()).contains(memberName);
    }
}
