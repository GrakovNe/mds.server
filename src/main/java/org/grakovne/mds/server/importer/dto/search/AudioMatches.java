package org.grakovne.mds.server.importer.dto.search;

import com.google.gson.annotations.SerializedName;

public class AudioMatches {

    @SerializedName("doc")
    private Integer id;

    @SerializedName("autor1_rusname")
    private String authorName;

    @SerializedName("altname")
    private String storyName;

    public AudioMatches() {
    }

    public Integer getId() {
        return id;
    }

    public String getAuthorName() {
        return authorName;
    }

    public String getStoryName() {
        return storyName;
    }

    @Override
    public String toString() {
        return "AudioMatches{" +
            "id=" + id +
            ", authorName='" + authorName + '\'' +
            ", storyName='" + storyName + '\'' +
            '}';
    }
}
