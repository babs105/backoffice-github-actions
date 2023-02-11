package sn.sastrans.backofficev2.security.services;


import sn.sastrans.backofficev2.security.models.User;

import java.util.List;

public interface UserService {
    User saveUser(User user);
    List<User> getAllUser();
    User getUserById(int id);
    void deleteUser(int id);
    Boolean existsByUsername(String username);
}

