package com.popielarski.market

import com.popielarski.market.product.domain.ProductDTO
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.domain.Page
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

    protected MockMvc mockMvc

    def setup() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(webApplicationContext)
                .alwaysDo(MockMvcResultHandlers.print())
                .build();
    }

    protected static final String buildJson(Object object) {
        return TestUtils.mapToJson(object);
    }

    protected static final <T> T buildObject(String json, Class<T> clazz) {
        return TestUtils.mapToObject(json, clazz);
    }

}
