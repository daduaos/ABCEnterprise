package com.abc.administration;

import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Date;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.abc.administration.controller.UserAdministrarionController;
import com.abc.administration.entities.User;
import com.abc.administration.services.UserAdministrarionService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserAdministrationAbcApplicationTests {

	@InjectMocks
	UserAdministrarionController userAdministrarionController;

	@InjectMocks
	UserAdministrarionService service;

	@Mock
	UserAdministrarionService userAdministrarionService;

	private MockMvc mock;

	private static final String CREATEUSERURL = "/users/create";

	private static final String DELETEUSERURL = "/users/delete/1";

	private static final Integer USERIDCREATED = 1;

	@Before
	public void beforeTest() {
		mock = MockMvcBuilders.standaloneSetup(userAdministrarionController).build();
	}

	@Test
	public void returnHttpCode405DeleteRequest() throws Exception {
		User user = new User();
		user.setName("test name");
		user.setLastName("test last name");
		user.setDni(12345);
		user.setPhone(12345678);
		user.setAddress("address test");
		user.setDateCreated(new Date());
		user.setDateUpdated(new Date());
		user.setId(1);

		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ").serializeNulls().create();
		String messageJson = gson.toJson(user);
		mock.perform(post(DELETEUSERURL).contentType(MediaType.APPLICATION_JSON_UTF8).content(messageJson)
				.accept(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isMethodNotAllowed());
	}

	@Test
	public void isCreatedUser() throws Exception {

		String body = "{\"name\":\"new name\",\"lastName\":\"last name\",\"dni\":\"123123123\"}";
		Mockito.when(userAdministrarionService.createUser(new User())).thenReturn(USERIDCREATED);
		mock.perform(post(CREATEUSERURL).contentType(MediaType.APPLICATION_JSON_UTF8).content(body)
				.accept(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isCreated());
	}

	@Test
	public void badRequestCreatedUser() throws Exception {

		String body = "{\"name\":\"new name\",\"lastName\":\"last name\",\"address\":\"new address\",\"phone\":\"123123\"}";
		Mockito.when(userAdministrarionService.createUser(new User())).thenReturn(USERIDCREATED);
		mock.perform(post(CREATEUSERURL).contentType(MediaType.APPLICATION_JSON_UTF8).content(body)
				.accept(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isBadRequest());
	}

	@Test
	public void requestIsNotValid() {
		User user = new User();
		user.setName("test name");
		user.setLastName("test last name");
		user.setPhone(12345678);
		user.setAddress("address test");
		ResponseEntity<String> response = userAdministrarionController.createUser(user);
		assertEquals("\"Bad Request\"", response.getBody());
	}

	@Test
	public void invalidUpdateId() {
		User user = new User();
		user.setId(-1);
		String response = service.updateUser(user);
		assertEquals("\"User not found\"", response);
	}

}
