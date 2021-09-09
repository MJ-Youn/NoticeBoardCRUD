package noticeBoardCrud;

/**
 * �Խ��� ������ ���� Ŭ����
 *
 * @author ������
 * @since 2021. 9. 7
 *
 */
public class Board {
    // �Խñ� ��� index PK
    private int boardIndex;
    // User�� ���̵� = User PK
    private String userId;
    // �Խ��� ���̵� FK - foreign key (user_id) references user (id);
    private String id;
    // �Խ��� ����
    private String title;
    // �Խ��� ����
    private String content;
    // �Խñ� �ۼ� �ð�
    private String createTime;
    // �Խñ� ���� �ð�
    private String updateTime;
    
    // �⺻ ������
    public Board() {}
    
    /**
     * �Խ��� - ������
     *
     * @author ������
     * @since 2021. 9. 9
     *
     * @param boardIndex
     * @param userId
     * @param id
     * @param title
     * @param content
     * @param createTime
     * @param updateTime
     */
    public Board(int boardIndex, String userId, String id, String title, String content, String createTime, String updateTime) {
        this.boardIndex = boardIndex;
        this.userId = userId;
        this.id = id;
        this.title = title;
        this.content = content;
        this.createTime = createTime;
        this.updateTime = updateTime;
    }


    // Board index setter/getter
    public int getBoardIndex() {
        return boardIndex;
    }
    public void setBoardIndex(int boardIndex) {
        this.boardIndex = boardIndex;
    }
    
    // User ID setter/getter
    public String getUserId() {
        return userId;
    }
    public void setUserId(String userId) {
        this.userId = userId;
    }
    
    // Board ID setter/getter
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    
    // Board Title setter/getter
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    
    // Board Content setter/getter
    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content;
    }
    
    // Board Create_Time setter/getter
    public String getCreateTime() {
        return createTime;
    }
    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }
    
    // Board Update_Time setter/getter
    public String getUpdateTime() {
        return updateTime;
    }
    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }


    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Board [boardIndex=");
        builder.append(boardIndex);
        builder.append(", userId=");
        builder.append(userId);
        builder.append(", id=");
        builder.append(id);
        builder.append(", title=");
        builder.append(title);
        builder.append(", content=");
        builder.append(content);
        builder.append(", createTime=");
        builder.append(createTime);
        builder.append(", updateTime=");
        builder.append(updateTime);
        builder.append("]\n");
        return builder.toString();
    }
}
