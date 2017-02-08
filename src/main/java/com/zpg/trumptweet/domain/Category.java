package com.zpg.trumptweet.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Category.
 */
@Entity
@Table(name = "category")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Category implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Size(min = 3, max = 30)
    @Column(name = "name", length = 30, nullable = false)
    private String name;

    @OneToMany(mappedBy = "category")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Charity> charities = new HashSet<>();

    @ManyToMany(mappedBy = "categories")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Tweetlog> tweetlogs = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Category name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Charity> getCharities() {
        return charities;
    }

    public Category charities(Set<Charity> charities) {
        this.charities = charities;
        return this;
    }

    public Category addCharity(Charity charity) {
        this.charities.add(charity);
        charity.setCategory(this);
        return this;
    }

    public Category removeCharity(Charity charity) {
        this.charities.remove(charity);
        charity.setCategory(null);
        return this;
    }

    public void setCharities(Set<Charity> charities) {
        this.charities = charities;
    }

    public Set<Tweetlog> getTweetlogs() {
        return tweetlogs;
    }

    public Category tweetlogs(Set<Tweetlog> tweetlogs) {
        this.tweetlogs = tweetlogs;
        return this;
    }

    public Category addTweetlog(Tweetlog tweetlog) {
        this.tweetlogs.add(tweetlog);
        tweetlog.getCategories().add(this);
        return this;
    }

    public Category removeTweetlog(Tweetlog tweetlog) {
        this.tweetlogs.remove(tweetlog);
        tweetlog.getCategories().remove(this);
        return this;
    }

    public void setTweetlogs(Set<Tweetlog> tweetlogs) {
        this.tweetlogs = tweetlogs;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Category category = (Category) o;
        if (category.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, category.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Category{" +
            "id=" + id +
            ", name='" + name + "'" +
            '}';
    }
}
