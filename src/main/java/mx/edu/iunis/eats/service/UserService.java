package mx.edu.iunis.eats.service;

import mx.edu.iunis.eats.domain.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    User createUser(User user);
    List<User> listUsers();
    Optional<User> findUserById(Long id);
    boolean userNameExists(String userName);
    User updateUser(Long id, User user);
    void deleteUser(Long id);
}
