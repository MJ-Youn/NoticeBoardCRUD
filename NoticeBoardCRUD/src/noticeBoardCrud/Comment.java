package noticeBoardCrud;

/**
 * ��� ������ ���� Ŭ����
 *
 * @author ������
 * @since 2021. 9. 7
 *
 */
public class Comment {
    // �Խñ� ��ȣ FK - foreign key (board_index) references board (board_index)
    private int boardIndex;
    // ��� ���̵� FK - foreign key (user_id) references user (id)
    private String id;
    // ���� ���̵� - User PK
    private String userId;
    // ��� ����
    private String content;
    // ���� �޸���� 1, ������ 0
    private boolean parentComment;
    // ��� ��ȣ - PK
    private int commentIndex;
    // ���� ��ȣ FK - foreign key (parent_index) references comment (comment_index)
    private int parentIndex;
    // ���� ����
    private int orderIndex;
    // ���� �鿩����
    private int indent;
    
    // �⺻ ������
    public Comment() {}
    
    /**
     * ��� - ������
     *
     * @author ������
     * @since 2021. 9. 8
     *
     * @param boardIndex
     * @param id
     * @param userId
     * @param content
     * @param parentComment
     * @param commentIndex
     * @param orderIndex
     * @param indent
     */
    public Comment(int boardIndex, String id, String userId, String content, boolean parentComment, int commentIndex, int ParentIndex, int orderIndex, int indent) {
        this.boardIndex = boardIndex;
        this.id = id;
        this.userId = userId;
        this.content = content;
        this.parentComment = parentComment;
        this.commentIndex = commentIndex;
        this.parentIndex = ParentIndex;
        this.orderIndex = orderIndex;
        this.indent = indent;
    }
    
    
    // Board index setter/getter
    public int getBoardIndex() {
        return boardIndex;
    }
    public void setBoardIndex(int boardIndex) {
        this.boardIndex = boardIndex;
    }
    
    // Comment ID setter/getter
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    
    // User ID setter/getter
    public String getUserId() {
        return userId;
    }
    public void setUserId(String userId) {
        this.userId = userId;
    }
    
    // Comment Content setter/getter
    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content;
    }
    
    // Comment Boolean Parent Comment State setter/getter - ��� ������ 1, �ƴϸ� 0
    public boolean isParentComment() {
        return parentComment;
    }
    public void setParentComment(boolean parentComment) {
        this.parentComment = parentComment;
    }
    
    // Comment index setter/getter - ��۹�ȣ
    public int getCommentIndex() {
        return commentIndex;
    }
    public void setCommentIndex(int commentIndex) {
        this.commentIndex = commentIndex;
    }
    
    // Comment Parent Index setter/getter
    public int getParentIndex() {
        return parentIndex;
    }
    public void setParentIndex(int parentIndex) {
        this.parentIndex = parentIndex;
    }
    
    // Comment Order setter/getter
    public int getOrderIndex() {
        return orderIndex;
    }
    public void setOrderIndex(int orderIndex) {
        this.orderIndex = orderIndex;
    }
    
    // Comment Indent setter/getter
    public int getIndent() {
        return indent;
    }
    public void setIndent(int indent) {
        this.indent = indent;
    }

    /**
     * toString
     */
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Comment [boardIndex=");
        builder.append(boardIndex);
        builder.append(", id=");
        builder.append(id);
        builder.append(", userId=");
        builder.append(userId);
        builder.append(", content=");
        builder.append(content);
        builder.append(", parentComment=");
        builder.append(parentComment);
        builder.append(", commentIndex=");
        builder.append(commentIndex);
        builder.append(", parentIndex=");
        builder.append(parentIndex);
        builder.append(", orderIndex=");
        builder.append(orderIndex);
        builder.append(", indent=");
        builder.append(indent);
        builder.append("]\n");
        return builder.toString();
    }
}