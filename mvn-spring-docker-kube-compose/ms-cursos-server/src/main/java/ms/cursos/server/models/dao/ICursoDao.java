package ms.cursos.server.models.dao;


import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import ms.cursos.server.models.entity.Curso;


//CLASE 41: 
//1.-SE BORRA DEL ENTITY CursoUsuario CUANDO EL ATRIBUTO usuarioId SEA IGUAL AL ID 
//@Modifying : CADA VEZ QUE SE HACER UN INSERT, DELETE O UPDATE, HAY QUE PONER ESTA ANOTACIÓN.

public interface ICursoDao extends CrudRepository<Curso,Long> {

	
	//1  //2
	@Modifying 
	@Query("delete from CursoUsuario cu where cu.usuarioId=?1")
	public void eliminarCursoUsuarioPorId(Long id);
	
}
