package com.popielarski.market.cart;

import com.google.common.collect.Sets;
import com.popielarski.market.common.domain.Calculator;
import com.popielarski.market.common.domain.BaseDTO;
import com.popielarski.market.common.domain.PriceDTO;
import com.popielarski.market.discount.domain.DiscountType;
import com.popielarski.market.item.ItemDTO;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Optional;
import java.util.Set;

@Data
@EqualsAndHashCode(callSuper = true)
public class CartDTO extends BaseDTO {

    private Set<ItemDTO> items = Sets.newHashSet();
    private DiscountType discount;
    private PriceDTO finalPrice;

    @Builder
    public CartDTO(Long id, Set<ItemDTO> items, DiscountType discount) {
        super(id);
        this.items = items;
        this.discount = discount;
    }

    public CartDTO() {

    }

    public PriceDTO getTotalPrice() {
        return items.stream()
                .map(ItemDTO::getTotalPrice)
                .reduce((firstTotalPrice, secondTotalPrice) -> Calculator.add(firstTotalPrice.getValue(), secondTotalPrice.getValue()))
                .get();
    }

    public void addItem(ItemDTO itemDTO) {
        items.add(itemDTO);
    }

    public void increaseQuantityOfItem(Integer quantity, String barCode) {
        Optional<ItemDTO> itemOptional = items.stream()
                .map(item -> item.increaseQuantity(quantity))
                .findAny();

        if (itemOptional.isPresent()) {
            items.add(itemOptional.get());
        } else {
            throw new UnsupportedOperationException(String.format("Item with barCode %s does not exist in cart", barCode));
        }
    }

    public PriceDTO getPrice() {
        return this.getFinalPrice() != null ?
                this.getFinalPrice() : this.getTotalPrice();
    }

    public static CartDTO emptyCart() {
        return new CartDTO();
    }
}
