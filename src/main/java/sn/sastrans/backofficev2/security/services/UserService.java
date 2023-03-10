package sn.sastrans.backofficev2.security.services;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import sn.sastrans.backofficev2.security.models.User;

import java.util.List;

public interface UserService {
    User saveUser(User user);
    Page<User> getAllUser(Pageable pageable);
    User getUserById(int id);
    void deleteUser(int id);
    Page<User> getAllUserByKeyword(String keyword, Pageable pageable);
    Boolean existsByUsername(String username);
}

