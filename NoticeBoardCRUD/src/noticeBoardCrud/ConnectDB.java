package noticeBoardCrud;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * CRUD 공통 소스 관리 - Connection, PreparedStatement, ResultSet
 *
 * @author 유영훈
 * @since 2021. 9. 8
 *
 */
public class ConnectDB {
    
    /**
     * DB 연결을 위한 Connection 객체 반환
     *
     * @author 유영훈
     * @since 2021. 9. 8
     *
     * @return Connection 객체 connection
     */
    public Connection connectDB() {
        
        Connection connection = null;
        
        // 데이터베이스 url
        String url = "jdbc:mysql://127.0.0.1:3306/noticeboard";
        // 데이터베이스 id, pw
        String dbId = "root";
        String dbPwd = "yyhj7424";
        
        try {
            connection = DriverManager.getConnection(url, dbId, dbPwd);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return connection;
    }
    
    /**
     * DB 연결을 해제하기 위한 close() method - insert, delete, update
     *
     * @author 유영훈
     * @since 2021. 9. 8
     *
     * @param statement
     * @param connection
     */
    public void close(PreparedStatement statement, Connection connection) {
        try {
            // 자원이 있다면 해제 
            if (statement != null) {
                statement.close();
            }
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * DB 연결을 해제하기 위한 close() method - select
     *
     * @author 유영훈
     * @since 2021. 9. 8
     *
     * @param resultSet
     * @param statement
     * @param connection
     */
    public void close(ResultSet resultSet, PreparedStatement statement, Connection connection) {
        try {
            // 자원이 있다면 해제
            if (resultSet != null) {
                resultSet.close();
            }
            if (statement != null) {
                statement.close();
            }
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
