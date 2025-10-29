package ms.usuarios.server.models.service;

import java.util.List;
import java.util.Optional;

import ms.usuarios.server.models.entity.Usuario;


//CLASE13
//1.-METODO Q BUSCA POR ID QUE DEVUELVE UN OBJ USUARIO,  PERO SE VA A MANEJAR CON UN OPTIONAL.
	//OPTIONAL ES UN WRAPPER. UNA CLASE QUE ENVUELVE AL OBJETO PARA SABEER SI ESTÁ PRESENTE EN LA CONSULTA. ES UNA FORMA PULENTA DE EVITAR QUE EL OBJ SEA NULO  Y DE ERROR.
//2.-GUARDAR PARA EDITAR E INSERTAR...DEVUELVE UN USUARIO PARA MOSTRARLO.
//CLASE29
//3.-MÉTODO PREPARADO... ESTE ESTÁ EN EL DAO.
//CLASE39
//4.-MÉTODO PARA LISTAR TODOS LOS USUARIOS SEGÚN EL ID.... ESTO PARA LA LISTA DE USUARIOS DEL CURSO. RECIBE UN ARREGLO DE IDS.

public interface IUsuarioService {

	
	List<Usuario>listar();
	//1
	Optional<Usuario>porId(Long id);
	//2
	Usuario guardar(Usuario usuario);
	
	void eliminar(Long id);
	//3
	Optional<Usuario>porEmail(String email);
	
	boolean existePorEmail(String email);
	
	//4
	List<Usuario>listarPorIds(Iterable<Long> ids);
	
	
}
