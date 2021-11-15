package com.usapd.backend.repository;

import com.usapd.backend.entity.UserCredentials;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserCredentialRepository extends JpaRepository<UserCredentials, String> {

}
