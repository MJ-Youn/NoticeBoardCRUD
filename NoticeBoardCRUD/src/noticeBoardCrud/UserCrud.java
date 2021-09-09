package noticeBoardCrud;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * user table�� �����͸� �����ϱ� ���� Ŭ����
 *
 * @author ������
 * @since 2021. 9. 8
 *
 */
public class UserCrud {

    // �����ͺ��̽��� ���� ���� �Է��ϴ� Scanner
    public Scanner input = new Scanner(System.in);

    /**
     * insert query�� �����ϱ� ���� create method
     *
     * @author ������
     * @since 2021. 9. 8
     *
     * @param user : User class�� �ʵ尪�� �޾ƿ�
     * 
     * @return createValue : ������ row(����)�� ���� ��ȯ
     */
    public int create(User user) {
        int createValue = 0;

        ConnectDB connectDB = new ConnectDB();
        Connection connection = null;
        PreparedStatement statement = null;

        // insert query
        String query = "insert into user(id, pwd, name, age, gender) values(?, ?, ?, ?, ?)";

        try {
            // DriverManager�� ���� DB����. - Ŭ������ ���� �����Ͽ� ȣ��
            connection = connectDB.connectDB();
            statement = connection.prepareStatement(query);

            // table�� ���� ������ �Է�
            System.out.println("input user data");
            // type �˻縦 ���� �ݺ���
            do {
                try {
                    System.out.println("user id : ");
                    user.setId(input.next());

                    System.out.println("user pwd : ");
                    user.setPwd(input.next());

                    System.out.println("user name : ");
                    user.setName(input.next());

                    System.out.println("user age : ");
                    user.setAge(input.nextInt());

                    System.out.println("user gender : ");
                    user.setGender(input.next());

                    break;
                    // type �˻縦 ���� NumberFormatException
                } catch (NumberFormatException e) {
                    System.out.println("\nPlease enter the correct type");
                    continue;
                }
            } while (user.getId() != null);

            // �Է¹��� �� ����
            statement.setString(1, user.getId());
            statement.setString(2, user.getPwd());
            statement.setString(3, user.getName());
            statement.setInt(4, user.getAge());
            statement.setString(5, user.getGender());

            // ������ row(����)�� ���� ��ȯ
            createValue = statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connectDB.close(statement, connection);
        }

        return createValue;
    }

    /**
     * select query�� �����ϱ� ���� read method
     *
     * @author ������
     * @since 2021. 9. 8
     *
     * @return userList : table�� ����� ������ �б� ���� �����ϴ� ����Ʈ
     */
    public List<User> read() {
        // table�� ����� ������ �б� ���� �����ϴ� ����Ʈ
        List<User> userList = new ArrayList<User>();

        // select query
        String query = "select * from user";

        ConnectDB connectDB = new ConnectDB();
        Connection connection = null;
        PreparedStatement statement = null;
        // ���� ����� �����ϱ� ���� ResultSet ��ü
        ResultSet resultSet = null;

        try {
            connection = connectDB.connectDB();
            statement = connection.prepareStatement(query);
            resultSet = statement.executeQuery();

            // table�� ����� ������ Ȯ��
            while (resultSet.next()) {
                // �ϳ��� ���ڵ带 User ��ü�� ����
                User user = new User(resultSet.getString("id"), resultSet.getString("pwd"), resultSet.getString("name"), resultSet.getInt("age"), resultSet.getString("gender"));
                // ����� ��ü�� ����Ʈ�� ����
                userList.add(user);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            System.exit(1);
        } finally {
            connectDB.close(resultSet, statement, connection);
        }

        return userList;
    }

    /**
     * update query�� �����ϱ� ���� update method
     *
     * @author ������
     * @since 2021. 9. 8
     *
     * @param user : User class�� �ʵ尪�� �޾ƿ�
     * @return updateValue : ������ row(����)�� ���� ��ȯ
     */
    public int update(User user) {
        int updateValue = 0;

        ConnectDB connectDB = new ConnectDB();
        Connection connection = null;
        PreparedStatement statement = null;

        // update query
        String query = "update user set pwd=?, name=?, age=?, gender=? where id=?";

        try {
            connection = connectDB.connectDB();
            statement = connection.prepareStatement(query);

            // id �Է�
            System.out.println("Enter the id to update");
            user.setId(input.next());
            statement.setString(5, user.getId());

//            while (statement.executeUpdate() == 0) {
//                System.out.println("id�� �ٽ� �Է��Ͻÿ�");
//                user.setId(input.next());
//                statement.setString(5, user.getId());
//            }
            
            // id check
            

            System.out.println("update user data");

            // update�� ���� �Է� �� type check, id �Է°��� ���ٸ� ����
            while (user.getId() != null) {
                try {
                    System.out.println("update pwd : ");
                    user.setPwd(input.next());
                    System.out.println("update name : ");
                    user.setName(input.next());
                    System.out.println("update age : ");
                    user.setAge(input.nextInt());
                    System.out.println("update gender : ");
                    user.setGender(input.next());
                    break;
                    // type check�� ���� exception
                } catch (NumberFormatException e) {
                    System.out.println("\nPlease enter the correct type");
                    continue;
                }
            }
            statement.setString(1, user.getPwd());
            statement.setString(2, user.getName());
            statement.setInt(3, user.getAge());
            statement.setString(4, user.getGender());

            updateValue = statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
            System.exit(1);
        } finally {
            connectDB.close(statement, connection);
        }

        return updateValue;
    }

    /**
     * delete query�� �����ϱ� ���� delete method
     *
     * @author ������
     * @since 2021. 9. 8
     *
     * @param user : User class�� �ʵ尪�� �޾ƿ�
     * @return deleteValue : ������ row(����)�� ���� ��ȯ
     */
    public int delete(User user) {
        int deleteValue = 0;

        ConnectDB connectDB = new ConnectDB();
        Connection connection = null;
        PreparedStatement statement = null;

        // delete query
        String query = "delete from user where id=?";

        try {

            connection = connectDB.connectDB();
            statement = connection.prepareStatement(query);

            // ������ row�� id �Է�
            System.out.println("Delete to input id : ");
            user.setId(input.nextLine());

            // id ���� üũ
//            List<User> userList = new ArrayList<>();
//            userList = read();
//            int index = 0;
//            while(!(userList.get(index).getId().equals(user.getId()))) {
//                user.setId(input.nextLine());
//            }
//            statement.setString(1, user.getId());
//            
            statement.setString(1, user.getId());
            while (statement.executeUpdate() == 0) {
                user.setId(input.nextLine());
                statement.setString(1, user.getId());
            }

            deleteValue = statement.executeUpdate();

            System.out.println("Delete Success" + "\n");

        } catch (SQLException e) {
            e.printStackTrace();
            System.exit(1);
        } finally {
            connectDB.close(statement, connection);
        }

        return deleteValue;
    }
}
