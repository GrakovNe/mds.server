package org.grakovne.mds.server.services;

import org.grakovne.mds.server.entity.Author;
import org.grakovne.mds.server.repositories.AuthorRepository;
import org.grakovne.mds.server.utils.CheckerUtils;
import org.grakovne.mds.server.utils.ConfigurationUtils;
import org.grakovne.mds.server.utils.ValidationUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

/**
 * Author service.
 */

@Service
public class AuthorService {

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private ConfigurationUtils configurationUtils;

    /**
     * Saves new author in DB.
     *
     * @param author author entity
     * @return saved author
     */
    public Author createAuthor(Author author) {

        ValidationUtils.validate(author);

        Author foundAuthor = authorRepository.findAllByFirstNameAndLastName(
            author.getFirstName(), author.getLastName());
        CheckerUtils.checkNull(foundAuthor);

        return authorRepository.save(author);
    }

    /**
     * Finds authors.
     *
     * @param pageNumber page number
     * @return page with authors
     */

    public Page<Author> findAuthors(Integer pageNumber) {
        return authorRepository.findAll(new PageRequest(pageNumber, configurationUtils.getPageSize()));
    }

    /**
     * Finds author by it's id.
     *
     * @param id author id
     * @return author entity
     */

    public Author findAuthor(Integer id) {
        Author author = authorRepository.findOne(id);
        CheckerUtils.checkNotNull(author);

        return author;
    }

    /**
     * Deletes author by it's id.
     *
     * @param id author id
     */

    public void deleteAuthor(Integer id) {
        Author author = findAuthor(id);
        authorRepository.delete(author);
    }

}
