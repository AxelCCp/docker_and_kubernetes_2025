package ms.usuarios.server.models.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ms.usuarios.server.feignclients.ICursoFeignClient;
import ms.usuarios.server.models.dao.IUsuarioDao;
import ms.usuarios.server.models.entity.Usuario;



//CLASE 24
//1.-METODO PREPARADO DESDE EL DAO 

@Service
public class UsuarioServiceImpl implements IUsuarioService{

	@Override
	@Transactional(readOnly = true)
	public List<Usuario> listar() {
		// TODO Auto-generated method stub
		return (List<Usuario>) usuarioDao.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public Optional<Usuario> porId(Long id) {
		// TODO Auto-generated method stub
		return usuarioDao.findById(id);
	}

	@Override
	@Transactional
	public Usuario guardar(Usuario usuario) {
		// TODO Auto-generated method stub
		return usuarioDao.save(usuario);
	}

	
	@Override
	@Transactional
	public void eliminar(Long id) {
		// TODO Auto-generated method stub
		usuarioDao.deleteById(id);
		cursoFeignClient.eliminarCursoUsuario(id);
	}
	
	
	//1
	@Override
	public Optional<Usuario> porEmail(String email) {
		// TODO Auto-generated method stub
		//return usuarioDao.findByEmail(email);
		return usuarioDao.encontrarPorEmail(email);
	}
	
	@Override
	public boolean existePorEmail(String email) {
		// TODO Auto-generated method stub
		return usuarioDao.existsByEmail(email);
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<Usuario> listarPorIds(Iterable<Long> ids) {
		// TODO Auto-generated method stub
		return (List<Usuario>) usuarioDao.findAllById(ids);
	}
	
	@Autowired
	private IUsuarioDao usuarioDao;
	
	@Autowired //CLASE 42
	private ICursoFeignClient cursoFeignClient;

	

	

	
}
