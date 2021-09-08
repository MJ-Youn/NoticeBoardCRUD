package noticeBoardCrud;

/**
 * user의 정보를 담은 클래스
 *
 * @author 유영훈
 * @since 2021. 9. 7
 *
 */
public class User {
    // User ID - PK
    private  String id;
    // User Password
    private String pwd;
    // User Name
    private String name;
    // User Age
    private int age;
    // User Gender
    private String gender;
    
    // 기본 생성자
    public User() {}
    
    /**
     * 사용자 - 생성자
     *
     * @author 유영훈
     * @since 2021. 9. 7
     *
     * @param id
     * @param pwd
     * @param name
     * @param age
     * @param gender
     */
    public User(String id, String pwd, String name, int age, String gender) {
        this.id = id;
        this.pwd = pwd;
        this.name = name;
        this.age = age;
        this.gender = gender;
    }
       
    // User ID setter/getter
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    
    // User Password setter/getter
    public String getPwd() {
        return pwd;
    }
    public void setPwd(String pwd) {
        this.pwd = pwd;
    }
    
    // User Name setter/getter
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    
    // User Age setter/getter
    public int getAge() {
        return age;
    }
    public void setAge(int age) {
        this.age = age;
    }
    
    // User Gender setter/getter
    public String getGender() {
        return gender;
    }
    public void setGender(String gender) {
        this.gender = gender;
    }

    // toString
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("User [id=");
        builder.append(id);
        builder.append(", pwd=");
        builder.append(pwd);
        builder.append(", name=");
        builder.append(name);
        builder.append(", age=");
        builder.append(age);
        builder.append(", gender=");
        builder.append(gender);
        builder.append("]");
        
        return builder.toString();
    }
}
