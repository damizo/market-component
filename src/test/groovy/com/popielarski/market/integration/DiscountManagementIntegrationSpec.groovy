package com.popielarski.market.integration

import com.popielarski.market.IntegrationSpec
import com.popielarski.market.checkout.domain.CheckoutFacade
import com.popielarski.market.discount.domain.DiscountFacade
import com.popielarski.market.product.domain.ProductFacade
import org.springframework.beans.factory.annotation.Autowired

/**
 * Created by damian on 10.09.18.
 */
class DiscountManagementIntegrationSpec extends IntegrationSpec {

    @Autowired
    private DiscountFacade discountFacade

    @Autowired
    private ProductFacade productFacade

    @Autowired
    private CheckoutFacade checkoutFacade

    def "should not apply discount for discounted cart"() {

    }

    def "should not apply discount for already paid cart"() {

    }

}
