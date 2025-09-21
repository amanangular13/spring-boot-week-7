package com.Aman.TestingApp;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.SpringBootTest;

//@SpringBootTest
@Slf4j
class TestingAppApplicationTests {

    @BeforeAll
    static void loadResources() {
        log.info("Loading Resources before testing application");
    }

    @AfterAll
    static void releaseResources() {
        log.info("Resources is getting released");
    }

    @BeforeEach
    void preProcessing() {
        log.info("Pre processing before test");
    }

    @AfterEach
    void postProcessing() {
        log.info("Post processing before test");
    }

	@Test
	void T1() {
      log.info("Test1 is running");
	}

    @Test
    void T2() {
        log.info("Test2 is running");
    }

    @Test
    @DisplayName("Test3")
    @Disabled
    void T3() {
        log.info("Test3 is running");
    }

}
