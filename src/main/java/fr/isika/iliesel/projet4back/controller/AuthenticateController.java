package fr.isika.iliesel.projet4back.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import fr.isika.iliesel.projet4back.config.JwtUtil;
import fr.isika.iliesel.projet4back.helper.UserNotFoundException;
import fr.isika.iliesel.projet4back.model.JwtRequest;
import fr.isika.iliesel.projet4back.model.JwtResponse;
import fr.isika.iliesel.projet4back.model.User;
import fr.isika.iliesel.projet4back.service.impl.UserDetailsServiceImpl;

@RestController
@CrossOrigin("*")
public class AuthenticateController {

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private UserDetailsServiceImpl userDetailsServiceImpl;

	@Autowired
	private JwtUtil jwtUtil;

	// Génération d'un token JWT pour un utilisateur authentifié
	@PostMapping("/generate-token")
	public ResponseEntity<?> generateToken(@RequestBody JwtRequest jwtRequest) throws Exception {

		try {
			// Tentative d'authentification de l'utilisateur
			authenticate(jwtRequest.getUsername(), jwtRequest.getPassword());
		} catch (UserNotFoundException e) {
			e.printStackTrace();
			throw new Exception("User not found");
		}

		// Si l'utilisateur est authentifié, génération du token JWT
		UserDetails userDetails = this.userDetailsServiceImpl.loadUserByUsername(jwtRequest.getUsername());
		String token = this.jwtUtil.generateToken(userDetails);
		return ResponseEntity.ok(new JwtResponse(token));
	}

	// Authentification d'un utilisateur
	private void authenticate(String username, String password) throws Exception {

		try {

			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));

		} catch (DisabledException e) {
			throw new Exception("USER DISABLED " + e.getMessage());
		} catch (BadCredentialsException e) {
			throw new Exception("Invalid Credentials " + e.getMessage());
		}

	}

	// Récupération des détails de l'utilisateur courant à partir de l'objet Principal
	@GetMapping("/current-user")
	public User getCurrentUser(Principal principal) {

		return (User) this.userDetailsServiceImpl.loadUserByUsername(principal.getName());

	}

}
