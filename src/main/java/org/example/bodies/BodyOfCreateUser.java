package org.example.bodies;

public class BodyOfCreateUser {
    private String email;
    private String password;
    private String name;

    public BodyOfCreateUser(String email, String password, String username) {
        this.email = email;
        this.password = password;
        name = username;
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

    public String getUsername() {
        return name;
    }

    public void setUsername(String username) {
        name = username;
    }
}