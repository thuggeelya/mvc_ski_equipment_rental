package org.example.web.dto;

public class Equipment {
    private Integer id;
    private String name;
    private String firmName;
    private String cost;
    private User owner;
    private String description;
    private boolean isFave = false;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFirmName() {
        return firmName;
    }

    public void setFirmName(String firmName) {
        this.firmName = firmName;
    }

    public String getCost() {
        return cost;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }

    public boolean isFave() {
        return isFave;
    }

    public void setFave() {
        isFave = !isFave;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Equipment{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", firmName='" + firmName + '\'' +
                ", cost='" + cost + '\'' +
                ", owner=" + owner +
                ", description='" + description + '\'' +
                ", isFave=" + isFave +
                '}';
    }
}