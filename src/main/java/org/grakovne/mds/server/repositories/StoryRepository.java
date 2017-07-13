package org.grakovne.mds.server.repositories;

import org.grakovne.mds.server.entity.Author;
import org.grakovne.mds.server.entity.Story;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * JPA Repository.
 */

@Repository
public interface StoryRepository extends JpaRepository<Story, Integer> {
    List<Story> findByTitleAndAuthorsAndYear(String title, List<Author> authors, Integer year);
}
