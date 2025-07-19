package ru.sibintec.solution.solution_sibintecTestTask_timesheet_service;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@OpenAPIDefinition(info = @Info(title = "Timesheet API", version = "1.0", description = "Timesheet service API"))
/**
 * Точка входа в приложение.
 */
public class TimesheetServiceApplication {

        /**
         * Запуск Spring Boot приложения.
         */
        public static void main(String[] args) {
                SpringApplication.run(TimesheetServiceApplication.class, args);
        }

}