/*
 * Copyright (c) 2015, 2099, Ashok and/or its affiliates. All rights reserved.
 * ASHOK PROPRIETARY/CONFIDENTIAL. Use is subject to license terms, But you are free to use it :).
 *
 */
package com.ashok.hiring.flipkartad;

import com.ashok.lang.inputs.InputReader;
import com.ashok.lang.inputs.Output;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

/**
 * Problem Name:
 * Link:
 *
 * @author Ashok Rajpurohit (ashok1113@gmail.com)
 */
public class AdEngine {
    public static Output out = new Output();
    public static InputReader in = new InputReader();

    public static void main(String[] args) throws IOException {
        try {
            solve();
        } finally {
            in.close();
            out.close();
        }
    }

    private static void solve() throws IOException {
        while (true) {

            out.println("Advertisements: " + Advertisements.advertisements);
            out.println("Advertisers: " + Advertisers.ADVERTISERS);
            out.println("Users: " + User.users);
            out.flush();
            try {
                startBidding(Advertisements.advertisements.get(in.readInt()));
                System.out.println("Enter the advertisement and advertiser ids");
                Advertisement advertisement = Advertisements.advertisements.get(in.readInt());
                Advertiser advertiser = Advertisers.getAdvertiser(in.readInt());
                System.out.println("Advertimsent: " + advertisement + ", ser" + advertiser);
                System.out.println("Enter the budget for criteria");
                addCriteria(advertiser, advertisement, in.readInt());
                System.out.println("Enter the user id for ad");
                int id = in.readInt();
                AdvertisementService.showAd(User.users.get(id));
            } catch (BusinessException e) {
                e.printStackTrace();
                continue;
            }
            out.println(in.read());
            out.flush();
        }
    }

    static void startBidding(Advertisement advertisement) throws BusinessException {
        synchronized (advertisement) {
            List<Bid> bids = collectBids(advertisement);
            BidSelector selector = getStrategy();
            Bid bid = BidSelectorService.selectBid(bids, selector);
            AdvertisementService.applyBid(bid, advertisement);
        }
    }

    static void addCriteria(Advertiser advertiser, Advertisement advertisement, int budget) throws BusinessException {
        AdvertisementService.createCriteria(advertisement, advertiser, budget);
    }

    public static BidSelector getStrategy() {
        try {
            out.println("Enter strategy");
            out.flush();
            BidSelectingStrategy strategy = BidSelectingStrategy.valueOf(in.read());
            return strategy;
        } catch (Exception e) {
            e.printStackTrace();
            out.println("Please retry");
            out.flush();
            return getStrategy();
        }
    }

    public static List<Bid> collectBids(Advertisement advertisement) {
        List<Bid> bids = new LinkedList<>();
        Random random = new Random();
        Advertisers.ADVERTISERS.forEach((a) -> bids.add(new Bid(a, random.nextInt(100) + 10)));
        return bids;
    }
}
