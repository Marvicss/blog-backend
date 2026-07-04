package com.example.blog_backend;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Disabled("Desabilitado porque requer PostgreSQL e Redis rodando localmente")
class BlogBackendApplicationTests {

	@Test
	void contextLoads() {
	}

}
