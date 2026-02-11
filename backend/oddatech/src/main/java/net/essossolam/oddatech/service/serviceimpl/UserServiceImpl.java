package net.essossolam.oddatech.service.serviceimpl;

import jakarta.annotation.PostConstruct;
import net.essossolam.oddatech.dto.LoginRequestDTO;
import net.essossolam.oddatech.dto.LoginResponseDTO;
import net.essossolam.oddatech.entity.User;
import net.essossolam.oddatech.repository.UserRepository;
import net.essossolam.oddatech.service.UserService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PostConstruct
    public void ensureAdminExists() {
        String adminEmail = "admin@gmail.com";
        String adminPassword = "password";
        Optional<User> existing = userRepository.findByEmail(adminEmail);
        if (existing.isEmpty()) {
            User admin = new User();
            admin.setEmail(adminEmail);
            admin.setPassword(adminPassword);
            userRepository.save(admin);
        }
    }

    @Override
    public LoginResponseDTO authenticate(LoginRequestDTO request) {
        Optional<User> userOpt = userRepository.findByEmail(request.email());
        if (userOpt.isEmpty()) return null;
        User user = userOpt.get();
        // Plain-text comparison (simple). For production replace with hashed passwords.
        if (!user.getPassword().equals(request.password())) {
            return null;
        }
        return new LoginResponseDTO(user.getTrackingId(), user.getEmail(), "Authenticated");
    }
}
