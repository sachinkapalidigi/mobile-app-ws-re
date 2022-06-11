package com.svr.app.ws.ui.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.svr.app.ws.ui.model.request.UpdateUserDetailsRequestModel;
import com.svr.app.ws.ui.model.request.UserDetailsRequestModel;
import com.svr.app.ws.ui.model.response.UserRest;

@RestController
@RequestMapping("users") // http://localhost:8080/users
public class UserController {
	
	Map<String, UserRest> users;
	
	@GetMapping
	public String getUsers(@RequestParam(value="page", defaultValue = "1") int page, 
			@RequestParam(value="limit", defaultValue="50") int limit,
			// if required is false for primitive data type and there is no default value - leads to error since it cannot be null
			@RequestParam(value="sort", required = false) String sort) {
		return "get user method with page = "+page + " limit = "+ limit +  " sort = " + sort;
	}
	
	@GetMapping(path="/{userId}", produces = {
			MediaType.APPLICATION_XML_VALUE, 
			MediaType.APPLICATION_JSON_VALUE})
	
	public ResponseEntity<UserRest> getUser(@PathVariable String userId) {
		UserRest returnValue = new UserRest();
		returnValue.setEmail("email");
		returnValue.setFirstName("fname");
		returnValue.setLastName("lname");
		
		return new ResponseEntity<UserRest>(returnValue ,HttpStatus.OK);
	}
	
	@PostMapping(consumes = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<UserRest> createUser(@Valid @RequestBody UserDetailsRequestModel userDetails) {
		
		UserRest returnValue = new UserRest();
		BeanUtils.copyProperties(userDetails, returnValue);
		
		if(users == null) users = new HashMap<String, UserRest>();
		
		String uuid = UUID.randomUUID().toString();
		returnValue.setUserId(uuid);
		users.put(uuid, returnValue);
		
		return new ResponseEntity<UserRest>(returnValue, HttpStatus.CREATED);
	}
	
	@PutMapping(path="/{userId}")
	public ResponseEntity<UserRest> updateuser(@PathVariable String userId,@Valid @RequestBody UpdateUserDetailsRequestModel userDetails) {
		// update first name and last name only
		if(!users.containsKey(userId)) return new ResponseEntity<UserRest>(HttpStatus.BAD_REQUEST);
		UserRest storedUserDetails = users.get(userId);
		storedUserDetails.setFirstName(userDetails.getFirstName());
		storedUserDetails.setLastName(userDetails.getLastName());
		
		return new ResponseEntity<UserRest>(storedUserDetails, HttpStatus.OK);
	}
	
	@DeleteMapping(path="/{userId}")
	public ResponseEntity<Void> deleteUser(@PathVariable String userId) {
		if(!users.containsKey(userId)) return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
		users.remove(userId);
		return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
	}
}
