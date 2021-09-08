package noticeBoardCrud;

/**
 * �Խ���, �����, ��� ���̺� �������� CRUD�� �����ϱ� ���� Main class
 *
 * @author ������
 * @since 2021. 9. 8
 *
 */
public class Main {

    /**
     * �Խ���, �����, ��� ���̺� �������� CRUD�� �����ϱ� ���� main method
     *
     * @author ������
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
     * JDBC ����̹��� �ε��ϱ� ���� �޼ҵ�
     *
     * @author ������
     * @since 2021. 9. 5
     *
     */
    public static void driverLoad() {
        
        // ����̹��� �ε��ϱ� ���� ����̹� Ŭ���� ���
        final String jdbcDriver = "org.mariadb.jdbc.Driver";
        
        // JDBC ����̹� �ε�
        try {
            Class.forName(jdbcDriver);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }
}
