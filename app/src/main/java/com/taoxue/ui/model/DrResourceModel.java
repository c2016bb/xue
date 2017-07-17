package com.taoxue.ui.model;

/**
 * Created by User on 2017/6/21.
 */

public class DrResourceModel extends BaseModel {
          private String description;
            private String name;
            private String resource_picture;

    @Override
    public String toString() {
        return "DrResourceModel{" +
                "description='" + description + '\'' +
                ", name='" + name + '\'' +
                ", resource_picture='" + resource_picture + '\'' +
                '}';
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getResource_picture() {
        return resource_picture;
    }

    public void setResource_picture(String resource_picture) {
        this.resource_picture = resource_picture;
    }
}
