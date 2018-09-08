package com.popielarski.market.cart.domain;

import com.popielarski.market.cart.domain.CartStatus;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@Builder
public class InitializedCartDTO {
    private String temporaryCode;
    private CartStatus status;
}
