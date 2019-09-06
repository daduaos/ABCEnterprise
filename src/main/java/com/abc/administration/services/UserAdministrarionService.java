package com.abc.administration.services;

import java.util.Date;
import java.util.Optional;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.abc.administration.entities.User;
import com.abc.administration.persistence.UserRepository;

@Service
public class UserAdministrarionService {

	@Autowired
	private UserRepository userRepository;

	Logger logger = LogManager.getLogger(UserAdministrarionService.class);

	public int createUser(User user) {

		User response = null;
		try {
			logger.info("{}", user.toString());
			if (user.getDateCreated() == null) {
				user.setDateCreated(new Date());
			}
			response = userRepository.save(user);
			logger.info("Object saved:{}", response.toString());
			return response.getId();
		} catch (Exception e) {
			logger.error("Exception:{}", e);
			return 0;
		}
	}

	public String updateUser(User user) {
		try {
			Optional<User> userFound = userRepository.findById(user.getId());
			if (userFound.isPresent()) {
				logger.info("{}", userFound.get().toString());

				userFound.get().setDateUpdated(new Date());
				if (user.getName() != null) {
					userFound.get().setName(user.getName());
				}
				if (user.getLastName() != null) {
					userFound.get().setLastName(user.getLastName());
				}
				if (user.getDni() != 0) {
					userFound.get().setDni(user.getDni());
				}
				if (user.getAddress() != null) {
					userFound.get().setAddress(user.getAddress());
				}
				if (user.getPhone() != 0) {
					userFound.get().setPhone(user.getPhone());
				}
				userRepository.save(userFound.get());

			} else {
				logger.info("User not found");
				return "\"User not found\"";
			}
		} catch (Exception e) {
			logger.error("Exception:{}", e);
			return "\"User not found\"";
		}
		return "\"Updated User by id\"";
	}

	public void deleteUser(int id) {
		try {
			userRepository.deleteById(id);
		} catch (Exception e) {
			logger.error("Exception:{}", e);
		}
	}

	public User getUserById(int id) {

		Optional<User> userFound = null;
		try {
			userFound = userRepository.findById(id);
			logger.info("User found: {}", userFound.get().toString());
			return userFound.get();
		} catch (Exception e) {
			logger.error("Exception:{}", e);
			return null;
		}
	}

	public Iterable<User> getUsers() {

		Iterable<User> userFound = null;
		try {
			userFound = userRepository.findAll();
			logger.info("User find all");
			return userFound;

		} catch (Exception e) {
			logger.error("Exception:{}", e);
			return null;
		}
	}
}
