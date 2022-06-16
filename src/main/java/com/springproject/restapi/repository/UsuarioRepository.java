package com.springproject.restapi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.springproject.restapi.model.Usuario;


public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

	
		@Query("select u from Usuario u where u.login = ?1")
		Usuario findUserByLogin(String login);
	
		@Query("select u from Usuario u where u.nome LIKE %?1%")
		List<Usuario> findUserByNome(String nome);
}
