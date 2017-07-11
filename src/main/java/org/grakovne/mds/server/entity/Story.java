package org.grakovne.mds.server.entity;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

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
    @OnDelete(action = OnDeleteAction.CASCADE)
    private ReaderAge readerAge;

    @OneToOne(mappedBy = "story")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Cover cover;

    @OneToMany(mappedBy = "story")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private List<Author> authors;

    @OneToMany(mappedBy = "story")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private List<Tag> tags;

    @OneToMany(mappedBy = "story")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private List<Genre> genres;

    @OneToMany(mappedBy = "story")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private List<ActionPlaceType> actionPlaceTypes;

    @OneToMany(mappedBy = "story")
    @OnDelete(action = OnDeleteAction.CASCADE)
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Story story = (Story) o;

        if (title != null ? !title.equals(story.title) : story.title != null) return false;
        if (year != null ? !year.equals(story.year) : story.year != null) return false;
        if (annotation != null ? !annotation.equals(story.annotation) : story.annotation != null) return false;
        if (readerAge != null ? !readerAge.equals(story.readerAge) : story.readerAge != null) return false;
        if (cover != null ? !cover.equals(story.cover) : story.cover != null) return false;
        if (authors != null ? !authors.equals(story.authors) : story.authors != null) return false;
        if (tags != null ? !tags.equals(story.tags) : story.tags != null) return false;
        if (genres != null ? !genres.equals(story.genres) : story.genres != null) return false;
        if (actionPlaceTypes != null ? !actionPlaceTypes.equals(story.actionPlaceTypes) : story.actionPlaceTypes != null)
            return false;
        return actionTimeTypes != null ? actionTimeTypes.equals(story.actionTimeTypes) : story.actionTimeTypes == null;
    }

    @Override
    public int hashCode() {
        int result = title != null ? title.hashCode() : 0;
        result = 31 * result + (year != null ? year.hashCode() : 0);
        result = 31 * result + (annotation != null ? annotation.hashCode() : 0);
        result = 31 * result + (readerAge != null ? readerAge.hashCode() : 0);
        result = 31 * result + (cover != null ? cover.hashCode() : 0);
        result = 31 * result + (authors != null ? authors.hashCode() : 0);
        result = 31 * result + (tags != null ? tags.hashCode() : 0);
        result = 31 * result + (genres != null ? genres.hashCode() : 0);
        result = 31 * result + (actionPlaceTypes != null ? actionPlaceTypes.hashCode() : 0);
        result = 31 * result + (actionTimeTypes != null ? actionTimeTypes.hashCode() : 0);
        return result;
    }
}


