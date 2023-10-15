package dev.lsrdev.authbasejwt;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
class AuthBaseJwtApplicationTests {

    private final Logger log = LoggerFactory.getLogger(AuthBaseJwtApplicationTests.class);

	@Test
	void contextLoads() {
        log.info("Context loaded");
        Assertions.assertTrue(true);
	}

}
