package org.grakovne.mds.server.importer.fantlab.dto;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Fantlab story DTO.
 */

public class FantLabStoryDto {

    @SerializedName("work_name")
    private String title;

    @SerializedName("authors")
    private List<FantLabStoryAuthor> authors;

    @SerializedName("image")
    private String imageUrl;

    @SerializedName("val_midmark")
    private Double rating;

    @SerializedName("val_voters")
    private Integer ratingVoters;

    @SerializedName("work_description")
    private String annotation;

    @SerializedName("work_type")
    private String storyType;

    @SerializedName("work_year")
    private Integer year;

    public FantLabStoryDto(String title, List<FantLabStoryAuthor> authors) {
        this.title = title;
        this.authors = authors;
    }

    public String getTitle() {
        return title;
    }

    public List<FantLabStoryAuthor> getAuthors() {
        return authors;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public Double getRating() {
        return rating;
    }

    public Integer getRatingVoters() {
        return ratingVoters;
    }

    public String getAnnotation() {
        return annotation;
    }

    public String getStoryType() {
        return storyType;
    }

    public Integer getYear() {
        return year;
    }

    @Override
    public String toString() {
        return "FantLabStoryDto{" +
            "title='" + title + '\'' +
            ", authors=" + authors +
            ", imageUrl='" + imageUrl + '\'' +
            ", rating=" + rating +
            ", ratingVoters=" + ratingVoters +
            ", annotation='" + annotation + '\'' +
            ", storyType='" + storyType + '\'' +
            ", year='" + year + '\'' +
            '}';
    }

    /**
     * Fantlab story author DTO.
     */

    public static class FantLabStoryAuthor {
        private String name;

        public FantLabStoryAuthor(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        @Override
        public String toString() {
            return "FantLabStoryAuthor{" +
                "name='" + name + '\'' +
                '}';
        }
    }
}
