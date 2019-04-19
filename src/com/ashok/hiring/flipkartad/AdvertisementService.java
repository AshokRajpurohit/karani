package com.ashok.hiring.flipkartad;

import com.ashok.lang.inputs.InputReader;

import java.io.IOException;

/**
 * @author Ashok Rajpurohit (ashok1113@gmail.com).
 */
public class AdvertisementService {
    static InputReader in = AdEngine.in;

    public static void applyBid(Bid bid, Advertisement advertisement) throws BusinessException {
        advertisement.setAdvertiser(bid.advertiser);
        System.out.println("Advertiser: " + bid.advertiser + " has the ad: " + advertisement);
    }

    public static void createCriteria(Advertisement advertisement, Advertiser advertiser, int budget) throws BusinessException {
        if (advertisement.advertiser != Advertiser.DEFAULT && advertisement.advertiser != advertiser)
            throw new BusinessException("Advertiser " + advertiser.name + " does not own the advertisement: " + advertisement.id);

        System.out.println("Enter the start age, end age, sex [M, F, X]");
        try {
            advertisement.addCriteria(in.readInt(), in.readInt(), in.read().charAt(0), budget);
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        System.out.println("criteria added for: " + advertisement + ", " + advertiser);
    }

    public static AdvertisementCriteria showAd(User user) throws BusinessException {
        AdvertisementCriteria criteria = null;
        for (Advertisement advertisement : Advertisements.advertisements) {
            try {
                criteria = advertisement.showAdd(user);
                break;
            } catch (Exception e) {
//                e.printStackTrace(); // no need to print anything.
            }
        }

        if (criteria == null) throw new BusinessException("No Add found for the user");
        System.out.println("add is shown to user is: " + criteria.advertisement.id);
        return criteria;
    }
}
