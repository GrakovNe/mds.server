package org.grakovne.mds.server.entity;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.time.LocalDateTime;

/**
 * JPA Entity.
 */

@Entity
public class StoryBookmark implements MdsEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Access(AccessType.PROPERTY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "userId")
    private User user;

    @ManyToOne
    @JoinColumn(name = "storyId")
    private Story story;

    private Integer timestamp;

    private LocalDateTime createDateTime;

    public StoryBookmark() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Story getStory() {
        return story;
    }

    public void setStory(Story story) {
        this.story = story;
    }

    public Integer getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Integer timestamp) {
        this.timestamp = timestamp;
    }

    public LocalDateTime getCreateDateTime() {
        return createDateTime;
    }

    public void setCreateDateTime(LocalDateTime createDateTime) {
        this.createDateTime = createDateTime;
    }

    @Override
    public String toString() {
        return "StoryBookmark{" +
            "id=" + id +
            ", user=" + user +
            ", story=" + story +
            ", timestamp=" + timestamp +
            '}';
    }
}
