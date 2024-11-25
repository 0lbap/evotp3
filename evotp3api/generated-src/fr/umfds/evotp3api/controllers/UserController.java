package fr.umfds.evotp3api.controllers;
import fr.umfds.evotp3api.models.User;
import fr.umfds.evotp3api.repositories.UserRepository;
import java.util.List;
import org.springframework.web.bind.annotation.*;
@RestController
@RequestMapping("/api/v1/users")
public class UserController {
    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping
    public List<User> list() {
        return userRepository.findAll();
    }
}
