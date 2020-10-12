import java.lang.Math;
import java.util.ArrayList;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.PauseTransition;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.util.Duration; 


public class GameManager {
	static PauseTransition halfPause = new PauseTransition(Duration.seconds(0.5));
	
	// use those constants to generate numbers in range 1-80
	final static int max = 80;
	final static int min = 1;
	final static int range = max - min + 1;
	
	static int i;
	static Timeline generationPause;
	static int animationTimes;
	static int currentDraw = 0;
	
	// generates a random number in our range
	public static int generateRandomNumber() {
		int result = (int)(Math.random() * range) + min;
		return result;
	}
	
	// generates a random number in specified range
	public static int generateNumberIn(int from, int to) {
		int numberRange = to - from + 1;
		int result = (int)(Math.random()*numberRange) + from;
		return result;
	}
	
	// generate a user specified amount of numbers in the range 1-80 and return them in an array list
	public static ArrayList<Integer> generateNumbers(int numCount){
		ArrayList<Integer> result = new ArrayList<Integer>();
		
		for(int i = 0; i < numCount; i++) {
			int cur = generateRandomNumber();
			while(result.contains(cur)) {
				cur = generateRandomNumber();
			}
			result.add(cur);
		}
		return result;
	}
	
	// the method is recursive
	public static void runGame(ArrayList<ButtonCheckbox> selected) {
		currentDraw++; // keep track of how many draws we have done already so we could break
		
		// reset all the buttons that were selected to be blue again (from green)
		for(ButtonCheckbox button : selected) {
			button.resetToBlue();
		}
		
		String spots = Keno.noSpots.getValue();
		String draws = Keno.noDrawings.getValue();
		// make sure the user selected the number of draws and spots
		if(spots == null) {
			Alert alert = new Alert(AlertType.ERROR, "Please select number of spots to play");
			alert.show();
			return;
		}
		if(draws == null) {
			Alert alert = new Alert(AlertType.ERROR, "Please select number of draws to play");
			alert.show();
			return;
		}
		// convert the user choice for number of draws and spots to an int
		selected.trimToSize();
		String numberOfSpotsS;
		if(spots.equals("10 Spots")) {
			 numberOfSpotsS = spots.substring(0, 2);
		}
		else {
			numberOfSpotsS = spots.substring(0, 1);
		}
		int numberOfSpots = Integer.parseInt(numberOfSpotsS);
		// make sure that user pick exaclty as many spots as the specified in the choice box
		if(numberOfSpots != selected.size()) {
			Alert alert = new Alert(AlertType.ERROR, "Please select " + numberOfSpots + " spots. Right now you have selected " + selected.size());
			alert.show();
			return;
		}
		int numberOfDraws = Integer.parseInt(draws.substring(0,1));
		
		// disable all the interractions so that the user couldn't have impact after the drawing has started
		Keno.grid.setDisable(true);
		Keno.noSpots.setDisable(true);
		Keno.noDrawings.setDisable(true);
		Keno.random.setDisable(true);
		Keno.run.setDisable(true);
		
		// create an observable list that we will use to display data in the list view
		ObservableList<Integer> ol = FXCollections.observableArrayList();
		// generate the numbers for the drawing
		ArrayList<Integer> drawingResults = generateNumbers(20);
		//System.out.println(drawingResults.toString());
		
		// initialize variables needed to break out of drawing animation
		i = 0; // current number that's being drawn (1-20)
		animationTimes = 0; // how many iterations of animation we already went through. makes sure we don't make the user wait for minutes until they see the drawing result
		// create a new timeline that will provide a small animation to drawing
		generationPause = new Timeline(new KeyFrame(Duration.seconds(0.1), new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				if(i==20) { // reached the end, break
					generationPause.stop();
					// the drawing is over we can update the scores
					Keno.updateScores(calculateDrawScore(drawingResults, selected));
					if(currentDraw < numberOfDraws) {
						// if we still have more draws to go only let the user start the next one
						Keno.run.setDisable(false);
						Keno.run.setText("Next draw");
						Keno.run.setOnAction(e->runGame(selected));
					}
					// is this was the last draw reset user interface to be as it was before the draws started
					if(currentDraw == numberOfDraws) {
						Keno.run.setDisable(false);
						Keno.run.setText("Start Draw");
						Keno.run.setOnAction(Keno.runButton);
				
						Keno.grid.setDisable(false);
						Keno.noSpots.setDisable(false);
						Keno.noDrawings.setDisable(false);
						Keno.random.setDisable(false);
						
						// reset for future runs
						currentDraw = 0;
					}
					return;
				}
				animationTimes++;
				// if we already displayed something in the spot we're generating now, remove it
				if(ol.size() != i) {
					ol.remove(i);
				}
				// generate a random number and display it
				int cur = generateRandomNumber();
				ol.add(cur);
				Keno.draws.setItems(ol);
				// if the number generated is the number that we drew
				if(cur == drawingResults.get(i)) {
					// if the number drawn was selected change it's button to green
					for(ButtonCheckbox button : selected) {
						if(Integer.parseInt(button.getText()) == drawingResults.get(i)) {
							button.changeColorsGB();
						}
					}
					i++; // move to next number in the drawing
					animationTimes = 0; // reset the animation times
				}
				else if(animationTimes == 10) { // if we are runnuing the animation for a while now display the number from the drawn array and mark the button green if it's a match
					ol.remove(i);
					ol.add(drawingResults.get(i));
					Keno.draws.setItems(ol);
					for(ButtonCheckbox button : selected) {
						if(Integer.parseInt(button.getText()) == drawingResults.get(i)) {
							button.changeColorsGB();
						}
					}
					i++;
					animationTimes = 0;
				}
			}
			
		}));
		
		generationPause.setCycleCount(Animation.INDEFINITE);
		generationPause.play(); // start the drawing
	}
	
	// returns the amount of winnings on this draw
	public static int calculateDrawScore(ArrayList<Integer> drawingResults, ArrayList<ButtonCheckbox> selected) {
		int noOfSpots = selected.size();
		int noOfMatched = 0;
		for(ButtonCheckbox button : selected) {
			if(button.getButtonStatus() == 2) {
				noOfMatched++;
			}
		}
		//System.out.println(noOfMatched);
		//System.out.println(drawingResults.toString());
		
		if(noOfSpots == 1 && noOfMatched == 1) {
			return 2;
		}
		else if(noOfSpots == 4) {
			if(noOfMatched == 2) {
				return 1;
			}
			else if(noOfMatched == 3) {
				return 5;
			}
			else if(noOfMatched == 4) {
				return 75;
			}
		}
		else if(noOfSpots == 8) {
			if(noOfMatched == 4) {
				return 2;
			}
			else if(noOfMatched == 5) {
				return 12;
			}
			else if(noOfMatched == 6) {
				return 50;
			}
			else if(noOfMatched == 7) {
				return 750;
			}
			else if(noOfMatched == 8) {
				return 10000;
			}
		}
		else if(noOfSpots == 8) {
			if(noOfMatched == 0) {
				return 5;
			}
			else if(noOfMatched == 5) {
				return 2;
			}
			else if(noOfMatched == 6) {
				return 15;
			}
			else if(noOfMatched == 7) {
				return 40;
			}
			else if(noOfMatched == 8) {
				return 450;
			}
			else if(noOfMatched == 9) {
				return 4250;
			}
			else if(noOfMatched == 10) {
				return 100000;
			}
		}
		
		return 0;
	}
}


