package cat.itacademy.s04.t01.level3.repository;

import cat.itacademy.s04.t01.level3.entities.User;
import org.springframework.stereotype.Repository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Repository()
public class InMemoryUserRepository implements UserRepository {

    private static List<User> users = new ArrayList<>();

    @Override
    public User save(User user) {
        users.add(user);
        return user;
    }

    @Override
    public List<User> findAll() {
        return List.copyOf(users);
    }

    @Override
    public Optional<User> findById(UUID id) {
        return users.stream()
                .filter(user -> user.getId().equals(id))
                .findFirst();
    }

    @Override
    public List<User> searchByName(String name) {
        if (name == null || name.trim().isEmpty()) {
            return List.copyOf(users);
        }

        String searchTerm = name.toLowerCase();

        return users.stream()
                .filter(user -> user.getName().toLowerCase().contains(searchTerm))
                .collect((Collectors.toList()));
    }

    @Override
    public boolean existsByEmail(String email) {
        return users.stream()
                .anyMatch(user -> user.getEmail().equalsIgnoreCase(email));
    }

    public void clearRepository() {
        users.clear();
    }
}