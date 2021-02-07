package com.sponsoredAd.services;


import com.sponsoredAd.DTOs.CampaignDto;
import com.sponsoredAd.entities.Product;

public interface SponsoredAdService {
    CampaignDto createCampaign(CampaignDto campaignDto);
    Product serveAd(String category);

}
