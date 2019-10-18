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

public class MemberDao {

    private DataSource dataSource;



    public MemberDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void insertMember(String memberName, String email) throws SQLException {

        try(Connection conn = dataSource.getConnection()){
            PreparedStatement statement = conn.prepareStatement(
                    "insert into members (member_name, email) values (?, ?)");
            statement.setString(1,memberName );
            statement.setString(2,email);
            statement.executeUpdate();
        }
    }
    public List<String> listAll() throws SQLException {
        try(Connection conn = dataSource.getConnection()){
            try(PreparedStatement statement = conn.prepareStatement(
                    "SELECT  * FROM members"
            )) {
                try(ResultSet rs = statement.executeQuery()){
                    List<String> result = new ArrayList<>();

                    while (rs.next()) {
                        result.add(rs.getString("member_name"));
                        result.add(rs.getString("email"));
                    }
                    return result;
                }
            }
        }
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

        MemberDao memberDao = new MemberDao(dataSource);
        memberDao.insertMember(memberName, email);
        System.out.println(memberDao.listAll());

    }
}
