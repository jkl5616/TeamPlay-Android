package skku.teamplay.model;

public class Course {
    private String name;
    private String prof;
    private int top;
    private int start;
    private int height;
    private int time;
    private transient int day;

    public Course() {

    }

    public Course(String name, String prof, int top, int start, int height, int time) {
        this.name = name;
        this.prof = prof;
        this.top = top;
        this.start = start;
        this.height = height;
        this.time = time;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProf() {
        return prof;
    }

    public void setProf(String prof) {
        this.prof = prof;
    }

    public int getTop() {
        return top;
    }

    public void setTop(int top) {
        this.top = top;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    @Override
    public boolean equals(Object o) {
        if(o instanceof Course) {
            Course c = (Course) o;
            return c.getName().equals(name) && c.getHeight() == height && c.getProf().equals(prof) && c.getTop() == top;
        }
        return false;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }
}
