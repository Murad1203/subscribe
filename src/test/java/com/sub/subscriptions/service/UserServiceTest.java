package com.sub.subscriptions.service;

import com.sub.subscriptions.exception.UserNotFoundException;
import com.sub.subscriptions.model.User;
import com.sub.subscriptions.repository.UserRepository;
import com.sub.subscriptions.service.imp.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userService;

    @Test
    void testCreateUser() {
        User user = new User();
        userService.createUser(user);
        verify(userRepository).save(user);
    }

    @Test
    void testGetUser_found() {
        User user = new User();
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        User result = userService.getUser(1L);

        assertEquals(user, result);
    }

    @Test
    void testGetUser_notFound() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> userService.getUser(1L));
    }

    @Test
    void testDeleteUser() {
        userService.deleteUser(1L);
        verify(userRepository).deleteById(1L);
    }

    @Test
    void testUpdateUser_notYetImplemented() {
        // Метод пока не реализован — это скорее напоминание
        User user = new User();
        assertDoesNotThrow(() -> userService.updateUser(1L, user));
        // Но как только реализуешь — обязательно допишем проверку логики
    }
}
