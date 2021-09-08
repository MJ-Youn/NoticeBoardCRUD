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
    // 데이터베이스에 넣을 값을 입력하는 Scanner
    public Scanner input = new Scanner(System.in);

    /**
     * insert query를 수행하기 위한 create method
     *
     * @author 유영훈
     * @since 2021. 9. 8
     *
     * @param board : Board class의 필드값을 받아옴
     * 
     * @return createValue : 변동된 row(개수)에 대해 반환
     */
    public int create(Board board) {
        int createValue = 0;

        // db 연결 클래스 객체
        ConnectDB connectDB = new ConnectDB();
        // user 필드값을 받아오기 위한 객체 - FK(user_id)
        UserCrud userCrud = new UserCrud();
        // user 필드값을 가진 리스트
        List<User> userList = new ArrayList<>();
        userList = userCrud.read();
        // board 필드값을 가진 리스트
        List<Board> boardList = new ArrayList<>();
        boardList = read();

        Connection connection = null;
        PreparedStatement statement = null;

        // DB datetime type에 들어갈 현재시간, 수정시간을 위한 객체
        Time timestamp = new Time(System.currentTimeMillis());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String time = sdf.format(timestamp);

        // insert query
        String query = "insert into board(board_index, user_id, id, title, content, create_time, update_time) values(?, ?, ?, ?, ?, ?, ?)";

        try {
            // DriverManager를 통한 DB연결. - 클래스로 따로 생성하여 호출
            connection = connectDB.connectDB();
            statement = connection.prepareStatement(query);

            // table에 넣을 데이터 입력
            System.out.println("input board data");

            // 댓글 번호 -> 없을 경우 0, 있을 경우 1씩 증가
            for (int index = 0; index < boardList.size(); index++) {
                if (boardList.get(index).getBoardIndex() == 0) {
                    board.setBoardIndex(1);
                    statement.setInt(1, board.getBoardIndex());
                } else if (boardList.get(index).getBoardIndex() > 0) {
                    board.setBoardIndex(boardList.get(index).getBoardIndex() + 1);
                    statement.setInt(1, board.getBoardIndex());
                }
            }

            // user id가 있을 경우 게시글 작성 가능 - 구현 x
            System.out.println("user id");
            board.setUserId(input.next());
            for (int index = 0; index < userList.size(); index++) {
                if (userList.get(index).getId().equals(board.getUserId())) {
                    statement.setString(2, board.getUserId());
                    break;
                }
            }

            // 게시판 아이디 입력
            System.out.println("board id : ");
            board.setId(input.next());

            // 게시판 제목입력
            System.out.println("board title : ");
            board.setTitle(input.next());

            // 게시판 내용입력
            System.out.println("board content : ");
            board.setContent(input.next());

            // 게시글 생성시간 입력
            System.out.println("board create_time : ");
            board.setCreateTime(time);

            // 게시글 수정시간 내용입력
            System.out.println("board update_time : ");
            board.setUpdateTime(time);

            // 입력받은 값 설정
            statement.setString(3, board.getId());
            statement.setString(4, board.getTitle());
            statement.setString(5, board.getContent());
            statement.setString(6, board.getUpdateTime());
            statement.setString(7, board.getUpdateTime());

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
     * @return boardList : table에 저장된 값들을 읽기 위해 저장하는 리스트
     */
    public List<Board> read() {
        // table에 저장된 값들을 읽기 위해 저장하는 리스트
        List<Board> boardList = new ArrayList<>();

        // select query
        String query = "select * from board";

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
                // 하나의 레코드를 board 객체에 저장
                Board board = new Board(resultSet.getInt("board_index"), resultSet.getString("user_id"), resultSet.getString("id"), resultSet.getString("title"), resultSet.getString("content"), resultSet.getString("create_time"),
                        resultSet.getString("update_time"));
                // 저장된 객체를 리스트에 저장
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
     * update query를 수행하기 위한 update method
     *
     * @author 유영훈
     * @since 2021. 9. 8
     *
     * @param Board : Board class의 필드값을 받아옴
     * @return updateValue : 변동된 row(개수)에 대해 반환
     */
    public int update(Board board) {
        int updateValue = 0;

        ConnectDB connectDB = new ConnectDB();
        
        List<Board> boardList = new ArrayList<>();
        boardList = read();
        
        // DB datetime type에 들어갈 현재시간, 수정시간을 위한 객체
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

            // 수정할 게시판 번호 입력
            System.out.println("Enter the board number to update");
            board.setBoardIndex(input.nextInt());
            statement.setInt(3, board.getBoardIndex());

            System.out.println("update board content data");

            
            // 게시판 번호가 없다면 댓글이 없음 -> 종료
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
            
            // 수정시간 업데이트
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
     * delete query를 수행하기 위한 delete method
     *
     * @author 유영훈
     * @since 2021. 9. 8
     *
     * @param board : board class의 필드값을 받아옴
     * @return deleteValue : 변동된 row(개수)에 대해 반환
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

            // 삭제할 row의 id 입력
            System.out.println("Delete to input board number : ");
            board.setBoardIndex(input.nextInt());

            // 아이디의 조회를 위해 setString으로 입력값 id를 준다
            statement.setInt(1, board.getBoardIndex());

            // 댓글의 유무 체크 - 댓글이 있다면 0보다 큼
            while (resultSet.next()) {
                if (resultSet.getInt("board_index") == board.getBoardIndex()) {
                    System.out.println("delete success");
                    deleteValue = statement.executeUpdate();

                    break;
                }
            }
            // executeUpdate == 0 -> 바뀐 레코드가 없음
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
