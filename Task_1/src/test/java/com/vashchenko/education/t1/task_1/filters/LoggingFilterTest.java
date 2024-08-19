package com.vashchenko.education.t1.task_1.filters;

import com.vashchenko.education.t1.task2.configuration.properties.LoggingProperties;
import com.vashchenko.education.t1.task2.filter.LoggingFilter;
import com.vashchenko.education.t1.task_1.repository.UserRepository;
import com.vashchenko.education.t1.task_1.service.OrderService;
import com.vashchenko.education.t1.task_1.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@ActiveProfiles("test")
@WebMvcTest
public class LoggingFilterTest {

    @MockBean
    UserService userService;

    @MockBean
    UserRepository userRepository;

    @MockBean
    OrderService orderService;


    @Autowired
    private MockMvc mockMvc;

    @Autowired
    LoggingProperties loggingProperties;

    @InjectMocks
    LoggingFilter loggingFilter = new LoggingFilter(loggingProperties);

    @MockBean
    Logger logger = LogManager.getLogger(LoggingFilter.class);

    @Test
    public void loggingFilterTest() throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/your-endpoint"))
                .andExpect(status().isOk())
                .andReturn();
    }
}
