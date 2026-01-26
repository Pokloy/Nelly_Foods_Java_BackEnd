package com.torrenueva.alier.common;

import com.torrenueva.alier.model.dao.UserInfoAccountDao;
import com.torrenueva.alier.model.dao.entity.UserEntity;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    private final UserInfoAccountDao userRepository;

    public DataInitializer(UserInfoAccountDao userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        // Check if admin user exists
        String adminEmail = "admin@example.com";
        boolean exists = userRepository.existsByEmail(adminEmail);

        if (!exists) {
            UserEntity admin = new UserEntity();
            admin.setFirstName("Admin");
            admin.setMiddleName("Admin");
            admin.setFamilyName("User");
            admin.setEmail(adminEmail);
            admin.setPhoneNumber("09123456789");
            admin.setAddress("Headquarters");
            admin.setPassword("$2a$12$JuSOEvoAqrHKNUIlZmI9CegYwIIPY6d9YO07fEm/gaYR2VXPbU/Iy"); // in real apps, hash this!
            admin.setUserType("ADMIN");
            admin.setDeleteFlag(false);

            userRepository.save(admin);
            System.out.println("Admin user created!");
        } else {
            System.out.println("Admin user already exists.");
        }
    }
}
