package com.popielarski.market.discount.multiitems;

import com.popielarski.market.item.domain.Item;
import com.popielarski.market.item.domain.MultiItemsDiscount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Set;

public interface MultiItemsDiscountRepository extends JpaRepository<MultiItemsDiscount, Long> {

}
