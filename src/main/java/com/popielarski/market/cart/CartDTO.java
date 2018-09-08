package com.popielarski.market.cart;

import com.google.common.collect.Sets;
import com.popielarski.market.common.Calculator;
import com.popielarski.market.common.domain.BaseDTO;
import com.popielarski.market.common.domain.Value;
import com.popielarski.market.discount.DiscountType;
import com.popielarski.market.item.domain.ItemDTO;
import lombok.Builder;
import lombok.Data;

import java.util.Optional;
import java.util.Set;

@Data
public class CartDTO extends BaseDTO {

    private Set<ItemDTO> items = Sets.newHashSet();
    private DiscountType discount;
    private Value finalPrice;

    @Builder
    public CartDTO(Long id, Set<ItemDTO> items, DiscountType discount) {
        super(id);
        this.items = items;
        this.discount = discount;
    }

    public CartDTO() {

    }

    public Value getTotalPrice() {
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

    public Value getPrice() {
        return this.getFinalPrice() != null ?
                this.getFinalPrice() : this.getTotalPrice();
    }

    public static CartDTO emptyCart() {
        return new CartDTO();
    }
}
