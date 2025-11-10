package ms.cursos.server.controller;


import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import jakarta.validation.Valid;
import ms.cursos.server.models.entity.Curso;
import ms.cursos.server.models.pojo.Usuario;
import ms.cursos.server.models.services.ICursoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import feign.FeignException;



@RestController
public class CursoController {

	@GetMapping("/listar")
	public ResponseEntity<List<Curso>>listar(){
		return ResponseEntity.status(HttpStatus.OK).body(cursoService.listar());
	}
	
	
	@GetMapping("/detalle/{id}")
	public ResponseEntity<?> detalle (@PathVariable Long id) {
		Optional<Curso>cursoOptional = cursoService.porIdConUsuarios(id);//cursoService.porId(id);
		if(cursoOptional.isPresent()) {
			return ResponseEntity.ok(cursoOptional.get());
		}
		return ResponseEntity.notFound().build();
	}
	
	
	@PostMapping("/crear")
	public ResponseEntity<?> crear(@Valid @RequestBody Curso curso, BindingResult result){
		if(result.hasErrors()) {
			return errores(result);
		}
		return ResponseEntity.status(HttpStatus.CREATED).body(cursoService.guardar(curso));
	}
	
	@PutMapping("/editar/{id}")
	public ResponseEntity<?>editar(@Valid @RequestBody Curso curso, BindingResult result, @PathVariable Long id){
		if(result.hasErrors()) {
			return errores(result);
		}
		Optional<Curso> o = cursoService.porId(id);
		if(o.isPresent()){
			Curso cursoDb = o.get();
			cursoDb.setNombre(curso.getNombre());
			return ResponseEntity.status(HttpStatus.CREATED).body(cursoService.guardar(cursoDb));
		}
		return ResponseEntity.notFound().build();
	}
	
	@DeleteMapping("/eliminar/{id}")
	public ResponseEntity<?>eliminar(@PathVariable Long id){
		Optional<Curso> o = cursoService.porId(id);
		if(o.isPresent()){
			cursoService.eliminar(id);
			return ResponseEntity.noContent().build();
		}
		return ResponseEntity.notFound().build();
	}
	
	
	
	@PutMapping("/asignar-usuario/{cursoId}")
	public ResponseEntity<?>asignarUsuario(@RequestBody Usuario usuario, @PathVariable Long cursoId){
		Optional<Usuario>o = null;
		try {
			o = cursoService.asinarUsuario(usuario, cursoId);
		}catch(FeignException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Collections.singletonMap("mensaje","No existe el usuario por el id o error en la comunicación: " + e.getMessage())); 
		}
		
		if(o.isPresent()) {
			return ResponseEntity.status(HttpStatus.CREATED).body(o.get());
		}else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
	}
	
	@PostMapping("/crear-usuario/{cursoId}")
	public ResponseEntity<?>crearUsuario(@RequestBody Usuario usuario, @PathVariable Long cursoId){
		Optional<Usuario>o = null;
		try {
			o = cursoService.crearUsuario(usuario, cursoId);
		}catch(FeignException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Collections.singletonMap("mensaje","No se pudo crear el usuario o error en la comunicación: " + e.getMessage())); 
		}
		
		if(o.isPresent()) {
			return ResponseEntity.status(HttpStatus.CREATED).body(o.get());
		}else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
	}

	//des asigna un alumno de un curso
	@DeleteMapping("/eliminar-usuario/{cursoId}")
	public ResponseEntity<?>eliminarUsuario(@RequestBody Usuario usuario, @PathVariable Long cursoId){
		Optional<Usuario>o = null;
		try {
			o = cursoService.eliminarUsuario(usuario, cursoId);
		}catch(FeignException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Collections.singletonMap("mensaje","No existe el usuario por el id o error en la comunicación: " + e.getMessage())); 
		}
		
		if(o.isPresent()) {
			return ResponseEntity.status(HttpStatus.OK).body(o.get());
		}else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
	}
	
	
	
	//CLASE41 - desasigna a un usuario de un curso. se llama cuando se elimina a un usuario en el ms-usuarios
	@DeleteMapping("/eliminar-curso-usuario/{id}")
	public ResponseEntity<?>eliminarCursoUsuario(@PathVariable Long id){
		cursoService.eliminarCursoUsuario(id);
		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
		
	}
	

	
	//----------
	private ResponseEntity<?>errores(BindingResult result){
		Map<String,String>errores = new HashMap<>();
		result.getFieldErrors().forEach(err->{
			errores.put(err.getField(), "El campo " + err.getField() + " " + err.getDefaultMessage());
		});
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errores);
	}
	//----------
	
	@Autowired 
	private ICursoService cursoService;
}
