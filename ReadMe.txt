Hey,

A little brief about my implementation :

I Used those properties :

    ConcurrentHashMap<String, ArrayList<Product>> categoryToProductsHash  :
        saves category name to products hash.
        when campaign is active, the products of the same category are ready to promote.

    ConcurrentHashMap<String, ArrayList<CampaignEntity>> categoryToCampaignsHash :
        saves all the campaigns active and deactivate campaigns.

    ConcurrentHashMap<String, Product> categoryToMaxPriceProduct
        saves the max price product for each active campaign category.

    ConcurrentHashMap<String, ArrayList<CampaignEntity>> categoryToActiveCampaignsHash
           saves all the active campaigns.

    Product maxPriceActiveProduct
        saves the promoted product with the max price.



- Implementation :
    create Campaign :
        gets a CampaignDTO  map it to campaignEntity ( same object with Date endDate, and Bollean isActive properties)
        and save the new campaign.

    in the background :
        there is a thread that will run every 24 hours (can be changed), that will check if there is new Active campaigns,
        or if there is campaigns that have to be deactivated, and update max price active products.


Thanks a lot,
Shahar.