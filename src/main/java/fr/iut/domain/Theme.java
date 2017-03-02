package fr.iut.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Theme.
 */
@Entity
@Table(name = "theme")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Theme implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "theme_concours",
               joinColumns = @JoinColumn(name="themes_id", referencedColumnName="id"),
               inverseJoinColumns = @JoinColumn(name="concours_id", referencedColumnName="id"))
    private Set<Concours> concours = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Theme name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Concours> getConcours() {
        return concours;
    }

    public Theme concours(Set<Concours> concours) {
        this.concours = concours;
        return this;
    }

    public Theme addConcours(Concours concours) {
        this.concours.add(concours);
        concours.getThemes().add(this);
        return this;
    }

    public Theme removeConcours(Concours concours) {
        this.concours.remove(concours);
        concours.getThemes().remove(this);
        return this;
    }

    public void setConcours(Set<Concours> concours) {
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
        Theme theme = (Theme) o;
        if (theme.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, theme.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Theme{" +
            "id=" + id +
            ", name='" + name + "'" +
            '}';
    }
}
