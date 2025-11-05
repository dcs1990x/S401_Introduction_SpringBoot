package cat.itacademy.s04.t01.level2.controllers;

import cat.itacademy.s04.t01.level2.entities.User;
import java.util.List;

public record UserReceiver(List<User> users) {}