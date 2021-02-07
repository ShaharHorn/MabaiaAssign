package com.sponsoredAd.mappers;

import com.sponsoredAd.DTOs.CampaignDto;
import com.sponsoredAd.entities.CampaignEntity;
import org.springframework.stereotype.Service;

public interface CampaignMapper {

    CampaignEntity toEntity(CampaignDto campaignDto);

    CampaignDto toDto(CampaignEntity campaignEntity);
}

