package org.james.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class User {
    private int id;
    private String login;
    private boolean admin;
    private String firstname;
    private String lastname;
    private String mail;
    @JsonProperty("created_on")
    private
    String createdOn;
    @JsonProperty("last_login_on")
    private
    String lastLoginOn;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public boolean isAdmin() {
        return admin;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(String createdOn) {
        this.createdOn = createdOn;
    }

    public String getLastLoginOn() {
        return lastLoginOn;
    }

    public void setLastLoginOn(String lastLoginOn) {
        this.lastLoginOn = lastLoginOn;
    }
}

/*

{
        "users": [
        {
        "id": 38,
        "login": "tw009077",
        "admin": true,
        "firstname": "淵雄",
        "lastname": "宋",
        "mail": "james_song@usiglobal.com",
        "created_on": "2020-02-03T09:52:33Z",
        "last_login_on": "2020-09-24T09:52:18Z"
        }
        ],
        "total_count": 1,
        "offset": 0,
        "limit": 25
        }
        */
