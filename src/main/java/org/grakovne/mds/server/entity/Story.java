package org.grakovne.mds.server.entity;

import javax.persistence.*;
import java.util.List;

@Entity
public class Story {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Access(AccessType.PROPERTY)
    private Integer id;

    private String title;
    private Integer year;
    private String annotation;

    @OneToOne(mappedBy = "story")
    private ReaderAge readerAge;

    @OneToOne(mappedBy = "story")
    private Cover cover;

    @OneToMany(mappedBy = "story")
    private List<Author> authors;

    @OneToMany(mappedBy = "story")
    private List<Tag> tags;

    @OneToMany(mappedBy = "story")
    private List<Genre> genres;

    @OneToMany(mappedBy = "story")
    private List<ActionPlaceType> actionPlaceTypes;

    @OneToMany(mappedBy = "story")
    private List<ActionTimeType> actionTimeTypes;

    public Story() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public String getAnnotation() {
        return annotation;
    }

    public void setAnnotation(String annotation) {
        this.annotation = annotation;
    }

    public ReaderAge getReaderAge() {
        return readerAge;
    }

    public void setReaderAge(ReaderAge readerAge) {
        this.readerAge = readerAge;
    }

    public Cover getCover() {
        return cover;
    }

    public void setCover(Cover cover) {
        this.cover = cover;
    }

    public List<Author> getAuthors() {
        return authors;
    }

    public void setAuthors(List<Author> authors) {
        this.authors = authors;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }

    public List<Genre> getGenres() {
        return genres;
    }

    public void setGenres(List<Genre> genres) {
        this.genres = genres;
    }

    public List<ActionPlaceType> getActionPlaceTypes() {
        return actionPlaceTypes;
    }

    public void setActionPlaceTypes(List<ActionPlaceType> actionPlaceTypes) {
        this.actionPlaceTypes = actionPlaceTypes;
    }

    public List<ActionTimeType> getActionTimeTypes() {
        return actionTimeTypes;
    }

    public void setActionTimeTypes(List<ActionTimeType> actionTimeTypes) {
        this.actionTimeTypes = actionTimeTypes;
    }

    @Override
    public String toString() {
        return "Story{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", year=" + year +
                ", annotation='" + annotation + '\'' +
                ", readerAge=" + readerAge +
                ", cover=" + cover +
                ", authors=" + authors +
                ", tags=" + tags +
                ", genres=" + genres +
                ", actionPlaceTypes=" + actionPlaceTypes +
                ", actionTimeTypes=" + actionTimeTypes +
                '}';
    }
}


