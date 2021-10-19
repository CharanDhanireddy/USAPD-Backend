package com.usapd.backend.repository;

import com.usapd.backend.entity.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


import java.util.ArrayList;
import java.util.List;

@Repository
public interface TestRepository extends CrudRepository<Test, Integer> {

    List<Test> findAllById(Integer Id);

}
