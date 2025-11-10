package cat.itacademy.s04.t01.level2.controllers;

import cat.itacademy.s04.t01.level2.UserNotFoundException;
import cat.itacademy.s04.t01.level2.entities.User;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
public class UserController {

    private static List<User> users = new ArrayList<>();

    @GetMapping("/users")
    public List<User> getUsers(){
        return users;
    }

    @GetMapping("/users/{id}")
    public User getUserById(@PathVariable UUID id) {
        return users.stream()
                .filter(u -> u.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new UserNotFoundException(id));
    }

    @GetMapping(value = "/users", params = "name")
    public List<User> getUserByName(@RequestParam String name) {
        if (name == null || name.isBlank()) {
            return users;
        }
        String searchName = name.toLowerCase();
        return users.stream()
                .filter(u -> u.getName().toLowerCase().contains(searchName))
                .toList();
    }

    @PostMapping("/users")
    public User postUser(@RequestBody User user){
        UUID userId = UUID.randomUUID();
        User newUser = new User();
        newUser.setId(userId);
        newUser.setName(user.getName());
        newUser.setEmail(user.getEmail());
        users.add(newUser);
        return newUser;
    }
}