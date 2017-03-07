package com.zpg.trumptweet.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A User_payment.
 */
@Entity
@Table(name = "user_payment")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class User_payment implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "token", nullable = false)
    private String token;

    @Column(name = "name")
    private String name;

    @NotNull
    @Column(name = "method", nullable = false)
    private String method;

    @Column(name = "last_four")
    private String lastFour;

    @ManyToOne(optional = false)
    @NotNull
    private User user;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public User_payment token(String token) {
        this.token = token;
        return this;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getName() {
        return name;
    }

    public User_payment name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMethod() {
        return method;
    }

    public User_payment method(String method) {
        this.method = method;
        return this;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getLastFour() {
        return lastFour;
    }

    public User_payment lastFour(String lastFour) {
        this.lastFour = lastFour;
        return this;
    }

    public void setLastFour(String lastFour) {
        this.lastFour = lastFour;
    }

    public User getUser() {
        return user;
    }

    public User_payment user(User user) {
        this.user = user;
        return this;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        User_payment user_payment = (User_payment) o;
        if (user_payment.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, user_payment.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "User_payment{" +
            "id=" + id +
            ", token='" + token + "'" +
            ", name='" + name + "'" +
            ", method='" + method + "'" +
            ", lastFour='" + lastFour + "'" +
            '}';
    }
}
