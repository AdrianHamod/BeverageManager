package ro.uaic.info.querybackendservice;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
@Import(ConfigRoot.class)
@Slf4j
public class QueryBackendServiceApplication {

    public static void main(String[] args) {
        log.info("Starting application");
        SpringApplication.run(QueryBackendServiceApplication.class, args);
    }

}
