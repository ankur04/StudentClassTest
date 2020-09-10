package com.example.demo;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

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

import com.example.demo.controller.RegisterController;
import com.example.demo.controller.StudentController;
import com.example.demo.models.Class;
import com.example.demo.models.Register;
import com.example.demo.models.Student;
import com.example.demo.repository.RegisterRepository;
import com.example.demo.repository.StudentRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

@ExtendWith(MockitoExtension.class)
public class RegisterControllerTest {

	private MockMvc mvc;
	
	@Mock
	private RegisterRepository repository;
	
	@Mock
	private StudentRepository studentRepo;
	
	@InjectMocks
	private RegisterController controller;
	
	private JacksonTester<List<Student>> jsonRegisterList;
	
	private JacksonTester<ResponseEntity<Object>> jsonPostRegister;
	private JacksonTester<Register> jsonRegister;

    @BeforeEach
    public void setup() {
        JacksonTester.initFields(this, new ObjectMapper());
        mvc = MockMvcBuilders.standaloneSetup(controller)
                .setControllerAdvice(new Exception())
                .build();
    }
    
    @Test
    public void assignStudentToClass() throws Exception {

    	Register register = new Register(1,2,8,"O");
    	MockHttpServletResponse response = mvc.perform(post("/assign")
    			.contentType(org.springframework.http.MediaType.APPLICATION_JSON)
    			.content(jsonRegister.write(register).getJson()))
    			.andReturn().getResponse();
    	assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
    }
    
    @Test
    public void unassignStudentToClass() throws Exception {
    	Register register = new Register(1,2,8,"O");
    	MockHttpServletResponse response = mvc.perform(post("/unassign")
    			.contentType(org.springframework.http.MediaType.APPLICATION_JSON)
    			.content(jsonRegister.write(register).getJson()))
    			.andReturn().getResponse();
    	assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
    }
    
    @Test
    public void retrieveAllStudents() throws Exception {
    	List<Student> students = new ArrayList<>();
    	Student student = new Student(2,"Ankur",23);
    	com.example.demo.models.Class class1= new Class(8, "Math", "mathematics");
    	class1.setGrade("O");
    	List<Class> classes = new ArrayList<>();
    	classes.add(class1);
    	student.setClasses(classes);
    	students.add(student);
    	when(studentRepo.findAll()).thenReturn(students);
    	Register register = new Register(1,2,8,"O");
    	when(repository.findRegisterByStudentidAndClassid(2, 8)).thenReturn(register);
    	MockHttpServletResponse response = mvc.perform(get("/studentsWithGrade")).andReturn().getResponse();
    	assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
    	assertThat(response.getContentAsString()).isEqualTo(jsonRegisterList.write(students).getJson());
    }
    
    

}
