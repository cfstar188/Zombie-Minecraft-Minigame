package me.cfstar188.zombiegame.builders;

import me.cfstar188.zombiegame.items.HealingItem;

/*
Pertains to the builder design pattern
*/
public class HealingItemBuilder {

    private double healthRestored;
    private double waitingTime;

    public HealingItemBuilder setHealthRestored(double healthRestored) {
        this.healthRestored = healthRestored;
        return this;
    }

    public HealingItemBuilder setWaitingTime(double waitingTime) {
        this.waitingTime = waitingTime;
        return this;
    }

    public HealingItem build() {
        return new HealingItem(healthRestored, waitingTime);
    }
}
