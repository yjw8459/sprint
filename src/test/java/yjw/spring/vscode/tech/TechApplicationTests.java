package yjw.spring.vscode.tech;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import lombok.extern.slf4j.Slf4j;
@Slf4j
@SpringBootTest
class TechApplicationTests {

	@Test
	void contextLoads() {
		int num = 1;
		log.info("msg");
		assertEquals(num, 1);
	}

}
