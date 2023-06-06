package com.food.ordering.domain.value_object;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;

public class Money {
    private final BigDecimal amounts;

    public Money(BigDecimal amounts) {
        this.amounts = amounts;
    }

    public BigDecimal getAmounts() {
        return amounts;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Money money = (Money) o;

        return Objects.equals(amounts, money.amounts);
    }

    @Override
    public int hashCode() {
        return amounts != null ? amounts.hashCode() : 0;
    }

    public boolean isGreaterThanZero() {
        return this.amounts.compareTo(BigDecimal.ZERO) > 0;
    }

    public boolean isGreaterThan(Money money) {
        return this.amounts.compareTo(money.getAmounts()) > 0;
    }

    public Money add(Money money) {
        return new Money(setScale(this.amounts.add(money.getAmounts())));
    }

    public Money subtract(Money money) {
        return new Money(setScale(this.amounts.subtract(money.getAmounts())));
    }

    public Money multiply(int multiplier) {
        return new Money(setScale(this.amounts.multiply(new BigDecimal(multiplier))));
    }

    private BigDecimal setScale(BigDecimal input) {
        return input.setScale(2, RoundingMode.HALF_EVEN);
    }
}
