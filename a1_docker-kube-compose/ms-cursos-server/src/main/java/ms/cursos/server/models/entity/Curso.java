package ms.cursos.server.models.entity;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.NotBlank;
import ms.cursos.server.models.pojo.Usuario;



@Entity
@Table(name="cursos")
public class Curso {
	
	public Curso() {
		
		cursoUsuarios = new ArrayList<>();
		usuarios = new ArrayList<>();
	 
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	
	public List<CursoUsuario> getCursoUsuarios() {
		return cursoUsuarios;
	}

	public void setCursoUsuarios(List<CursoUsuario> cursoUsuarios) {
		this.cursoUsuarios = cursoUsuarios;
	}

	public void addCursoUsuario(CursoUsuario cursoUsuario) {
		cursoUsuarios.add(cursoUsuario);
	}
	public void removeCursoUsuario(CursoUsuario cursoUsuario) {
		cursoUsuarios.remove(cursoUsuario);
	}
	
	public List<Usuario> getUsuarios() {
		return usuarios;
	}
	public void setUsuarios(List<Usuario> usuarios) {
		this.usuarios = usuarios;
	}
	


	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	//@NotEmpty
	@NotBlank
	private String nombre;
	
	@OneToMany(cascade=CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name="curso_id")									//curso_id ES LA KEY FORANEA DE LA RELACIOÓN Y VA A APARECER EN LA TABLA DE CURSOUSUARIO. 
	private List<CursoUsuario> cursoUsuarios;
	
	@Transient  //ESTE ATRIBUTO NO ESTÁ MAPEADO A TABLAS. SE VA USAR PARA OBTENER LOS DATOS COMPLETOS DEL USUARIO DESDE EL OTRO MICROSERVICIO.
	private List<Usuario>usuarios;

}
