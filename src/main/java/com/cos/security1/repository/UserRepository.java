package com.cos.security1.repository;

import com.cos.security1.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

//@Repository  JpaRepository 를 상속했으므로 선언하지 않아도 자동 주입.
public interface UserRepository extends JpaRepository<User, Integer> {
}
