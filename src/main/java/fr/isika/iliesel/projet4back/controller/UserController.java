package fr.isika.iliesel.projet4back.controller;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fr.isika.iliesel.projet4back.helper.UserFoundException;
import fr.isika.iliesel.projet4back.model.Match;
import fr.isika.iliesel.projet4back.model.Role;
import fr.isika.iliesel.projet4back.model.User;
import fr.isika.iliesel.projet4back.model.UserRole;
import fr.isika.iliesel.projet4back.service.UserService;

@RestController
@RequestMapping("/user")
@CrossOrigin("*")
public class UserController {

	@Autowired
	private UserService userService;

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	//Endpoint de test
	@GetMapping("/test")
	public String test() {
		return "Welcome to backend api of TotoFoot";
	}

	//Endpoint pour créer un utilisateur
	@PostMapping("/")
	public User createUser(@RequestBody User user) throws Exception {

		user.setProfile("default.png");

		// codage du mot de passe avec bcryptpasswordencoder
		user.setPassword(this.bCryptPasswordEncoder.encode(user.getPassword()));

		Set<UserRole> roles = new HashSet<>();

		Role role = new Role();
		role.setRoleId(45L);
		role.setRoleName("NORMAL");

		UserRole userRole = new UserRole();
		userRole.setUser(user);
		userRole.setRole(role);

		roles.add(userRole);

		List<Match> matchsFav = new ArrayList<Match>();
		user.setMatchsFav(matchsFav);

		return this.userService.createUser(user, roles);
	}

	//Récupérer un utilisateur spécifique
	@GetMapping("/{username}")
	public User getUser(@PathVariable("username") String username) {
		return this.userService.getUser(username);
	}

	//Récupérer tous les utilisateurs
	@GetMapping("/users")
	public List<User> getUsers() {
		return this.userService.getAllUser();
	}

	//Supprimer un utilisateur
	@DeleteMapping("/delete/{userId}")
	public void deleteUser(@PathVariable("userId") Long userId) {
		this.userService.deleteUser(userId);
	}

	//Mettre à jour un utilisateur
	@PutMapping("/update")
	public User updateUser(User user) {
		return this.userService.updateUser(user);
	}

	//Bannir un utilisateur
	@PostMapping("/ban/{username}")
	public void banUser(@PathVariable("username") String username) {
		User currentUser = this.userService.getUser(username);
		currentUser.setEnabled(false);
		this.userService.updateUser(currentUser);
	}

	//Débannir un utilisateur
	@PostMapping("/deban/{username}")
	public void debanUser(@PathVariable("username") String username) {
		User currentUser = this.userService.getUser(username);
		currentUser.setEnabled(true);
		this.userService.updateUser(currentUser);
	}

	// Gestionnaire d'exception
	@ExceptionHandler(UserFoundException.class)
	public ResponseEntity<?> exceptionHandler(UserFoundException ex) {
		return ResponseEntity.ok(ex.getMessage());
	}
}
