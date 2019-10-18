package no.group37;

import java.util.ArrayList;
import java.util.List;

public class MemberDao {

    private List<String> members = new ArrayList<>();

    public void insertMember(String memberName) {
        members.add(memberName);
    }

    public List<String> listAll() {
        return members;
    }
}
