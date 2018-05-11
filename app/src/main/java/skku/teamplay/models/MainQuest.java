package skku.teamplay.models;


public class MainQuest {
    private String title;

    public MainQuest() {    }

    public MainQuest(String title) {
        this.title = title;
    }

    public void delete (){
        //delete this quest
    }
    public void change () {
        // change this quest
    }

    public String getTitle() { return title; }

    public void setTitle(String title) { this.title = title; }
}
