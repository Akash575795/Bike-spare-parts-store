package com.akashmailapalli.bike_spare_parts_store.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.akashmailapalli.bike_spare_parts_store.model.User;

@Repository
public interface UserRepository extends JpaRepository<User,Long>{

    User findByUsername(String username);
}
