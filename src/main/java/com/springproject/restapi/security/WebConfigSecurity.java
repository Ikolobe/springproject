package com.springproject.restapi.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.springproject.restapi.service.ImplementacaoUserDetailsService;

@Configuration
@EnableWebSecurity
/*Mapeia URL, enderecos, autoriza ou bloqueia acesso a URL*/
public class WebConfigSecurity extends WebSecurityConfigurerAdapter{

	@Autowired
	private ImplementacaoUserDetailsService implementacaoUserDetailsService;
	
	/*Configura as solicitações de acesso por Http*/
	@Override
		protected void configure(HttpSecurity http) throws Exception {
		
		/*Ativando a porteção contra usuário que não estão validados por token*/
		http.csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
		.disable().authorizeRequests().antMatchers("/").permitAll()
		
		/*UTL de Logout - Redireciona após o user deslogar do sistema*/
		.anyRequest().authenticated().and().logout().logoutSuccessUrl("/index")
		
		/*Mapeia URL de logout e invalida o usuário*/
		.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
	
		/*Filtra requisições de login para autenticação*/
		.and().addFilterBefore(new JWTLoginFilter("/login", authenticationManager()),UsernamePasswordAuthenticationFilter.class)
		/*Filtra demais requisições para verificar a presença do TOKEN*/
		.addFilterBefore(new JWTApiAutenticacaoFilter(), UsernamePasswordAuthenticationFilter.class);
	}
	
	
	@Override
		protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		/*Service que irá consultar o usuário no banco de dados*/
		auth.userDetailsService(implementacaoUserDetailsService)
		.passwordEncoder(new BCryptPasswordEncoder());
		
		}
	
}
