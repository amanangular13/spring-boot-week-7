package com.Aman.TestingApp;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

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

    @Test
    void T4() {
        int a = 5;
        int b = 10;
        int expected = 15;
        Assertions.assertEquals(expected, addTwoNumbers(a, b));
        log.info("addTwoNumbers giving correct results");
    }

    int addTwoNumbers(int a, int b) {
        return a + b;
    }

    @Test
    void T5() {
        assertThat(5)
                .isEqualTo(5)
                .isNotEqualTo(10)
                .isGreaterThan(2);

        assertThat("Hello world")
                .startsWith("He")
                .endsWith("ld")
                .contains("llo w");

        assertThat(true).isTrue();

        assertThat(List.of("apple", "banana"))
                .contains("apple")
                .doesNotContain("Orange")
                .hasSize(2);

        log.info("All asserts are successful");
    }

    @Test
    @DisplayName("T6")
    void testDivideTwoNumber_whenDenominatorIsZero_thenArithmeticException() {
        int a = 10;
        int b = 0;

        assertThatThrownBy(() -> divideTwoNumbers(a, b))
                .isInstanceOf(ArithmeticException.class)
                .hasMessage("/ by zero");
    }

    int divideTwoNumbers(int a, int b) {
//        throw new ArrayIndexOutOfBoundsException("error");
        try {
            return a / b;
        } catch (ArithmeticException e) {
            log.error("Arithmetic Exception {}", e.getLocalizedMessage());
            throw new ArithmeticException(e.getLocalizedMessage());
        }
    }

}
