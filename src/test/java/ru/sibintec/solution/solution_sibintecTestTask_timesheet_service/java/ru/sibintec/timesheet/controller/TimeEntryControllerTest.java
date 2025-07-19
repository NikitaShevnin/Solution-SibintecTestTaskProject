package ru.sibintec.solution.solution_sibintecTestTask_timesheet_service.java.ru.sibintec.timesheet.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import ru.sibintec.solution.solution_sibintecTestTask_timesheet_service.repository.TimeEntryRepository;

import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Интеграционные тесты контроллера табелей.
 */
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional
class TimeEntryControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private TimeEntryRepository repository;

    /** Проверка успешного добавления записи через контроллер. */
    @Test
    @WithMockUser
    void addTimeEntry() throws Exception {
        long count = repository.count();
        String json = "{" +
                "\"employeeId\":1," +
                "\"date\":\"2025-09-01\"," +
                "\"hoursWorked\":8}";
        mockMvc.perform(post("/api/time-entries")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.employeeId", is(1)));
        assertEquals(count + 1, repository.count());
    }

    /** Получение списка записей. */
    @Test
    @WithMockUser
    void list() throws Exception {
        mockMvc.perform(get("/api/time-entries"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
    }
}