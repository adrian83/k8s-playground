package com.github.adrian83.mordeczki.auth.model.entity;

import java.time.LocalDateTime;

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
    @JoinColumn(name = "account_id", referencedColumnName = "email")
    private Account account;

    @Column(name = "creation_time_utc")
    private LocalDateTime creationTimeUtc;

    public ResetPassword() {
	super();
    }

    public ResetPassword(String id, Account account, LocalDateTime creationTimeUtc) {
	super();
	this.id = id;
	this.account = account;
	this.creationTimeUtc = creationTimeUtc;
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

    public LocalDateTime getCreationTimeUtc() {
	return creationTimeUtc;
    }

    public void setCreationTimeUtc(LocalDateTime creationTimeUtc) {
	this.creationTimeUtc = creationTimeUtc;
    }

}
