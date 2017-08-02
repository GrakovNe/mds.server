package org.grakovne.mds.server.services;

import com.google.common.base.Strings;
import org.grakovne.mds.server.entity.Genre;
import org.grakovne.mds.server.repositories.GenreRepository;
import org.grakovne.mds.server.utils.CheckerUtils;
import org.grakovne.mds.server.utils.ConfigurationUtils;
import org.grakovne.mds.server.utils.ValidationUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

/**
 * Genre service.
 */

@Service
public class GenreService {

    @Autowired
    private GenreRepository genreRepository;

    @Autowired
    private ConfigurationUtils configurationUtils;

    /**
     * Saves new genre in DB.
     *
     * @param genre genre entity
     * @return saved genre
     */
    public Genre createGenre(Genre genre) {

        ValidationUtils.validate(genre);

        Genre foundGenre = genreRepository.findAllByValue(genre.getValue());
        CheckerUtils.checkNull(foundGenre);

        return genreRepository.save(genre);
    }

    /**
     * Finds genres.
     *
     * @param pageNumber page number
     * @return page with genres
     */

    public Page<Genre> findGenres(Integer pageNumber) {
        return genreRepository.findAll(new PageRequest(pageNumber, configurationUtils.getPageSize()));
    }

    /**
     * Finds genres by it's id.
     *
     * @param id genre id
     * @return genre entity
     */

    public Genre findGenreById(Integer id) {
        Genre genre = genreRepository.findOne(id);
        CheckerUtils.checkNotNull(genre);

        return genre;
    }

    /**
     * Finds genre by it's name.
     *
     * @param value genre name
     * @return genre entity
     */

    public Genre findGenreByValue(String value) {
        if (Strings.isNullOrEmpty(value)) {
            throw new IllegalArgumentException("genre's value can't be null");
        }

        Genre genre = genreRepository.findAllByValue(value);
        CheckerUtils.checkNotNull(genre);

        return genre;
    }

    /**
     * Deletes genre by it's id.
     *
     * @param id genre id
     */
    public void deleteGenre(Integer id) {
        Genre genre = findGenreById(id);
        genreRepository.delete(genre);
    }

    /**
     * Saves genres if it's not saved yet.
     *
     * @param genres genres set
     * @return genres set
     */

    public Set<Genre> persistGenreList(Set<Genre> genres) {

        if (null == genres) {
            return null;
        }

        Set<Genre> persistGenres = new HashSet<>();

        for (Genre genre : genres) {
            Genre savedGenre = genreRepository.findAllByValue(genre.getValue());

            if (null == savedGenre) {
                savedGenre = genreRepository.save(genre);
            }

            persistGenres.add(savedGenre);
        }

        return persistGenres;
    }
}
