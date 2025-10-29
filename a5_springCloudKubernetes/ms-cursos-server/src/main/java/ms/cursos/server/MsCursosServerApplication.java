package ms.cursos.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class MsCursosServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsCursosServerApplication.class, args);
	}

}
