package mx.edu.iunis.eats.service;

import mx.edu.iunis.eats.domain.User;
import mx.edu.iunis.eats.repository.PersonalDataRepository;
import mx.edu.iunis.eats.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;
    private final PersonalDataRepository personalDataRepository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository,
                           PersonalDataRepository personalDataRepository,
                           PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.personalDataRepository = personalDataRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User createUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
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
    public boolean userNameExists(String userName) {
        return userRepository.findByUserName(userName).isPresent();
    }

    @Override
    public User updateUser(Long id, User user) {
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("No existe el usuario " + id));
        existingUser.setUserName(user.getUserName());
        existingUser.setRole(user.getRole());
        if (user.getPassword() != null && !user.getPassword().isBlank()) {
            existingUser.setPassword(passwordEncoder.encode(user.getPassword()));
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
