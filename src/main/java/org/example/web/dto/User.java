package org.example.web.dto;

public class User {

    private int id;
    private String email; // login
    private String password;
    private Person person;
    private UserEquipment userEquipment;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
        this.person.setId(id);
        this.userEquipment.setId(id);
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Person getPerson() {
        if (person == null) {
            person = new Person();
            person.setId(-1);
        }
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
        this.person.setId(id);
    }

    public UserEquipment getUserEquipment() {
        if (userEquipment == null) {
            userEquipment = new UserEquipment();
        }
        return userEquipment;
    }

    public void setUserEquipment(UserEquipment userEquipment) {
        this.userEquipment = userEquipment;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", person=" + person +
                '}';
    }
}