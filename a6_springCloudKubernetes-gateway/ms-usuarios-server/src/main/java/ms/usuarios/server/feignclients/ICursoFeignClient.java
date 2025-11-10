package ms.usuarios.server.feignclients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

//@FeignClient(name="ms-cursos-server", url="host.docker.internal:8002")	//para microservicio usuarios en contenedor docker y microservicio cursos sin contenedor docker
//@FeignClient(name="ms-cursos-server", url="${ms.cursos.server.url}")
@FeignClient(name="ms-cursos-server")
public interface ICursoFeignClient {
	
	@DeleteMapping("/eliminar-curso-usuario/{id}")
	public void eliminarCursoUsuario(@PathVariable Long id);

}
