public class User {
    private  Long id;
    private  String firstname;
    private  String secondname;
    private Integer age;
    private String address;
    private String phoneNumber;
    private String email;

    public User(Long id, String firstname,String secondname, Integer age, String address, String phoneNumber, String email) {
        this.id = id;
        this.firstname = firstname;
        this.secondname = secondname;
        this.age = age;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.email = email;
    }

    public String getFirstname() {
        return firstname;
    }

    public Long getId() {
        return id;
    }

    public String getSecondname() {
        return secondname;
    }

    public Integer getAge() {
        return age;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setSecondname(String lastname) {
        this.secondname = lastname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
