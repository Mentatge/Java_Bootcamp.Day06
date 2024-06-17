package edu.school21.models;

import java.util.Objects;

public class User {
    private Long id;
    private String login;
    private String password;
    private Boolean authenticateStatus;

    public User() {
    }

    public User(Long id, String login, String password, Boolean authenticateStatus) {
        this.id = id;
        this.login = login;
        this.password = password;
        this.authenticateStatus = authenticateStatus;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean getAuthenticateStatus() {
        return authenticateStatus;
    }

    public void setAuthenticateStatus(Boolean authenticateStatus) {
        this.authenticateStatus = authenticateStatus;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id) && Objects.equals(login, user.login) && Objects.equals(password, user.password) && Objects.equals(authenticateStatus, user.authenticateStatus);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, login, password, authenticateStatus);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", authenticateStatus=" + authenticateStatus +
                '}';
    }
}
