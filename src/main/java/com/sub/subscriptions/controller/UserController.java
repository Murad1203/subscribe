package com.sub.subscriptions.controller;


import com.sub.subscriptions.model.User;
import com.sub.subscriptions.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    /**
     * Получить пользователя по ID.
     * @param id идентификатор пользователя
     * @return найденный пользователь
     */
    @GetMapping("/{id}")
    public ResponseEntity<User> getUser(@PathVariable long id) {
        User user = userService.getUser(id);
        return ResponseEntity.ok(user);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable long id) {
        userService.deleteUser(id);
        return ResponseEntity.ok("Успешно удален");
    }

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user) {
        userService.createUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(user);
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable long id, @RequestBody User user) {
        userService.updateUser(id, user);
        return ResponseEntity.ok(user);
    }

}
