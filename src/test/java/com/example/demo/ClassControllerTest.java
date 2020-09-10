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

import com.example.demo.controller.ClassController;
import com.example.demo.repository.ClassRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

@ExtendWith(MockitoExtension.class)
public class ClassControllerTest {

	private MockMvc mvc;
	
	@Mock
	private ClassRepository repository;
	
	@InjectMocks
	private ClassController controller;
	
	private JacksonTester<List<com.example.demo.models.Class>> jsonClassList;
	
	private JacksonTester<ResponseEntity<Object>> jsonPostClass;
	private JacksonTester<com.example.demo.models.Class> jsonClass;

    @BeforeEach
    public void setup() {
        JacksonTester.initFields(this, new ObjectMapper());
        mvc = MockMvcBuilders.standaloneSetup(controller)
                .setControllerAdvice(new Exception())
                .build();
    }
    
    @Test
    public void canRetrieveAllStudents() throws Exception {
    	List<com.example.demo.models.Class> classes = new ArrayList<>();
    	com.example.demo.models.Class class1 = new com.example.demo.models.Class(1,"Physics","Grade 12 class");
    	com.example.demo.models.Class class2 = new com.example.demo.models.Class(2,"Chemistry","Grade 11 class");
    	classes.add(class1);
    	classes.add(class2);
    	when(repository.findAll()).thenReturn(classes);
    	MockHttpServletResponse response = mvc.perform(get("/class")).andReturn().getResponse();
    	assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
    	assertThat(response.getContentAsString()).isEqualTo(jsonClassList.write(classes).getJson());
    }
    
    
    @Test
    public void createStudentTest() throws Exception {

    	com.example.demo.models.Class class1 = new com.example.demo.models.Class(2,"Chemistry","Grade 11 class");
    	MockHttpServletResponse response = mvc.perform(post("/class").contentType(org.springframework.http.MediaType.APPLICATION_JSON)
    			.content(jsonClass.write(class1).getJson()))
    			.andReturn().getResponse();
    	assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
    }
    
    @Test
    public void createStudentTestErrorWithoutBody() throws Exception {

    	MockHttpServletResponse response = mvc.perform(post("/class"))
    			.andReturn().getResponse();
    	assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }
    
    @Test
    public void updateStudentTest() throws Exception {

    	com.example.demo.models.Class class1 = new com.example.demo.models.Class(2,"Chemistry","Grade 11 class");
    	MockHttpServletResponse response = mvc.perform(put("/class").contentType(org.springframework.http.MediaType.APPLICATION_JSON)
    			.content(jsonClass.write(class1).getJson()))
    			.andReturn().getResponse();
    	assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
    }
    
    @Test
    public void deleteStudentTest() throws Exception {

    	MockHttpServletResponse response = mvc.perform(delete("/class?id=1"))
    			.andReturn().getResponse();
    	assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
    	assertThat(response.getContentAsString()).isEqualTo("class successfully deleted");
    }
    
    
}
