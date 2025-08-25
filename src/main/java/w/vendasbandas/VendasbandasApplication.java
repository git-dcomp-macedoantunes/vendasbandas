package w.vendasbandas;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"controller", "mediator", "model", "w.vendasbandas"})

public class VendasbandasApplication {

    public static void main(String[] args) {
        SpringApplication.run(VendasbandasApplication.class, args);
    }
}

