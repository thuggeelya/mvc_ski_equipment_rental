package org.example.web.dto;

public class User {

    private int id;
    private String email; // login
    private String password;
    private Person person;
    private UserEquipment userEquipment;
    private Role role;

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
            this.setPerson(new Person());
        }
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
        this.person.setId(id);
    }

    public UserEquipment getUserEquipment() {
        if (userEquipment == null) {
            this.setUserEquipment(new UserEquipment());
        }
        return userEquipment;
    }

    public void setUserEquipment(UserEquipment userEquipment) {
        this.userEquipment = userEquipment;
        this.userEquipment.setId(id);
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", person=" + person +
                ", " + role +
                '}';
    }
}