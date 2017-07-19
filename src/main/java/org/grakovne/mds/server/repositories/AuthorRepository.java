package org.grakovne.mds.server.repositories;

import org.grakovne.mds.server.entity.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * JPA Repository.
 */

@Repository
public interface AuthorRepository extends JpaRepository<Author, Integer> {
    /**
     * Finds one author by first and last name.
     *
     * @param firstName author's first name
     * @param lastName  author's last name
     * @return author entity
     */
    Author findAllByFirstNameAndLastName(String firstName, String lastName);
}
