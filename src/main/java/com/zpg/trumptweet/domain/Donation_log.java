package com.zpg.trumptweet.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Donation_log.
 */
@Entity
@Table(name = "donation_log")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Donation_log implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @DecimalMin(value = "1")
    @Column(name = "amount", precision=10, scale=2, nullable = false)
    private BigDecimal amount;

    @Column(name = "processed")
    private Boolean processed;

    @Column(name = "processed_date")
    private ZonedDateTime processed_date;

    @ManyToOne(optional = false)
    @NotNull
    private User user;

    @ManyToOne(optional = false)
    @NotNull
    private Category category;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "donation_log_user_tweet_log",
               joinColumns = @JoinColumn(name="donation_logs_id", referencedColumnName="id"),
               inverseJoinColumns = @JoinColumn(name="user_tweet_logs_id", referencedColumnName="id"))
    private Set<User_tweet_log> user_tweet_logs = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public Donation_log amount(BigDecimal amount) {
        this.amount = amount;
        return this;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Boolean isProcessed() {
        return processed;
    }

    public Donation_log processed(Boolean processed) {
        this.processed = processed;
        return this;
    }

    public void setProcessed(Boolean processed) {
        this.processed = processed;
    }

    public ZonedDateTime getProcessed_date() {
        return processed_date;
    }

    public Donation_log processed_date(ZonedDateTime processed_date) {
        this.processed_date = processed_date;
        return this;
    }

    public void setProcessed_date(ZonedDateTime processed_date) {
        this.processed_date = processed_date;
    }

    public User getUser() {
        return user;
    }

    public Donation_log user(User user) {
        this.user = user;
        return this;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Category getCategory() {
        return category;
    }

    public Donation_log category(Category category) {
        this.category = category;
        return this;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Set<User_tweet_log> getUser_tweet_logs() {
        return user_tweet_logs;
    }

    public Donation_log user_tweet_logs(Set<User_tweet_log> user_tweet_logs) {
        this.user_tweet_logs = user_tweet_logs;
        return this;
    }

    public Donation_log addUser_tweet_log(User_tweet_log user_tweet_log) {
        this.user_tweet_logs.add(user_tweet_log);
        user_tweet_log.getDonation_logs().add(this);
        return this;
    }

    public Donation_log removeUser_tweet_log(User_tweet_log user_tweet_log) {
        this.user_tweet_logs.remove(user_tweet_log);
        user_tweet_log.getDonation_logs().remove(this);
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
        Donation_log donation_log = (Donation_log) o;
        if (donation_log.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, donation_log.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Donation_log{" +
            "id=" + id +
            ", amount='" + amount + "'" +
            ", processed='" + processed + "'" +
            ", processed_date='" + processed_date + "'" +
            '}';
    }
}
