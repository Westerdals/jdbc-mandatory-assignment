package no.group37;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

public class MemberTest {

    @Test
    void shoultRetrieveMemberList(){
        MemberDao memberDao = new MemberDao();
        String memberName = "Ingrid";
        memberDao.insertMember(memberName);
        assertThat(memberDao.listAll()).contains(memberName);

    }

}
