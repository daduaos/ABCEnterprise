package com.abc.administration.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.abc.administration.entities.User;
import com.abc.administration.services.UserAdministrarionService;

@RestController
@RequestMapping(path = "/users")
public class UserAdministrarionController {

	@Autowired
	private UserAdministrarionService userAdministrarionService;

	@PostMapping(path = "/create", consumes = "application/json", produces = "application/json")
	public ResponseEntity<String> createUser(@RequestBody User body) {
		if (validateRequest(body)) {
			return new ResponseEntity<String>("\"Bad Request\"", HttpStatus.BAD_REQUEST);
		}
		int response = userAdministrarionService.createUser(body);
		return new ResponseEntity<String>("\"Created id:" + response + "\"", HttpStatus.CREATED);

	}

	private boolean validateRequest(User body) {
		return body.getName() == null || body.getName().isEmpty() || body.getLastName() == null
				|| body.getLastName().isEmpty() || body.getDni() == 0;
	}

	@PostMapping(path = "/update", consumes = "application/json", produces = "application/json")
	public ResponseEntity<String> updateUserById(@RequestBody User body) {
		if (body.getId() == 0) {
			return new ResponseEntity<String>("\"Bad Request\"", HttpStatus.BAD_REQUEST);
		}
		String response = userAdministrarionService.updateUser(body);
		return new ResponseEntity<String>(response, HttpStatus.CREATED);

	}

	@GetMapping(path = "/delete/{id}")
	public ResponseEntity<String> deleteUser(@PathVariable int id) {
		userAdministrarionService.deleteUser(id);
		return new ResponseEntity<String>("\"Deleted\"", HttpStatus.OK);

	}

	@PostMapping(path = "getUser")
	public User getUser(@RequestBody User body) {
		if (body.getId() == 0) {
			return null;
		}
		return userAdministrarionService.getUserById(body.getId());
	}

	@GetMapping(path = "getAll")
	public List<User> getAllUsers() {
		return (List<User>) userAdministrarionService.getUsers();
	}

}
