package no.group37;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class MemberTest {

    @Test
    void shoultRetrieveMemberList(){
        MemberDao memberDao = new MemberDao();
        memberDao.insertMember(memberName);
        assertThat(memberDao.listAll()).contains(memberName);
    }

}
