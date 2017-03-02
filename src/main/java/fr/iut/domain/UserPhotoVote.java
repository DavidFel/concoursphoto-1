package fr.iut.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A UserPhotoVote.
 */
@Entity
@Table(name = "user_photo_vote")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class UserPhotoVote implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "stars", nullable = false)
    private Integer stars;

    @NotNull
    @Column(name = "date", nullable = false)
    private LocalDate date;

    @ManyToOne
    private SiteUser siteUser;

    @ManyToOne
    private Photo photo;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getStars() {
        return stars;
    }

    public UserPhotoVote stars(Integer stars) {
        this.stars = stars;
        return this;
    }

    public void setStars(Integer stars) {
        this.stars = stars;
    }

    public LocalDate getDate() {
        return date;
    }

    public UserPhotoVote date(LocalDate date) {
        this.date = date;
        return this;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public SiteUser getSiteUser() {
        return siteUser;
    }

    public UserPhotoVote siteUser(SiteUser siteUser) {
        this.siteUser = siteUser;
        return this;
    }

    public void setSiteUser(SiteUser siteUser) {
        this.siteUser = siteUser;
    }

    public Photo getPhoto() {
        return photo;
    }

    public UserPhotoVote photo(Photo photo) {
        this.photo = photo;
        return this;
    }

    public void setPhoto(Photo photo) {
        this.photo = photo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        UserPhotoVote userPhotoVote = (UserPhotoVote) o;
        if (userPhotoVote.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, userPhotoVote.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "UserPhotoVote{" +
            "id=" + id +
            ", stars='" + stars + "'" +
            ", date='" + date + "'" +
            '}';
    }
}
