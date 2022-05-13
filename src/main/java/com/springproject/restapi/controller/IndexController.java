package com.springproject.restapi.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.springproject.restapi.model.Usuario;
import com.springproject.restapi.repository.UsuarioRepository;

@RestController/*Arquitetura REST*/
@RequestMapping(value = "/usuario")
public class IndexController {

	@Autowired
	private UsuarioRepository usuarioRepository;
	
	/*Serviço RESTful*/
	@GetMapping(value = "/{id}", produces = "application/json")
	public ResponseEntity<Usuario> buscarPorId(@PathVariable(value = "id")Long id) {
	
	Usuario usuario = usuarioRepository.findById(id).get();
		
	return new ResponseEntity<Usuario>(usuario, HttpStatus.OK);
	}
	
	@GetMapping(value = "/", produces = "application/json")
	public ResponseEntity<List<Usuario>> init() {
	
	List<Usuario> usuarios = (List<Usuario>) usuarioRepository.findAll();
		
	return new ResponseEntity<List<Usuario>>(usuarios, HttpStatus.OK);
	}
	
	@PostMapping(value= "/" , produces = "application/json")
	public ResponseEntity<Usuario> cadastrar(@RequestBody Usuario usuario){
		
		Usuario usuarioSalvo = usuarioRepository.save(usuario);
		
		return new ResponseEntity<Usuario>(usuarioSalvo , HttpStatus.OK);
			
	}
	
	@PutMapping(value = "/", produces = "application/json")
	public ResponseEntity<Usuario> atualizar(@RequestBody Usuario usuario){
		
		Usuario usuarioAtualizado = usuarioRepository.save(usuario);
		
		return new ResponseEntity<Usuario>(usuarioAtualizado, HttpStatus.OK);
			
	}
	
	@DeleteMapping(value = "/{id}", produces = "application/json")
	public ResponseEntity<String> deletar(@PathVariable Long id) {
		
		usuarioRepository.deleteById(id);
		
		return new ResponseEntity<String>("Usuário Deletado", HttpStatus.OK);
		
	}
}
