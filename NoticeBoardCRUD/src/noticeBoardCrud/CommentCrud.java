package noticeBoardCrud;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CommentCrud {
    // 데이터베이스에 넣을 값을 입력하는 Scanner
    public Scanner input = new Scanner(System.in);

    /**
     * insert query를 수행하기 위한 create method
     *
     * @author 유영훈
     * @since 2021. 9. 8
     *
     * @param comment : Comment class의 필드값을 받아옴
     * 
     * @return createValue : 변동된 row(개수)에 대해 반환
     */
    public int create(Comment comment) {
        int createValue = 0;

        // db연결 클래스 객체
        ConnectDB connectDB = new ConnectDB();
        // user 필드값을 받아오기 위한 객체 - FK를 설정하기 위해
        UserCrud userCrud = new UserCrud();
        // 게시판 필드값을 받아오기 위한 객체 - FK를 설정하기 위해
        BoardCrud boardCrud = new BoardCrud();

        // FK 설정을 위한 user 리스트
        List<User> userList = new ArrayList<>();
        userList = userCrud.read();
        // FK 설정을 위한 게시판 리스트
        List<Board> boardList = new ArrayList<>();
        boardList = boardCrud.read();
        // FK 설정을 위한 댓글 리스트
        List<Comment> commentList = new ArrayList<>();
        commentList = read();

        Connection connection = null;
        PreparedStatement statement = null;

        // insert query
        String query = "insert into comment(id, content, board_index, user_id, comment_index) values(?, ?, ?, ?, ?)";

        try {
            // DriverManager를 통한 DB연결. - 클래스로 따로 생성하여 호출
            connection = connectDB.connectDB();
            statement = connection.prepareStatement(query);

            // table에 넣을 데이터 입력
            System.out.println("input comment data");

            // id 입력 - 형식 검사 필요
            System.out.println("comment ID : ");
            comment.setId(input.next());

            // 댓글 내용입력
            System.out.println("comment content : ");
            comment.setContent(input.next());

            // 입력받은 값 설정
            statement.setString(1, comment.getId());
            statement.setString(2, comment.getContent());

            // 댓글을 달 게시판의 번호가 있다면 작성 가능
            System.out.println("board_index : ");
            comment.setBoardIndex(input.nextInt());
            for (int index = 0; index < boardList.size(); index++) {
                // 게시판의 인덱스와 댓글의 게시판 인덱스가 같다면 댓글 작성 가능 상태이므로 작성
                if (boardList.get(index).getBoardIndex() == comment.getBoardIndex()) {
                    statement.setInt(3, comment.getBoardIndex());
                    break;
                }
            }

            // user id가 있을 경우에만 게시글 작성 가능 - 구현 x
            System.out.println("user_id : ");
            comment.setUserId(input.next());
            for (int index = 0; index < userList.size(); index++) {
                if (userList.get(index).getId().equals(comment.getUserId())) {
                    statement.setString(4, comment.getUserId());
                    break;
                }
            }

            // 댓글 번호 -> 없을 경우 0, 있을 경우 1씩 증가
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

            // 변동된 row(개수)에 대해 반환
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
     * select query를 수행하기 위한 read method
     *
     * @author 유영훈
     * @since 2021. 9. 8
     *
     * @return commentList : table에 저장된 값들을 읽기 위해 저장하는 리스트
     */
    public List<Comment> read() {
        // table에 저장된 값들을 읽기 위해 저장하는 리스트
        List<Comment> commentList = new ArrayList<Comment>();

        // select query
        String query = "select * from comment";

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
                Comment comment = new Comment(resultSet.getInt("board_index"), resultSet.getString("id"), resultSet.getString("user_id"), resultSet.getString("content"), resultSet.getBoolean("parent_comment"), resultSet.getInt("comment_index"),
                        resultSet.getInt("parent_index"), resultSet.getInt("order_index"), resultSet.getInt("indent"));
                // 저장된 객체를 리스트에 저장
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
     * update query를 수행하기 위한 update method
     *
     * @author 유영훈
     * @since 2021. 9. 8
     *
     * @param comment : Comment class의 필드값을 받아옴
     * @return updateValue : 변동된 row(개수)에 대해 반환
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

            // id 입력
            System.out.println("Enter the comment number to update");
            System.out.println("update content : ");
            comment.setCommentIndex(input.nextInt());
            statement.setInt(2, comment.getCommentIndex());

            System.out.println("update comment content data");

            // 댓글 내용 수정, 댓글 번호가 없다면 댓글이 없음 -> 종료
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
     * delete query를 수행하기 위한 delete method
     *
     * @author 유영훈
     * @since 2021. 9. 8
     *
     * @param comment : Comment class의 필드값을 받아옴
     * @return deleteValue : 변동된 row(개수)에 대해 반환
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

            // 삭제할 row의 id 입력
            System.out.println("Delete to input comment number : ");
            comment.setCommentIndex(input.nextInt());

            // 아이디의 조회를 위해 setString으로 입력값 id를 준다
            statement.setInt(1, comment.getCommentIndex());

            // 댓글의 유무 체크 - 댓글이 있다면 0보다 큼
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
