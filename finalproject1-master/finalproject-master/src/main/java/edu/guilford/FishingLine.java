package edu.guilford;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.util.Duration;

public class FishingLine extends GraphicalObjects {
    int endingX;
    int endingY;
    Line line;
    Timeline lineTransition;
    Timeline circleTransition;

    // constructor
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

    // getters and setters
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

    // transition method inherited from GraphicalObjects
    @Override
    public void transition(ImageView image) {
        ;
    }

    // TransitionlineUpward method
    public void transitionLineUpward() {
        lineTransition = new Timeline();
        lineTransition.getKeyFrames()
                .add(new KeyFrame(Duration.seconds(7),
                        new KeyValue(line.endXProperty(), 339),
                        new KeyValue(line.endYProperty(), 183)));
        lineTransition.setCycleCount(1);
        lineTransition.play();
    }

    // transitionLineDownward method
    public void transitionLineDownward() {
        // add a circle to the end of the line
        Circle circle = new Circle(317, 827, 5);
        lineTransition = new Timeline();
        lineTransition.getKeyFrames()
                .add(new KeyFrame(Duration.seconds(7),
                        new KeyValue(line.endXProperty(), 317),
                        new KeyValue(line.endYProperty(), 827)));

        // have the circle follow the line
        lineTransition.getKeyFrames()
                .add(new KeyFrame(Duration.seconds(7),
                        new KeyValue(circle.centerXProperty(), 339),
                        new KeyValue(circle.centerYProperty(), 183)));
        lineTransition.setCycleCount(1);
        lineTransition.play();
    }
}