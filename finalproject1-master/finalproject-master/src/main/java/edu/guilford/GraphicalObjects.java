package edu.guilford;

import javafx.scene.image.ImageView;

public abstract class GraphicalObjects {
    // abstract class for all graphical objects in the game; they all have an x and
    // y, and may need to be transitioned in some way
    protected int xPosition;
    protected int yPosition;

    // abstract method for transitioning the object
    public abstract void transition(ImageView image);

}
