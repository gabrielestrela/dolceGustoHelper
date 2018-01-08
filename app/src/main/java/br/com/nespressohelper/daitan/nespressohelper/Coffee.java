package br.com.nespressohelper.daitan.nespressohelper;

import com.google.gson.annotations.SerializedName;

/**
 * Created by estrela on 12/21/17.
 */

public class Coffee {

    private String name;
    private int capsules;

    @SerializedName("mainWater")
    private int bars1;

    @SerializedName("mainMilk")
    private int bars2;

    private int intensity;
    private String description;
    private transient byte[] image;

    public String getName() {
        return name;
    }

    public int getCapsules() {
        return capsules;
    }

    public int getBars1() {
        return bars1;
    }

    public int getBars2() {
        return bars2;
    }

    public int getIntensity() {
        return intensity;
    }

    public String getDescription() {
        return description;
    }

    public byte[] getImage() {
        return image;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCapsules(int capsules) {
        this.capsules = capsules;
    }

    public void setBars1(int bars1) {
        this.bars1 = bars1;
    }

    public void setBars2(int bars2) {
        this.bars2 = bars2;
    }

    public void setIntensity(int intensity) {
        this.intensity = intensity;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return "Name: " + getName() + '\n' + "bars1: " + getBars1() + '\n' + "bars2: " + getBars2() + "\n" + "Intensity: " + getIntensity() + "\n" + "Description: " + getDescription();
    }
}
