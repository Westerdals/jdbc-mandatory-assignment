package no.group37;

import org.h2.jdbcx.JdbcDataSource;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.assertj.core.api.Assertions.assertThat;


public class MemberTest {



    @Test
    void shouldSaveAllMembersFields() throws SQLException {
        JdbcDataSource dataSource = DatabaseTest.testDataSource();

        MemberDao memberDao = new MemberDao(dataSource);
        Member member = sampleMember();
        long id = memberDao.insert(member);
        member.setId(id);
        assertThat(member).hasNoNullFieldsOrProperties();
        assertThat(memberDao.retrieve(id))
                .isEqualToComparingFieldByField(member);
    }

    //maybe we can write test that will check if

    private Member sampleMember(){
        Member member = new Member();
        member.setMemberName("Ingrid");
        member.setMail("ingrid@gmail.com");
        return member;
    }
}
