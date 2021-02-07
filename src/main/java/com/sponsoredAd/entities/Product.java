package com.sponsoredAd.entities;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Product {
    String title;
    String category;
    double price;
    String serialNumber;

}
