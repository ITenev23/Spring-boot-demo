package bg.paysafe.springboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.sql.SQLException;

@EnableSwagger2
@SpringBootApplication
public class
PaysafeApplication {

    public static void main(String[] args) throws SQLException {
        SpringApplication.run(PaysafeApplication.class, args);
    }

}
