package org.grakovne.mds.server.entity;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import java.util.List;
import java.util.Set;

/**
 * JPA Entity.
 */

@Entity
public class Story implements MdsEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Access(AccessType.PROPERTY)
    private Integer id;

    private String title;

    private Integer year;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
        name = "story_authors",
        joinColumns = @JoinColumn(
            name = "story_id",
            referencedColumnName = "id"
        ),
        inverseJoinColumns = @JoinColumn(
            name = "author_id",
            referencedColumnName = "id"
        ))
    private List<Author> authors;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
        name = "story_genres",
        joinColumns = @JoinColumn(
            name = "story_id",
            referencedColumnName = "id"
        ),
        inverseJoinColumns = @JoinColumn(
            name = "author_id",
            referencedColumnName = "id"
        ))
    private Set<Genre> genres;

    @OneToOne(cascade = CascadeType.ALL)
    private Rating rating;

    @OneToOne(cascade = CascadeType.ALL)
    private Cover cover;

    private String annotation;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
        name = "story_tags",
        joinColumns = @JoinColumn(
            name = "story_id",
            referencedColumnName = "id"
        ),
        inverseJoinColumns = @JoinColumn(
            name = "author_id",
            referencedColumnName = "id"
        ))
    private Set<Tag> tags;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
        name = "story_action_place_types",
        joinColumns = @JoinColumn(
            name = "story_id",
            referencedColumnName = "id"
        ),
        inverseJoinColumns = @JoinColumn(
            name = "author_id",
            referencedColumnName = "id"
        ))

    private Set<ActionPlaceType> actionPlaceTypes;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
        name = "story_action_time_type",
        joinColumns = @JoinColumn(
            name = "story_id",
            referencedColumnName = "id"
        ),
        inverseJoinColumns = @JoinColumn(
            name = "author_id",
            referencedColumnName = "id"
        ))
    private Set<ActionTimeType> actionTimeTypes;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinTable(
        name = "story_plot_type",
        joinColumns = @JoinColumn(
            name = "story_id",
            referencedColumnName = "id"
        ),
        inverseJoinColumns = @JoinColumn(
            name = "author_id",
            referencedColumnName = "id"
        ))
    private PlotType plotType;

    private String url;

    private Long fileSize;

    private Long fileQuality;

    private Integer length;

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

    public List<Author> getAuthors() {
        return authors;
    }

    public void setAuthors(List<Author> authors) {
        this.authors = authors;
    }

    public Set<Genre> getGenres() {
        return genres;
    }

    public void setGenres(Set<Genre> genres) {
        this.genres = genres;
    }

    public Rating getRating() {
        return rating;
    }

    public void setRating(Rating rating) {
        this.rating = rating;
    }

    public Cover getCover() {
        return cover;
    }

    public void setCover(Cover cover) {
        this.cover = cover;
    }

    public String getAnnotation() {
        return annotation;
    }

    public void setAnnotation(String annotation) {
        this.annotation = annotation;
    }

    public Set<Tag> getTags() {
        return tags;
    }

    public void setTags(Set<Tag> tags) {
        this.tags = tags;
    }

    public Set<ActionPlaceType> getActionPlaceTypes() {
        return actionPlaceTypes;
    }

    public void setActionPlaceTypes(Set<ActionPlaceType> actionPlaceTypes) {
        this.actionPlaceTypes = actionPlaceTypes;
    }

    public Set<ActionTimeType> getActionTimeTypes() {
        return actionTimeTypes;
    }

    public void setActionTimeTypes(Set<ActionTimeType> actionTimeTypes) {
        this.actionTimeTypes = actionTimeTypes;
    }

    public PlotType getPlotType() {
        return plotType;
    }

    public void setPlotType(PlotType plotType) {
        this.plotType = plotType;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Long getFileSize() {
        return fileSize;
    }

    public void setFileSize(Long fileSize) {
        this.fileSize = fileSize;
    }

    public Long getFileQuality() {
        return fileQuality;
    }

    public void setFileQuality(Long fileQuality) {
        this.fileQuality = fileQuality;
    }

    public Integer getLength() {
        return length;
    }

    public void setLength(Integer length) {
        this.length = length;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Story story = (Story) o;

        if (title != null ? !title.equals(story.title) : story.title != null) {
            return false;
        }
        if (year != null ? !year.equals(story.year) : story.year != null) {
            return false;
        }
        if (authors != null ? !authors.equals(story.authors) : story.authors != null) {
            return false;
        }
        if (genres != null ? !genres.equals(story.genres) : story.genres != null) {
            return false;
        }
        if (rating != null ? !rating.equals(story.rating) : story.rating != null) {
            return false;
        }
        if (cover != null ? !cover.equals(story.cover) : story.cover != null) {
            return false;
        }
        if (annotation != null ? !annotation.equals(story.annotation) : story.annotation != null) {
            return false;
        }
        if (tags != null ? !tags.equals(story.tags) : story.tags != null) {
            return false;
        }
        if (actionPlaceTypes != null
            ? !actionPlaceTypes.equals(story.actionPlaceTypes) : story.actionPlaceTypes != null) {
            return false;
        }
        if (actionTimeTypes != null ? !actionTimeTypes.equals(story.actionTimeTypes) : story.actionTimeTypes != null) {
            return false;
        }
        if (plotType != null ? !plotType.equals(story.plotType) : story.plotType != null) {
            return false;
        }
        if (url != null ? !url.equals(story.url) : story.url != null) {
            return false;
        }
        if (fileSize != null ? !fileSize.equals(story.fileSize) : story.fileSize != null) {
            return false;
        }
        if (fileQuality != null ? !fileQuality.equals(story.fileQuality) : story.fileQuality != null) {
            return false;
        }
        return length != null ? length.equals(story.length) : story.length == null;
    }

    @Override
    public int hashCode() {
        int result = title != null ? title.hashCode() : 0;
        result = 31 * result + (year != null ? year.hashCode() : 0);
        result = 31 * result + (authors != null ? authors.hashCode() : 0);
        result = 31 * result + (genres != null ? genres.hashCode() : 0);
        result = 31 * result + (rating != null ? rating.hashCode() : 0);
        result = 31 * result + (cover != null ? cover.hashCode() : 0);
        result = 31 * result + (annotation != null ? annotation.hashCode() : 0);
        result = 31 * result + (tags != null ? tags.hashCode() : 0);
        result = 31 * result + (actionPlaceTypes != null ? actionPlaceTypes.hashCode() : 0);
        result = 31 * result + (actionTimeTypes != null ? actionTimeTypes.hashCode() : 0);
        result = 31 * result + (plotType != null ? plotType.hashCode() : 0);
        result = 31 * result + (url != null ? url.hashCode() : 0);
        result = 31 * result + (fileSize != null ? fileSize.hashCode() : 0);
        result = 31 * result + (fileQuality != null ? fileQuality.hashCode() : 0);
        result = 31 * result + (length != null ? length.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Story{" +
            "id=" + id +
            ", title='" + title + '\'' +
            ", year=" + year +
            ", authors=" + authors +
            ", genres=" + genres +
            ", rating=" + rating +
            ", cover=" + cover +
            ", annotation='" + annotation + '\'' +
            ", tags=" + tags +
            ", actionPlaceTypes=" + actionPlaceTypes +
            ", actionTimeTypes=" + actionTimeTypes +
            ", plotType=" + plotType +
            ", url=" + url +
            ", fileSize=" + fileSize +
            ", fileQuality=" + fileQuality +
            ", length=" + length +
            '}';
    }
}
