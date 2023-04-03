package fr.isika.iliesel.projet4back.config;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response,
						 AuthenticationException authException) throws IOException, ServletException {
		response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized : Server");
		// La méthode commence() est appelée pour toutes les demandes non authentifiées
		// pour lesquelles Spring Security requiert une authentification avant de procéder.
		// Dans ce cas, elle envoie une erreur "Unauthorized" avec un code d'état 401.
		// Cela indique que l'utilisateur n'est pas autorisé à accéder à la ressource demandée.
	}

}
