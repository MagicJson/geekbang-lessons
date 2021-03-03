package org.geektimes.projects.user.repository.work;

import org.geektimes.projects.user.domain.User;
import org.geektimes.projects.user.repository.UserRepository;
import org.geektimes.projects.user.repository.work.manager.MyDBConnectionManager;
import sun.misc.IOUtils;

import javax.sql.DataSource;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.*;
import java.util.Collection;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 统一的数据仓储
 *
 * @author: magic_json
 * @create: 2021-03-02 21:01
 * @since 1.0
 **/
public class MyDBUserRepository implements UserRepository {

    private PreparedStatement preparedStatement = null;

    private static Connection connection = null;

    public static final String DROP_USERS_TABLE_DDL_SQL = "DROP TABLE users";

    public static final String CREATE_USERS_TABLE_DDL_SQL = "CREATE TABLE users(" +
            "id INT NOT NULL PRIMARY KEY GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1), " +
            "name VARCHAR(16) NOT NULL, " +
            "password VARCHAR(64) NOT NULL, " +
            "email VARCHAR(64) NOT NULL, " +
            "phoneNumber VARCHAR(64) NOT NULL" +
            ")";

    public static final String INSERT_USER_DML_SQL = "INSERT INTO users(name,password,email,phoneNumber) VALUES(?,?,?,?) ";

    @Override
    public boolean save(User user) {
        try {
            connection = MyDBConnectionManager.getConnectionByJNDI();
            if (Objects.nonNull(connection)) {
                preparedStatement =  connection.prepareStatement(INSERT_USER_DML_SQL);
                preparedStatement.setString(1,user.getName());
                preparedStatement.setString(2,user.getPassword());
                preparedStatement.setString(3,user.getEmail());
                preparedStatement.setString(4,user.getPhoneNumber());
                int result = preparedStatement.executeUpdate();
                if(result != 0){
                    return true;
                }
            }
        } catch (Exception e) {
            return false;
        }
        return false;
    }

    @Override
    public boolean deleteById(Long userId) {
        return false;
    }

    @Override
    public boolean update(User user) {
        return false;
    }

    @Override
    public User getById(Long userId) {
        return null;
    }

    @Override
    public User getByNameAndPassword(String userName, String password) {
        return null;
    }

    @Override
    public Collection<User> getAll() {
        return null;
    }


    static {
            initCreateTable();
    }

    private static void initCreateTable(){
        Statement statement = null;
        try {
            connection = MyDBConnectionManager.getConnectionByJNDI();
            connection.createStatement();
            statement.execute(DROP_USERS_TABLE_DDL_SQL);
            statement.execute(CREATE_USERS_TABLE_DDL_SQL);
        }catch (Exception e){
            try {
                statement.execute(CREATE_USERS_TABLE_DDL_SQL);
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }

    }

}

