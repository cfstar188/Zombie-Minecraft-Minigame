package me.cfstar188.zombiegame.items;

public class HealingItem {

    private final long healthRestored;
    private final long waitingTime;

    public HealingItem(long healthRestored, long waitingTime) {
        this.healthRestored = healthRestored;
        this.waitingTime = waitingTime;
    }

    public long getHealthRestored() {
        return healthRestored;
    }

    public long getWaitingTime() {
        return waitingTime;
    }

}
