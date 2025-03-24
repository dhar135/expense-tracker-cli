package io.github.dhar135.expense_tracker;

import io.github.dhar135.expense_tracker.expense.repository.ExpenseRepository;
import io.github.dhar135.expense_tracker.expense.service.ExpenseService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class ExpenseTrackerApplicationTests {

    @Autowired
    private ApplicationContext applicationContext;

    @Test
    void contextLoads() {
        // Verify that the application context loads successfully
        assertNotNull(applicationContext);
    }

    @Test
    void verifyBeansAreLoaded() {
        // Verify that key components are properly autowired in the Spring context
        assertNotNull(applicationContext.getBean(ExpenseService.class));
        assertNotNull(applicationContext.getBean(ExpenseRepository.class));
    }
}
