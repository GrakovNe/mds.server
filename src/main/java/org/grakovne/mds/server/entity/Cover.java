package org.grakovne.mds.server.entity;

import javax.persistence.*;

@Entity
public class Cover {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Access(AccessType.PROPERTY)
    private Integer id;

    @OneToOne
    @JoinColumn(name = "storyId")
    private Story story;

    private String base64EncodedImage;

    public Cover() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getBase64EncodedImage() {
        return base64EncodedImage;
    }

    public void setBase64EncodedImage(String base64EncodedImage) {
        this.base64EncodedImage = base64EncodedImage;
    }

    public Story getStory() {
        return story;
    }

    public void setStory(Story story) {
        this.story = story;
    }

    @Override
    public String toString() {
        return "Cover{" +
                "id=" + id +
                ", story=" + story +
                ", base64EncodedImage='" + base64EncodedImage + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Cover cover = (Cover) o;

        return base64EncodedImage != null ? base64EncodedImage.equals(cover.base64EncodedImage) : cover.base64EncodedImage == null;
    }

    @Override
    public int hashCode() {
        return base64EncodedImage != null ? base64EncodedImage.hashCode() : 0;
    }
}
