package com.usapd.backend.repository;

import com.usapd.backend.entity.Test;
import net.minidev.json.JSONObject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


import java.util.List;

@Repository
public interface TestRepository extends JpaRepository<Test, Integer> {

    @Query(value = "SELECT * FROM vdhavaleswarapu.test WHERE username like %:username%",
            nativeQuery = true)
    List<JSONObject> selectUsersByUsername(
            @Param("username") String name);

}
