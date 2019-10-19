package no.group37;

import java.util.Objects;

public class Member {
    private String memberName;
    private String mail;
    private Long id;


    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    public String getMemberName() {
        return this.memberName;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getMail() {
        return this.mail;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return   id + ". Name: " + memberName + " Email: " + mail + "\n";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Member member = (Member) o;
        return Objects.equals(memberName, member.memberName) &&
                Objects.equals(mail, member.mail) &&
                Objects.equals(id, member.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(memberName, mail, id);
    }
}
