package com.mindBowser.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mindBowser.model.User;

/**
 * The interface User repository.
 *
 * @author krishna
 */
@Repository
public interface ManagerRepository extends JpaRepository<User, Long> {}
