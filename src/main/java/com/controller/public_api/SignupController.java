package com.controller.public_api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bean.LoginBean;
import com.bean.ResponseBean;
import com.bean.RoleBean;
import com.bean.UserBean;
import com.repository.RoleRepository;
import com.repository.UserRepository;
import com.service.TokenService;

@RestController
@RequestMapping("/public")
public class SignupController {
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	RoleRepository roleRepository;
	
	@Autowired
	BCryptPasswordEncoder bCryptPassword;
	
	@Autowired
	TokenService tokenService;
	

	@PostMapping("/signup")
	public ResponseEntity<?> signup(@RequestBody UserBean users){
		
		UserBean dbUser = userRepository.findByEmail(users.getEmail());
		ResponseBean<UserBean> res = new ResponseBean<>();
		if (dbUser == null) {

			RoleBean role = roleRepository.findByRoleName("user");
			users.setRole(role);
			String encPassword = bCryptPassword.encode(users.getPassword());
			users.setPassword(encPassword);
			userRepository.save(users);
		
			res.setData(users);
			res.setMessage("successfully signup");
			System.out.println(users.getFirstName());
			System.out.println(users.getEmail());
			System.out.println(users.getGender());
			return ResponseEntity.ok(res);
		} else {
			res.setMessage("duplicate email not valid");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(res);
		}		
	}
	

	@PostMapping("/login")
	public ResponseEntity<?> authenticate(@RequestBody LoginBean login) {
		UserBean user = userRepository.findByEmail(login.getEmail());

		System.out.println("user in login api ==="+user.getFirstName()+ "role -=="+user.getRole());
		System.out.println(login.getEmail());
		System.out.println("login api called...");
		
		if (user == null || !bCryptPassword.matches(login.getPassword(), user.getPassword())) {
			ResponseBean<LoginBean> res = new ResponseBean<>();
			res.setData(login);
			res.setMessage("Invalid Credentials");
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(res);
		} else {
			String authToken = tokenService.generateToken(16);

			user.setAuthToken(authToken);
			userRepository.save(user);
					
			ResponseBean<UserBean> res = new ResponseBean<>();
			res.setData(user);
			res.setMessage("Successfully Login");
			return ResponseEntity.ok(res);
		}
	}
}