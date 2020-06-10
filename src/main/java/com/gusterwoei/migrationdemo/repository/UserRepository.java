package com.gusterwoei.migrationdemo.repository;

import com.gusterwoei.migrationdemo.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Guster
 * Created at 10/06/2020
 **/

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}
