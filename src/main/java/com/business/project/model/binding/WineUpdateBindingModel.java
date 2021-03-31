package com.business.project.model.binding;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

public class WineUpdateBindingModel {

    private BigDecimal price;
    private int amount;

    public WineUpdateBindingModel() {
    }

    @NotNull(message = "You must write price!")
    @DecimalMin(value = "12", message = "Price should be at least 12lv. You don't sell cheap wine!")
    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    @NotNull
    @Min(value = 1, message = "You should add at least one wine!")
    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}
