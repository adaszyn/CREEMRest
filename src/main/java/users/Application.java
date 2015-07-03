package users;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.support.ClassPathXmlApplicationContext;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        ApplicationContext context =
                new ClassPathXmlApplicationContext("Spring-Module.xml");

        ConsumedEnergyDAO consumedenergyDAO = (ConsumedEnergyDAO) context.getBean("consumedenergyDAO");
        ConsumedEnergy consumedEnergy = consumedenergyDAO.getConsumedEnergyById(1111115022);
        System.out.println(consumedEnergy);
        SpringApplication.run(Application.class, args);
    }
}