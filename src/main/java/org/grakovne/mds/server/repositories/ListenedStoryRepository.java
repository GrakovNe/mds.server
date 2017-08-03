package org.grakovne.mds.server.repositories;

import org.grakovne.mds.server.entity.ListenedStory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ListenedStoryRepository extends JpaRepository<ListenedStory, Integer> {
}
