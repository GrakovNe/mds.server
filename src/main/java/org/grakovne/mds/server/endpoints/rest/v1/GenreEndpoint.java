package org.grakovne.mds.server.endpoints.rest.v1;

import org.grakovne.mds.server.endpoints.rest.v1.support.ApiResponse;
import org.grakovne.mds.server.entity.Genre;
import org.grakovne.mds.server.services.GenreService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Spring genre endpoint.
 */

@RestController
@RequestMapping("/api/v1/genre")
public class GenreEndpoint {

    private final Logger logger = LoggerFactory.getLogger(GenreEndpoint.class);

    @Autowired
    private GenreService genreService;

    /**
     * Finds genres without filters.
     *
     * @param pageNumber page number
     * @return page with genres
     */

    @RequestMapping(value = "", method = RequestMethod.GET)
    public ApiResponse<Page<Genre>> findGenres(@RequestParam(required = false, defaultValue = "0") Integer pageNumber) {
        Page<Genre> genres = genreService.findGenres(pageNumber);
        return new ApiResponse<>(genres);
    }

    /**
     * Finds genre by it's id.
     *
     * @param id genre id
     * @return genre entity
     */
    @RequestMapping(value = "{id}", method = RequestMethod.GET)
    public ApiResponse<Genre> findGenre(Integer id) {
        Genre genre = genreService.findGenreById(id);
        return new ApiResponse<>(genre);
    }

    /**
     * Saves new genre in db.
     *
     * @param genre genre dto.
     * @return genre entity
     */

    @RequestMapping(value = "", method = RequestMethod.POST)
    public ApiResponse<Genre> createGenre(@RequestBody Genre genre) {
        Genre savedGenre = genreService.createGenre(genre);
        return new ApiResponse<>(savedGenre);
    }

    /**
     * Deletes genre by it's id.
     *
     * @param integer genre id
     * @return status message
     */

    @RequestMapping(value = "{id}", method = RequestMethod.DELETE)
    public ApiResponse deleteGenre(@PathVariable Integer integer) {
        genreService.deleteGenre(integer);
        return new ApiResponse("Genre has been deleted");
    }
}
