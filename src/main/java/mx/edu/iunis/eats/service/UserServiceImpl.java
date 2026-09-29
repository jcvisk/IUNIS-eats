package mx.edu.iunis.eats.service;

import mx.edu.iunis.eats.domain.User;
import mx.edu.iunis.eats.repository.PersonalDataRepository;
import mx.edu.iunis.eats.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;
    private final PersonalDataRepository personalDataRepository;

    public UserServiceImpl(UserRepository userRepository, PersonalDataRepository personalDataRepository) {
        this.userRepository = userRepository;
        this.personalDataRepository = personalDataRepository;
    }

    @Override
    public User createUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public List<User> listUsers() {
        return userRepository.findAll();
    }

    @Override
    public Optional<User> findUserById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public User updateUser(Long id, User user) {
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("No existe el usuario " + id));
        existingUser.setUserName(user.getUserName());
        existingUser.setRole(user.getRole());
        if (user.getPassword() != null && !user.getPassword().isBlank()) {
            existingUser.setPassword(user.getPassword());
        }
        return userRepository.save(existingUser);
    }

    @Override
    @Transactional
    public void deleteUser(Long id) {
        personalDataRepository.deleteAllByUserId(id);
        userRepository.deleteById(id);
    }
}
