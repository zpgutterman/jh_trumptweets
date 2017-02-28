package com.zpg.trumptweet.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Tweetlog.
 */
@Entity
@Table(name = "tweetlog")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Tweetlog implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Size(min = 2, max = 600)
    @Column(name = "tweet", length = 600, nullable = false)
    private String tweet;

    @NotNull
    @Column(name = "tweet_date", nullable = false)
    private ZonedDateTime tweet_date;

    @NotNull
    @Size(min = 2, max = 50)
    @Column(name = "handle", length = 50, nullable = false)
    private String handle;

    @Column(name = "processed")
    private Boolean processed = false;

    @Column(name = "categorize_time")
    private ZonedDateTime categorize_time;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "tweetlog_category",
               joinColumns = @JoinColumn(name="tweetlogs_id", referencedColumnName="id"),
               inverseJoinColumns = @JoinColumn(name="categories_id", referencedColumnName="id"))
    private Set<Category> categories = new HashSet<>();

    @ManyToOne
    private User categorize_user;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTweet() {
        return tweet;
    }

    public Tweetlog tweet(String tweet) {
        this.tweet = tweet;
        return this;
    }

    public void setTweet(String tweet) {
        this.tweet = tweet;
    }

    public ZonedDateTime getTweet_date() {
        return tweet_date;
    }

    public Tweetlog tweet_date(ZonedDateTime tweet_date) {
        this.tweet_date = tweet_date;
        return this;
    }

    public void setTweet_date(ZonedDateTime tweet_date) {
        this.tweet_date = tweet_date;
    }

    public String getHandle() {
        return handle;
    }

    public Tweetlog handle(String handle) {
        this.handle = handle;
        return this;
    }

    public void setHandle(String handle) {
        this.handle = handle;
    }

    public Boolean isProcessed() {
        return processed;
    }

    public Tweetlog processed(Boolean processed) {
        this.processed = processed;
        return this;
    }

    public void setProcessed(Boolean processed) {
        this.processed = processed;
    }

    public ZonedDateTime getCategorize_time() {
        return categorize_time;
    }

    public Tweetlog categorize_time(ZonedDateTime categorize_time) {
        this.categorize_time = categorize_time;
        return this;
    }

    public void setCategorize_time(ZonedDateTime categorize_time) {
        this.categorize_time = categorize_time;
    }

    public Set<Category> getCategories() {
        return categories;
    }

    public Tweetlog categories(Set<Category> categories) {
        this.categories = categories;
        return this;
    }

    public Tweetlog addCategory(Category category) {
        this.categories.add(category);
        category.getTweetlogs().add(this);
        return this;
    }

    public Tweetlog removeCategory(Category category) {
        this.categories.remove(category);
        category.getTweetlogs().remove(this);
        return this;
    }

    public void setCategories(Set<Category> categories) {
        this.categories = categories;
    }

    public User getCategorize_user() {
        return categorize_user;
    }

    public Tweetlog categorize_user(User user) {
        this.categorize_user = user;
        return this;
    }

    public void setCategorize_user(User user) {
        this.categorize_user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Tweetlog tweetlog = (Tweetlog) o;
        if (tweetlog.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, tweetlog.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Tweetlog{" +
            "id=" + id +
            ", tweet='" + tweet + "'" +
            ", tweet_date='" + tweet_date + "'" +
            ", handle='" + handle + "'" +
            ", processed='" + processed + "'" +
            ", categorize_time='" + categorize_time + "'" +
            '}';
    }
}
