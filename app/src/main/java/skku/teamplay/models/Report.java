package skku.teamplay.models;

import android.graphics.Color;

import com.github.mikephil.charting.data.PieEntry;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Report{
    private String description, title;
    private int value, max, color;
    private List<Integer> values = new ArrayList<>();

    public Report(String description, String title) {
        this.title = title;
        this.description = description;

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

    public void setRandomValues(int n){
        Random rand = new Random();
        for (int i = 0; i < n; i++){
            values.add(rand.nextInt(100) + 1);
        }
    }

    public List<PieEntry> getEntries(){
        List<PieEntry> entries = new ArrayList<>();
        for (Integer num : values){
            entries.add(new PieEntry(num));
        }

        return entries;
    }

}

