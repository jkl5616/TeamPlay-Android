package skku.teamplay.models;


public class MainQuest {
    private String title;
    private String count;
    private String nextID;

    public MainQuest() {    }

    public MainQuest(String title, String count, String nextID) {
        this.title = title;
        this.count = count;
        this.nextID = nextID;
    }

    public void delete (){
        //delete this quest
    }
    public void change () {
        // change this quest
    }

    public String getTitle() { return title; }
    public String getCount() { return count; }
    public String getNextID() { return nextID; }


    public void setTitle(String title) { this.title = title; }
    public void setCount(String count) { this.count = count; }
    public void setNextID(String nextID) { this.nextID = nextID; }
}
