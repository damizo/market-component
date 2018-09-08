package com.popielarski.market

import com.popielarski.market.MarketApplication
import com.popielarski.market.common.exception.ExceptionHandlerController
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.annotation.Rollback
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.result.MockMvcResultHandlers
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.web.context.WebApplicationContext
import spock.lang.Specification

import javax.transaction.Transactional

@SpringBootTest(classes = MarketApplication.class)
@Rollback
@Transactional
class IntegrationSpec extends Specification {

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private ExceptionHandlerController exceptionHandler;

    protected MockMvc mockMvc


    def setup() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(webApplicationContext)
                .alwaysDo(MockMvcResultHandlers.print())
                .build();
    }
}
