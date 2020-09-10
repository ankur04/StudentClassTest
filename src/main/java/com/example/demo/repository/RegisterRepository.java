package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.demo.models.Register;

public interface RegisterRepository extends JpaRepository<Register, Integer>{
	
	@Query("SELECT r FROM Register r WHERE r.studentid = ?1 and r.classid = ?2")
	Register findRegisterByStudentidAndClassid(Integer studentid, Integer classid);

}
