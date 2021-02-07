package com.sponsoredAd.services;

import com.sponsoredAd.DTOs.CampaignDto;
import com.sponsoredAd.entities.CampaignEntity;
import com.sponsoredAd.entities.Product;
import com.sponsoredAd.mappers.CampaignMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class SponsoredAdServiceImp implements SponsoredAdService {
    final private CampaignMapper campaignMapper;

    ConcurrentHashMap<String, ArrayList<Product>> categoryToProductsHash = new ConcurrentHashMap<>();
    ConcurrentHashMap<String, ArrayList<CampaignEntity>> categoryToCampaignsHash = new ConcurrentHashMap<>();
    ConcurrentHashMap<String, Product> categoryToMaxPriceProduct = new ConcurrentHashMap<>();
    ConcurrentHashMap<String, ArrayList<CampaignEntity>> categoryToActiveCampaignsHash = new ConcurrentHashMap<>();
    Product maxPriceActiveProduct;

    @Autowired
    public SponsoredAdServiceImp(CampaignMapper campaignMapper) {
        this.campaignMapper = campaignMapper;
        addProducts();
        Timer timer = new Timer();
        TimerTask t = new TimerTask() {
            @Override
            public void run() {
                activateCampaigns();
                deactivateCampaigns();
                updateMaxPriceActiveProduct();
            }
        };
        timer.schedule(t, 1l, 1);
    }

    private void addProducts() {
        Product p1, p2, p3, p4;
        p1 = new Product("game", "smartphone", 1500, "1");
        p2 = new Product("pcGame", "smartphone", 2600, "2");
        p3 = new Product("nike", "shoes", 300, "3");
        p4 = new Product("pc", "computer", 5600, "4");
        categoryToProductsHash.put("smartphone", new ArrayList<>());
        categoryToProductsHash.put("shoes", new ArrayList<>());
        categoryToProductsHash.put("computer", new ArrayList<>());

        categoryToProductsHash.get(p1.getCategory()).add(p1);
        categoryToProductsHash.get(p2.getCategory()).add(p2);
        categoryToProductsHash.get(p3.getCategory()).add(p3);
        categoryToProductsHash.get(p4.getCategory()).add(p4);
    }

    private void updateMaxPriceActiveProduct() {
        for (String key : categoryToActiveCampaignsHash.keySet()) {
            for (Product product : categoryToProductsHash.get(key)) {
                if (categoryToMaxPriceProduct.get(key) == null || categoryToMaxPriceProduct.get(key).getPrice() <= product.getPrice()) {
                    categoryToMaxPriceProduct.put(key, product);
                    if (maxPriceActiveProduct != null) {
                        if (maxPriceActiveProduct.getPrice() <= product.getPrice())
                            maxPriceActiveProduct = product;
                    }
                    else {
                        maxPriceActiveProduct = product;
                    }
                }
            }
        }
    }

    private void deactivateCampaigns() {
        if (!categoryToActiveCampaignsHash.isEmpty()) {
            for (Map.Entry<String, ArrayList<CampaignEntity>> entry : categoryToActiveCampaignsHash.entrySet()) {
                for (CampaignEntity campaignEntity : entry.getValue()) {
                    if (LocalDate.now().isBefore(campaignEntity.getStartDate()) || LocalDate.now().isAfter(campaignEntity.getEndDate())) {
                        entry.getValue().remove(campaignEntity);
                        campaignEntity.setActive(false);
                    }
                }
            }
        }
    }

    private void activateCampaigns() {
        if (!categoryToCampaignsHash.isEmpty()) {
            for (Map.Entry<String, ArrayList<CampaignEntity>> entry : categoryToCampaignsHash.entrySet()) {
                for (CampaignEntity campaignEntity : entry.getValue()) {
                    if (campaignEntity != null) {
                        activateCampaign(campaignEntity);
                    }
                }

            }
        }
    }

    private void activateCampaign(CampaignEntity campaignEntity) {
        if (LocalDate.now().isAfter(campaignEntity.getStartDate().minusDays(1)) && LocalDate.now().isBefore(campaignEntity.getEndDate().plusDays(1))) {
            if (categoryToActiveCampaignsHash.get(campaignEntity.getCategory()) == null || categoryToActiveCampaignsHash.get(campaignEntity.getCategory()).isEmpty()) {
                ArrayList<CampaignEntity> newCategory = new ArrayList<>();
                categoryToActiveCampaignsHash.put(campaignEntity.getCategory(), newCategory);
            }
            campaignEntity.setActive(true);
        }
        if (!categoryToCampaignsHash.get(campaignEntity.getCategory()).contains(campaignEntity)) {
            categoryToCampaignsHash.get(campaignEntity.getCategory()).add(campaignEntity);
        }
    }


    @Override
    public CampaignDto createCampaign(CampaignDto campaignDto) {
        CampaignEntity campaignEntity = campaignMapper.toEntity(campaignDto);
        if (categoryToCampaignsHash.get(campaignEntity.getCategory()) == null) {
            ArrayList<CampaignEntity> newCategory = new ArrayList<>();
            categoryToCampaignsHash.put(campaignEntity.getCategory(), newCategory);
        }
        categoryToCampaignsHash.get(campaignEntity.getCategory()).add(campaignEntity);
        activateCampaign(campaignEntity);
        return campaignMapper.toDto(campaignEntity);
    }

    @Override
    public Product serveAd(String category) {
        if (categoryToMaxPriceProduct.get(category) != null) {
            return categoryToMaxPriceProduct.get(category);
        } else {
            return maxPriceActiveProduct;
        }
    }
}
