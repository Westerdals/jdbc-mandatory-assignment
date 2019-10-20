package no.group37;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public abstract class AbstractDao<T> {
    protected DataSource dataSource;


    public AbstractDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public abstract void insertObject(T project, PreparedStatement statement) throws SQLException;

    public void insert(T projectName, String sql1) throws SQLException {
        try (Connection conn = dataSource.getConnection()) {
            try (PreparedStatement statement = conn.prepareStatement(sql1/*, PreparedStatement.RETURN_GENERATED_KEYS*/)) {
                insertObject(projectName, statement);
                statement.executeUpdate();

                /*ResultSet generatedKeys = statement.getGeneratedKeys();
                generatedKeys.next();
                return  generatedKeys.getLong(1);*/
            }
        }
    }

    public List<T> listAll(String sql) throws SQLException {
        try (Connection connection = dataSource.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                try (ResultSet rs = statement.executeQuery()) {
                    List<T> result = new ArrayList<>();

                    while (rs.next()) {
                        result.add(readObject(rs));
                    }
                    return result;
                }
            }
        }
    }



    protected abstract T readObject(ResultSet rs) throws SQLException;
}
