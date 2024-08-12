package com.github.adrian83.mordeczki.auth.model.entity;

import java.time.ZonedDateTime;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "AUTH_ENABLE_ACCOUNT")
public class EnableAccount {

    @Id
    @Column(name = "token")
    private String token;

    @OneToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "account_id", referencedColumnName = "id")
    private Account account;

    @Column(name = "creation_time")
    private ZonedDateTime creationTime;

    public EnableAccount() {
        super();
    }

    public EnableAccount(String token, Account account, ZonedDateTime creationTime) {
        super();
        this.account = account;
        this.token = token;
        this.creationTime = creationTime;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public ZonedDateTime getCreationTime() {
        return creationTime;
    }

    public void setCreationTimeUtc(ZonedDateTime creationTime) {
        this.creationTime = creationTime;
    }

}
