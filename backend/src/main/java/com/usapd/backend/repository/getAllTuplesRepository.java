package com.usapd.backend.repository;

import com.usapd.backend.entity.Test;
import net.minidev.json.JSONObject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface getAllTuplesRepository extends JpaRepository<Test, Integer> {
        @Query(value = "select sum(num_rows)as countTotal from (SELECT table_name,num_rows FROM all_tables WHERE owner = 'VDHAVALESWARAPU')",
            nativeQuery = true)
    String getAllTuples();

}
