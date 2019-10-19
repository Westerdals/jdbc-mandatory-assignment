package no.group37;

import org.flywaydb.core.Flyway;
import org.postgresql.ds.PGSimpleDataSource;

import javax.sql.DataSource;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import java.util.*;

public class MemberDao extends AbstractDao<Member> {


    public MemberDao(DataSource dataSource) {
        super(dataSource);
    }

    @Override
    public void insertObject(Member member, PreparedStatement statement) throws SQLException {
        statement.setString(1, member.getMemberName());
        statement.setString(2, member.getMail());
    }

    @Override
    protected Member readObject(ResultSet rs) throws SQLException {
        Member member = new Member();
        member.setId(rs.getLong("id"));
        member.setMemberName(rs.getString("member_name"));
        member.setMail(rs.getString("email"));
        return member;
    }

    public static void main(String[] args) throws IOException, SQLException {
        Scanner input = new Scanner(System.in);

        System.out.println("Add a new member name:");
        String memberName = input.nextLine();
        System.out.println("Add a new member email:");
        String email = input.nextLine();

        if(memberName.isEmpty() || email.isEmpty()){
            System.out.println("You didnt write correct name or email. Try again.");
        } else {


            Properties properties = new Properties();
            properties.load(new FileReader("projectdb.properties"));

            PGSimpleDataSource dataSource = new PGSimpleDataSource();
            dataSource.setUrl(properties.getProperty("dataSource.url"));
            dataSource.setPassword(properties.getProperty("dataSource.password"));
            dataSource.setUser(properties.getProperty("dataSource.user"));

            Flyway.configure().dataSource(dataSource).load().migrate();


            Member member = new Member();
            member.setMemberName(memberName);
            member.setMail(email);
            MemberDao memberDao = new MemberDao(dataSource);
            memberDao.insert(member);
            // im gonna turn this line below into a method :P so its not that long. the code below does that we dont print brackets of array anymore so it looks nicer
            System.out.println(Arrays.toString((memberDao.listAll()).toArray()).replace("[", " ").replace("]", "").replace(",", ""));


        }
    }

    public long insert(Member member) throws SQLException {
         return insert(member, "insert into members (member_name, email) values (?, ?)");
    }

    public List<Member> listAll() throws SQLException {
        return listAll("select * from members");
    }
}
