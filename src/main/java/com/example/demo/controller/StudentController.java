package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.models.Student;
import com.example.demo.repository.StudentRepository;

@RestController
public class StudentController {

	@Autowired
	StudentRepository repository;

	@GetMapping("/students")
	public List<Student> getAllStudents() {
		return repository.findAll();
	}

	@PostMapping("/students")
	public ResponseEntity<Object> createStudent(@RequestBody Student student) {
		if (student != null) {
			try {
				Student createdObject = repository.save(student);
				return ResponseEntity.ok(createdObject);
			} catch (Exception ex) {
				return ResponseEntity.badRequest().body(ex.getMessage());
			}
		}
		return ResponseEntity.unprocessableEntity().body("Invalid json");
	}
	
	@PutMapping("/students")
	public ResponseEntity<Object> updateStudent(@RequestBody Student student) {
		if (student != null) {
			try {
				repository.save(student);
				return ResponseEntity.ok("student successfully updated");
			} catch (Exception ex) {
				return ResponseEntity.badRequest().body(ex.getMessage());
			}
		}
		return ResponseEntity.unprocessableEntity().body("Invalid json");
	}
	
	@DeleteMapping("/students")
	public ResponseEntity<Object> deleteStudent(@RequestParam String id){
		if (id != null) {
			try {
				repository.deleteById(Integer.valueOf(id));
				return ResponseEntity.ok("student successfully deleted");
			} catch (Exception ex) {
				return ResponseEntity.badRequest().body(ex.getMessage());
			}
		}
		return ResponseEntity.unprocessableEntity().body("Invalid id");
	}

}
