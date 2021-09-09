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
 * user table의 데이터를 제어하기 위한 클래스
 *
 * @author 유영훈
 * @since 2021. 9. 8
 *
 */
public class UserCrud {

    // 데이터베이스에 넣을 값을 입력하는 Scanner
    public Scanner input = new Scanner(System.in);

    /**
     * insert query를 수행하기 위한 create method
     *
     * @author 유영훈
     * @since 2021. 9. 8
     *
     * @param user : User class의 필드값을 받아옴
     * 
     * @return createValue : 변동된 row(개수)에 대해 반환
     */
    public int create(User user) {
        int createValue = 0;

        ConnectDB connectDB = new ConnectDB();
        Connection connection = null;
        PreparedStatement statement = null;

        // insert query
        String query = "insert into user(id, pwd, name, age, gender) values(?, ?, ?, ?, ?)";

        try {
            // DriverManager를 통한 DB연결. - 클래스로 따로 생성하여 호출
            connection = connectDB.connectDB();
            statement = connection.prepareStatement(query);

            // table에 넣을 데이터 입력
            System.out.println("input user data");
            // type 검사를 위한 반복문
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
                    // type 검사를 위한 NumberFormatException
                } catch (NumberFormatException e) {
                    System.out.println("\nPlease enter the correct type");
                    continue;
                }
            } while (user.getId() != null);

            // 입력받은 값 설정
            statement.setString(1, user.getId());
            statement.setString(2, user.getPwd());
            statement.setString(3, user.getName());
            statement.setInt(4, user.getAge());
            statement.setString(5, user.getGender());

            // 변동된 row(개수)에 대해 반환
            createValue = statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connectDB.close(statement, connection);
        }

        return createValue;
    }

    /**
     * select query를 수행하기 위한 read method
     *
     * @author 유영훈
     * @since 2021. 9. 8
     *
     * @return userList : table에 저장된 값들을 읽기 위해 저장하는 리스트
     */
    public List<User> read() {
        // table에 저장된 값들을 읽기 위해 저장하는 리스트
        List<User> userList = new ArrayList<User>();

        // select query
        String query = "select * from user";

        ConnectDB connectDB = new ConnectDB();
        Connection connection = null;
        PreparedStatement statement = null;
        // 쿼리 결과를 제어하기 위한 ResultSet 객체
        ResultSet resultSet = null;

        try {
            connection = connectDB.connectDB();
            statement = connection.prepareStatement(query);
            resultSet = statement.executeQuery();

            // table에 저장된 데이터 확인
            while (resultSet.next()) {
                // 하나의 레코드를 User 객체에 저장
                User user = new User(resultSet.getString("id"), resultSet.getString("pwd"), resultSet.getString("name"), resultSet.getInt("age"), resultSet.getString("gender"));
                // 저장된 객체를 리스트에 저장
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
     * update query를 수행하기 위한 update method
     *
     * @author 유영훈
     * @since 2021. 9. 8
     *
     * @param user : User class의 필드값을 받아옴
     * @return updateValue : 변동된 row(개수)에 대해 반환
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

            // id 입력
            System.out.println("Enter the id to update");
            user.setId(input.next());
            statement.setString(5, user.getId());

//            while (statement.executeUpdate() == 0) {
//                System.out.println("id를 다시 입력하시오");
//                user.setId(input.next());
//                statement.setString(5, user.getId());
//            }
            
            // id check
            

            System.out.println("update user data");

            // update할 값들 입력 및 type check, id 입력값이 없다면 종료
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
                    // type check를 위한 exception
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
     * delete query를 수행하기 위한 delete method
     *
     * @author 유영훈
     * @since 2021. 9. 8
     *
     * @param user : User class의 필드값을 받아옴
     * @return deleteValue : 변동된 row(개수)에 대해 반환
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

            // 삭제할 row의 id 입력
            System.out.println("Delete to input id : ");
            user.setId(input.nextLine());

            // id 유무 체크
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
