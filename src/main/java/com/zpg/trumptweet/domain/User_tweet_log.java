package com.zpg.trumptweet.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A User_tweet_log.
 */
@Entity
@Table(name = "user_tweet_log")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class User_tweet_log implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "charge", precision=10, scale=2)
    private BigDecimal charge;

    @ManyToOne
    private User user;

    @ManyToOne
    private Tweetlog tweet;

    @ManyToMany(mappedBy = "user_tweet_logs")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<User_balances> user_balances = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getCharge() {
        return charge;
    }

    public User_tweet_log charge(BigDecimal charge) {
        this.charge = charge;
        return this;
    }

    public void setCharge(BigDecimal charge) {
        this.charge = charge;
    }

    public User getUser() {
        return user;
    }

    public User_tweet_log user(User user) {
        this.user = user;
        return this;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Tweetlog getTweet() {
        return tweet;
    }

    public User_tweet_log tweet(Tweetlog tweetlog) {
        this.tweet = tweetlog;
        return this;
    }

    public void setTweet(Tweetlog tweetlog) {
        this.tweet = tweetlog;
    }

    public Set<User_balances> getUser_balances() {
        return user_balances;
    }

    public User_tweet_log user_balances(Set<User_balances> user_balances) {
        this.user_balances = user_balances;
        return this;
    }

    public User_tweet_log addUser_balances(User_balances user_balances) {
        this.user_balances.add(user_balances);
        user_balances.getUser_tweet_logs().add(this);
        return this;
    }

    public User_tweet_log removeUser_balances(User_balances user_balances) {
        this.user_balances.remove(user_balances);
        user_balances.getUser_tweet_logs().remove(this);
        return this;
    }

    public void setUser_balances(Set<User_balances> user_balances) {
        this.user_balances = user_balances;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        User_tweet_log user_tweet_log = (User_tweet_log) o;
        if (user_tweet_log.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, user_tweet_log.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "User_tweet_log{" +
            "id=" + id +
            ", charge='" + charge + "'" +
            '}';
    }
}
