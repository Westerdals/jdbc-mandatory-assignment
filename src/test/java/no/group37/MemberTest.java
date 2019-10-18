package no.group37;

import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.assertj.core.api.Assertions.*;

public class MemberTest {

    @Test
    void shoultRetrieveMemberList() throws SQLException {
        MemberDao memberDao = new MemberDao(dataSource);
        String memberName = "Ingrid";
        String email = "ingrid@mail.no";
        memberDao.insertMember(memberName, email);
        assertThat(memberDao.listAll()).contains(memberName);
    }
}
