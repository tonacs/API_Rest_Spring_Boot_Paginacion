package com.tona.springboot.app.controllers;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

//import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
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


import com.tona.springboot.app.entity.User;
import com.tona.springboot.app.service.UserService;

@RestController
@RequestMapping("/api/users")
public class UserController {

	@Autowired
	private UserService userService;

	// Create a new user
	@PostMapping
	public ResponseEntity<?> create(@RequestBody User user) {
		return ResponseEntity.status(HttpStatus.CREATED).body(userService.save(user));
	}

//	Read an user
	@GetMapping("/{id}")
	public ResponseEntity<?> read(@PathVariable Long id) {
		Optional<User> oUser = userService.findById(id);
		if (!oUser.isPresent()) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(oUser);
	}

//	Update an User
	@PutMapping("/{id}")
	public ResponseEntity<?> update(@RequestBody User userDetails, @PathVariable(value = "id") Long userId) {
		Optional<User> user = userService.findById(userId);

		if (!user.isPresent()) {
			return ResponseEntity.notFound().build();
		}
//		Otra forma, sólo que ésta incluye actualizar el id
//		BeanUtils.copyProperties(userDetails, user.get());

		user.get().setName(userDetails.getName());
		user.get().setSurname(userDetails.getSurname());
		user.get().setEmail(userDetails.getEmail());
		user.get().setEnabled(userDetails.getEnabled());
		return ResponseEntity.status(HttpStatus.CREATED).body(userService.save(user.get()));
	}

//	Delete an user
	@DeleteMapping("/{id}")
	public ResponseEntity<?> delete(@PathVariable(value = "id") Long userId) {

		if (!userService.findById(userId).isPresent()) {
			return ResponseEntity.notFound().build();
		}

		userService.deleteById(userId);
		return ResponseEntity.ok().build();

	}
	
	
//	Read all users
	
	@GetMapping
	public List<User> readAll(){
		List<User> users=StreamSupport
							.stream(userService.findAll().spliterator(), false)
							.collect(Collectors.toList());
		return users;
	}
	
//	Pagination 
	
	@GetMapping
	public ResponseEntity<?> getAll(@RequestParam(name = "page",defaultValue = "0") int page) {
		Pageable pageRequest=PageRequest.of(page, 5);
		//5 es el número de registros a mostrar
		
		
		try {
			return ResponseEntity.status(HttpStatus.OK).body(userService.findAll(pageRequest));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No existen registros");
		}
	}

	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
