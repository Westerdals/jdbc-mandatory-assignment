package no.group37;

public class Member {
    private String memberName;
    private String mail;

    public void setMemberName(String memberName){
        this.memberName = memberName;
    }

    public String getMemberName(){
        return memberName;
    }

    public void setMail(String mail){
        this.mail = mail;
    }

    public String getMail(){
        return this.mail;
    }

    @Override
    public String toString() {
        return "Member{" +
                "memberName='" + memberName + '\'' +
                ", mail='" + mail + '\'' +
                '}';
    }
}
