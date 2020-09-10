package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.models.Class;
import com.example.demo.models.Register;
import com.example.demo.models.Student;
import com.example.demo.repository.RegisterRepository;
import com.example.demo.repository.StudentRepository;

@RestController
public class RegisterController {
	
	@Autowired
	RegisterRepository repo;
	
	@Autowired
	StudentRepository studentRepo;
	
	@PostMapping("/assign")
	public ResponseEntity<Object> assignStudentToAClass(@RequestBody Register register) {
		if (register != null) {
			try {
				Register createdObject = repo.save(register);
				return ResponseEntity.ok(createdObject);
			} catch (Exception ex) {
				ex.printStackTrace();
				return ResponseEntity.badRequest().body(ex.getMessage());
			}
		}
		return ResponseEntity.unprocessableEntity().body("Invalid json");
	}
	
	@PostMapping("/unassign")
	public ResponseEntity<Object> unassignStudentToAClass(@RequestBody Register register) {
		if (register != null) {
			try {
				Register registerFound = repo.findRegisterByStudentidAndClassid(register.getStudentid(), register.getClassid());
				repo.delete(registerFound);
				return ResponseEntity.ok("successfully unassigned student with class");
			} catch (Exception ex) {
				return ResponseEntity.badRequest().body(ex.getMessage());
			}
		}
		return ResponseEntity.unprocessableEntity().body("Invalid json");
	}
	
	@GetMapping("/studentsWithGrade")
	public List<Student> getAllStudentsWithGrade() {
		List<Student> students = studentRepo.findAll();
		students.stream().forEach(student -> {
			 List<Class> classes = student.getClasses();
			 classes.stream().forEach(classObj -> {
				 Register found = repo.findRegisterByStudentidAndClassid(student.getStudentid(), classObj.getClassid());
				 classObj.setGrade(found.getGrade());
			 });
		});
		return students;
	}

}
