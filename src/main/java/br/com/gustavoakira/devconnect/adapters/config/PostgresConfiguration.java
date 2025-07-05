package br.com.gustavoakira.devconnect.adapters.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class PostgresConfiguration {

    @Value("${db.config.username}")
    private String username;

    @Value("${db.config.password}")
    private String password;

    @Value("${db.config.url}")
    private String url;

    @Bean
    public DataSource getDataSource(){
        return DataSourceBuilder.create()
                .username(username)
                .password(password)
                .url(url)
                .driverClassName("org.postgresql.Driver")
                .build();
    }
}
