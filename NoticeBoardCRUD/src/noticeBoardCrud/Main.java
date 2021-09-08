package noticeBoardCrud;

import java.util.Scanner;

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

        try (
                // switch������ ��ȣ�� �Է¹ޱ� ���� Scanner�� ����
                Scanner input = new Scanner(System.in)
                ) {
            String selectNum = "";

            // 1-CREATE, 2-READ, 3-UPDATE, 4-DELETE, ENTER-����
            do {
                System.out.println("Select Num : 1=USER, 2=BOARD, 3=COMMENT, Exit=Enter");
                selectNum = input.nextLine();

                // JDBC Driver load
                driverLoad();

                switch (selectNum) {
                case "1":
                    System.out.println("Select Num : 1=CREATE, 2=READ, 3=UPDATE, 4=DELETE, Exit=Enter");
                    selectNum = input.nextLine();
                    switch (selectNum) {
                    case "1":
                        userCrud.create(user);
                        break;
                    case "2":
                        userCrud.read();
                        break;
                    case "3":
                        userCrud.update(user);
                        break;
                    case "4":
                        userCrud.delete(user);
                        break;
                    }
                    break;
                case "2":
                    System.out.println("Select Num : 1=CREATE, 2=READ, 3=UPDATE, 4=DELETE, Exit=Enter");
                    selectNum = input.nextLine();
                    switch (selectNum) {
                    case "1":
                        boardCrud.create(board);
                        break;
                    case "2":
                        boardCrud.read();
                        break;
                    case "3":
                        boardCrud.update(board);
                        break;
                    case "4":
                        boardCrud.delete(board);
                        break;
                    }
                    break;
                case "3":
                    System.out.println("Select Num : 1=CREATE, 2=READ, 3=UPDATE, 4=DELETE, Exit=Enter");
                    selectNum = input.nextLine();
                    switch (selectNum) {
                    case "1":
                        commentCrud.create(comment);
                        break;
                    case "2":
                        commentCrud.read();
                        break;
                    case "3":
                        commentCrud.update(comment);
                        break;
                    case "4":
                        commentCrud.delete(comment);
                        break;
                    }
                    break;
                }
            } while (!selectNum.equals(""));
        }
        System.out.println("System Exit");
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
