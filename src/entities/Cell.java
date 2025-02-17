package src.entities;

import src.game.Color;

public class Cell {
    private Color color;
    private String imageFileName;

    public Cell() {
        color = new Color(0, 0, 0);
        // creates an instance of color object with input values
        imageFileName = null;
    }

    public void setColor(Color c) {
        color = c;
    }

    public Color getColor() {
        return color;
    }

    public String getImageFileName() {
        return imageFileName;
    }

    public void setImageFileName(String fileName) {// this is just a setter for images
        imageFileName = fileName;
    }
}