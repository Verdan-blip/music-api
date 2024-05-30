package org.muztache.api.repository;

import org.muztache.api.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findUserByLogin(String login);

    Optional<User> findUserByEmail(String email);

    List<User> findAllByLoginContainingIgnoreCase(String login);

    boolean existsUserByLogin(String login);

    boolean existsUserByEmail(String email);
}
