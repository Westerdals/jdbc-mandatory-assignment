package no.group37;

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

    public long getId(){
        return this.id;
    }

    @Override
    public String toString() {
        return   id + ". Name: " + memberName + " Email: " + mail + "\n";

                /*"Member{" +
                "memberName='" + memberName + '\'' +
                ", mail='" + mail + '\'' +
                ", id ='" + id + '\'' +
                '}'; */
    }

}
