package noticeBoardCrud;

/**
 * 게시판 정보를 담은 클래스
 *
 * @author 유영훈
 * @since 2021. 9. 7
 *
 */
public class Board {
    // 게시글 목록 index PK
    private int boardIndex;
    // User의 아이디 = User PK
    private String userId;
    // 게시판 아이디 FK - foreign key (user_id) references user (id);
    private String id;
    // 게시판 제목
    private String title;
    // 게시판 내용
    private String content;
    // 게시글 작성 시간
    private String createTime;
    // 게시글 수정 시간
    private String updateTime;
    
    // 기본 생성자
    public Board() {}
    
    /**
     * 게시판 - 생성자
     *
     * @author 유영훈
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
