package skku.teamplay.models;

import android.graphics.Color;

public class Report{
    private String description, title;
    private int value, max, color;

    public Report(String description, String title, int value, int max) {
        this.title = title;
        this.description = description;
        this.value = value;
        this.max = max;

        this.color = Color.BLUE;
    }
    public String getTitle(){
        return this.title;
    }
    public String getDescription(){
        return this.description;
    }
    public int getMax(){
        return this.max;
    }
    public int getValue(){
        return this.value;
    }
    public void setTitle(String title){
        this.title = title;
    }
    public void setDescription(String description){
        this.description = description;
    }
    public void setValue(int value){
        this.value = value;
    }
    public void setColor(int color){
        this.color = color;
    }
    public int getColor(){
        return this.color;
    }
}

