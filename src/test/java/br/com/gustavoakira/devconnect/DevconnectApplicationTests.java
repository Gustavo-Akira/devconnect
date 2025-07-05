package br.com.gustavoakira.devconnect;

import br.com.gustavoakira.devconnect.adapters.config.PostgresConfiguration;
import br.com.gustavoakira.devconnect.shared.BasePostgresTest;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.testcontainers.junit.jupiter.Testcontainers;

@SpringBootTest
@Testcontainers
class DevconnectApplicationTests extends BasePostgresTest {

	@Test
	void contextLoads() {
	}

}
