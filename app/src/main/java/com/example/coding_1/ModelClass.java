package com.example.coding_1;

public class ModelClass {
    int photo;
    String name,town;

    public ModelClass(int photo,String name,String town){
        this.photo=photo;
        this.name=name;
        this.town=town;

    }

    public int getPhoto(){
        return photo;
    }

    public void setPhoto(int photo){
        this.photo=photo;
    }

    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name=name;
    }

    public String getTown() {
        return town;
    }

    public void setTown(String town) {
        this.town = town;
    }
}
