package com.example.demo;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.example.demo.controller.StudentController;
import com.example.demo.models.Student;
import com.example.demo.repository.StudentRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

@ExtendWith(MockitoExtension.class)
public class StudentControllerTest {

	private MockMvc mvc;
	
	@Mock
	private StudentRepository repository;
	
	@InjectMocks
	private StudentController controller;
	
	private JacksonTester<List<Student>> jsonStudentsList;
	
	private JacksonTester<ResponseEntity<Object>> jsonPostStudent;
	private JacksonTester<Student> jsonStudent;

    @BeforeEach
    public void setup() {
        JacksonTester.initFields(this, new ObjectMapper());
        mvc = MockMvcBuilders.standaloneSetup(controller)
                .setControllerAdvice(new Exception())
                .build();
    }
    
    @Test
    public void canRetrieveAllStudents() throws Exception {
    	List<Student> students = new ArrayList<>();
    	Student student = new Student(1,"Ankur",23);
    	Student student2 = new Student(2,"Jacob",16);
    	students.add(student);
    	students.add(student2);
    	when(repository.findAll()).thenReturn(students);
    	MockHttpServletResponse response = mvc.perform(get("/students")).andReturn().getResponse();
    	assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
    	assertThat(response.getContentAsString()).isEqualTo(jsonStudentsList.write(students).getJson());
    }
    
    
    @Test
    public void createStudentTest() throws Exception {

    	Student student = new Student(1,"Ankur",23);
    	MockHttpServletResponse response = mvc.perform(post("/students").contentType(org.springframework.http.MediaType.APPLICATION_JSON)
    			.content(jsonStudent.write(student).getJson()))
    			.andReturn().getResponse();
    	assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
    }
    
    @Test
    public void createStudentTestErrorWithoutBody() throws Exception {

    	Student student = new Student(1,"Ankur",23);
    	MockHttpServletResponse response = mvc.perform(post("/students"))
    			.andReturn().getResponse();
    	assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }
    
    @Test
    public void updateStudentTest() throws Exception {

    	Student student = new Student(1,"Ankur",23);
    	MockHttpServletResponse response = mvc.perform(put("/students").contentType(org.springframework.http.MediaType.APPLICATION_JSON)
    			.content(jsonStudent.write(student).getJson()))
    			.andReturn().getResponse();
    	assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
    }
    
    @Test
    public void deleteStudentTest() throws Exception {

    	MockHttpServletResponse response = mvc.perform(delete("/students?id=1"))
    			.andReturn().getResponse();
    	assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
    	assertThat(response.getContentAsString()).isEqualTo("student successfully deleted");
    }
    
    
}
