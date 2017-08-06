package org.grakovne.mds.server.importers.fantlab;

import com.google.common.base.Strings;
import org.grakovne.mds.server.importers.ImporterException;
import org.grakovne.mds.server.importers.fantlab.api.ApiFactory;
import org.grakovne.mds.server.importers.fantlab.api.ApiService;
import org.grakovne.mds.server.importers.fantlab.dto.FantLabStoryDto;
import org.grakovne.mds.server.importers.fantlab.dto.search.AudioMatches;
import org.grakovne.mds.server.importers.fantlab.dto.search.SearchResult;
import org.grakovne.mds.server.utils.AudioUtils;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Service for import story metadata from fantlab public api.
 */

@Service
public class FantLabMetaImporter {

    /**
     * Returns FantLab DTO for audio file.
     *
     * @param audioFile File with ID3 tags
     * @return Fantlab DTO
     */

    public FantLabStoryDto importMetaFromAudio(File audioFile) {
        AudioUtils audioUtils = new AudioUtils();

        String searchQuery = prepareSearchQuery(
            audioUtils.getAudioFileTitle(audioFile),
            audioUtils.getAudioFileTitle(audioFile)
        );

        try {
            AudioMatches matches = findStories(searchQuery);
            return getStoryMetaData(matches);
        } catch (IOException ex) {
            return importLocalMetaFromAudio(audioFile);
        }
    }

    private FantLabStoryDto importLocalMetaFromAudio(File audioFile) {
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

        SearchResult response =
            ApiFactory
                .getRetrofit()
                .create(ApiService.class)
                .findStories(searchPhrase)
                .execute()
                .body();

        List<AudioMatches> foundStories = response.getMatches();

        if (foundStories.isEmpty()) {
            throw new ImporterException("Can't find story");
        }

        return foundStories.get(0);
    }

    private FantLabStoryDto getStoryMetaData(AudioMatches audioMatches) throws IOException {
        return ApiFactory
            .getRetrofit()
            .create(ApiService.class)
            .findStoryMetaData(getStoryUrl(audioMatches))
            .execute()
            .body();
    }

    private String prepareSearchQuery(String title, String artist) {
        return artist + " " + title;
    }

    private String getStoryUrl(AudioMatches audioMatches) {
        return "work" + audioMatches.getId() + ".json";
    }
}
