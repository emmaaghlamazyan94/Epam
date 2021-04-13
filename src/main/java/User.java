public class User {
    private String name;
    private String email;
    private String status;
    private String gender;

    public static User getRandomUser() {
        User user = new User();
        user.setName("Emma" + System.currentTimeMillis());
        user.setEmail("Emma" + System.currentTimeMillis() + "@gmail.com");
        user.setStatus("Active");
        user.setGender("Female");
        return user;
    }

    public String getName() {
        return name;
    }

    public User setName(String name) {
        this.name = name;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public User setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getStatus() {
        return status;
    }

    public User setStatus(String status) {
        this.status = status;
        return this;
    }

    public String getGender() {
        return gender;
    }

    public User setGender(String gender) {
        this.gender = gender;
        return this;
    }
}