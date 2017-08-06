package org.grakovne.mds.server.repositories;

import org.grakovne.mds.server.entity.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * JPA Repository.
 */

@Repository
public interface UserRoleRepository extends JpaRepository<UserRole, Integer> {

    /**
     * Returns role by name of user.
     * @param name userName
     * @return UserRole
     */

    UserRole findByName(String name);
}
