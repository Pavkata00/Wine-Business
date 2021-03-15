package com.business.project.model.binding;

import com.business.project.model.entity.FactoryEntity;
import com.business.project.model.entity.enums.TypeEnum;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDate;

public class WineAddBindingModel {

    private String name;
    private String description;
    private String imageUrl;
    private BigDecimal price;
    private int amount;
    private LocalDate madeDate;

    //todo check for type mistake make it enum
    private String type;
    private String factory;

    @NotNull
    @Length(min = 3, message = "Length must be at least 3 characters!")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @NotNull
    @Length(min = 5, message = "Description must be at least 5 characters!")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @NotNull
    @Length(min = 5, message = "Image length must be at least 5 characters!")
    //todo consider adding regex
    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    @NotNull(message = "You must write price!")
    @DecimalMin(value = "1", message = "Price should be at least 12lv. You don't sell cheap wine!")
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

    @NotNull(message = "You must choose date!")
    @PastOrPresent
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    public LocalDate getMadeDate() {
        return madeDate;
    }

    public void setMadeDate(LocalDate madeDate) {
        this.madeDate = madeDate;
    }

    @NotBlank(message = "You must choose type!")
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }


    @NotBlank(message = "You must choose factory!")
    public String getFactory() {
        return factory;
    }

    public void setFactory(String factory) {
        this.factory = factory;
    }
}
