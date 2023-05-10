package edu.guilford;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.effect.Glow;
import javafx.scene.image.ImageView;
import javafx.animation.PathTransition;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

/** Public Class Fishing Pane is where all the SeaCreature objects and from other classes will be instantiated into our mulitple panes
 * @param GAME_OVER_TIME is the time that the game will end
 * @param totalPoints is the total points that the user has earned
 * @param seaCreaturesArray is the array that will hold all the SeaCreature objects
 * @param scoreLabel is the label that will display the score
 * @param modeLabel is the label that will display the mode
 * @param seaCreature is the SeaCreature object that will be instantiated
 * @param catchCircle is the circle that will be used to catch the SeaCreature objects
 * @param lineTransition is the transition of the line
 * @param circleTransition is the transition of the circle
 * @param caught is the ArrayList that will hold all the SeaCreature objects that have been caught
 * @param caughtCreatures is the label that will display the SeaCreature objects that have been caught
 */

public class FishingPane extends Pane {

        /** "caught" static ArrayList that will be called in the HelpAnalysis Pane class  */
        static ArrayList<SeaCreatures> caught = new ArrayList<SeaCreatures>();

        /** create a getter for "caught" static ArrayList in order to reference it in methods  */
        public ArrayList<SeaCreatures> getCaught() {
                return caught;
        }

        final int GAME_OVER_TIME = 300;

        static int totalPoints;

        /** SeaCreature Array for the FishingPane class that generates all the objects on the screen*/ 
        ArrayList<SeaCreatures> seaCreaturesArray = new ArrayList<SeaCreatures>();

        /** Add a getter for the SeaCreatures array for the same reason we added a getter for the caught array */
        public ArrayList<SeaCreatures> getSeaCreaturesArray() {
                return seaCreaturesArray;
        }

        /** Instantiate a score label */
        Label scoreLabel = new Label("Score: 0");

        /** Instantiate a mode label */
        Label modeLabel = new Label("Mode: Easy");

        /** Instantiate a SeaCreature object */
        SeaCreatures seaCreature = new SeaCreatures();

        /** Instantiate a circle to be used as the catch circle (AKA hook) */
        Circle catchCircle;

        /** Instantiate a line transition */
        public Circle getCatchCircle() {
                return catchCircle;
        }

        boolean gameComplete = false;

        Random random = new Random();
        Timer timer;

        Button stopButton = new Button("Stop");

        FishingLine fishingLine = new FishingLine();

        // create a getter method for fishingLine

        // Constructor
        public FishingPane(SeaCreatures seaCreature) {
                this.seaCreature = seaCreature;

                int numOfSeaCreatures = 50;

                // smaller screen, make line move a little faster, levels (easy, medium, hard),
                // have wooden stick anywhere, if all fish from the array are gone, up the level
                // or end the game

                System.out.println(seaCreature.getName());

                Timer countingTimer = new Timer();

                TimerTask task = new TimerTask() {

                        private int secondsPassed = 0;
                        Label timerLabel = new Label("Time: " + 0);

                        @Override
                        public void run() {
                                if (secondsPassed >= 0) {
                                        // System.out.println(secondsPassed + " seconds");
                                        secondsPassed = secondsPassed + 1;
                                }
                                if (secondsPassed > GAME_OVER_TIME) {
                                        System.out.println("Game Over");
                                        countingTimer.cancel();
                                        gameComplete = true;
                                        fishingLine.transitionLineUpward();
                                        //have it so that the poin
                                }
                                Platform.runLater(() -> {
                                        if (secondsPassed >= GAME_OVER_TIME) {
                                                timerLabel.setText("Game Over");
                                                // set the proportions of the label so it can fit gameover
                                                timerLabel.setPrefSize(120, 50);
                                                // have label scooch over to the left to be aligned with the score label
                                                timerLabel.setLayoutX(380);
                                        } else {
                                                timerLabel.setText("Time: " + secondsPassed);
                                                timerLabel.setFont(new Font("Arial", 20));
                                                timerLabel.setTextFill(Color.WHITE);
                                                timerLabel.setLayoutX(400);
                                                timerLabel.setLayoutY(75);
                                                // set the size of the label
                                                timerLabel.setPrefSize(100, 50);
                                                // set the background of time label to be cadet blue
                                                timerLabel.setStyle("-fx-background-color: darkblue");
                                                // center the text
                                                timerLabel.setAlignment(Pos.CENTER);
                                                Glow glow = new Glow();
                                                glow.setLevel(10);
                                                timerLabel.setEffect(glow);
                                                try {
                                                        getChildren().add(timerLabel);
                                                } catch (Exception e) {
                                                }
                                        }
                                });
                        }
                };

                countingTimer.scheduleAtFixedRate(task, 0, 1000);

                // have sea creature objects from indices 0-24 appear on the screen between 0
                // and 30 seconds
                Timer timer1 = new Timer();
                TimerTask task1 = new TimerTask() {
                        @Override
                        public void run() {
                                for (int number = 0; number < 25; number++) {
                                        SeaCreatures seaCreatureObject = new SeaCreatures();
                                        seaCreaturesArray.add(seaCreatureObject);
                                        getChildren().add(seaCreatureObject.getImage());
                                }
                        }
                };

                // instantiating the fisherman
                Fisherman fisherman = new Fisherman();
                fisherman.setPosition(fisherman.getXPosition(), fisherman.getYPosition());

                // create an array list of 9 random sea creatures
                for (int number = 0; number < numOfSeaCreatures; number++) {
                        SeaCreatures seaCreatureObject = new SeaCreatures();
                        seaCreaturesArray.add(seaCreatureObject);
                        this.getChildren().add(seaCreatureObject.getImage());
                        // for (int cycleCount = seaCreatureObject.getTransition().getCycleCount();
                        // cycleCount % 1 == cycleCount; cycleCount++) {
                        // //remove the old fish
                        // this.getChildren().remove(seaCreatureObject.getImage());
                        // seaCreatureObject = new SeaCreatures();
                        // //seaCreatureObject.transition(seaCreatureObject.getImage());
                        // System.out.println(seaCreatureObject.getTransition().getCycleCount());
                        // }
                }

                // iterate through the array list and use transition method to animate each sea
                // creature
                for (int number = 0; number < seaCreaturesArray.size(); number++) {
                        SeaCreatures seaCreatureObject = (SeaCreatures) seaCreaturesArray.get(number);
                        seaCreatureObject.transition(seaCreatureObject.getImage());
                        // set the transition to repeat forever
                        // seaCreatureObject.getTransition().setOnFinished(e -> {
                        // //have the transition repeat forever
                        // //remove the image from the pane and array list
                        // this.getChildren().remove(seaCreatureObject.getImage());
                        // //remove from the array list
                        // //seaCreaturesArray.remove(seaCreatureObject);
                        // //add a new random one to the pane and array list
                        // SeaCreatures newSeaCreatureObject = new SeaCreatures();
                        // //set all properties of the new sea creature to the old one
                        // seaCreatureObject.setName(newSeaCreatureObject.getName());
                        // seaCreatureObject.setPoints(newSeaCreatureObject.getPoints());
                        // seaCreatureObject.setImage(newSeaCreatureObject.getImage());
                        // seaCreatureObject.setTransition(newSeaCreatureObject.getTransition());
                        // seaCreatureObject.transition(seaCreatureObject.getImage());
                        // this.getChildren().add(seaCreatureObject.getImage());
                        // //newSeaCreatureObject.transition(newSeaCreatureObject.getImage());
                        // });

                }

                // for loop for seaCreatures that once the transition is finished, it removes
                // the image from the pane and the array list and adds a new random one
                // to the pane and array list. It keeps doing this every time the transition is
                // finished for each object, so it is constantly adding and removing
                // objects from the pane and array list

                // get each element of the arraylist
                SeaCreatures seaCreature1 = (SeaCreatures) seaCreaturesArray.get(0);
                SeaCreatures seaCreature2 = (SeaCreatures) seaCreaturesArray.get(1);
                SeaCreatures seaCreature3 = (SeaCreatures) seaCreaturesArray.get(2);
                SeaCreatures seaCreature4 = (SeaCreatures) seaCreaturesArray.get(3);
                SeaCreatures seaCreature5 = (SeaCreatures) seaCreaturesArray.get(4);
                SeaCreatures seaCreature6 = (SeaCreatures) seaCreaturesArray.get(5);
                SeaCreatures seaCreature7 = (SeaCreatures) seaCreaturesArray.get(6);
                SeaCreatures seaCreature8 = (SeaCreatures) seaCreaturesArray.get(7);
                SeaCreatures seaCreature9 = (SeaCreatures) seaCreaturesArray.get(8);

                // event listener for each cycle of the transition

                // for each new cycle of the transition, have a new random sea creature appear
                // and replace the attributes of the old one

                // //while loop for each sea creature that once the transition is finished, it
                // removes the image from the pane and the array list and adds a new random one
                // //to the pane and array list. It keeps doing this every time the transition
                // is finished for each object, so it is constantly adding and removing

                // //make this transition repeat forever
                // seaCreature1.getTransition().setOnFinished(e -> {
                // //have the transition repeat forever
                // //remove the image from the pane and array list
                // //this.getChildren().remove(seaCreature1.getImage());
                // //remove from the array list
                // //seaCreaturesArray.remove(seaCreatureObject);
                // //add a new random one to the pane and array list
                // SeaCreatures newSeaCreatureObject = new SeaCreatures();
                // //set all properties of the new sea creature to the old one
                // seaCreature1.setName(newSeaCreatureObject.getName());
                // seaCreature1.setPoints(newSeaCreatureObject.getPoints());
                // seaCreature1.setImage(newSeaCreatureObject.getImage());
                // seaCreature1.setTransition(newSeaCreatureObject.getTransition());
                // seaCreature1.transition(seaCreature1.getImage());
                // this.getChildren().add(seaCreature1.getImage());
                // //newSeaCreatureObject.transition(newSeaCreatureObject.getImage());
                // });

                // will do rest if the code above works

                // add the fisherman and fishingline to the pane
                this.getChildren().add(fisherman.getImage());
                this.getChildren().add(fishingLine.getLine());

                // Setting circle properties so it follows the fishingline
                // add a circle to the line that follows the line
                Line line = fishingLine.getLine();
                catchCircle = new Circle(line.getEndX(), line.getEndY(), 5);
                this.getChildren().add(catchCircle);
                // have circle folllow the end of the line
                catchCircle.centerXProperty().bind(line.endXProperty());
                catchCircle.centerYProperty().bind(line.endYProperty());

                System.out.println(caught.size());

                // For the score label
                // add the score label to the pane and set its properties]
                this.getChildren().add(scoreLabel);
                // add it to the top middle
                scoreLabel.setLayoutX(400);
                scoreLabel.setLayoutY(10);
                // add purple background to score label
                scoreLabel.setStyle("-fx-background-color: PURPLE;");
                // add white text to score label
                scoreLabel.setTextFill(Color.WHITE);
                // make font size 20
                scoreLabel.setFont(new Font(20));
                // make label same size as button
                scoreLabel.setPrefSize(100, 50);
                // make text centered
                scoreLabel.setAlignment(Pos.CENTER);

                // add mode label
                this.getChildren().add(modeLabel);
                // add it to the bottom left corner
                modeLabel.setLayoutX(10);
                modeLabel.setLayoutY(700);
                // add purple background to mode label
                modeLabel.setStyle("-fx-background-color: PURPLE;");
                // add white text to mode label
                modeLabel.setTextFill(Color.WHITE);
                // make font size 20
                modeLabel.setFont(new Font(20));
                // make label fit text
                modeLabel.setPrefSize(130, 50);
                // make text centered
                modeLabel.setAlignment(Pos.CENTER);

                modeLabel.setVisible(false);

                // parameters represent x1, y1, x2, y2 which are the starting and ending points
                // of the line
                // -467/14
                // have the line
                line = new Line(339, 183, 339, 650);
                line.setStrokeWidth(2);
                line.setStartX(339);
                line.setStartY(183);
                line.setEndX(317);
                line.setEndY(827);

                // use translate transition to move line downward
                PathTransition lineTransition = new PathTransition();
                lineTransition.setNode(line);
                lineTransition.setDuration(javafx.util.Duration.seconds(10));
                lineTransition.setPath(line);
                lineTransition.play();

                // add buttons
                // add help button to the top right of the pane
                Button helpButton = new Button("Help");
                helpButton.setLayoutX(780);
                helpButton.setLayoutY(10);
                helpButton.setPrefSize(100, 20);
                // set color to purple and text to white
                helpButton.setStyle("-fx-background-color: PURPLE; -fx-text-fill: #ffffff; -fx-font-size: 20px;");
                this.getChildren().add(helpButton);

                // add save button the top left of the pane
                Button saveButton = new Button("Save");
                saveButton.setLayoutX(10);
                saveButton.setLayoutY(750);
                saveButton.setPrefSize(100, 20);
                saveButton.setStyle("-fx-background-color: PURPLE; -fx-text-fill: #ffffff;-fx-font-size: 20px;");
                saveButton.setOpacity(0);
                this.getChildren().add(saveButton);

                // Event handler that handles the keyboard input to manipulate the line and then
                // catch the fish
                this.addEventHandler(KeyEvent.ANY, new EventHandler<Event>() {

                        @Override
                        public void handle(Event event) {
                                if (event instanceof KeyEvent) {
                                        KeyEvent keyEvent = (KeyEvent) event;
                                        if (keyEvent.getCode() == KeyCode.UP) {
                                                fishingLine.transitionLineUpward();
                                                // catchFish();
                                        } else if (keyEvent.getCode() == KeyCode.DOWN) {
                                                fishingLine.transitionLineDownward();
                                                // catchFish();
                                        }
                                        // The event below allows for a specific letter to catch a fish
                                        else if (keyEvent.getCode() == KeyCode.C) {
                                                catchFish();
                                        }

                                        // The below code allows for any keyboard key to catch a fish
                                        // else if (keyEvent.getEventType() == KeyEvent.KEY_PRESSED) {
                                        // catchFish();
                                        // }

                                }

                        }

                });

                // GameTimer gameTimer = new GameTimer();
                // Label timeLabel = new Label();
                // timeLabel.setLayoutX(10);
                // timeLabel.setLayoutY(10);
                // timeLabel.setPrefSize(100, 50);
                // timeLabel.setStyle("-fx-background-color: PURPLE;");
                // timeLabel.setTextFill(Color.WHITE);
                // timeLabel.setFont(new Font(20));
                // this.getChildren().add(timeLabel);
                // while (!gameComplete) {
                // timeLabel.setText("" + gameTimer.getElapsedTimeSecs());
                // if (gameTimer.getElapsedTimeSecs() == 300) {
                // gameComplete = true;
                // }
                // }

        }

        public void catchFish() {
                for (int i = 0; i < seaCreaturesArray.size(); i++) {
                        SeaCreatures seaCreatureObject = (SeaCreatures) seaCreaturesArray.get(i);
                        // use getBoundsinParents method to see if the line intersects with the image of
                        // the sea creature
                        if (catchCircle.getBoundsInParent()
                                        .intersects(seaCreatureObject.getImage().getBoundsInParent())) {
                                // remove the image from the pane
                                this.getChildren().remove(seaCreatureObject.getImage());
                                // remove the sea creature from the array list
                                seaCreaturesArray.remove(seaCreatureObject);
                                // add the sea creature to the caught arraylist
                                caught.add(seaCreatureObject);
                                // System.out.println(caught.size());
                                // add all creature points together from the caught arraylist
                                totalPoints = 0;
                                for (int number = 0; number < caught.size(); number++) {
                                        SeaCreatures seaCreatureObject1 = (SeaCreatures) caught.get(number);
                                        totalPoints += seaCreatureObject1.getPoints();
                                        // make the score label display the total points
                                        scoreLabel.setText("Score: " + totalPoints);
                                        //if seconds passed is greater than game time, then game is over
                                        // for (int j = 0; j < seaCreaturesArray.size(); j++) {
                                        //         SeaCreatures seaCreatureObject2 = (SeaCreatures) seaCreaturesArray
                                        //                         .get(i);
                                        //         if (totalPoints > 0 && totalPoints < 10) {
                                        //                 seaCreatureObject2.setMode("easy");
                                        //                 // change mode label to medium
                                        //                 modeLabel.setText("Mode: Easy");
                                        //                 //seaCreatureObject2.transition1(seaCreatureObject2.getImage());
                                        //         } else if (totalPoints > 10 && totalPoints < 40) {
                                        //                 seaCreatureObject2.setMode("medium");
                                        //                 // change mode label to medium
                                        //                 modeLabel.setText("Mode: Medium");
                                        //                 //seaCreatureObject2.transition1(seaCreatureObject2.getImage());
                                        //         } else if (totalPoints > 40) {
                                        //                 seaCreatureObject2.setMode("hard");
                                        //                 // change mode label to medium
                                        //                 modeLabel.setText("Mode: Hard");
                                        //                 //seaCreatureObject2.transition1(seaCreatureObject2.getImage());
                                        //         }
                                        // }
                                }
                        }

                }
        }
};

class KeyPane extends Pane {

        SeaCreatures seaCreature = new SeaCreatures();

        private Label smallFishLabel;
        private Label largeFishLabel;
        private Label woodenStickLabel;
        private Label octopusLabel;

        public KeyPane() {
                // label to get points for the smallfish from the seacreature class
                SeaCreatures smallFish = new SeaCreatures("Small Fish", "easy");
                smallFishLabel = new Label("Small Fish: " + smallFish.getPoints() + " points");
                // label to get points for the largefish from the seacreature class
                SeaCreatures largeFish = new SeaCreatures("Large Fish", "easy");
                largeFishLabel = new Label("Large Fish: " + largeFish.getPoints() + " points");
                // label to get points for the woodenstick from the seacreature class
                SeaCreatures woodenStick = new SeaCreatures("Wooden Stick", "easy");
                woodenStickLabel = new Label("Wooden Stick: " + woodenStick.getPoints() + " points");
                // label to get points for the octopus from the seacreature class
                SeaCreatures octopus = new SeaCreatures("Octopus", "easy");
                octopusLabel = new Label("Octopus: " + octopus.getPoints() + " points");

                // set the layout of the labels
                smallFishLabel.setLayoutX(10);
                smallFishLabel.setLayoutY(10);
                largeFishLabel.setLayoutX(10);
                largeFishLabel.setLayoutY(30);
                woodenStickLabel.setLayoutX(10);
                woodenStickLabel.setLayoutY(50);
                octopusLabel.setLayoutX(10);
                octopusLabel.setLayoutY(70);
                this.getChildren().addAll(smallFishLabel, largeFishLabel, woodenStickLabel, octopusLabel);

                Button helpButton = new Button("Help");
                helpButton.setLayoutX(780);
                helpButton.setLayoutY(10);
                helpButton.setPrefSize(100, 20);
                // set color to purple and text to white
                helpButton.setStyle("-fx-background-color: PURPLE; -fx-text-fill: #ffffff; -fx-font-size: 20px;");
                this.getChildren().add(helpButton);

                // when the help button is clicked, the help stage will show
                helpButton.setOnAction(e -> {
                        Stage helpStage = new Stage();
                        Pane helpPane = new Pane();
                        Scene helpScene = new Scene(helpPane, 500, 500);
                        helpStage.setScene(helpScene);
                        helpStage.setTitle("Help");
                        helpStage.show();

                        // add instructions at the top of the pane
                        Label instructions = new Label("How to Play: ");
                        instructions.setAlignment(Pos.CENTER);
                        instructions.setPrefSize(500, 50);
                        // Make the label purple and text white
                        instructions.setStyle(
                                        "-fx-background-color: PURPLE; -fx-text-fill: #ffffff; -fx-font-size: 20px;");
                        helpPane.getChildren().add(instructions);
                        Label step1 = new Label(
                                        "Step 1: Move the fishing line up and down using the arrow keys.");
                        step1.setWrapText(true);
                        step1.setTextAlignment(TextAlignment.JUSTIFY);
                        step1.setPrefSize(500, 30);
                        step1.setLayoutY(50);
                        helpPane.getChildren().add(step1);
                        // Make the label purple and text white
                        step1.setStyle(
                                        "-fx-background-color: PURPLE; -fx-text-fill: #ffffff; -fx-font-size: 15px;");
                        // Give the label a thin black border
                        step1.setBorder(new Border(
                                        new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY,
                                                        BorderWidths.DEFAULT)));
                        Label step2 = new Label("Step 2: Press the 'C' key to catch a fish.");
                        step2.setWrapText(true);
                        step2.setTextAlignment(TextAlignment.JUSTIFY);
                        step2.setPrefSize(500, 30);
                        step2.setLayoutY(80);
                        helpPane.getChildren().add(step2);
                        // Make the label purple and text white
                        step2.setStyle(
                                        "-fx-background-color: PURPLE; -fx-text-fill: #ffffff; -fx-font-size: 15px;");
                        // Give the label a thin black border
                        step2.setBorder(new Border(
                                        new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY,
                                                        BorderWidths.DEFAULT)));
                        Label step3 = new Label(
                                        "Step 3: Have fun! You have 300 seconds to catch as many fish as you can.");
                        step3.setWrapText(true);
                        step3.setTextAlignment(TextAlignment.JUSTIFY);
                        step3.setPrefSize(500, 30);
                        step3.setLayoutY(110);
                        helpPane.getChildren().add(step3);
                        // Make the label purple and text white
                        step3.setStyle(
                                        "-fx-background-color: PURPLE; -fx-text-fill: #ffffff; -fx-font-size: 15px;");
                        // Give the label a thin black border
                        step3.setBorder(new Border(
                                        new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY,
                                                        BorderWidths.DEFAULT)));

                        HBox recentCatch = new HBox();
                        recentCatch.setLayoutY(140);
                        Label recentCatchLabel = new Label("Your Latest Catch: ");
                        recentCatchLabel.setPrefSize(250, 50);
                        recentCatchLabel.setAlignment(Pos.CENTER);
                        recentCatchLabel.setStyle(
                                        "-fx-background-color: PURPLE; -fx-text-fill: #ffffff; -fx-font-size: 20px;");
                        recentCatch.getChildren().add(recentCatchLabel);
                        // Give the label a thin black border
                        recentCatch.setBorder(new Border(
                                        new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY,
                                                        BorderWidths.DEFAULT)));
                        helpPane.getChildren().add(recentCatch);

                        if (FishingPane.caught.size() > 0) {
                                SeaCreatures lastCaught = (SeaCreatures) FishingPane.caught
                                                .get(FishingPane.caught.size() - 1);
                                ImageView image = lastCaught.pullImage();
                                image.setFitHeight(50);
                                image.setPreserveRatio(true);
                                recentCatch.getChildren().add(image);
                        } else {
                                Label noImage = new Label("No images to display yet.");
                                noImage.setPrefSize(250, 50);
                                noImage.setAlignment(Pos.CENTER);
                                noImage.setStyle(
                                                "-fx-background-color: PURPLE; -fx-text-fill: #ffffff; -fx-font-size: 20px;");
                                recentCatch.getChildren().add(noImage);

                        }

                        displayAnalysis(helpPane);

                        // add a button to save the game in the bottom left corner
                        Button saveButton = new Button("Save Game");
                        saveButton.setLayoutX(10);
                        saveButton.setLayoutY(450);
                        saveButton.setPrefSize(100, 20);
                        // set color to purple and text to white
                        saveButton.setStyle(
                                        "-fx-background-color: PURPLE; -fx-text-fill: #ffffff; -fx-font-size: 15px;");
                        helpPane.getChildren().add(saveButton);

                        // Add a field next to the save button to prompt the user for a username
                        // this username will be used to save the game under a file
                        TextField usernameField = new TextField();
                        usernameField.setLayoutX(120);
                        usernameField.setLayoutY(450);
                        usernameField.setPrefSize(100, 20);
                        helpPane.getChildren().add(usernameField);

                        // Once the user enters a username and clicks the save button, the game will
                        // save under that username and write the information to a file
                        saveButton.setOnAction(e2 -> {
                                // grab the username from the text field
                                String username = usernameField.getText();
                                // create a new file with the username
                                File file = new File(username + ".txt");
                                // if the file already exists, display a message saying that the file
                                // already exists
                                if (file.exists()) {
                                        Label fileExists = new Label("This file already exists!");
                                        fileExists.setLayoutX(120);
                                        fileExists.setLayoutY(480);
                                        helpPane.getChildren().add(fileExists);
                                        // if the file exists, overwrite the file with the new information
                                        try {
                                                // create a new file writer
                                                FileWriter writer = new FileWriter(file);
                                                // write the username to the file
                                                writer.write(username + "\n");
                                                // write the score to the file
                                                writer.write(FishingPane.totalPoints + "\n");
                                                // write the number of small fish caught to the file
                                                // close the writer
                                                writer.close();
                                                fileExists.setText("File overwritten!");
                                        } catch (IOException e1) {
                                                e1.printStackTrace();
                                        }
                                }
                                // if the file does not exist, create a new file and write the information
                                // to the file
                                else {
                                        try {
                                                // create a new file
                                                file.createNewFile();
                                                // create a new file writer
                                                FileWriter writer = new FileWriter(file);
                                                // write the username to the file
                                                writer.write(username + "\n");
                                                // write the score to the file
                                                writer.write(FishingPane.totalPoints + "\n");
                                                // write the number of small fish caught to the file
                                                // close the writer
                                                writer.close();
                                        } catch (IOException e1) {
                                                e1.printStackTrace();
                                        }
                                }
                        });

                });

        }

        // this method will display the analysis of the objects caught by the user
        public void displayAnalysis(Pane helpPane) {

                // ** THIS NEEDS TO BE DECLARED FIRST */
                ArrayList<SeaCreatures> caught = FishingPane.caught;
                // Once this is done, add a switch statement to decide what the pane should show

                // grab the length of the list and check the last 3 objects in the list
                // provide suggestions based on this information

                // if the list is empty, display a message saying that the user has not caught
                // anything yet
                if (caught.size() == 0) {
                        Label noCatch = new Label("You have not caught anything yet!");
                        noCatch.setAlignment(Pos.CENTER);
                        noCatch.setLayoutY(200);
                        noCatch.setPrefWidth(500);
                        // Set the label to purple and the text to white
                        noCatch.setStyle(
                                        "-fx-background-color: PURPLE; -fx-text-fill: #ffffff; -fx-font-size: 20px;");
                        helpPane.getChildren().add(noCatch);
                }
                // if the user has caught 1 object, display what type of object it is and
                // how many points it is worth
                else if (caught.size() >= 1) {
                        Label oneCatch = new Label(
                                        "You have caught a " + caught.get(caught.size() - 1).getName() + " worth "
                                                        + caught.get(caught.size() - 1).getPoints() + " points!");
                        oneCatch.setLayoutY(200);
                        oneCatch.setPrefWidth(500);
                        oneCatch.setAlignment(Pos.CENTER);
                        // italicize the text
                        oneCatch.setStyle("-fx-font-style: italic");
                        // Set the label to purple and the text to white
                        oneCatch.setStyle(
                                        "-fx-background-color: PURPLE; -fx-text-fill: #ffffff; -fx-font-size: 20px;");
                        helpPane.getChildren().add(oneCatch);
                        // if the last three objects in the caught array are woodensticks, tell the user
                        // to try a different bait
                        // if the last three objects in the caught array are woodensticks, tell the user
                        // to try a different bait
                        if (caught.get(caught.size() - 1).getName().equals("Wooden Stick")
                                        && caught.get(caught.size() - 2).getName().equals("Wooden Stick")
                                        && caught.get(caught.size() - 3).getName().equals("Wooden Stick")) {
                                Label woodenStickAnalysis = new Label(
                                                "You have caught 3 wooden sticks in a row! Try a different bait!");
                                woodenStickAnalysis.setLayoutX(10);
                                woodenStickAnalysis.setLayoutY(250);
                                // bold the label
                                woodenStickAnalysis.setStyle("-fx-font-weight: bold");
                                woodenStickAnalysis.setAlignment(Pos.CENTER);
                                helpPane.getChildren().add(woodenStickAnalysis);
                        } else if (caught.get(caught.size() - 1).getName().equals("Octopus")
                                        && caught.get(caught.size() - 2).getName().equals("Octopus")
                                        && caught.get(caught.size() - 3).getName().equals("Octopus")) {
                                Label octopusAnalysis = new Label(
                                                "You have caught 3 octopuses in a row! You are doing a great job!");
                                octopusAnalysis.setLayoutX(10);
                                octopusAnalysis.setLayoutY(250);
                                // bold the label
                                octopusAnalysis.setStyle("-fx-font-weight: bold");
                                octopusAnalysis.setAlignment(Pos.CENTER);
                                helpPane.getChildren().add(octopusAnalysis);
                        } else if (caught.get(caught.size() - 1).getName().equals("Small Fish")
                                        && caught.get(caught.size() - 2).getName().equals("Small Fish")
                                        && caught.get(caught.size() - 3).getName().equals("Small Fish")) {
                                Label smallFishAnalysis = new Label(
                                                "You have caught 3 small fish in a row! Try upgrading to larger fish!");
                                smallFishAnalysis.setLayoutX(10);
                                smallFishAnalysis.setLayoutY(250);
                                // bold the label
                                smallFishAnalysis.setStyle("-fx-font-weight: bold");
                                smallFishAnalysis.setAlignment(Pos.CENTER);
                                helpPane.getChildren().add(smallFishAnalysis);
                        } else if (caught.get(caught.size() - 1).getName().equals("Large Fish")
                                        && caught.get(caught.size() - 2).getName().equals("Large Fish")
                                        && caught.get(caught.size() - 3).getName().equals("Large Fish")) {
                                Label largeFishAnalysis = new Label(
                                                "You have caught 3 large fish in a row! Try to catch an octopus (they're 30 points)!");
                                largeFishAnalysis.setLayoutX(10);
                                largeFishAnalysis.setLayoutY(250);
                                // bold the label
                                largeFishAnalysis.setStyle("-fx-font-weight: bold");
                                largeFishAnalysis.setAlignment(Pos.CENTER);
                                helpPane.getChildren().add(largeFishAnalysis);
                        }
                }

        }

        // else if the last three are woodensticks, tell user to try a different bait
        // else if the last three are octopus, tell user that they are doing a great job
        // else if the last three are smallfish, tell user that they should upgrade to
        // larger fish

        // create a method for counting time using a timer
        // public void countTime() {
        // Timer countingTimer = new Timer();
        // int seconds;
        // // timer counts in seconds starting with 0
        // // use a for loop to count up to 300 seconds
        // for (seconds = 0; seconds < 300; seconds++) {
        // // use a timer to count up to 300 seconds
        // countingTimer.scheduleAtFixedRate(new TimerTask() {
        // public void run() {
        // System.out.println(seconds);
        // }
        // }, 0, 1000);
        // }

        // }

}

// do a setonFinished for each transition to make it repeat with each object in
// the arraylist

// // when the fish has completed the animation, remove it from the pane
// smallFishTransition.setOnFinished(e -> {
// this.getChildren().remove(smallFish);
// smallFish = new ImageView(
// new File(FishingPane.class.getResource("smallfish.png").getPath()).toURI()
// .toString());
// // add the new fish to the pane
// this.getChildren().add(smallFish);
// smallFishTransition.setNode(smallFish);
// // set duration between 10 and 15 seconds randomly
// smallFishTransition.setDuration(javafx.util.Duration.seconds(random.nextInt(5)
// + 10));
// smallFishTransition.setByX(-1000);
// // set the new fish to the far right of the pane in the middle
// smallFish.setX(900);
// // set Y to a random number between 400 and 500
// smallFish.setY(random.nextInt(100) + 400);
// // animate the new fish
// smallFishTransition.play();

// });

// // when smallfish2 has completed the animation, remove it from the pane
// smallFishTransition2.setOnFinished(e -> {
// this.getChildren().remove(smallFish2);
// smallFish2 = new ImageView(
// new File(FishingPane.class.getResource("smallfish_2.png").getPath()).toURI()
// .toString());
// // add the new fish to the pane
// this.getChildren().add(smallFish2);
// smallFishTransition2.setNode(smallFish2);
// smallFishTransition2.setDuration(javafx.util.Duration.seconds(random.nextInt(5)
// + 10));
// smallFishTransition2.setByX(-1000);
// // set the new fish to the far right of the pane in the middle
// smallFish2.setX(900);
// // set Y to a random number between 400 and 500
// smallFish2.setY(random.nextInt(100) + 400);
// // animate the new fish
// smallFishTransition2.play();
// });

// // add the same thing for smallfish3
// smallFishTransition3.setOnFinished(e -> {
// this.getChildren().remove(smallFish3);
// smallFish3 = new ImageView(
// new File(FishingPane.class.getResource("smallfish_3.png").getPath()).toURI()
// .toString());
// // add the new fish to the pane
// this.getChildren().add(smallFish3);
// smallFishTransition3.setNode(smallFish3);
// smallFishTransition3.setDuration(javafx.util.Duration.seconds(random.nextInt(5)
// + 10));
// smallFishTransition3.setByX(-1000);
// // set the new fish to the far right of the pane in the middle
// smallFish3.setX(900);
// // set Y to a random number between 400 and 500
// smallFish3.setY(random.nextInt(100) + 400);
// // animate the new fish
// smallFishTransition3.play();
// });

// // add the same thing for largefish
// largeFishTransition.setOnFinished(e -> {
// this.getChildren().remove(largeFish);
// largeFish = new ImageView(
// new File(FishingPane.class.getResource("largefish.png").getPath()).toURI()
// .toString());
// // add the new fish to the pane
// this.getChildren().add(largeFish);
// largeFishTransition.setNode(largeFish);
// largeFishTransition.setDuration(javafx.util.Duration.seconds(random.nextInt(5)
// + 10));
// largeFishTransition.setByX(-1000);
// // set the new fish to the far right of the pane in the middle
// largeFish.setX(900);
// // set Y to a random number between 400 and 500
// largeFish.setY(random.nextInt(100) + 600);
// // animate the new fish
// largeFishTransition.play();
// });

// // add the same thing for largefish2
// // largeFishTransition2.setOnFinished(e -> {
// // this.getChildren().remove(largeFish2);
// // largeFish2 = new ImageView(
// // new
// File(FishingPane.class.getResource("largefish_2.png").getPath()).toURI()
// // .toString());
// // // add the new fish to the pane
// // this.getChildren().add(largeFish2);
// // largeFishTransition2.setNode(largeFish2);
// //
// largeFishTransition2.setDuration(javafx.util.Duration.seconds(random.nextInt(5)
// + 10));
// // largeFishTransition2.setByX(-1000);
// // // set the new fish to the far right of the pane in the middle
// // largeFish2.setX(900);
// // // set Y to a random number between 400 and 500
// // largeFish2.setY(random.nextInt(100) + 600);
// // // animate the new fish
// // largeFishTransition2.play();
// // });

// // add the same thing for woodenstick
// woodenStickTransition.setOnFinished(e -> {
// this.getChildren().remove(woodenStick);
// woodenStick = new ImageView(
// new File(FishingPane.class.getResource("woodenstick.png").getPath()).toURI()
// .toString());
// // add the new fish to the pane
// this.getChildren().add(woodenStick);
// woodenStickTransition.setNode(woodenStick);
// woodenStickTransition.setDuration(javafx.util.Duration.seconds(random.nextInt(5)
// + 10));
// woodenStickTransition.setByX(-1000);
// // set the new fish to the far right of the pane in the middle
// woodenStick.setX(900);
// // set Y to a random number between 400 and 500
// woodenStick.setY(random.nextInt(100) + 600);
// // rotate wooden stick
// RotateTransition rotateagain = new RotateTransition();
// rotateagain.setNode(woodenStick);
// rotateagain.setDuration(javafx.util.Duration.seconds(15));
// rotateagain.setByAngle(360);
// rotateagain.setCycleCount(Animation.INDEFINITE);
// // animate and rotate the woodenstick
// woodenStickTransition.play();
// rotateagain.play();
// });

// //add the same thing for octopus
// octopusTransition.setOnFinished(e -> {
// this.getChildren().remove(octopus);
// octopus = new ImageView(
// new File(FishingPane.class.getResource("octopus.png").getPath()).toURI()
// .toString());
// // add the new fish to the pane
// this.getChildren().add(octopus);
// octopusTransition.setNode(octopus);
// //set duration between 15 and 30 seconds
// octopusTransition.setDuration(javafx.util.Duration.seconds(random.nextInt(30)
// + 30));
// octopusTransition.setByX(-1000);
// // set the new fish to the far right of the pane in the middle
// octopus.setX(900);
// // set Y to a random number between 700 and 800
// octopus.setY(random.nextInt(100) + 700);
// // animate the new fish
// octopusTransition.play();
// });

// have line extend down to the bottom of the pane and then back up with every
// spacebar press
// TranslateTransition lineTransition = new TranslateTransition();
// lineTransition.setOnFinished(e -> {
// lineTransition.setNode(line);
// lineTransition.setDuration(javafx.util.Duration.seconds(8));
// lineTransition.setByY(183);
// lineTransition.setByX(339);
// lineTransition.play();
// lineTransition.setOnFinished(ev -> {
// lineTransition.setNode(line);
// lineTransition.setDuration(javafx.util.Duration.seconds(8));
// lineTransition.setByY(829);
// lineTransition.setByX(317);
// lineTransition.play();
// });

// event listener for space bar
// this.setOnKeyPressed(e -> {
// if (e.getCode() == KeyCode.SPACE) {
// // if the line is not already extended, extend it
// line = new Line(339, 183, 339, 650 + 100);
// line.setEndY(650 + 100);
// line.setEndX(339);

// }
// });