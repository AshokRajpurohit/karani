package com.ashok.hiring.flipkartad;

import java.util.Set;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author Ashok Rajpurohit (ashok1113@gmail.com).
 */
public class Advertisement {
    public final int id;
    public final int costPerAd;
    public Advertiser advertiser = Advertiser.DEFAULT; // No One.
    Set<AdvertisementCriteria> criterias = new ConcurrentSkipListSet<>();
    volatile AtomicInteger budget = new AtomicInteger(0);

    public Advertisement(int id, int cost) {
        this.id = id;
        costPerAd = cost;
    }

    public void setAdvertiser(Advertiser advertiser) throws BusinessException {
        synchronized (this) {
            if (budget.get() > 0)
                throw new BusinessException("Advertisement is not available for bid " + this);

            this.advertiser = advertiser;
        }
    }

    public void addCriteria(int fromAge, int toAge, char gender, int budget) {
        synchronized (this) {
            AdvertisementCriteria criteria = new AdvertisementCriteria(this, fromAge, toAge, gender);
            criteria.budget.addAndGet(budget);
            this.budget.addAndGet(budget);
        }
    }

    public int hashCode() {
        return id;
    }

    public boolean equals(Object o) {
        return (o instanceof Advertisement) && (id == ((Advertisement) o).id);
    }

    public AdvertisementCriteria findAd(User user) {
        synchronized (this) {
            return criterias.stream().filter(t -> t.budget.get() > 0).filter((t) -> t.gender == Gender.UNISEX || t.gender == user.gender).filter((t) -> t.ageFrom >= user.age && t.ageTo <= user.age).findAny().get();
        }
    }

    public String toString() {
        return "" + id;
    }

    public AdvertisementCriteria showAdd(User user) throws BusinessException {
        synchronized (this) {
            if (budget.get() <= 0) throw new BusinessException("No Budget remained");
            AdvertisementCriteria criteria = findAd(user);
            criteria.budget.decrementAndGet();
            budget.decrementAndGet();
            return criteria;
        }
    }
}
