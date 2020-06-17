package com.gusterwoei.migrationdemo;

import com.github.javafaker.Faker;
import com.gusterwoei.migrationdemo.domain.User;
import com.gusterwoei.migrationdemo.repository.UserRepository;
import org.flywaydb.core.api.MigrationInfo;
import org.flywaydb.core.api.callback.Callback;
import org.flywaydb.core.api.callback.Context;
import org.flywaydb.core.api.callback.Event;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import java.util.List;

/**
 * @author Guster
 * Created at 10/06/2020
 **/

@Component
public class MyFlywayCallback implements Callback {

    private UserRepository userRepository;

    public MyFlywayCallback(@Lazy UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public boolean supports(Event event, Context context) {
        return event.equals(Event.AFTER_EACH_MIGRATE);
    }

    @Override
    public boolean canHandleInTransaction(Event event, Context context) {
        return false;
    }

    @Override
    public void handle(Event event, Context context) {
        switch (event) {
            case BEFORE_EACH_MIGRATE:
                // Some logic before the migration begins...
                break;
            case AFTER_EACH_MIGRATE: {
                MigrationInfo migration = context.getMigrationInfo();
                String version = migration.getVersion().getVersion();
                Faker faker = new Faker();

                System.out.println("> Running Migration Version [" + version + "]");

                switch (version) {
                    case "1.0.0": {
                        // loading some dummy data for the first version
                        for (int i = 0; i < 10; i++) {
                            User user = new User(faker.name().username(), faker.internet().password(), faker.internet().emailAddress());
                            userRepository.save(user);
                        }
                        break;
                    }
                    case "1.0.1": {
                        // add role to existing users if possible
                        List<User> users = userRepository.findAll();
                        users.forEach(user -> {
                            user.setRole(User.ROLE_USER);
                            userRepository.save(user);
                        });

                        // Also, let's add create a new user with role 'ROLE_ADMIN'
                        User admin = new User(faker.name().username(), faker.internet().password(), faker.internet().emailAddress());
                        admin.setRole(User.ROLE_ADMIN);
                        userRepository.save(admin);
                        break;
                    }
                }

                break;
            }
        }
    }
}
