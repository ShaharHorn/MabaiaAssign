package com.sponsoredAd.entities;

import lombok.Data;

import java.time.LocalDate;

@Data
public class CampaignEntity {
    String name;
    String category;
    double bid;
    LocalDate startDate;
    LocalDate endDate;
    boolean isActive;
}


