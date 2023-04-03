package fr.isika.iliesel.projet4back.config;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import fr.isika.iliesel.projet4back.service.impl.UserDetailsServiceImpl;
import io.jsonwebtoken.ExpiredJwtException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

	private static final Logger LOGGER = org.slf4j.LoggerFactory.getLogger(JwtAuthenticationFilter.class);

	@Autowired
	private UserDetailsServiceImpl userDetailsServiceImpl;

	@Autowired
	private JwtUtil jwtUtil;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		final String requestTokenHeader = request.getHeader("Authorization");

		LOGGER.info(requestTokenHeader);
		String username = null;
		String jwtToken = null;

		if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")) {
			// Si le header Authorization contient le mot clé "Bearer"
			jwtToken = requestTokenHeader.substring(7);

			try {
				// Extraction du nom d'utilisateur du token JWT
				username = this.jwtUtil.extractUsername(jwtToken);

			} catch (ExpiredJwtException e) {
				// Si le token JWT a expiré, une exception est levée
				e.printStackTrace();
				LOGGER.warn("jwt token has expired");

			} catch (Exception e) {
				// Si une autre exception est levée, cela signifie que le token JWT n'est pas valide
				e.printStackTrace();
				LOGGER.error("error");
			}

		} else {
			// Si le header Authorization ne contient pas le mot clé "Bearer", le token est considéré comme invalide
			LOGGER.error("Invalid token, not start with bearer string");
		}

		// Si le nom d'utilisateur est valide et qu'il n'y a pas encore d'authentification en cours
		if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {

			final UserDetails userDetails = this.userDetailsServiceImpl.loadUserByUsername(username);
			if (this.jwtUtil.validateToken(jwtToken, userDetails)) {
				// Si le token JWT est valide, l'utilisateur est authentifié
				UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
						userDetails, null, userDetails.getAuthorities());
				usernamePasswordAuthenticationToken
						.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

				SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
			}
		} else {
			LOGGER.error("Token is not valid");
		}

		filterChain.doFilter(request, response);

	}

}
