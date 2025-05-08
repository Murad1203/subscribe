package com.sub.subscriptions.service;

import com.sub.subscriptions.model.User;
import org.springframework.stereotype.Service;

@Service
public interface UserService {
    void createUser(User user);
    User getUser(long id);
    void updateUser(long id, User user);
    void deleteUser(long id);
}
