package atm_machine.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.mysql.cj.jdbc.MysqlDataSource;
import javax.sql.DataSource;


@Configuration
public class BeanConfig {

    @Bean
    public DataSource dataSource() {
        final MysqlDataSource dataSource = new MysqlDataSource();

        dataSource.setUrl("jdbc:mysql://localhost:3306/atm_machine");
        dataSource.setUser("root");
        dataSource.setPassword("");

        return dataSource;
    }

    @Bean
    public ObjectMapper objectMapper(){

        return new ObjectMapper();
    }
}
