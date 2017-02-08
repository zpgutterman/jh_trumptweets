package com.zpg.trumptweet.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A Tweet_log.
 */
@Entity
@Table(name = "tweet_log")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Tweet_log implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Size(max = 600)
    @Column(name = "tweet", length = 600, nullable = false)
    private String tweet;

    @NotNull
    @Column(name = "tweet_date", nullable = false)
    private ZonedDateTime tweet_date;

    @NotNull
    @Size(min = 2)
    @Column(name = "handle", nullable = false)
    private String handle;

    @Column(name = "processed")
    private Boolean processed;

    @Column(name = "categorize_time")
    private ZonedDateTime categorize_time;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTweet() {
        return tweet;
    }

    public Tweet_log tweet(String tweet) {
        this.tweet = tweet;
        return this;
    }

    public void setTweet(String tweet) {
        this.tweet = tweet;
    }

    public ZonedDateTime getTweet_date() {
        return tweet_date;
    }

    public Tweet_log tweet_date(ZonedDateTime tweet_date) {
        this.tweet_date = tweet_date;
        return this;
    }

    public void setTweet_date(ZonedDateTime tweet_date) {
        this.tweet_date = tweet_date;
    }

    public String getHandle() {
        return handle;
    }

    public Tweet_log handle(String handle) {
        this.handle = handle;
        return this;
    }

    public void setHandle(String handle) {
        this.handle = handle;
    }

    public Boolean isProcessed() {
        return processed;
    }

    public Tweet_log processed(Boolean processed) {
        this.processed = processed;
        return this;
    }

    public void setProcessed(Boolean processed) {
        this.processed = processed;
    }

    public ZonedDateTime getCategorize_time() {
        return categorize_time;
    }

    public Tweet_log categorize_time(ZonedDateTime categorize_time) {
        this.categorize_time = categorize_time;
        return this;
    }

    public void setCategorize_time(ZonedDateTime categorize_time) {
        this.categorize_time = categorize_time;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Tweet_log tweet_log = (Tweet_log) o;
        if (tweet_log.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, tweet_log.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Tweet_log{" +
            "id=" + id +
            ", tweet='" + tweet + "'" +
            ", tweet_date='" + tweet_date + "'" +
            ", handle='" + handle + "'" +
            ", processed='" + processed + "'" +
            ", categorize_time='" + categorize_time + "'" +
            '}';
    }
}
