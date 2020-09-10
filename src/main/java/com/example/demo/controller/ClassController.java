package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.models.Class;
import com.example.demo.models.Student;
import com.example.demo.repository.ClassRepository;
import com.example.demo.repository.StudentRepository;


@RestController
public class ClassController {

	@Autowired
	ClassRepository repository;

	@GetMapping("/class")
	public List<com.example.demo.models.Class> getAllStudents() {
		return repository.findAll();
	}

	@PostMapping("/class")
	public ResponseEntity<Object> createStudent(@RequestBody com.example.demo.models.Class classObj) {
		if (classObj != null) {
			try {
				Class createdObject = repository.save(classObj);
				return ResponseEntity.ok(createdObject);
			} catch (Exception ex) {
				return ResponseEntity.badRequest().body(ex.getMessage());
			}
		}
		return ResponseEntity.unprocessableEntity().body("Invalid json");
	}
	
	@PutMapping("/class")
	public ResponseEntity<Object> updateStudent(@RequestBody Class classObj) {
		if (classObj != null) {
			try {
				repository.save(classObj);
				return ResponseEntity.ok("Class successfully updated");
			} catch (Exception ex) {
				return ResponseEntity.badRequest().body(ex.getMessage());
			}
		}
		return ResponseEntity.unprocessableEntity().body("Invalid json");
	}
	
	@DeleteMapping("/class")
	public ResponseEntity<Object> deleteStudent(@RequestParam String id){
		if (id != null) {
			try {
				repository.deleteById(Integer.valueOf(id));
				return ResponseEntity.ok("class successfully deleted");
			} catch (Exception ex) {
				return ResponseEntity.badRequest().body(ex.getMessage());
			}
		}
		return ResponseEntity.unprocessableEntity().body("Invalid id");
	}

}