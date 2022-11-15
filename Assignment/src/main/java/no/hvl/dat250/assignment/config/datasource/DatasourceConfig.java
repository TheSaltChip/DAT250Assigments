package no.hvl.dat250.assignment.config.datasource;

import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import javax.sql.DataSource;

@Configuration
@EnableJpaRepositories(
        basePackages = {"no.hvl.dat250.assignment.repository"}
)
public class DatasourceConfig {

    @Bean
    public DataSource getDataSource() {
        return DataSourceBuilder.create()
                .driverClassName("org.postgresql.Driver")
                .url("jdbc:postgresql://localhost:5432/FeedApp")
                .username(DatasourceCredentials.USERNAME)
                .password(DatasourceCredentials.PASSWORD)
                .build();
    }
}
