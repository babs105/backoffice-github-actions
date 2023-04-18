package sn.sastrans.backofficev2.security.servicesImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import sn.sastrans.backofficev2.exception.BadRequestException;
import sn.sastrans.backofficev2.security.models.User;
import sn.sastrans.backofficev2.security.repositories.UserRepository;
import sn.sastrans.backofficev2.security.services.UserService;
import sn.sastrans.backofficev2.trace.models.Evenement;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserRepository userRepository;


    @Override
    public User saveUser(User user) {
        if(user.getUsername().isEmpty() || user.getUsername().length()==0 ){
            throw new BadRequestException("L'Email ou le Mot de passe ne doit pas être vide");
        }
        return userRepository.save(user);
    }

    @Override
    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }


    @Override
    public Page<User> getAllUser(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    @Override
    public Page<User> getAllUserByKeyword(String keyword, Pageable pageable) {
        return userRepository.findByKeyword(keyword, pageable);
    }

    @Override
    public User getUserById(int id) {
        return userRepository.findById(id).get();
    }

    @Override
    public void deleteUser(int id) {
        userRepository.deleteById(id);
    }

    @Override
    public Boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }
}
