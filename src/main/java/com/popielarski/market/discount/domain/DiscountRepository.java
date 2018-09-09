package com.popielarski.market.discount.domain;

import org.springframework.data.jpa.repository.JpaRepository;

 interface DiscountRepository extends JpaRepository<Discount, Long> {
}
