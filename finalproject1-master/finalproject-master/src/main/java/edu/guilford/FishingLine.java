package edu.guilford;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.util.Duration;

/**
 * FishingLine class is a subclass of GraphicalObjects and is used to create a
 * line that is used in the fishing game
 * @param endingX is the ending x position of the line
 * @param endingY is the ending y position of the line
 * @param line is the line object
 * @param lineTransition is the transition of the line
 * @param circleTransition is the transition of the circle, following the line
 */
public class FishingLine extends GraphicalObjects {
    int endingX;
    int endingY;
    Line line;
    Timeline lineTransition;
    Timeline circleTransition;

    /**
     * FishingLine constructor that sets the x and y positions of the line and the
     * ending x and y positions of the line
     */
    public FishingLine() {
        super();
        xPosition = 339;
        yPosition = 183;
        endingX = 320;// 339
        endingY = 300; // 650
        line = new Line(xPosition, yPosition, endingX, endingY);
        // set line color
        line.setStrokeWidth(2);
        line.setStroke(javafx.scene.paint.Color.BLACK);

    }

    /** Getters and Setters */
    public int getEndingX() {
        return endingX;
    }

    public int getEndingY() {
        return endingY;
    }

    public Line getLine() {
        return line;
    }

    public void setEndingX(int endingX) {
        this.endingX = endingX;
    }

    public void setEndingY(int endingY) {
        this.endingY = endingY;
    }

    public void setLine(Line line) {
        this.line = line;
    }

    /** Transition method that is inherited from GraphicalObjects */
    @Override
    public void transition(ImageView image) {
        ;
    }

    /** TransitionlineUpward method that moves the line up the screen */
    public void transitionLineUpward() {
        lineTransition = new Timeline();
        lineTransition.getKeyFrames()
                .add(new KeyFrame(Duration.seconds(7),
                        new KeyValue(line.endXProperty(), 339),
                        new KeyValue(line.endYProperty(), 183)));
        lineTransition.setCycleCount(1);
        lineTransition.play();
    }

    /** TransitionLineDownward method that moves the line down the screen */
    public void transitionLineDownward() {
        /** Add a circle to the end of the line **/
        Circle circle = new Circle(317, 827, 5);
        lineTransition = new Timeline();
        lineTransition.getKeyFrames()
                .add(new KeyFrame(Duration.seconds(7),
                        new KeyValue(line.endXProperty(), 317),
                        new KeyValue(line.endYProperty(), 827)));

        /** Circle that follows the line */
        lineTransition.getKeyFrames()
                .add(new KeyFrame(Duration.seconds(7),
                        new KeyValue(circle.centerXProperty(), 339),
                        new KeyValue(circle.centerYProperty(), 183)));
        lineTransition.setCycleCount(1);
        lineTransition.play();
    }
}