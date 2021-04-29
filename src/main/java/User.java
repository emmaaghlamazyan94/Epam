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

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
}