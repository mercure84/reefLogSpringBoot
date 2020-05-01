package com.reeflog.reeflogapi.repository;

import com.reeflog.reeflogapi.beans.helpers.PasswordRecover;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PasswordRecoverRepository extends JpaRepository<PasswordRecover, Integer> {

}
