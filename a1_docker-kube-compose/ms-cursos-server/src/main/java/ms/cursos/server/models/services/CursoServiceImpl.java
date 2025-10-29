package ms.cursos.server.models.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ms.cursos.server.feignclients.IUsuarioClientFeign;
import ms.cursos.server.models.dao.ICursoDao;
import ms.cursos.server.models.entity.Curso;
import ms.cursos.server.models.entity.CursoUsuario;
import ms.cursos.server.models.pojo.Usuario;


@Service
public class CursoServiceImpl implements ICursoService{

	@Override
	@Transactional(readOnly = true)
	public List<Curso> listar() {
		// TODO Auto-generated method stub
		return (List<Curso>) cursoDao.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public Optional<Curso> porId(Long id) {
		// TODO Auto-generated method stub
		return cursoDao.findById(id);
	}

	@Override
	@Transactional
	public Curso guardar(Curso curso) {
		// TODO Auto-generated method stub
		return cursoDao.save(curso);
	}

	@Override
	@Transactional
	public void eliminar(Long id) {
		// TODO Auto-generated method stub
		cursoDao.deleteById(id);
	}

	
	
	@Override
	@Transactional
	public Optional<Usuario> asinarUsuario(Usuario usuario, Long cursoId) {
		// TODO Auto-generated method stub
		Optional<Curso> o = cursoDao.findById(cursoId);
		if(o.isPresent()) {
			Usuario usuarioMicroserv = usuarioClientFeign.detalle(usuario.getId());
			Curso curso = o.get();
			CursoUsuario cursoUsuario = new CursoUsuario();
			cursoUsuario.setUsuarioId(usuarioMicroserv.getId()); //SE USA usuarioMicroserv YA Q YA ESTÁ VERIFICADO CON EL ISPRESENT().
			curso.addCursoUsuario(cursoUsuario);
			cursoDao.save(curso);
			return Optional.of(usuarioMicroserv);
		}
		return 	Optional.empty();
	}
	
	

	
	@Override
	@Transactional
	public Optional<Usuario> crearUsuario(Usuario usuario, Long cursoId) {
		// TODO Auto-generated method stub
		Optional<Curso> o = cursoDao.findById(cursoId);
		if(o.isPresent()) {
			Usuario usuarioNuevoMicroserv = usuarioClientFeign.crear(usuario);
			Curso curso = o.get();
			CursoUsuario cursoUsuario = new CursoUsuario();
			cursoUsuario.setUsuarioId(usuarioNuevoMicroserv.getId()); //SE USA usuarioMicroserv YA Q YA ESTÁ VERIFICADO CON EL ISPRESENT().
			curso.addCursoUsuario(cursoUsuario);
			cursoDao.save(curso);
			return Optional.of(usuarioNuevoMicroserv);
		}
		return 	Optional.empty();
	}
	
	

	
	@Override
	@Transactional
	public Optional<Usuario> eliminarUsuario(Usuario usuario, Long cursoId) {
		// TODO Auto-generated method stub
		Optional<Curso> o = cursoDao.findById(cursoId);
		if(o.isPresent()) {
			Usuario usuarioMicroserv = usuarioClientFeign.detalle(usuario.getId());
			Curso curso = o.get();
			CursoUsuario cursoUsuario = new CursoUsuario();
			cursoUsuario.setUsuarioId(usuarioMicroserv.getId()); //SE USA usuarioMicroserv YA Q YA ESTÁ VERIFICADO CON EL ISPRESENT().
			curso.removeCursoUsuario(cursoUsuario);
			cursoDao.save(curso);
			return Optional.of(usuarioMicroserv);
		}
		return 	Optional.empty();
	}
	
	
	
	
	//ESTE MÉTODO SE USA EN EL REST  DETALLE, REEMPLAZANDOLO POR EL PORID()
	@Override
	@Transactional(readOnly = true)
	public Optional<Curso> porIdConUsuarios(Long id) {
		// TODO Auto-generated method stub
		Optional<Curso>o = cursoDao.findById(id);
		if(o.isPresent()) {
			Curso curso = o.get();
			//SE VALIDA SI LA LISTA DE LOS USUARIOS EN EL CURSO, ESTÁ VACÍA O NO.
			if(curso.getCursoUsuarios().isEmpty() == false) {
				List<Long>ids = curso.getCursoUsuarios().stream().map(cursoUsuarios -> cursoUsuarios.getUsuarioId()).collect(Collectors.toList());
				List<Usuario>usuarios = usuarioClientFeign.obtenerAlumnosPorCurso(ids);
				curso.setUsuarios(usuarios);
			}
			return Optional.of(curso);
		}
		return Optional.empty();
	}
	
	
	

	//CLASE41
	@Override
	@Transactional
	public void eliminarCursoUsuario(Long id) {
		// TODO Auto-generated method stub
		cursoDao.eliminarCursoUsuarioPorId(id);
	}
	
	
	
	@Autowired
	private ICursoDao cursoDao;
	
	@Autowired
	private IUsuarioClientFeign usuarioClientFeign;

	

	


}
