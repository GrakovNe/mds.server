package org.grakovne.mds.server.services;

import org.grakovne.mds.server.entity.User;
import org.grakovne.mds.server.exceptons.EntityAlreadyExistException;
import org.grakovne.mds.server.repositories.UserRepository;
import org.grakovne.mds.server.utils.ConfigurationUtils;
import org.grakovne.mds.server.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ConfigurationUtils configurationUtils;

    @Override
    public User loadUserByUsername(String username) {
        User user = userRepository.findByUsername(username);

        if (user == null) {
            throw new UsernameNotFoundException(username);
        }

        return user;
    }

    public Page<User> findUsers(Integer pageNumber) {
        return userRepository.findAll(new PageRequest(pageNumber, configurationUtils.getPageSize()));
    }

    public User createUser(User user) {
        checkFound(user);

        user.setPassword(SecurityUtils.hashSha512(user.getPassword()));
        user.setAccountNonExpired(true);
        user.setAccountNonLocked(true);
        user.setCredentialsNonExpired(true);
        user.setEnabled(true);

        return persistUser(user);
    }

    private User persistUser(User user) {
        return userRepository.save(user);
    }

    private void checkFound(User user) {
        User foundUser = userRepository.findByUsername(user.getUsername());

        if (null != foundUser) {
            throw new EntityAlreadyExistException(User.class);
        }
    }
}