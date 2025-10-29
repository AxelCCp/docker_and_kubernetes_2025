package ms.usuarios.server.models.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import ms.usuarios.server.models.entity.Usuario;


//CLASE29
//1.-PARA VALIDAR SI EXISTE UN USUARIO POR EL CORREO
	//1.1.-POR NOMBRE DE MÉTODO.
	//CLASE30
	//1.2.- '?1'  SE REEMPLAZA POR String 'email'.
	//1.3.-AQUÍ VALIDA SI EXISTE EL EMAIL Y DEVUELVE TRUE O FALSE

public interface IUsuarioDao extends CrudRepository <Usuario, Long> {

	//1.1 
	Optional<Usuario>findByEmail(String email);
	//1.2
	@Query("select u from Usuario u where u.email=?1")
	Optional<Usuario>encontrarPorEmail(String email);
	//1.3
	boolean existsByEmail(String email);
	
}
