package me.cfstar188.zombiegame.items;

public class HealingItem {

    private final double healthRestored;
    private final double waitingTime;

    public HealingItem(double healthRestored, double waitingTime) {
        this.healthRestored = healthRestored;
        this.waitingTime = waitingTime;
    }

    public double getHealthRestored() {
        return healthRestored;
    }

    public double getWaitingTime() {
        return waitingTime;
    }

}
