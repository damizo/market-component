package com.popielarski.market.integration

import com.popielarski.market.IntegrationSpec
import com.popielarski.market.common.domain.PriceDTO
import com.popielarski.market.common.utils.PageableHelper
import com.popielarski.market.configuration.DataContainer
import com.popielarski.market.product.domain.ProductConfiguration
import com.popielarski.market.product.domain.ProductDTO
import com.popielarski.market.product.domain.ProductFacade
import com.popielarski.market.product.domain.ProductRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.http.MediaType
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.web.servlet.MvcResult
import org.springframework.test.web.servlet.ResultActions

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@ContextConfiguration(classes =
        [
                ProductConfiguration.class,
        ])
class ProductManagementIntegrationSpec extends IntegrationSpec {

    @Autowired
    private ProductFacade productFacade;

    @Autowired
    private ProductRepository productRepository;

    private DataContainer dataContainer;

    def setup() {
        dataContainer = new DataContainer();
    }

    def 'should add new product'() {
        when: 'I try to add new product'
        ProductDTO productDTO = ProductDTO.builder()
                .price(PriceDTO.of(1520))
                .quantity(10)
                .name("Aquarium")
                .barCode("X_11100")
                .build();

        ResultActions resultActions = this.mockMvc
                .perform(post("/api/v1/products")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(buildJson(productDTO)));

        then: 'new product has been added'
        MvcResult mvcResult = resultActions.andExpect(status().isOk())
                .andReturn()

        ProductDTO resultDTO = buildObject(mvcResult.getResponse().getContentAsString(), ProductDTO.class)
        resultDTO.id != null
    }

    def 'should display all products'() {
        setup:
        productRepository.deleteAll()

        def cognac = productFacade.createProduct(dataContainer.cognac())
        def whisky = productFacade.createProduct(dataContainer.whisky())
        def rum = productFacade.createProduct(dataContainer.rum())

        Page<ProductDTO> expectedPage = PageableHelper.of(Arrays.asList(cognac, whisky, rum), PageRequest.of(0, 20));

        when: 'I want to display all products'
        ResultActions resultActions = this.mockMvc.perform(get("/api/v1/products"))

        then: 'created products are displayed on a list'
        resultActions.andExpect(status().isOk())
                .andExpect(content().json(buildJson(expectedPage)))
    }

    def 'should add quantity in product'() {
        setup:
        def vodka = productFacade.createProduct(dataContainer.vodka())

        when: "I want to add quantity for product"
        Integer quantity = 10
        ResultActions resultActions = this.mockMvc
                .perform(put("/api/v1/products/{productId}?quantity={quantity}", vodka.id, quantity));

        vodka.quantity += quantity
        then: 'quantity increased to 25'
        resultActions.andExpect(status().isOk())
                .andExpect(content().json(buildJson(vodka)))

    }


}