package org.grakovne.mds.server.repositories;

import org.grakovne.mds.server.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * JPA Repository.
 */

public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * Returns user by username.
     *
     * @param username user login
     * @return User object
     */

    User findByUsername(String username);
}
