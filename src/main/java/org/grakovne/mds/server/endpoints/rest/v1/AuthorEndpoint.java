package org.grakovne.mds.server.endpoints.rest.v1;

import org.grakovne.mds.server.endpoints.rest.v1.support.ApiResponse;
import org.grakovne.mds.server.entity.Author;
import org.grakovne.mds.server.services.AuthorSearchService;
import org.grakovne.mds.server.services.AuthorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * Spring author endpoint.
 */

@RestController
@RequestMapping("/api/v1/author")
public class AuthorEndpoint {
    private final Logger logger = LoggerFactory.getLogger(AuthorEndpoint.class);

    @Autowired
    private AuthorService authorService;

    @Autowired
    private AuthorSearchService authorSearchService;

    /**
     * Finds authors without filters.
     *
     * @param pageNumber page number
     * @return page with authors
     */

    @RequestMapping(value = "", method = RequestMethod.GET)
    public ApiResponse<Page<Author>> findAuthors(
        @RequestParam(required = false, defaultValue = "0") Integer pageNumber) {

        Page<Author> authors = authorService.findAuthors(pageNumber);
        return new ApiResponse<>(authors);
    }

    /**
     * Returns page of authors with advanced filters.
     * @param pageNumber page number
     * @param name authors name
     * @param orderDirection asc or desc
     * @return page with authors
     */

    @RequestMapping(value = "search", method = RequestMethod.GET)
    public ApiResponse<Page<Author>> findAuthors(
        @RequestParam(required = false, defaultValue = "0") String pageNumber,
        @RequestParam(required = false, defaultValue = "") String name,
        @RequestParam(required = false, defaultValue = "desc") String orderDirection) {

        Map<String, String> params = new HashMap<>();

        params.put("name", name);
        params.put("page", pageNumber);
        params.put("orderDirection", orderDirection);

        return new ApiResponse<>(authorSearchService.findAuthor(params));
    }

    /**
     * Finds author by it's id.
     *
     * @param id id
     * @return author entity
     */

    @RequestMapping(value = "{id}", method = RequestMethod.GET)
    public ApiResponse<Author> findAuthor(@PathVariable Integer id) {
        Author author = authorService.findAuthor(id);
        return new ApiResponse<>(author);
    }

    /**
     * Saves new author in db.
     *
     * @param author author dto
     * @return author entity
     */

    @RequestMapping(value = "", method = RequestMethod.POST)
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResponse<Author> createAuthor(@RequestBody Author author) {
        Author savedAuthor = authorService.createAuthor(author);
        return new ApiResponse<>(savedAuthor);
    }

    /**
     * Deletes author by it's id.
     *
     * @param id id
     * @return status message
     */

    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(value = "{id}", method = RequestMethod.DELETE)
    public ApiResponse deleteAuthor(@PathVariable Integer id) {
        authorService.deleteAuthor(id);
        return new ApiResponse("Author has been deleted");
    }
}
