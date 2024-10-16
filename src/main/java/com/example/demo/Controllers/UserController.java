package com.example.demo.Controllers;

import com.example.demo.DTOS.UserRecordDto;
import com.example.demo.Model.User;
import com.example.demo.Repository.UserRepository;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @PostMapping
    public ResponseEntity<?> saveUser(@RequestBody @Valid UserRecordDto userRecordDto) {
        User user = new User();
        BeanUtils.copyProperties(userRecordDto, user);

        User savedUser = userRepository.save(user);

        return ResponseEntity.status(HttpStatus.CREATED).body(savedUser);
    }


    @GetMapping
    public ResponseEntity<?> getUser(
            @RequestParam(name = "id", required = false) Integer id,
            @RequestParam(name = "username", required = false) String username) {

        if (id != null) {
            Optional<User> user = userRepository.findById(id);
            return user.map(ResponseEntity::ok)
                    .orElseGet(() -> {
                        Map<String, String> response = new HashMap<>();
                        response.put("error", "Usuário com id " + id + " não encontrado");
                        return ResponseEntity.status(HttpStatus.NOT_FOUND).body((User) response);
                    });
        }

        if (username != null) {
            Optional<User> user = userRepository.findByUsername(username);
            return user.map(ResponseEntity::ok)
                    .orElseGet(() -> {
                        Map<String, String> response = new HashMap<>();
                        response.put("error", "Usuário com username " + username + " não encontrado");
                        return ResponseEntity.status(HttpStatus.NOT_FOUND).body((User) response);
                    });
        }

        List<User> allUsers = userRepository.findAll();
        return ResponseEntity.ok(allUsers);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateUser(@PathVariable Integer id, @RequestBody @Valid UserRecordDto userRecordDto) {
        Optional<User> userOptional = userRepository.findById(id);

        if (userOptional.isPresent()) {
            var user = userOptional.get();
            BeanUtils.copyProperties(userRecordDto, user);
            return ResponseEntity.ok(userRepository.save(user));
        } else {
            Map<String, String> response = new HashMap<>();
            response.put("error", "Usuário com id " + id + " não encontrado");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Integer id) {
        Optional<User> userOptional = userRepository.findById(id);

        if (userOptional.isPresent()) {
            userRepository.delete(userOptional.get());
            return ResponseEntity.noContent().build();
        } else {
            Map<String, String> response = new HashMap<>();
            response.put("error", "Usuário com id " + id + " não encontrado");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }
}
