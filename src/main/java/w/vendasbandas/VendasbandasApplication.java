package w.vendasbandas;

import java.io.IOException;
import mediator.LogMediator;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication //inicialização do springboot
public class VendasbandasApplication {
    public static void main(String[] args){
            LogMediator mediator = new LogMediator();
        try {
            mediator.getDataService().readFromFiles();
        } catch (IOException ex) {
            System.getLogger(VendasbandasApplication.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }
            SpringApplication.run(VendasbandasApplication.class, args);  
        }
    }
 

