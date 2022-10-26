package com.wilsonbeda.workshopmongo.resources;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.wilsonbeda.workshopmongo.domain.User;
import com.wilsonbeda.workshopmongo.dto.UserDTO;
import com.wilsonbeda.workshopmongo.services.UserService;

@RestController
@RequestMapping(value="/users")
public class UserResource {

	
	@Autowired
	private UserService service;
	
	@RequestMapping(method=RequestMethod.GET) // Aka @GetMapping
	public ResponseEntity<List<User>> findAll() {
		List<User> list = service.findAll();
		List<UserDTO> listDto = list.stream().map(x -> new UserDTO(x)).collect(Collectors.toList());
		return ResponseEntity.ok().body(list);
	}
	
	@RequestMapping(value ="/{id}", method=RequestMethod.GET) // Aka @GetMapping
	public ResponseEntity<UserDTO> findById( @PathVariable String id) {
		User obj = service.findById(id);
		return ResponseEntity.ok().body(new UserDTO(obj));
	}
	
	@RequestMapping(method=RequestMethod.POST) // Aka @PostMapping
	public ResponseEntity<Void> insert(@RequestBody UserDTO objDto) {
		User obj = service.fromDTO(objDto);
		obj = service.insert(obj);
		// boa prática
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getId()).toUri();
		return ResponseEntity.created(uri).build();
	}
	
	@RequestMapping(value ="/{id}", method=RequestMethod.DELETE) // Aka @GetMapping
	public ResponseEntity<Void> delete ( @PathVariable String id) {
		service.delete(id);
		return ResponseEntity.noContent().build();
	}
	
}
