package ms.usuarios.server.controller;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import jakarta.validation.Valid;
import ms.usuarios.server.models.entity.Usuario;
import ms.usuarios.server.models.service.IUsuarioService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class UsuarioController {

	//CLASE 136 --------------------------
	@Autowired 
	private ApplicationContext context;
	//PARA SIMULAR UN QUIEBRE DE LA APP
	@GetMapping("/crash")
	public void crash() {
		((ConfigurableApplicationContext)context).close();
	}
	//-------------------------- CLASE 136 
	
	
	@GetMapping("/listar")
	public Map<String,List<Usuario>>listar(){
		return Collections.singletonMap("usuarios", usuarioService.listar());
	}
	
	
	@GetMapping("/detalle/{id}")
	public ResponseEntity<?> detalle (@PathVariable Long id) {
		Optional<Usuario>usuarioOptional = usuarioService.porId(id);
		if(usuarioOptional.isPresent()) {
			//return ResponseEntity.ok(usuarioOptional.get());
			return ResponseEntity.ok().body(usuarioOptional.get());
		}
		return ResponseEntity.notFound().build();
	}
	
	
	@PostMapping("/crear")
	public ResponseEntity<?> crear(@Valid @RequestBody Usuario usuario, BindingResult result){
		
		if(result.hasErrors()) {
			Map<String,String>errores = new HashMap<>();
			result.getFieldErrors().forEach(err->{
				errores.put(err.getField(), "El campo " + err.getField() + " " + err.getDefaultMessage());
			});
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errores);
		}
		//SE MOVIÓ DEBAJO DEL IF DE ARRIBA, PARA Q NO DE ERROR DE NULL POINTER EXCEPTION SI SE MANDA UN ARRAY VACÍO.
		//if(!usuario.getEmail().isEmpty() && usuarioService.porEmail(usuario.getEmail()).isPresent()) {
				if(!usuario.getEmail().isEmpty() && usuarioService.existePorEmail(usuario.getEmail())) {
					//ES COMO UN MAP PERO SOLO ALMACENA UNA KEY Y UN VALUE
					return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Collections.singletonMap("mensaje", "Ya existe un usuario con ese email"));
				}
		return ResponseEntity.status(HttpStatus.CREATED).body(usuarioService.guardar(usuario));
	}
	
	
	@PutMapping("/editar/{id}")
	public ResponseEntity<?>editar(@Valid @RequestBody Usuario usuario, BindingResult result, @PathVariable Long id){
		if(result.hasErrors()) {
			return errores(result);
		}
		Optional<Usuario> o = usuarioService.porId(id); 
		if(o.isPresent()){
			Usuario usuarioDb = o.get();
			//
			if(!usuario.getEmail().isEmpty() && !usuario.getEmail().equalsIgnoreCase(usuarioDb.getEmail()) && usuarioService.porEmail(usuario.getEmail()).isPresent()) {
				//ES COMO UN MAP PERO SOLO ALMACENA UNA KEY Y UN VALUE
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Collections.singletonMap("mensaje", "Ya existe un usuario con ese email"));
			}
			//
			usuarioDb.setNombre(usuario.getNombre());
			usuarioDb.setEmail(usuario.getEmail());
			usuarioDb.setPassword(usuario.getPassword());
			return ResponseEntity.status(HttpStatus.CREATED).body(usuarioService.guardar(usuarioDb));
		}
		return ResponseEntity.notFound().build();
	}
	
	
	@DeleteMapping("/eliminar/{id}")
	public ResponseEntity<?>eliminar(@PathVariable Long id){
		Optional<Usuario> o = usuarioService.porId(id); 
		if(o.isPresent()){
			usuarioService.eliminar(id);
			return ResponseEntity.noContent().build();
		}
		return ResponseEntity.notFound().build();
	}
	
	
	//CLASE39  //ESTE MÉTODO SERÁ USADO POR EL MICROSERVICIO CURSO.
	//SE ENVÍA LA LISTA COMO UN PARAMETRO DEL REQUEST Y SE RECIVE CON @REQUESPARAM. SI FUESE POST , SERÍA REQUESTBODY. 
	//EN POSTMAN EN PARAMS, PONE LA KEY ids Y SE LE PASAN LOS IDS DE ESTA FORMA  1,3,4,5
	@GetMapping("/usuarios-por-curso")
	public ResponseEntity<?>obtenerAlumnosPorCurso(@RequestParam List<Long>ids){
		return ResponseEntity.status(HttpStatus.OK).body(usuarioService.listarPorIds(ids));
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
	private IUsuarioService usuarioService;
}
