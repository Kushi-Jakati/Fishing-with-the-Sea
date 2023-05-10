package edu.guilford;

import java.io.File;

import javafx.animation.Animation;
import javafx.animation.TranslateTransition;
import javafx.scene.image.ImageView;

/**
 * This class is for the fisherman object. It extends the GraphicalObjects class
 * and implements the Transitionable interface.
 * @param image is the image of the fisherman
 * @param transition is the transition of the fisherman (however, we never used it, it was a bonus feature to have the fisherman move across the screen, but time did not allow it)
 */
public class Fisherman extends GraphicalObjects {
    private ImageView image;
    private TranslateTransition transition;

    public Fisherman(ImageView image, TranslateTransition transition) {
        this.image = image;
        this.transition = transition;
    }

    /**
     * Constructor that sets the location of the fisherman object, and adds the
     * image
     */
    public Fisherman() {
        super();
        xPosition = 325;
        yPosition = 165;
        image = new ImageView(
                new File(FishingPane.class.getResource("fisherman.png").getPath()).toURI().toString());
    }

    /** Getters and setters for the fisherman object */
    public ImageView getImage() {
        return image;
    }

    public TranslateTransition getTransition() {
        return transition;
    }

    public void setImage(ImageView image) {
        this.image = image;
    }

    public void setTransition(TranslateTransition transition) {
        this.transition = transition;
    }

    public int getXPosition() {
        return xPosition;
    }

    public int getYPosition() {
        return yPosition;
    }

    /** Method transition that could be used to move the fisherman object */
    @Override
    public void transition(ImageView image) {
    }

    /** Transition method that is inherited from the GraphicalObjects superclass */
    public void setPosition(int x, int y) {
        image.setX(x);
        image.setY(y);
    }

}