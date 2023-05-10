package edu.guilford;

import javafx.scene.image.ImageView;

/**
 * This is the abstract superclass for all graphical objects in the game.
 * It contains the x and y positions of the object, and an abstract method for
 * transitioning the object.
 * @param xPosition is the x position of the object
 * @param yPosition is the y position of the object
 */
public abstract class GraphicalObjects {

    protected int xPosition;
    protected int yPosition;

    /** Abstract method for transitioning the object. The parameter is the image of the object. */
    public abstract void transition(ImageView image);

}
