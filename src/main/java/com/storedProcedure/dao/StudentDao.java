package com.storedProcedure.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.storedProcedure.model.Student;

@Repository
public interface StudentDao extends JpaRepository<Student, Long> {

}

