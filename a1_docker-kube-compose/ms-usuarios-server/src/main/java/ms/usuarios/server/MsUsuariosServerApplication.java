package ms.usuarios.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class MsUsuariosServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsUsuariosServerApplication.class, args);
	}

}
