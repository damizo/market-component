package com.popielarski.market.acceptance

import com.google.common.collect.Sets
import com.popielarski.market.IntegrationSpec
import com.popielarski.market.checkout.domain.CheckoutProcessStatus
import com.popielarski.market.checkout.domain.CheckoutReceiptDTO
import com.popielarski.market.checkout.domain.CheckoutScanDTO
import com.popielarski.market.checkout.domain.CheckoutStatusDTO
import com.popielarski.market.common.domain.Calculator
import com.popielarski.market.common.domain.PriceDTO
import com.popielarski.market.common.domain.Quantity
import com.popielarski.market.discount.domain.DiscountConfiguration
import com.popielarski.market.discount.domain.DiscountDTO
import com.popielarski.market.discount.domain.DiscountType
import com.popielarski.market.product.domain.ProductConfiguration
import com.popielarski.market.product.domain.ProductDataContainer
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.web.servlet.MvcResult
import org.springframework.test.web.servlet.ResultActions

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@ContextConfiguration(classes =
        [
                DiscountConfiguration.class,
                ProductConfiguration.class
        ])
class MarketManagementAcceptanceSpec extends IntegrationSpec {

    private ProductDataContainer dataContainer;

    def setup() {
        dataContainer = new ProductDataContainer();
    }

    def 'should buy 2 items without any discount'() {
        when: 'client comes up to checkout number 4'
        Integer checkoutNumber = 4;
        ResultActions comeUpToCheckoutResult = this.mockMvc
                .perform(get("/api/v1/checkouts/{checkoutNumber}", checkoutNumber))

        MvcResult mvcResult = comeUpToCheckoutResult.andReturn();
        CheckoutStatusDTO resultDTO = buildObject(mvcResult.getResponse().getContentAsString(), CheckoutStatusDTO.class)
        CheckoutStatusDTO expectedCheckoutStatusDTO = new CheckoutStatusDTO(resultDTO.getCartId(),
                checkoutNumber,
                CheckoutProcessStatus.WHILE_SCANNING);

        then: 'checkout becomes occupied by him'
        comeUpToCheckoutResult
                .andExpect(status().isOk())
                .andExpect(content().json(buildJson(expectedCheckoutStatusDTO)));

        when: 'cashier scans wine'
        ResultActions resultAfterScanningWine = this.mockMvc
                .perform(post("/api/v1/checkouts/{checkoutNumber}/carts/{barCode}",
                checkoutNumber, dataContainer.wine().barCode));

        CheckoutScanDTO expectedCheckoutScanDTO = CheckoutScanDTO.builder()
                .checkoutNumber(checkoutNumber)
                .items(Sets.newHashSet(dataContainer.wineDTO(Quantity.ONE)))
                .totalPrice(PriceDTO.of(20))
                .build()

        then: 'price to pay increases to 350'
        resultAfterScanningWine
                .andExpect(status().isOk())
                .andExpect(content().json(buildJson(expectedCheckoutScanDTO)))

        when: 'cashier scans flakes'
        ResultActions resultAfterScanningFlakes = this.mockMvc
                .perform(post("/api/v1/checkouts/{checkoutNumber}/carts/{barCode}",
                checkoutNumber, dataContainer.flakes().barCode));

        expectedCheckoutScanDTO.totalPrice = Calculator.add(expectedCheckoutScanDTO.totalPrice, PriceDTO.of(3))
        expectedCheckoutScanDTO.items = Sets.newHashSet(
                dataContainer.wineDTO(Quantity.ONE),
                dataContainer.flakesDTO(Quantity.ONE))

        then: 'price to pay increases to 440'
        resultAfterScanningFlakes
                .andExpect(status().isOk())
                .andExpect(content().json(buildJson(expectedCheckoutScanDTO)));

        when: 'client pays for purchases of products that does not contain discounts'
        Integer amountPayment = 23;

        ResultActions receipt = this.mockMvc
                .perform(put("/api/v1/checkouts/{checkoutNumber}?amountPayment={amountPayment}",
                checkoutNumber,
                amountPayment))

        CheckoutReceiptDTO expectedCheckoutSummaryDTO = CheckoutReceiptDTO.builder()
                .status(CheckoutProcessStatus.PAID)
                .items(Sets.newHashSet(dataContainer.flakesDTO(Quantity.ONE), dataContainer.wineDTO(Quantity.ONE)))
                .finalPrice(PriceDTO.of(amountPayment))
                .change(PriceDTO.of(0))
                .build()

        then: 'client gets receipt without any discount'
        receipt.andExpect(status().isOk())
                .andExpect(content().json(buildJson(expectedCheckoutSummaryDTO)))
    }

    def 'should buy 3 items with "Bought Together" discount'() {
        when: 'client comes up to checkout number 2'
        Integer checkoutNumber = 2;

        ResultActions comeUpToCheckoutResult = this.mockMvc
                .perform(get("/api/v1/checkouts/{checkoutNumber}", checkoutNumber))

        MvcResult mvcResult = comeUpToCheckoutResult.andReturn();
        CheckoutStatusDTO resultDTO = buildObject(mvcResult.getResponse().getContentAsString(), CheckoutStatusDTO.class)
        CheckoutStatusDTO expectedCheckoutStatusDTO = new CheckoutStatusDTO(resultDTO.getCartId(),
                checkoutNumber,
                CheckoutProcessStatus.WHILE_SCANNING);

        then: 'checkout becomes occupied by him'
        comeUpToCheckoutResult
                .andExpect(status().isOk())
                .andExpect(content().json(buildJson(expectedCheckoutStatusDTO)));

        when: 'cashier scans wine twice'
        this.mockMvc.perform(post("/api/v1/checkouts/{checkoutNumber}/carts/{barCode}",
                checkoutNumber, dataContainer.wine().barCode));

        ResultActions resultAfterScanningWines = this.mockMvc.perform(post("/api/v1/checkouts/{checkoutNumber}/carts/{barCode}",
                checkoutNumber, dataContainer.wine().barCode));

        CheckoutScanDTO expectedCheckoutScanDetails = CheckoutScanDTO.builder()
                .checkoutNumber(checkoutNumber)
                .items(Sets.newHashSet(dataContainer.wineDTO(Quantity.TWO)))
                .totalPrice(PriceDTO.of(40))
                .build()

        then: 'price to pay increases to 40'
        resultAfterScanningWines
                .andExpect(status().isOk())
                .andExpect(content().json(buildJson(expectedCheckoutScanDetails)))

        when: 'cashier scans rafaello'
        ResultActions resultAfterScanningRafaello = this.mockMvc.perform(post("/api/v1/checkouts/{checkoutNumber}/carts/{barCode}",
                checkoutNumber, dataContainer.rafaello().barCode));

        expectedCheckoutScanDetails.items.add(dataContainer.rafaelloDTO(Quantity.ONE))
        expectedCheckoutScanDetails.totalPrice = Calculator.add(expectedCheckoutScanDetails.totalPrice, PriceDTO.of(10))

        then: 'price to pay increases to 50'
        resultAfterScanningRafaello
                .andExpect(status().isOk())
                .andExpect(content().json(buildJson(expectedCheckoutScanDetails)))

        when: 'client assign "Bought Together" discount'
        ResultActions resultAfterDiscount = this.mockMvc
                .perform(put("/api/v1/discounts/boughtTogether/carts/{cartId}", resultDTO.getCartId()))

        DiscountDTO discountDTO = DiscountDTO.builder()
                .priceBeforeDiscount(PriceDTO.of(50))
                .priceAfterDiscount(PriceDTO.of(41))
                .type(DiscountType.BOUGHT_TOGETHER)
                .build();

        then: 'final price is decreased by 30 percent of pair of products price'
        resultAfterDiscount.andExpect(status().isOk())
                .andExpect(content().json(buildJson(discountDTO)))

        when: 'client pays for cart'
        Integer amountPayment = 60
        ResultActions resultActions = this.mockMvc.perform(put("/api/v1/checkouts/{checkoutNumber}?amountPayment={amountPayment}",
                checkoutNumber,
                amountPayment));

        CheckoutReceiptDTO checkoutReceiptDTO = CheckoutReceiptDTO.builder()
                .finalPrice(PriceDTO.of(41))
                .items(Sets.newHashSet(dataContainer.wineDTO(Quantity.TWO), dataContainer.rafaelloDTO(Quantity.ONE)))
                .appliedDiscount(DiscountType.BOUGHT_TOGETHER)
                .status(CheckoutProcessStatus.PAID)
                .change(PriceDTO.of(19))
                .build();

        then: 'client gets receipt'
        resultActions.andExpect(status().isOk())
                .andExpect(content().json(buildJson(checkoutReceiptDTO)))
    }


    def 'should buy 2 items with "Multi-Items" discount'() {
        when: 'client comes up to checkout number 1'
        Integer checkoutNumber = 1;

        ResultActions comeUpToCheckoutResult = this.mockMvc
                .perform(get("/api/v1/checkouts/{checkoutNumber}", checkoutNumber))

        MvcResult mvcResult = comeUpToCheckoutResult.andReturn();
        CheckoutStatusDTO resultDTO = buildObject(mvcResult.getResponse().getContentAsString(), CheckoutStatusDTO.class)
        CheckoutStatusDTO checkoutStatusDTO = new CheckoutStatusDTO(resultDTO.getCartId(),
                checkoutNumber,
                CheckoutProcessStatus.WHILE_SCANNING);

        then: 'checkout becomes occupied by him'
        comeUpToCheckoutResult
                .andExpect(status().isOk())
                .andExpect(content().json(buildJson(checkoutStatusDTO)));

        when: 'cashier scans flakes twice'
        this.mockMvc.perform(post("/api/v1/checkouts/{checkoutNumber}/carts/{barCode}",
                checkoutNumber, dataContainer.flakes().barCode));

        ResultActions resultAfterScanningFlakes = this.mockMvc.perform(post("/api/v1/checkouts/{checkoutNumber}/carts/{barCode}",
                checkoutNumber, dataContainer.flakes().barCode));

        CheckoutScanDTO checkoutScanDetails = CheckoutScanDTO.builder()
                .checkoutNumber(checkoutNumber)
                .items(Sets.newHashSet(dataContainer.flakesDTO(2)))
                .totalPrice(PriceDTO.of(6))
                .build()
        then: 'price to pay increases to 6'
        resultAfterScanningFlakes
                .andExpect(status().isOk())
                .andExpect(content().json(buildJson(checkoutScanDetails)))

        when: 'client assign "Multi-Items" discount for his cart'
        ResultActions resultAfterDiscount = this.mockMvc
                .perform(put("/api/v1/discounts/multiItems/carts/{cartId}", resultDTO.getCartId()))

        DiscountDTO discountDTO = DiscountDTO.builder()
                .priceBeforeDiscount(PriceDTO.of(6))
                .priceAfterDiscount(PriceDTO.of(4.8))
                .type(DiscountType.MULTI_ITEMS)
                .build();

        then: 'final price is decreased by 20 percent'
        resultAfterDiscount.andExpect(status().isOk())
                .andExpect(content().json(buildJson(discountDTO)))

        when: 'client pays for cart'
        Integer amountPayment = 5
        ResultActions resultActions = this.mockMvc.perform(put("/api/v1/checkouts/{checkoutNumber}?amountPayment={amountPayment}",
                checkoutNumber,
                amountPayment));

        CheckoutReceiptDTO checkoutReceiptDTO = CheckoutReceiptDTO.builder()
                .finalPrice(PriceDTO.of(4.8))
                .items(Sets.newHashSet(dataContainer.flakesDTO(2)))
                .appliedDiscount(DiscountType.MULTI_ITEMS)
                .status(CheckoutProcessStatus.PAID)
                .change(PriceDTO.of(0.2))
                .build();

        then: 'client gets receipt'
        resultActions.andExpect(status().isOk())
                .andExpect(content().json(buildJson(checkoutReceiptDTO)))
    }


}
