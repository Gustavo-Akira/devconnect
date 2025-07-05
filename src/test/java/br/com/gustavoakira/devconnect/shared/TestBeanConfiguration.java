package br.com.gustavoakira.devconnect.shared;

import org.junit.jupiter.api.TestInstance;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class TestBeanConfiguration extends BasePostgresTest{
    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper(); // real instance
    }


}
