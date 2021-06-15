package sample;

import java.awt.image.BufferedImage;

public class FinalMap {

    public String name;
    public BufferedImage icon;
    public String type;

    public String getName() {
        return name;
    }

    public void setName (String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType (String type) {
        this.type = type;
    }

    public BufferedImage getIcon() {
        return icon;
    }

    public void setIcon (BufferedImage icon) {
        this.icon = icon;
    }

}
