package com.popielarski.market.discount;


import com.popielarski.market.common.domain.BaseEntity;
import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "DISCOUNTS")
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Inheritance(strategy = InheritanceType.JOINED)
public class Discount extends BaseEntity {

    protected String description;

    @Column(name = "TYPE", updatable = false)
    @Enumerated(EnumType.STRING)
    private DiscountType type;

    @Column(name = "UNIT", updatable = false)
    @Enumerated
    private DiscountUnit unit;

}
