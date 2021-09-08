package noticeBoardCrud;

/**
 * 게시판, 사용자, 댓글 테이블 데이터의 CRUD를 실행하기 위한 Main class
 *
 * @author 유영훈
 * @since 2021. 9. 8
 *
 */
public class Main {

    /**
     * 게시판, 사용자, 댓글 테이블 데이터의 CRUD를 실행하기 위한 main method
     *
     * @author 유영훈
     * @since 2021. 9. 8
     *
     * @param args
     */
    public static void main(String[] args) {

        // driver load
        driverLoad();
        
        User user = new User();
        UserCrud userCrud = new UserCrud();
        
        Board board = new Board();
        BoardCrud boardCrud = new BoardCrud();
        
        Comment comment = new Comment();
        CommentCrud commentCrud = new CommentCrud();

//        userCrud.create(user);
//        userCrud.read();
//        userCrud.update(user);
//        userCrud.delete(user);
//
//        boardCrud.create(board);
//        System.out.println(boardCrud.read().toString());
//        boardCrud.update(board);
//        boardCrud.delete(board);
//        
//        commentCrud.create(comment);
//        System.out.println(commentCrud.read());
//        commentCrud.update(comment);
//        commentCrud.delete(comment);
    }

    /**
     * JDBC 드라이버를 로드하기 위한 메소드
     *
     * @author 유영훈
     * @since 2021. 9. 5
     *
     */
    public static void driverLoad() {
        
        // 드라이버를 로드하기 위한 드라이버 클래스 경로
        final String jdbcDriver = "org.mariadb.jdbc.Driver";
        
        // JDBC 드라이버 로드
        try {
            Class.forName(jdbcDriver);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }
}
