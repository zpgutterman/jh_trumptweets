package com.zpg.trumptweet.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A User_balances.
 */
@Entity
@Table(name = "user_balances")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class User_balances implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "balance", precision=10, scale=2)
    private BigDecimal balance;

    @ManyToOne
    private User user;

    @ManyToOne
    private Category category;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "user_balances_user_tweet_log",
               joinColumns = @JoinColumn(name="user_balances_id", referencedColumnName="id"),
               inverseJoinColumns = @JoinColumn(name="user_tweet_logs_id", referencedColumnName="id"))
    private Set<User_tweet_log> user_tweet_logs = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public User_balances balance(BigDecimal balance) {
        this.balance = balance;
        return this;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public User getUser() {
        return user;
    }

    public User_balances user(User user) {
        this.user = user;
        return this;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Category getCategory() {
        return category;
    }

    public User_balances category(Category category) {
        this.category = category;
        return this;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Set<User_tweet_log> getUser_tweet_logs() {
        return user_tweet_logs;
    }

    public User_balances user_tweet_logs(Set<User_tweet_log> user_tweet_logs) {
        this.user_tweet_logs = user_tweet_logs;
        return this;
    }

    public User_balances addUser_tweet_log(User_tweet_log user_tweet_log) {
        this.user_tweet_logs.add(user_tweet_log);
        user_tweet_log.getUser_balances().add(this);
        return this;
    }

    public User_balances removeUser_tweet_log(User_tweet_log user_tweet_log) {
        this.user_tweet_logs.remove(user_tweet_log);
        user_tweet_log.getUser_balances().remove(this);
        return this;
    }

    public void setUser_tweet_logs(Set<User_tweet_log> user_tweet_logs) {
        this.user_tweet_logs = user_tweet_logs;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        User_balances user_balances = (User_balances) o;
        if (user_balances.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, user_balances.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "User_balances{" +
            "id=" + id +
            ", balance='" + balance + "'" +
            '}';
    }
}
