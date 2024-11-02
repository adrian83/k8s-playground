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
@Table(name = "AUTH_RESET_PASSWORD")
public class ResetPassword {

    @Id
    @Column(name = "id")
    private String id;

    @OneToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "account_id", referencedColumnName = "id")
    private Account account;

    @Column(name = "creation_time")
    private ZonedDateTime creationTime;

    public ResetPassword() {
        super();
    }

    public ResetPassword(String id, Account account, ZonedDateTime creationTime) {
        super();
        this.id = id;
        this.account = account;
        this.creationTime = creationTime;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public ZonedDateTime getCreationTimeUtc() {
        return creationTime;
    }

    public void setCreationTimeUtc(ZonedDateTime creationTime) {
        this.creationTime = creationTime;
    }

}
