package fr.isika.iliesel.projet4back.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import fr.isika.iliesel.projet4back.service.impl.UserDetailsServiceImpl;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class MySecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private JwtAuthenticationEntryPoint unauthorizedHandler;

	@Autowired
	private JwtAuthenticationFilter jwtAuthenticationFilter;

	@Autowired
	private UserDetailsServiceImpl userDetailsServiceImpl;

	@Override
	@Bean
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		// Configuration du service UserDetailsService et du mot de passe encoder pour l'authentification
		auth.userDetailsService(this.userDetailsServiceImpl).passwordEncoder(passwordEncoder());
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		// Configuration de la politique de sécurité pour les requêtes HTTP
		http.csrf().disable().cors().disable().authorizeRequests()
				// Autorisation des requêtes pour certains endpoints
				.antMatchers("/generate-token", "/user/", "/user/test", "/**").permitAll()
				.antMatchers(HttpMethod.OPTIONS).permitAll()
				// Toutes les autres requêtes nécessitent une authentification
				.anyRequest().authenticated()
				// Gestion des exceptions lorsqu'une demande non autorisée est reçue
				.and().exceptionHandling().authenticationEntryPoint(unauthorizedHandler)
				// Désactivation de la gestion de session
				.and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

		// Ajout du filtre d'authentification JWT pour toutes les demandes HTTP
		http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
	}
}
