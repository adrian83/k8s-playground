package com.github.adrian83.mordeczki.auth.model.entity;

import java.time.ZonedDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "AUTH_REFRESH_TOKEN")
public class RefreshToken {

    @Id
    @Column(name = "id", unique = true)
    private String id;

    @Column(name = "email", unique = true)
    private String email;

    @Column(name = "expiration_time")
    private ZonedDateTime expirationTime;

    public RefreshToken() {
        super();
    }

    public RefreshToken(String id, String email, ZonedDateTime expirationTime) {
        super();
        this.id = id;
        this.email = email;
        this.expirationTime = expirationTime;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public ZonedDateTime getExpirationTime() {
        return expirationTime;
    }

    public void setExpirationTime(ZonedDateTime expirationTime) {
        this.expirationTime = expirationTime;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

}
