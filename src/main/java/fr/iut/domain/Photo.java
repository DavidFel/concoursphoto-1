package fr.iut.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Photo.
 */
@Entity
@Table(name = "photo")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Photo implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "title", nullable = false)
    private String title;

    @NotNull
    @Column(name = "date_create", nullable = false)
    private LocalDate dateCreate;

    @NotNull
    @Column(name = "uri", nullable = false)
    private String uri;

    @Lob
    @Column(name = "image")
    private byte[] image;

    @Column(name = "image_content_type")
    private String imageContentType;

    @Column(name = "description")
    private String description;

    @NotNull
    @Column(name = "size", nullable = false)
    private Integer size;

    @NotNull
    @Column(name = "format", nullable = false)
    private String format;

    @Column(name = "score")
    private Float score;

    @Column(name = "nb_vue")
    private Integer nbVue;

    @OneToMany(mappedBy = "photo")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<UserPhotoVote> userPhotoVotes = new HashSet<>();

    @OneToMany(mappedBy = "photo")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<UserPhotoComment> userPhotoComments = new HashSet<>();

    @ManyToOne
    private SiteUser siteUser;

    @ManyToOne
    private Concours concours;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public Photo title(String title) {
        this.title = title;
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public LocalDate getDateCreate() {
        return dateCreate;
    }

    public Photo dateCreate(LocalDate dateCreate) {
        this.dateCreate = dateCreate;
        return this;
    }

    public void setDateCreate(LocalDate dateCreate) {
        this.dateCreate = dateCreate;
    }

    public String getUri() {
        return uri;
    }

    public Photo uri(String uri) {
        this.uri = uri;
        return this;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public byte[] getImage() {
        return image;
    }

    public Photo image(byte[] image) {
        this.image = image;
        return this;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public String getImageContentType() {
        return imageContentType;
    }

    public Photo imageContentType(String imageContentType) {
        this.imageContentType = imageContentType;
        return this;
    }

    public void setImageContentType(String imageContentType) {
        this.imageContentType = imageContentType;
    }

    public String getDescription() {
        return description;
    }

    public Photo description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getSize() {
        return size;
    }

    public Photo size(Integer size) {
        this.size = size;
        return this;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public String getFormat() {
        return format;
    }

    public Photo format(String format) {
        this.format = format;
        return this;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public Float getScore() {
        return score;
    }

    public Photo score(Float score) {
        this.score = score;
        return this;
    }

    public void setScore(Float score) {
        this.score = score;
    }

    public Integer getNbVue() {
        return nbVue;
    }

    public Photo nbVue(Integer nbVue) {
        this.nbVue = nbVue;
        return this;
    }

    public void setNbVue(Integer nbVue) {
        this.nbVue = nbVue;
    }

    public Set<UserPhotoVote> getUserPhotoVotes() {
        return userPhotoVotes;
    }

    public Photo userPhotoVotes(Set<UserPhotoVote> userPhotoVotes) {
        this.userPhotoVotes = userPhotoVotes;
        return this;
    }

    public Photo addUserPhotoVote(UserPhotoVote userPhotoVote) {
        this.userPhotoVotes.add(userPhotoVote);
        userPhotoVote.setPhoto(this);
        return this;
    }

    public Photo removeUserPhotoVote(UserPhotoVote userPhotoVote) {
        this.userPhotoVotes.remove(userPhotoVote);
        userPhotoVote.setPhoto(null);
        return this;
    }

    public void setUserPhotoVotes(Set<UserPhotoVote> userPhotoVotes) {
        this.userPhotoVotes = userPhotoVotes;
    }

    public Set<UserPhotoComment> getUserPhotoComments() {
        return userPhotoComments;
    }

    public Photo userPhotoComments(Set<UserPhotoComment> userPhotoComments) {
        this.userPhotoComments = userPhotoComments;
        return this;
    }

    public Photo addUserPhotoComment(UserPhotoComment userPhotoComment) {
        this.userPhotoComments.add(userPhotoComment);
        userPhotoComment.setPhoto(this);
        return this;
    }

    public Photo removeUserPhotoComment(UserPhotoComment userPhotoComment) {
        this.userPhotoComments.remove(userPhotoComment);
        userPhotoComment.setPhoto(null);
        return this;
    }

    public void setUserPhotoComments(Set<UserPhotoComment> userPhotoComments) {
        this.userPhotoComments = userPhotoComments;
    }

    public SiteUser getSiteUser() {
        return siteUser;
    }

    public Photo siteUser(SiteUser siteUser) {
        this.siteUser = siteUser;
        return this;
    }

    public void setSiteUser(SiteUser siteUser) {
        this.siteUser = siteUser;
    }

    public Concours getConcours() {
        return concours;
    }

    public Photo concours(Concours concours) {
        this.concours = concours;
        return this;
    }

    public void setConcours(Concours concours) {
        this.concours = concours;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Photo photo = (Photo) o;
        if (photo.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, photo.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Photo{" +
            "id=" + id +
            ", title='" + title + "'" +
            ", dateCreate='" + dateCreate + "'" +
            ", uri='" + uri + "'" +
            ", image='" + image + "'" +
            ", imageContentType='" + imageContentType + "'" +
            ", description='" + description + "'" +
            ", size='" + size + "'" +
            ", format='" + format + "'" +
            ", score='" + score + "'" +
            ", nbVue='" + nbVue + "'" +
            '}';
    }
}
