package mx.edu.iunis.eats.service;

import mx.edu.iunis.eats.domain.User;

import java.util.List;

public interface UserService {
    User createUser(User user);
    List<User> listUser();
    void deleteUser(User user);
    User updateUser(User user);
}
