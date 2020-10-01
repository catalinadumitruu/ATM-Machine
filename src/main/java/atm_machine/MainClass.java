package atm_machine;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MainClass {

     public static void main(String[] args) {

         SpringApplication.run(MainClass.class);
         System.out.println("ATM Machine loading ...");
     }
}
