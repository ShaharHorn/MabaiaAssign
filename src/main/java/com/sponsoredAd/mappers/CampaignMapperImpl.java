package com.sponsoredAd.mappers;

import com.sponsoredAd.DTOs.CampaignDto;
import com.sponsoredAd.entities.CampaignEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;


@Service
public class CampaignMapperImpl implements CampaignMapper {
    final static long CAMPAIGN_ACTIVE_DAYS = 10;

    @Override
    public CampaignEntity toEntity(CampaignDto campaignDto) {
        if ( campaignDto == null ) {
            return null;
        }
        CampaignEntity campaignEntity = new CampaignEntity();
        campaignEntity.setName( campaignDto.getName() );
        campaignEntity.setCategory( campaignDto.getCategory());
        campaignEntity.setBid(campaignDto.getBid());
        campaignEntity.setStartDate(campaignDto.getStartDate());

        if(campaignDto.getStartDate() != null) {
            campaignEntity.setEndDate(campaignDto.getStartDate().plusDays(CAMPAIGN_ACTIVE_DAYS));
        }
        if(LocalDate.now().isAfter(campaignEntity.getStartDate().minusDays(1)) && LocalDate.now().isBefore(campaignEntity.getEndDate().plusDays(1)))
        {
            campaignEntity.setActive(true);
        }

        return campaignEntity;
    }

    @Override
    public CampaignDto toDto(CampaignEntity campaignEntity) {
        if(campaignEntity == null)
        {
            return null;
        }
        CampaignDto campaignDto = new CampaignDto();
        campaignDto.setBid(campaignEntity.getBid());
        campaignDto.setCategory(campaignEntity.getCategory());
        campaignDto.setName(campaignEntity.getName());
        campaignDto.setStartDate(campaignEntity.getStartDate());

        return campaignDto;
    }
}
