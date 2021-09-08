package noticeBoardCrud;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CommentCrud {
    // �����ͺ��̽��� ���� ���� �Է��ϴ� Scanner
    public Scanner input = new Scanner(System.in);

    /**
     * insert query�� �����ϱ� ���� create method
     *
     * @author ������
     * @since 2021. 9. 8
     *
     * @param comment : Comment class�� �ʵ尪�� �޾ƿ�
     * 
     * @return createValue : ������ row(����)�� ���� ��ȯ
     */
    public int create(Comment comment) {
        int createValue = 0;

        // db���� Ŭ���� ��ü
        ConnectDB connectDB = new ConnectDB();
        // user �ʵ尪�� �޾ƿ��� ���� ��ü - FK�� �����ϱ� ����
        UserCrud userCrud = new UserCrud();
        // �Խ��� �ʵ尪�� �޾ƿ��� ���� ��ü - FK�� �����ϱ� ����
        BoardCrud boardCrud = new BoardCrud();

        // FK ������ ���� user ����Ʈ
        List<User> userList = new ArrayList<>();
        userList = userCrud.read();
        // FK ������ ���� �Խ��� ����Ʈ
        List<Board> boardList = new ArrayList<>();
        boardList = boardCrud.read();
        // FK ������ ���� ��� ����Ʈ
        List<Comment> commentList = new ArrayList<>();
        commentList = read();

        Connection connection = null;
        PreparedStatement statement = null;

        // insert query
        String query = "insert into comment(id, content, board_index, user_id, comment_index) values(?, ?, ?, ?, ?)";

        try {
            // DriverManager�� ���� DB����. - Ŭ������ ���� �����Ͽ� ȣ��
            connection = connectDB.connectDB();
            statement = connection.prepareStatement(query);

            // table�� ���� ������ �Է�
            System.out.println("input comment data");

            // id �Է� - ���� �˻� �ʿ�
            System.out.println("comment ID : ");
            comment.setId(input.next());

            // ��� �����Է�
            System.out.println("comment content : ");
            comment.setContent(input.next());

            // �Է¹��� �� ����
            statement.setString(1, comment.getId());
            statement.setString(2, comment.getContent());

            // ����� �� �Խ����� ��ȣ�� �ִٸ� �ۼ� ����
            System.out.println("board_index : ");
            comment.setBoardIndex(input.nextInt());
            for (int index = 0; index < boardList.size(); index++) {
                // �Խ����� �ε����� ����� �Խ��� �ε����� ���ٸ� ��� �ۼ� ���� �����̹Ƿ� �ۼ�
                if (boardList.get(index).getBoardIndex() == comment.getBoardIndex()) {
                    statement.setInt(3, comment.getBoardIndex());
                    break;
                }
            }

            // user id�� ���� ��쿡�� �Խñ� �ۼ� ���� - ���� x
            System.out.println("user_id : ");
            comment.setUserId(input.next());
            for (int index = 0; index < userList.size(); index++) {
                if (userList.get(index).getId().equals(comment.getUserId())) {
                    statement.setString(4, comment.getUserId());
                    break;
                }
            }

            // ��� ��ȣ -> ���� ��� 0, ���� ��� 1�� ����
            System.out.println("comment_index : ");
            for (int index = 0; index < commentList.size(); index++) {
                if (commentList.get(index).getCommentIndex() == 0) {
                    comment.setCommentIndex(1);
                    statement.setInt(5, comment.getCommentIndex());
                } else if (commentList.get(index).getCommentIndex() > 0) {
                    comment.setCommentIndex(commentList.get(index).getCommentIndex() + 1);
                    statement.setInt(5, comment.getCommentIndex());
                }
            }

            // ������ row(����)�� ���� ��ȯ
            createValue = statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            System.exit(1);
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
     * @return commentList : table�� ����� ������ �б� ���� �����ϴ� ����Ʈ
     */
    public List<Comment> read() {
        // table�� ����� ������ �б� ���� �����ϴ� ����Ʈ
        List<Comment> commentList = new ArrayList<Comment>();

        // select query
        String query = "select * from comment";

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
                Comment comment = new Comment(resultSet.getInt("board_index"), resultSet.getString("id"), resultSet.getString("user_id"), resultSet.getString("content"), resultSet.getBoolean("parent_comment"), resultSet.getInt("comment_index"),
                        resultSet.getInt("parent_index"), resultSet.getInt("order_index"), resultSet.getInt("indent"));
                // ����� ��ü�� ����Ʈ�� ����
                commentList.add(comment);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            System.exit(1);
        } finally {
            connectDB.close(resultSet, statement, connection);
        }

        return commentList;
    }

    /**
     * update query�� �����ϱ� ���� update method
     *
     * @author ������
     * @since 2021. 9. 8
     *
     * @param comment : Comment class�� �ʵ尪�� �޾ƿ�
     * @return updateValue : ������ row(����)�� ���� ��ȯ
     */
    public int update(Comment comment) {
        int updateValue = 0;

        ConnectDB connectDB = new ConnectDB();
        Connection connection = null;
        PreparedStatement statement = null;

        // update query
        String query = "update comment set content=? where comment_index=?";

        try {
            connection = connectDB.connectDB();
            statement = connection.prepareStatement(query);

            // id �Է�
            System.out.println("Enter the comment number to update");
            System.out.println("update content : ");
            comment.setCommentIndex(input.nextInt());
            statement.setInt(2, comment.getCommentIndex());

            System.out.println("update comment content data");

            // ��� ���� ����, ��� ��ȣ�� ���ٸ� ����� ���� -> ����
            if (comment.getCommentIndex() > 0) {
                System.out.println("update content : ");
                comment.setContent(input.next());
                updateValue = statement.executeUpdate();
            }

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
     * @param comment : Comment class�� �ʵ尪�� �޾ƿ�
     * @return deleteValue : ������ row(����)�� ���� ��ȯ
     */
    public int delete(Comment comment) {
        int deleteValue = 0;

        ConnectDB connectDB = new ConnectDB();
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        // delete query
        String deleteQuery = "delete from comment where comment_index=?";
        String selectQuery = "select * from comment";

        try {

            connection = connectDB.connectDB();
            statement = connection.prepareStatement(deleteQuery);
            resultSet = statement.executeQuery(selectQuery.toString());

            // ������ row�� id �Է�
            System.out.println("Delete to input comment number : ");
            comment.setCommentIndex(input.nextInt());

            // ���̵��� ��ȸ�� ���� setString���� �Է°� id�� �ش�
            statement.setInt(1, comment.getCommentIndex());

            // ����� ���� üũ - ����� �ִٸ� 0���� ŭ
            while (resultSet.next()) {
                if (resultSet.getInt("comment_index") == comment.getCommentIndex()) {
                    System.out.println("delete success");
                    deleteValue = statement.executeUpdate();

                    break;
                }
            }

            if (deleteValue == 0) {
                System.out.println("delete fail");
                delete(comment);
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
