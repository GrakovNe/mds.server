package org.grakovne.mds.server.importer;

import com.google.common.base.Strings;
import org.grakovne.mds.server.importer.dto.FantLabStoryDto;
import org.grakovne.mds.server.importer.dto.search.AudioMatches;
import org.grakovne.mds.server.importer.dto.search.AudioMetaData;
import org.grakovne.mds.server.importer.retrofit.ApiFactory;
import org.grakovne.mds.server.importer.retrofit.FantLabService;
import org.grakovne.mds.server.utils.AudioUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FantLabMetaImporter {

    public FantLabStoryDto importMetaFromAudio(File audioFile) {
        AudioUtils audioUtils = new AudioUtils();

        String searchQuery = prepareSearchQuery(audioUtils.getAudioFileTitle(audioFile), audioUtils.getAudioFileTitle(audioFile));
        try {
            AudioMatches matches = findStories(searchQuery);
            return getStoryMetaData(matches);
        } catch (IOException ex) {
            return importLocalMetaFromAudio(audioFile);
        }
    }

    public FantLabStoryDto importLocalMetaFromAudio(File audioFile) {
        AudioUtils audioUtils = new AudioUtils();

        List<FantLabStoryDto.FantLabStoryAuthor> authorsList = new ArrayList<>();
        authorsList.add(new FantLabStoryDto.FantLabStoryAuthor(audioUtils.getAudioFileArtist(audioFile)));
        FantLabStoryDto result = new FantLabStoryDto(audioUtils.getAudioFileTitle(audioFile), authorsList);

        if (Strings.isNullOrEmpty(result.getTitle()) || Strings.isNullOrEmpty(result.getAuthors().get(0).getName())) {
            throw new ImporterException("Can't import story");
        }

        return result;
    }

    private AudioMatches findStories(String searchPhrase) throws IOException {
        AudioMetaData response = ApiFactory.getRetrofit().create(FantLabService.class).findStories(searchPhrase).execute().body();
        List<AudioMatches> foundStories = response.getMatches();

        if (foundStories.isEmpty()) {
            throw new StoriesNotFoundException();
        }

        return foundStories.get(0);
    }

    private FantLabStoryDto getStoryMetaData(AudioMatches audioMatches) throws IOException {
        return ApiFactory.getRetrofit().create(FantLabService.class).findStoryMetaData(getStoryUrl(audioMatches)).execute().body();
    }

    private String prepareSearchQuery(String title, String artist) {
        return artist + " " + title;
    }

    private String getStoryUrl(AudioMatches audioMatches) {
        return "work" + audioMatches.getId() + ".json";
    }
}
