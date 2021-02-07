package com.sponsoredAd.DTOs;

import lombok.Data;

import java.time.LocalDate;

@Data
public class CampaignDto {
    String name;
    String category;
    double bid;
    LocalDate startDate;
}
