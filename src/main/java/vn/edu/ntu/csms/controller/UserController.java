package vn.edu.ntu.csms.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import vn.edu.ntu.csms.entity.User;
import vn.edu.ntu.csms.service.UserService;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "*") // Hỗ trợ kết nối Frontend mọi cổng
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public List<User> getAll() { return userService.getAllUsers(); }

    @PostMapping
    public User create(@RequestBody User user) { return userService.createUser(user); }
    @PostMapping("/login")
    public User login(@RequestBody Map<String, String> credentials) {
        String email = credentials.get("email");
        String password = credentials.get("password");
        return userService.login(email, password);
    }
}