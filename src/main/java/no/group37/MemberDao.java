package no.group37;

import org.flywaydb.core.Flyway;
import org.postgresql.ds.PGSimpleDataSource;

import javax.sql.DataSource;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Scanner;

public class MemberDao extends AbstractDao<Member> {

    private List<Member> members = new ArrayList<>();

    public MemberDao(DataSource dataSource) {
        super(dataSource);
    }

    public void insert(Member member){
        members.add(member);
    }

    @Override
    public void insertObject(Member member, PreparedStatement statement) throws SQLException {
        statement.setString(1, member.getMemberName());
        statement.setString(2, member.getMail());
    }

    public List<Member> listAll() throws SQLException {
        return members;
    }

    @Override
    protected Member readObject(ResultSet rs) throws SQLException {
        Member member = new Member();
        member.setMemberName(rs.getString(1));
        member.setMail(rs.getString(2));
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
}
