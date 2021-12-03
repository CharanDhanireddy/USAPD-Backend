package com.usapd.backend.repository;

import com.usapd.backend.entity.Test;
import net.minidev.json.JSONObject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface getAllTuplesRepository extends JpaRepository<Test, Integer> {
        @Query(value = "SELECT SUM(COUNT) FROM " +
                "(SELECT table_name," +
                    "to_number(extractvalue(xmltype(dbms_xmlgen.getxml('select count(*) c FROM '||owner||'.'||table_name)),'/ROWSET/ROW/C'))" +
                    "AS count " +
                "FROM all_tables " +
                "WHERE owner = 'VDHAVALESWARAPU')",
            nativeQuery = true)
    String getAllTuples();

}
