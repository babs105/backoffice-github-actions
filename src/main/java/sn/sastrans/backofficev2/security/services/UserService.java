package sn.sastrans.backofficev2.security.services;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import sn.sastrans.backofficev2.security.models.User;

import java.util.List;

public interface UserService {
    User saveUser(User user);
    User getUserByUsername(String username);
    Page<User> getAllUser(Pageable pageable);
    Page<User> getAllUserByKeyword(String keyword, Pageable pageable);
    User getUserById(int id);
    void deleteUser(int id);

    Boolean existsByUsername(String username);

    User updateUser(Integer id, User user);
}

