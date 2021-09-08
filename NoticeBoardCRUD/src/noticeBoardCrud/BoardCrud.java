package noticeBoardCrud;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class BoardCrud {
    // �����ͺ��̽��� ���� ���� �Է��ϴ� Scanner
    public Scanner input = new Scanner(System.in);

    /**
     * insert query�� �����ϱ� ���� create method
     *
     * @author ������
     * @since 2021. 9. 8
     *
     * @param board : Board class�� �ʵ尪�� �޾ƿ�
     * 
     * @return createValue : ������ row(����)�� ���� ��ȯ
     */
    public int create(Board board) {
        int createValue = 0;

        // db ���� Ŭ���� ��ü
        ConnectDB connectDB = new ConnectDB();
        // user �ʵ尪�� �޾ƿ��� ���� ��ü - FK(user_id)
        UserCrud userCrud = new UserCrud();
        // user �ʵ尪�� ���� ����Ʈ
        List<User> userList = new ArrayList<>();
        userList = userCrud.read();
        // board �ʵ尪�� ���� ����Ʈ
        List<Board> boardList = new ArrayList<>();
        boardList = read();

        Connection connection = null;
        PreparedStatement statement = null;

        // DB datetime type�� �� ����ð�, �����ð��� ���� ��ü
        Time timestamp = new Time(System.currentTimeMillis());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String time = sdf.format(timestamp);

        // insert query
        String query = "insert into board(board_index, user_id, id, title, content, create_time, update_time) values(?, ?, ?, ?, ?, ?, ?)";

        try {
            // DriverManager�� ���� DB����. - Ŭ������ ���� �����Ͽ� ȣ��
            connection = connectDB.connectDB();
            statement = connection.prepareStatement(query);

            // table�� ���� ������ �Է�
            System.out.println("input board data");

            // ��� ��ȣ -> ���� ��� 0, ���� ��� 1�� ����
            for (int index = 0; index < boardList.size(); index++) {
                if (boardList.get(index).getBoardIndex() == 0) {
                    board.setBoardIndex(1);
                    statement.setInt(1, board.getBoardIndex());
                } else if (boardList.get(index).getBoardIndex() > 0) {
                    board.setBoardIndex(boardList.get(index).getBoardIndex() + 1);
                    statement.setInt(1, board.getBoardIndex());
                }
            }

            // user id�� ���� ��� �Խñ� �ۼ� ���� - ���� x
            System.out.println("user id");
            board.setUserId(input.next());
            for (int index = 0; index < userList.size(); index++) {
                if (userList.get(index).getId().equals(board.getUserId())) {
                    statement.setString(2, board.getUserId());
                    break;
                }
            }

            // �Խ��� ���̵� �Է�
            System.out.println("board id : ");
            board.setId(input.next());

            // �Խ��� �����Է�
            System.out.println("board title : ");
            board.setTitle(input.next());

            // �Խ��� �����Է�
            System.out.println("board content : ");
            board.setContent(input.next());

            // �Խñ� �����ð� �Է�
            System.out.println("board create_time : ");
            board.setCreateTime(time);

            // �Խñ� �����ð� �����Է�
            System.out.println("board update_time : ");
            board.setUpdateTime(time);

            // �Է¹��� �� ����
            statement.setString(3, board.getId());
            statement.setString(4, board.getTitle());
            statement.setString(5, board.getContent());
            statement.setString(6, board.getUpdateTime());
            statement.setString(7, board.getUpdateTime());

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
     * @return boardList : table�� ����� ������ �б� ���� �����ϴ� ����Ʈ
     */
    public List<Board> read() {
        // table�� ����� ������ �б� ���� �����ϴ� ����Ʈ
        List<Board> boardList = new ArrayList<>();

        // select query
        String query = "select * from board";

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
                // �ϳ��� ���ڵ带 board ��ü�� ����
                Board board = new Board(resultSet.getInt("board_index"), resultSet.getString("user_id"), resultSet.getString("id"), resultSet.getString("title"), resultSet.getString("content"), resultSet.getString("create_time"),
                        resultSet.getString("update_time"));
                // ����� ��ü�� ����Ʈ�� ����
                boardList.add(board);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            System.exit(1);
        } finally {
            connectDB.close(resultSet, statement, connection);
        }

        return boardList;
    }

    /**
     * update query�� �����ϱ� ���� update method
     *
     * @author ������
     * @since 2021. 9. 8
     *
     * @param Board : Board class�� �ʵ尪�� �޾ƿ�
     * @return updateValue : ������ row(����)�� ���� ��ȯ
     */
    public int update(Board board) {
        int updateValue = 0;

        ConnectDB connectDB = new ConnectDB();
        
        List<Board> boardList = new ArrayList<>();
        boardList = read();
        
        // DB datetime type�� �� ����ð�, �����ð��� ���� ��ü
        Time timestamp = new Time(System.currentTimeMillis());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String time = sdf.format(timestamp);
        
        Connection connection = null;
        PreparedStatement statement = null;

        // update query
        String query = "update board set content=?, update_time=? where board_index=?";

        try {
            connection = connectDB.connectDB();
            statement = connection.prepareStatement(query);

            // ������ �Խ��� ��ȣ �Է�
            System.out.println("Enter the board number to update");
            board.setBoardIndex(input.nextInt());
            statement.setInt(3, board.getBoardIndex());

            System.out.println("update board content data");

            
            // �Խ��� ��ȣ�� ���ٸ� ����� ���� -> ����
            System.out.println("update content : ");
            board.setContent(input.next());
            int index = 0;
            while (boardList.get(index).getBoardIndex() != board.getBoardIndex()) {
                index++;
                System.out.println("update content : ");
                board.setContent(input.next());
                statement.setString(1, board.getContent());
            }
            statement.setString(1, board.getContent());
            
            // �����ð� ������Ʈ
            board.setUpdateTime(time);
            statement.setString(2, board.getUpdateTime());
            
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
     * @param board : board class�� �ʵ尪�� �޾ƿ�
     * @return deleteValue : ������ row(����)�� ���� ��ȯ
     */
    public int delete(Board board) {
        int deleteValue = 0;

        ConnectDB connectDB = new ConnectDB();
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        // delete query
        String deleteQuery = "delete from board where board_index=?";
        String selectQuery = "select * from board";

        try {

            connection = connectDB.connectDB();
            statement = connection.prepareStatement(deleteQuery);
            resultSet = statement.executeQuery(selectQuery.toString());

            // ������ row�� id �Է�
            System.out.println("Delete to input board number : ");
            board.setBoardIndex(input.nextInt());

            // ���̵��� ��ȸ�� ���� setString���� �Է°� id�� �ش�
            statement.setInt(1, board.getBoardIndex());

            // ����� ���� üũ - ����� �ִٸ� 0���� ŭ
            while (resultSet.next()) {
                if (resultSet.getInt("board_index") == board.getBoardIndex()) {
                    System.out.println("delete success");
                    deleteValue = statement.executeUpdate();

                    break;
                }
            }
            // executeUpdate == 0 -> �ٲ� ���ڵ尡 ����
            if (deleteValue == 0) {
                System.out.println("delete fail");
                delete(board);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            System.exit(1);
        } finally {
            connectDB.close(statement, connection);
        }

        return deleteValue;
    }
}
