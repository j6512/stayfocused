package com.j6512.stayfocused.models.data;

import com.j6512.stayfocused.models.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Integer> {

    User findByUsername(String username);
}
