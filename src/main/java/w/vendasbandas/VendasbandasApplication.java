package w.vendasbandas;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import mediator.LogMediator;

@SpringBootApplication
@ComponentScan(basePackages = {"controller", "mediator", "model", "w.vendasbandas"})

public class VendasbandasApplication {

    public static void main(String[] args) {
        LogMediator mediator = new LogMediator();
        try {
            mediator.getDataService().readFromFiles();
        } catch (Exception e) {
            System.out.println("Erro ao ler arquivos: " + e.getMessage());
        }
        SpringApplication.run(VendasbandasApplication.class, args);
    }
}

