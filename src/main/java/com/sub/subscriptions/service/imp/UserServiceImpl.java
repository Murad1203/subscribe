package com.sub.subscriptions.service.imp;

import com.sub.subscriptions.exception.UserNotFoundException;
import com.sub.subscriptions.model.User;
import com.sub.subscriptions.repository.UserRepository;
import com.sub.subscriptions.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;

public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public void createUser(User user) {
        userRepository.save(user);
    }

    @Override
    public User getUser(long id) {
        return userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("User not found with id: " + id));
    }

    @Override
    public void updateUser(long id, User user) {
    }

    @Override
    public void deleteUser(long id) {
        userRepository.deleteById(id);
    }
}
