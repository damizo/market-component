package com.popielarski.market.acceptance

import com.google.common.collect.Sets
import com.popielarski.market.IntegrationSpec
import com.popielarski.market.configuration.DataContainer
import com.popielarski.market.ItemConfiguration
import com.popielarski.market.TestUtils
import com.popielarski.market.checkout.domain.CheckoutProcessStatus
import com.popielarski.market.checkout.domain.CheckoutReceiptDTO
import com.popielarski.market.checkout.domain.CheckoutScanDTO
import com.popielarski.market.checkout.domain.CheckoutStatus
import com.popielarski.market.checkout.domain.CheckoutStatusDTO
import com.popielarski.market.configuration.DataConfiguration
import com.popielarski.market.configuration.DiscountConfiguration
import com.popielarski.market.discount.DiscountDTO
import com.popielarski.market.discount.DiscountType
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.web.servlet.MvcResult
import org.springframework.test.web.servlet.ResultActions

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@ContextConfiguration(classes =
        [
                ItemConfiguration.class,
                DiscountConfiguration.class,
                DataConfiguration.class
        ])
class MarketManagementAcceptanceSpec extends IntegrationSpec {

    private DataContainer dataContainer;

    def setup() {
        dataContainer = new DataContainer();
    }

    def 'should buy 2 items without any discount'() {
        when: 'client comes up to checkout number 4'
        ResultActions comeUpToCheckoutResult = this.mockMvc
                .perform(get("/api/v1/checkouts/{checkoutNumber}", 4))

        MvcResult mvcResult = comeUpToCheckoutResult.andReturn();
        CheckoutStatusDTO resultDTO = buildObject(mvcResult.getResponse().getContentAsString(), CheckoutStatusDTO.class)
        CheckoutStatusDTO checkoutStatusDTO = new CheckoutStatusDTO(resultDTO.getCartId(), 4, CheckoutStatus.SCANNING);

        then: 'checkout becomes occupied by him'
        comeUpToCheckoutResult
                .andExpect(status().isOk())
                .andExpect(content().json(buildJson(checkoutStatusDTO)));

        when: 'cashier scans wine'
        ResultActions resultAfterScanningWine = this.mockMvc
                .perform(post("/api/v1/checkouts/{checkoutNumber}/carts/{barCode}",
                4, dataContainer.wine().barCode));

        CheckoutScanDTO checkoutScanDetails = CheckoutScanDTO.builder()
                .checkoutNumber(4)
                .items(Sets.newHashSet(dataContainer.wineDTO(1)))
                .totalPrice(350L)
                .build()

        then: 'price to pay increases to 350'
        resultAfterScanningWine
                .andExpect(status().isOk())
                .andExpect(content().json(buildJson(checkoutScanDetails)))

        when: 'cashier scans flakes'
        ResultActions resultAfterScanningFlakes = this.mockMvc
                .perform(post("/api/v1/checkouts/{checkoutNumber}/carts/{barCode}",
                4, dataContainer.flakes().barCode));

        checkoutScanDetails.totalPrice += 90L
        checkoutScanDetails.items = Sets.newHashSet(
                dataContainer.wineDTO(1),
                dataContainer.flakesDTO(1))

        then: 'price to pay increases to 440'
        resultAfterScanningFlakes
                .andExpect(status().isOk())
                .andExpect(content().json(buildJson(checkoutScanDetails)));

        when: 'client pays for purchases of products that does not contain discounts'
        Integer amountPayment = 440;

        ResultActions receipt = this.mockMvc
                .perform(put("/api/v1/checkouts/{checkoutNumber}?amountPayment={amountPayment}",
                4,
                amountPayment))

        CheckoutReceiptDTO checkoutSummaryDTO = CheckoutReceiptDTO.builder()
                .status(CheckoutProcessStatus.PAID)
                .items(Sets.newHashSet(dataContainer.flakesDTO(1), dataContainer.wineDTO(1)))
                .finalPrice(440L)
                .change(0L)
                .build()

        then: 'client gets receipt without any discount'
        receipt.andExpect(status().isOk())
                .andExpect(content().json(buildJson(checkoutSummaryDTO)))
    }

    def 'should buy 2 items with "Bought Together" discount'() {
        when: 'client comes up to checkout number 2'
        ResultActions comeUpToCheckoutResult = this.mockMvc
                .perform(get("/api/v1/checkouts/{checkoutNumber}", 2))

        MvcResult mvcResult = comeUpToCheckoutResult.andReturn();
        CheckoutStatusDTO resultDTO = buildObject(mvcResult.getResponse().getContentAsString(), CheckoutStatusDTO.class)
        CheckoutStatusDTO checkoutStatusDTO = new CheckoutStatusDTO(resultDTO.getCartId(), 2, CheckoutStatus.SCANNING);

        then: 'checkout becomes occupied by him'
        comeUpToCheckoutResult
                .andExpect(status().isOk())
                .andExpect(content().json(buildJson(checkoutStatusDTO)));

        when: 'cashier scans wine twice'
        this.mockMvc.perform(post("/api/v1/checkouts/{checkoutNumber}/carts/{barCode}",
                2, dataContainer.wine().barCode));

        ResultActions resultAfterScanningWines = this.mockMvc.perform(post("/api/v1/checkouts/{checkoutNumber}/carts/{barCode}",
                2, dataContainer.wine().barCode));

        CheckoutScanDTO checkoutScanDetails = CheckoutScanDTO.builder()
                .checkoutNumber(2)
                .items(Sets.newHashSet(dataContainer.wineDTO(2)))
                .totalPrice(700L)
                .build()

        then: 'price to pay increases to 700'
        resultAfterScanningWines
                .andExpect(status().isOk())
                .andExpect(content().json(buildJson(checkoutScanDetails)))

        when: 'cashier scans rafaello'
        ResultActions resultAfterScanningRafaello = this.mockMvc.perform(post("/api/v1/checkouts/{checkoutNumber}/carts/{barCode}",
                2, dataContainer.rafaello().barCode));

        checkoutScanDetails.items.add(dataContainer.rafaelloDTO(1))
        checkoutScanDetails.totalPrice = checkoutScanDetails.totalPrice + 70

        then: 'price to pay increases to 770'
        resultAfterScanningRafaello
                .andExpect(status().isOk())
                .andExpect(content().json(buildJson(checkoutScanDetails)))

        when: 'client assign discount for his cart'
        ResultActions resultAfterDiscount = this.mockMvc
                .perform(put("/api/v1/discounts/boughtTogether/carts/{cartId}", resultDTO.getCartId()))

        DiscountDTO discountDTO = DiscountDTO.builder()
                .priceBeforeDiscount(770)
                .priceAfterDiscount(385)
                .type(DiscountType.BOUGHT_TOGETHER)
                .build();

        then: 'price to pay decreased  385'
        resultAfterDiscount.andExpect(status().isOk())
                .andExpect(content().json(buildJson(discountDTO)))

        when: 'client pays for cart'
        Integer amountPayment = 385
        ResultActions resultActions = this.mockMvc.perform(put("/api/v1/checkouts/{checkoutNumber}?amountPayment={amountPayment}",
                2,
                amountPayment));

        CheckoutReceiptDTO checkoutReceiptDTO = CheckoutReceiptDTO.builder()
                .finalPrice(385)
                .items(Sets.newHashSet(dataContainer.wineDTO(2), dataContainer.rafaelloDTO(1)))
                .appliedDiscount(DiscountType.BOUGHT_TOGETHER)
                .status(CheckoutProcessStatus.PAID)
                .change(0L)
        .build();

        then: 'client gets receipt'
        resultActions.andExpect(status().isOk())
                .andExpect(content().json(buildJson(checkoutReceiptDTO)))
    }


    def 'should buy 2 items with "Multi-Items" discount'() {
        when: 'client comes up to checkout number 2'
        ResultActions comeUpToCheckoutResult = this.mockMvc
                .perform(get("/api/v1/checkouts/{checkoutNumber}", 1))

        MvcResult mvcResult = comeUpToCheckoutResult.andReturn();
        CheckoutStatusDTO resultDTO = buildObject(mvcResult.getResponse().getContentAsString(), CheckoutStatusDTO.class)
        CheckoutStatusDTO checkoutStatusDTO = new CheckoutStatusDTO(resultDTO.getCartId(), 1, CheckoutStatus.SCANNING);

        then: 'checkout becomes occupied by him'
        comeUpToCheckoutResult
                .andExpect(status().isOk())
                .andExpect(content().json(buildJson(checkoutStatusDTO)));

        when: 'cashier scans flakes twice'
        this.mockMvc.perform(post("/api/v1/checkouts/{checkoutNumber}/carts/{barCode}",
                1, dataContainer.flakes().barCode));

        ResultActions resultAfterScanningFlakes = this.mockMvc.perform(post("/api/v1/checkouts/{checkoutNumber}/carts/{barCode}",
                1, dataContainer.flakes().barCode));

        CheckoutScanDTO checkoutScanDetails = CheckoutScanDTO.builder()
                .checkoutNumber(1)
                .items(Sets.newHashSet(dataContainer.flakesDTO(2)))
                .totalPrice(180L)
                .build()
        then: 'price to pay increases to 180'
        resultAfterScanningFlakes
                .andExpect(status().isOk())
                .andExpect(content().json(buildJson(checkoutScanDetails)))

        when: 'client assign discount for his cart'
        ResultActions resultAfterDiscount = this.mockMvc
                .perform(put("/api/v1/discounts/multiItems/carts/{cartId}", resultDTO.getCartId()))

        DiscountDTO discountDTO = DiscountDTO.builder()
                .priceBeforeDiscount(180)
                .priceAfterDiscount(144)
                .type(DiscountType.MULTI_ITEMS)
                .build();

        then: 'price to pay decreased by 36'
        resultAfterDiscount.andExpect(status().isOk())
                .andExpect(content().json(buildJson(discountDTO)))

        when: 'client pays for cart'
        Integer amountPayment = 180
        ResultActions resultActions = this.mockMvc.perform(put("/api/v1/checkouts/{checkoutNumber}?amountPayment={amountPayment}",
                1,
                amountPayment));

        CheckoutReceiptDTO checkoutReceiptDTO = CheckoutReceiptDTO.builder()
                .finalPrice(144)
                .items(Sets.newHashSet(dataContainer.flakesDTO(2)))
                .appliedDiscount(DiscountType.MULTI_ITEMS)
                .status(CheckoutProcessStatus.PAID)
                .change(36)
                .build();

        then: 'client gets receipt'
        resultActions.andExpect(status().isOk())
                .andExpect(content().json(buildJson(checkoutReceiptDTO)))
    }

    private static final String buildJson(Object object) {
        return TestUtils.mapToJson(object);
    }

    private static final <T> T buildObject(String json, Class<T> clazz) {
        return TestUtils.mapToObject(json, clazz);
    }

}
