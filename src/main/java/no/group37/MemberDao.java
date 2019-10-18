package no.group37;

import org.flywaydb.core.Flyway;
import org.postgresql.ds.PGSimpleDataSource;

import javax.sql.DataSource;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Scanner;

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
        System.out.println(memberDao.listAll());


    }

    public void insert(Member member) throws SQLException {
        insert(member, "insert into members (member_name, email) values (?, ?)");
    }

    public List<Member> listAll() throws SQLException {
        return listAll("select * from members");
    }
}
