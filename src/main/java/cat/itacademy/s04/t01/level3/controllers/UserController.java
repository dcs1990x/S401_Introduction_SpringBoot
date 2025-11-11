package cat.itacademy.s04.t01.level3.controllers;

import cat.itacademy.s04.t01.level2.UserNotFoundException;
import cat.itacademy.s04.t01.level3.entities.User;
import cat.itacademy.s04.t01.level3.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.UUID;

@RestController
public class UserController {

    private final UserRepository userRepository;

    public UserController(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    @GetMapping("/users")
    public List<User> getUsers(){
        return userRepository.findAll();
    }

    @GetMapping("/users/{id}")
    public User getUserById(@PathVariable UUID id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
    }

    @GetMapping(value = "/users", params = "name")
    public List<User> getUserByName(@RequestParam String name) {
        return userRepository.searchByName(name);
    }

    @PostMapping("/users")
    public ResponseEntity<User> postUser(@RequestBody User user){
        User savedUser = userRepository.save(new User(user.getName(), user.getEmail()));
        return ResponseEntity.status(HttpStatus.CREATED).body(savedUser);
    }
}