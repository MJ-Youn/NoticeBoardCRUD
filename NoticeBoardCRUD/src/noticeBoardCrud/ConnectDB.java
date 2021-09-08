package noticeBoardCrud;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * CRUD ���� �ҽ� ���� - Connection, PreparedStatement, ResultSet
 *
 * @author ������
 * @since 2021. 9. 8
 *
 */
public class ConnectDB {
    
    /**
     * DB ������ ���� Connection ��ü ��ȯ
     *
     * @author ������
     * @since 2021. 9. 8
     *
     * @return Connection ��ü connection
     */
    public Connection connectDB() {
        
        Connection connection = null;
        
        // �����ͺ��̽� url
        String url = "jdbc:mysql://127.0.0.1:3306/noticeboard";
        // �����ͺ��̽� id, pw
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
     * DB ������ �����ϱ� ���� close() method - insert, delete, update
     *
     * @author ������
     * @since 2021. 9. 8
     *
     * @param statement
     * @param connection
     */
    public void close(PreparedStatement statement, Connection connection) {
        try {
            // �ڿ��� �ִٸ� ���� 
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
     * DB ������ �����ϱ� ���� close() method - select
     *
     * @author ������
     * @since 2021. 9. 8
     *
     * @param resultSet
     * @param statement
     * @param connection
     */
    public void close(ResultSet resultSet, PreparedStatement statement, Connection connection) {
        try {
            // �ڿ��� �ִٸ� ����
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
