package com.popielarski.market.checkout;

import com.popielarski.market.checkout.domain.CheckoutFacade;
import com.popielarski.market.checkout.domain.CheckoutReceiptDTO;
import com.popielarski.market.checkout.domain.CheckoutScanDTO;
import com.popielarski.market.checkout.domain.CheckoutStatusDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/checkouts")
@RequiredArgsConstructor
@Slf4j
class CheckoutController {

    private final CheckoutFacade checkoutFacade;

    @GetMapping(value = "/{checkoutNumber}")
    CheckoutStatusDTO initializeCheckout(@PathVariable Integer checkoutNumber) {
        log.debug("Come up to checkout number: {}", checkoutNumber);
        return checkoutFacade.initializeScanning(checkoutNumber);
    }

    @PostMapping(value = "/{checkoutNumber}/carts/{barCode}")
    CheckoutScanDTO scan(@PathVariable Integer checkoutNumber, @PathVariable String barCode) {
        return checkoutFacade.scanItem(checkoutNumber, barCode);
    }

    @PutMapping(value = "/{checkoutNumber}")
    CheckoutReceiptDTO pay(@PathVariable Integer checkoutNumber, @RequestParam Integer amountPayment) {
        return checkoutFacade.makePayment(checkoutNumber, amountPayment);
    }
}
