package cat.itacademy.s04.t01.level2.controllers;

import cat.itacademy.s04.t01.level2.entities.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
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