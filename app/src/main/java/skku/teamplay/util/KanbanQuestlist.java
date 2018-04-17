package skku.teamplay.util;

public class KanbanQuestlist {
    private String itemTitle;
    private String itemDescription;
    private String itemDate;
    private String itemOwner;

    public void setTitle(String title) { itemTitle = title; }
    public void setItemDescription(String description) { itemDescription = description; }
    public void setItemDate(String date) { itemDate = date; }
    public void setItemOwner(String owner) { itemOwner = owner; }

    public String getItemTitle() { return this.itemTitle; }
    public String getItemDescription() { return this.itemDescription; }
    public String getItemDate() { return this.itemDate; }
    public String getItemOwner() { return this.itemOwner; }
}