package com.sponsoredAd.controllers;
import com.sponsoredAd.DTOs.CampaignDto;
import com.sponsoredAd.entities.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import com.sponsoredAd.services.*;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class campaignsController {
    final SponsoredAdService sponsoredAdService;

    @PostMapping("/createCampaign")
    CampaignDto createCampaign(@RequestBody CampaignDto campaignDto){
        return sponsoredAdService.createCampaign(campaignDto);
    }

    @GetMapping("/serveAd")
    Product serveAd(String category) {
        return sponsoredAdService.serveAd(category);
    }

}
