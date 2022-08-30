package com.controller.private_api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bean.RoleBean;
import com.repository.RoleRepository;

@RestController
@RequestMapping("/private")
public class RoleController {

	@Autowired
	RoleRepository roleRepository;

	@PostMapping("/addrole")
	public ResponseEntity<?> addRole(@RequestBody RoleBean role) {
		roleRepository.save(role);
		return ResponseEntity.ok(role);
	}
	
	@GetMapping("/getallroles")
	public ResponseEntity<?> getAllRoles(){
		
		return ResponseEntity.ok(roleRepository.findAll());
	}

}
