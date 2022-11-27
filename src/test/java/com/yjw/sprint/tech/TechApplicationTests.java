package com.yjw.sprint.tech;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class TechApplicationTests {

	@Test
	void contextLoads() {
		int num = 1;
		assertEquals(num, 1);
	}

}
