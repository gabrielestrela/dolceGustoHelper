package br.com.nespressohelper.daitan.nespressohelper;

/**
 * Created by estrela on 12/21/17.
 */

public class Coffee {

    private String name;
    private int capsules;
    private int bars1;
    private int bars2;
    private String description;
    private String imgName;
//    private int imageId;
    private byte[] image;

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

    public String getDescription() {
        return description;
    }

    public byte[] getImage() {
        return image;
    }

    public String getImgName() {
        return imgName;
    }

//    public int getImageId() {
//        return imageId;
//    }

    public void setImgName(String imgName) {
        this.imgName = imgName;
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

    public void setDescription(String description) {
        this.description = description;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

//    public void setImageId(int imageId) {
//        this.imageId = imageId;
//    }
}
