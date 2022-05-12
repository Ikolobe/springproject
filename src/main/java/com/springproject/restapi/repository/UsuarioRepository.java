package com.springproject.restapi.repository;

import org.springframework.data.repository.CrudRepository;

import com.springproject.restapi.model.Usuario;


public interface UsuarioRepository extends CrudRepository<Usuario, Long> {

}
