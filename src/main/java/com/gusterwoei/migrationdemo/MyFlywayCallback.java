package com.gusterwoei.migrationdemo;

import com.gusterwoei.migrationdemo.domain.User;
import com.gusterwoei.migrationdemo.repository.UserRepository;
import org.flywaydb.core.api.MigrationInfo;
import org.flywaydb.core.api.callback.Callback;
import org.flywaydb.core.api.callback.Context;
import org.flywaydb.core.api.callback.Event;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.util.Random;

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
            case AFTER_EACH_MIGRATE: {
                MigrationInfo migration = context.getMigrationInfo();
                String version = migration.getVersion().getVersion();

                switch (version) {
                    case "2.0.2": {
                        System.out.println("Executing migration callback for version: " + version);
                        User user = new User();
                        user.setUsername("lorem-" + (new Random()).nextInt(100));
                        user.setPassword("password");
                        user.setEmail("lorem@ipsum.com");
                        user.setPhone("0161234567");
                        userRepository.save(user);
                        break;
                    }
                }

                break;
            }
        }
    }
}
