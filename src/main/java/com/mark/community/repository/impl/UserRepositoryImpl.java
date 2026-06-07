package com.mark.community.repository.impl;

import com.mark.community.entity.User;
import com.mark.community.repository.UserRepository;
import org.springframework.stereotype.Repository;
import tools.jackson.databind.ObjectMapper;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Repository
public class UserRepositoryImpl implements UserRepository {
    private final ObjectMapper objectMapper;
    private final String FILE = "db/users.json";
    private final Map<String, User> users = new HashMap<>();
    private final Map<String, String> emailToUserId = new HashMap<>();
    private int nextUserId = 1;

    public UserRepositoryImpl(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
        File jsonFile = new File(FILE);
        if (jsonFile.exists() && jsonFile.length() > 0) {
            User[] userData = objectMapper.readValue(jsonFile, User[].class);

            for (User user : userData) {
                users.put(user.getUserId(), user);
                emailToUserId.put(user.getEmail(), user.getUserId());

                int num = Integer.parseInt(user.getUserId().substring(1));

                if (num >= nextUserId) nextUserId = num + 1;
            }
        }
    }

    private void saveJson() {
        objectMapper.writeValue(new File(FILE), users.values());
    }

    public User save(User user) {
        if(user.getUserId() == null){
            user.setUserId("U" + nextUserId++);
        }
        users.put(user.getUserId(), user);
        emailToUserId.put(user.getEmail(), user.getUserId());
        saveJson();
        return user;
    }

    @Override
    public Optional<User> findById(String userId) {
        return  Optional.ofNullable(users.get(userId));
    }

    @Override
    public Optional<User> findByEmail(String email) {
        String userId = emailToUserId.get(email);
        return Optional.ofNullable(users.get(userId));
    }

    public Optional<User> findByEmailAndPassword(String email, String password){
        String userId = emailToUserId.get(email);
        User user = users.get(userId);
        if(user != null && user.getPassword().equals(password)) return Optional.of(user);

        return Optional.empty();
    }

    @Override
    public boolean existByUser(String userId) {
        User user = users.get(userId);
        return user != null && !user.isDeleted();
    }
}
