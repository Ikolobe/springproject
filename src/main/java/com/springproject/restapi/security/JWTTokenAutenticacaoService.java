package com.springproject.restapi.security;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.springproject.restapi.ApplicationContextLoad;
import com.springproject.restapi.model.Usuario;
import com.springproject.restapi.repository.UsuarioRepository;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
@Component
public class JWTTokenAutenticacaoService {
	
	/*Tempo de validade do Token 2 dias*/
	private static final long EXPIRATION_TIME = 172800000;
	
	/*Uma senha unica para compor a autenticacao e ajudar na segurança*/
	private static final String SECRET = "SenhaExtremamenteSecreta";

	/*Prefixo padão de Token*/
	private static final String TOKEN_PREFIX = 	"Bearer";

	/**/
	private static final String HEADER_STRING = "Authorization";
	
	
	/*Gerando token de autenticacao e adicionando ao cabeçalho e respota Http*/
	public void addAuthentication(HttpServletResponse response, String username) throws Exception {
		
		/*Montagem do Token*/
		String JWT = Jwts.builder().setSubject(username)
				.setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
				.signWith(SignatureAlgorithm.HS512, SECRET).compact();
		
		/*Junta o token com o prefixo*/
		String token = TOKEN_PREFIX + " " + JWT;
		
		/*adiciona no cabeçalho http*/
		response.addHeader(HEADER_STRING, token);
		
		/*Escreve token como resposta no corpo http*/
		response.getWriter().write("{\"Authorization\": \""+token+"\"}");
		
	}
	
	/*Retorn o usuário validado com o token ou caso não seja valido retorna null*/
	public Authentication getAuthentication(HttpServletRequest request) {
		
		/*Pega o token enviado no cabeçalho http*/
		String token = request.getHeader(HEADER_STRING);
		
		if (token != null) {
			
			/*Faz a validação do token do usuário na requisição*/
			String user = Jwts.parser().setSigningKey(SECRET)
					.parseClaimsJws(token.replace(TOKEN_PREFIX, ""))
					.getBody().getSubject();
			
			if (user != null) {
				
				Usuario usuario = ApplicationContextLoad.getApplicationContext()
						.getBean(UsuarioRepository.class).findUserByLogin(user);
				
				/*Retorna o usuario logado*/
				if (usuario != null) {
					
					return new UsernamePasswordAuthenticationToken(
							usuario.getLogin(),
							usuario.getSenha(),
							usuario.getAuthorities());
					
				}
				
			}
			
		}
			return null;
		
	}	
	
	
	
}
