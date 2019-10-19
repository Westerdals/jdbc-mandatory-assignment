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

    public long insert(Member member) throws SQLException {
         return insert(member,
                 "insert into members (member_name, email) values (?, ?)"
         );
    }

    public List<Member> listAll() throws SQLException {
        return listAll(
                "select * from members"
        );
    }
}
