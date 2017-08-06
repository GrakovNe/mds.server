package org.grakovne.mds.server.importers.fantlab.converters;

import org.apache.commons.io.FileUtils;
import org.apache.tomcat.util.codec.binary.Base64;
import org.grakovne.mds.server.entity.Author;
import org.grakovne.mds.server.entity.Cover;
import org.grakovne.mds.server.entity.Rating;
import org.grakovne.mds.server.entity.Story;
import org.grakovne.mds.server.entity.Tag;
import org.grakovne.mds.server.importers.fantlab.dto.FantLabStoryDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.HashSet;
import java.util.Set;

/**
 * Converter between Mds story entity and Fantlab story entity.
 */

@Service
public class FantLabStoryConverter {

    private static final Logger LOGGER = LoggerFactory.getLogger(FantLabStoryConverter.class);

    /**
     * Converts FantLabStoryDto to Story entity.
     *
     * @param dto FantLabStoryDto
     * @return Story object
     */

    public Story convertFromFantLabStory(FantLabStoryDto dto) {
        Story result = new Story();

        result.setTitle(dto.getTitle());
        result.setAnnotation(dto.getAnnotation());
        result.setYear(dto.getYear());

        setStoryAuthors(result, dto);
        setStoryRating(result, dto);
        setStoryType(result, dto);

        try {
            setStoryCover(result, dto);
        } catch (IOException ex) {
            LOGGER.warn("Can't import cover");
        }

        return result;
    }

    private void setStoryType(Story result, FantLabStoryDto dto) {
        Tag tag = new Tag();
        tag.setValue(dto.getStoryType());

        Set<Tag> tagsSet = new HashSet<>();
        tagsSet.add(tag);

        result.setTags(tagsSet);
    }

    private void setStoryRating(Story result, FantLabStoryDto dto) {
        Rating rating = new Rating();

        rating.setValue(dto.getRating());
        rating.setVoters(dto.getRatingVoters());
        rating.setStory(result);

        result.setRating(rating);
    }

    private void setStoryCover(Story result, FantLabStoryDto dto) throws IOException {
        File coverFile = File.createTempFile("story_image", ".cover");
        FileUtils.copyURLToFile(new URL("http:" + dto.getImageUrl()), coverFile);
        String base64EncodedFile = Base64.encodeBase64String(FileUtils.readFileToByteArray(coverFile));

        Cover cover = new Cover();
        cover.setStory(result);
        cover.setBase64EncodedCover(base64EncodedFile);

        result.setCover(cover);
    }

    private void setStoryAuthors(Story story, FantLabStoryDto storyDto) {
        Set<Author> authors = new HashSet<>(storyDto.getAuthors().size());

        storyDto.getAuthors().forEach(author -> authors.add(setStoryAuthor(author)));
        story.setAuthors(authors);
    }

    private Author setStoryAuthor(FantLabStoryDto.FantLabStoryAuthor dtoAuthor) {
        Author author = new Author();
        author.setName(dtoAuthor.getName());

        return author;
    }
}