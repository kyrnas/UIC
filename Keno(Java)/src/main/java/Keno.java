import java.util.ArrayList;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.binding.DoubleBinding;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

public class Keno extends Application {
	
	private MenuBar menu;
	private Menu tab1;
	
	private Scene gameScene;
	
	static BorderPane borderPane;
	
	public static ChoiceBox<String> noSpots;
	public static ChoiceBox<String> noDrawings;
	
	public static ListView<Integer> draws;
	
	public static GridPane grid;
	
	public static int drawScoreInt;
	public static int totalScoreInt;
	
	static Label overallScore;
	static Label drawingScore;
	static Label drawsLabel;
	
	public static Button run;
	public static EventHandler<ActionEvent> runButton;
	
	public static Button random;
	
	public static void main(String[] args) {
		launch(args);
	}

	public void start(Stage primaryStage) throws Exception {
		primaryStage.setTitle("Keno");
		
		// create the welcome screen and display it
		Scene welcomeScreen = welcomeScene();
		primaryStage.setScene(welcomeScreen);
		// disable resizing of the window
		primaryStage.setResizable(false);
		primaryStage.initStyle(StageStyle.UNIFIED);
		
		// once user clicks on an empty space load the game scene
		// using EventFilter caused problems when interacting with the menu
		welcomeScreen.setOnMousePressed(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent event) {
				primaryStage.setScene(gameScene());
				primaryStage.setResizable(true);
				primaryStage.setMinHeight(600);
				primaryStage.setMinWidth(1000);
			}
		});
		
		primaryStage.show();
		
		
	}

	private Scene welcomeScene() {
		// create the menu
		menu = new MenuBar();
		tab1 = new Menu("Menu");
		MenuItem rules = new MenuItem("See Rules");
		MenuItem odds = new MenuItem("See Odds and Prizes");
		MenuItem exit = new MenuItem("Exit");
		// assign menu buttons
		rules.setOnAction(e->showRules());
		odds.setOnAction(e->showOdds());
		exit.setOnAction(e->Platform.exit());
		
		tab1.getItems().add(rules);
		tab1.getItems().add(odds);
		tab1.getItems().add(exit);
		
		menu.getMenus().add(tab1);
		
		// create the base for the scene
		BorderPane borderPane = new BorderPane();
		// set the menu to be at the top of the scene
		borderPane.setTop(menu);
		// create and set the background for the welcome screen
		Image backgroundImage = new Image("KenoLogo.jpg");
		BackgroundSize backSize = new BackgroundSize(700, 700, true, true, true, true);
		BackgroundImage bi = new BackgroundImage(backgroundImage, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, backSize);
		Background background = new Background(bi);
		
		borderPane.setBackground(background);
		
		// create a label with instruction to proceed
		Label continueLabel = new Label("Click anywhere to continue... Loading may take some time");
		continueLabel.setStyle("-fx-font-size: 35;"+"-fx-text-fill: white;"+"-fx-font-family: Serif;");
		borderPane.setCenter(continueLabel);
		continueLabel.setTranslateY(70);
				
		// generate the scene
		Scene welcomeScreen = new Scene(borderPane, 1000, 1000);
		
		return welcomeScreen;
	}
	
	private Scene gameScene() {
		// create a new item for the menu
		MenuItem changeLook = new MenuItem("New Look");
		changeLook.setOnAction(e->newLook());
		tab1.getItems().add(0, changeLook);
		// create an array list that will keep track of what buttons are currently checked
		ArrayList<ButtonCheckbox> selectedButtons = new ArrayList<>();
		
		// create an event handler for the grid buttons
		EventHandler<ActionEvent> checkButtonPressed = new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				// get a reference to the button that triggered the event
				ButtonCheckbox cur = (ButtonCheckbox) event.getTarget();
				// get the number of spots that we selected by the user from the choice box
				// convert it to integer
				String spots = Keno.noSpots.getValue();
				selectedButtons.trimToSize();
				String numberOfSpotsS;
				if(spots.equals("10 Spots")) {
					 numberOfSpotsS = spots.substring(0, 2);
				}
				else {
					numberOfSpotsS = spots.substring(0, 1);
				}
				int numberOfSpots = Integer.parseInt(numberOfSpotsS);
				
				// don't allow the user to check more buttons than the number of spots they selected
				if(numberOfSpots == selectedButtons.size() && !cur.getChecked()) {
					Alert alert = new Alert(AlertType.ERROR, "You cannot select more than " + numberOfSpots + " spots.");
					alert.show();
					return;
				}
				
				// when button pressed if it was checked before we need to remove it from list of checked buttons
				// if it wasn't checked add to the checked list
				if(cur.getChecked()) {
					selectedButtons.remove(cur);
				}
				else {
					selectedButtons.add(cur);
				}
				// update the status of the button inside it's class
				cur.swapCheckedStatus();
			}
			
		};
		
		// create an array that will contain references to all buttons in the gird
		ArrayList<ButtonCheckbox> buttons = new ArrayList<>();
		// create all the buttons, give them labels and assign action event
		for(int i = 1; i < 10; i++) {
			ButtonCheckbox b = new ButtonCheckbox("0"+Integer.toString(i));
			b.setOnAction(checkButtonPressed);
			buttons.add(b);
		}
		for(int i = 10; i <= 80; i++) {
			ButtonCheckbox b = new ButtonCheckbox(Integer.toString(i));
			b.setOnAction(checkButtonPressed);
			buttons.add(b);
		}
		// create a grid
		grid = new GridPane();
		grid.setAlignment(Pos.CENTER);
		// insert the buttons from previously created array into the gird
		// the grid should be 8x10
		for(int i = 0; i < 8; i++) {
			for(int j = 1; j <= 10; j++) {
				grid.add(buttons.get((i*10 + j - 1)), j, i);
			}
		}
		// disable the interactions with the grid (they will be enabled once the user selects number of spots)
		grid.setDisable(true);
		
		// create choice boxes for number of spots and number of draws
		noSpots = new ChoiceBox<String>();
		noSpots.getItems().addAll("1 Spot", "4 Spots", "8 Spots", "10 Spots");
		noDrawings = new ChoiceBox<String>();
		noDrawings.getItems().addAll("1 Drawing", "2 Drawings", "3 Drawings", "4 Drawings");
		// make sure that the grid is enabled once the user selects number of draws
		// if user changed the number of draws they want all the selections on the gird will be removed
		noSpots.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				grid.setDisable(false);
				for(ButtonCheckbox button : selectedButtons) {
					button.setChecked(false);
				}
				selectedButtons.clear();
			}
			
		});
		
		// create the button that will select spots for the user
		random = new Button("Select spots for me");
		random.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				// get the number of spots that the user wants to play
				String spots = Keno.noSpots.getValue();
				// make sure the user selected the number of spots they want
				if(spots == null) {
					Alert alert = new Alert(AlertType.ERROR, "Please select number of spots to play");
					alert.show();
					return;
				}
				// convert number of spots from string to int
				String numberOfSpotsS;
				if(spots.equals("10 Spots")) {
					 numberOfSpotsS = spots.substring(0, 2);
				}
				else {
					numberOfSpotsS = spots.substring(0, 1);
				}
				int numberOfSpots = Integer.parseInt(numberOfSpotsS);
				// generate the numbers that will be selected
				ArrayList<Integer> toSelect = GameManager.generateNumbers(numberOfSpots);
				// uncheck all the buttons that were checked and empty the checked list
				for(ButtonCheckbox button : selectedButtons) {
					button.setChecked(false);
				}
				selectedButtons.clear();
				// check all the buttons with the numbers generated in toSelect
				for(Integer number : toSelect) {
					// convert int into a useable string to compare
					String sNumber;
					if(number < 10) {
						sNumber = "0" + number.toString();
					}
					else {
						sNumber = number.toString();
					}
					// linearly go through all the buttons and check them if they are in the toSelect array
					for(ButtonCheckbox button : buttons) {
						if(button.getText().equals(sNumber)) {
							button.setChecked(true);
							selectedButtons.add(button);
							break;
						}
					}
				}
			}
		});
		
		// start generating the scene
		// borderPane will be the base
		borderPane = new BorderPane();
		// this vbox will contain the majority of buttons
		VBox vBox = new VBox(menu);
		HBox scores = new HBox();
		HBox numberOf = new HBox();
		numberOf.getChildren().addAll(noSpots, noDrawings);
		vBox.getChildren().addAll(scores, numberOf);
		vBox.getChildren().add(random);
		
		// initialize the int scores and the labels that will display them
		totalScoreInt = 0;
		drawScoreInt = 0;
		overallScore = new Label("Current score: " + totalScoreInt);
		drawingScore = new Label("Last drawing score: " + drawScoreInt);
		scores.getChildren().addAll(overallScore, drawingScore);
		drawingScore.setTranslateX(20);
		// assign the top of the borderpane to be vbox
		borderPane.setTop(vBox);
		// set grid to be in the center
		borderPane.setCenter(grid);
		BorderPane.setAlignment(grid, Pos.CENTER);
		
		// create the draws label and listview
		drawsLabel = new Label("DRAWS: ");
		draws = new ListView<>();
		// adjust sizes to make look a little less ugly
		draws.setPrefHeight(25);
		draws.setMinHeight(25);
		draws.setMaxWidth(550);
		draws.setPrefWidth(538);
		draws.setMinWidth(538);
		// create a new vbox to contain the elements for drawing
		VBox drawsVBox = new VBox(drawsLabel, draws);
		// disable their visibility until we start drawing
		draws.setVisible(false);
		drawsLabel.setVisible(false);
		// set the orientation to be horizontal and align the box in the center
		draws.setOrientation(Orientation.HORIZONTAL);
		drawsVBox.setAlignment(Pos.CENTER);
		VBox.setMargin(drawsVBox, new Insets(20, 0, 0, 0));
		
		// create a run button that will start the draws
		run = new Button("Start Draw");
		// create a handler for the start button
		runButton = new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				// make sure we dont add the box to the scene more than once
				// (checks for other mistakes happen later)
				if(!vBox.getChildren().contains(drawsVBox))
					vBox.getChildren().add(drawsVBox);
				
				// make draws visible
				draws.setVisible(true);
				drawsLabel.setVisible(true);
				// make call to start drawing
				GameManager.runGame(selectedButtons);
			}
		};
		// assign the event to the button
		run.setOnAction(runButton);
		// place the button in its place
		borderPane.setBottom(run);
		BorderPane.setAlignment(run, Pos.BOTTOM_CENTER);

		
		gameScene = new Scene(borderPane, 1000, 1000);
		
		return gameScene;
	}
	
	private void showRules() {
		// create a new dialog window that will display the rules written as text
		ButtonType close = new ButtonType("Close", ButtonData.CANCEL_CLOSE);
		Dialog<String> dialog = new Dialog<>();
		dialog.setTitle("Rules");
		dialog.setContentText("Keno is a popular gambling game offered in many modern casinos and also offered as a game in many state lotteries.\n\n"
				+ "Players wager by choosing a set amount of numbers( pick 2 numbers, pick 10 numbers, etc.) ranging from 1 to 80. After all players have made their wagers and picked their "
				+ "numbers, twenty numbers are drawn at random, between 1 and 80 with no duplicates.\n\n"
				+ "Players win by matching a set amount of their numbers to the numbers that are randomly drawn.");
		dialog.getDialogPane().getButtonTypes().add(close);
		dialog.getDialogPane().lookupButton(close).setDisable(false);
		dialog.showAndWait();	
	}
	
	private void showOdds() {
		// create a dialog window that will show the picture that contains the odds and winnings
		Image prizesAndOdds = new Image("KenoPrizeChart2020.jpg");
		ImageView vPrizesAndOdds = new ImageView(prizesAndOdds);
		vPrizesAndOdds.setPreserveRatio(true);
		
		ButtonType close = new ButtonType("Close", ButtonData.CANCEL_CLOSE);
		
		Dialog<ImageView> dialog = new Dialog<>();
		dialog.setTitle("Prizes and Odds");
		dialog.setGraphic(vPrizesAndOdds);
		dialog.getDialogPane().getButtonTypes().add(close);
		dialog.getDialogPane().lookupButton(close).setDisable(false);
		dialog.showAndWait();
	}
	
	private void newLook() {
		// slightly adjust the overall style of the game scene
		// (not available on the welcoming screen)
		overallScore.setStyle("-fx-font-size: 22;"+"-fx-text-fill: purple;"+"-fx-font-family: Serif;");
		drawingScore.setStyle("-fx-font-size: 22;"+"-fx-text-fill: purple;"+"-fx-font-family: Serif;");
		drawsLabel.setStyle("-fx-font-size: 22;"+"-fx-text-fill: purple;"+"-fx-font-family: Serif;");
		
		borderPane.setStyle("-fx-background-color: lightPink;");
		draws.setStyle("-fx-font-size: 22;-fx-border-color: purple;");
		draws.setPrefHeight(100);
		draws.setMinHeight(100);
		draws.setMaxWidth(1000);
		draws.setPrefWidth(1000);
		draws.setMinWidth(1000);
		
	}
	
	public static void updateScores(int score) {
		// change the labels to display the updated score
		// the call to this method provides the score that
		// was drawn recently
		drawScoreInt = score;
		totalScoreInt += score;
		
		drawingScore.setText("Last drawing score: " + drawScoreInt + " (hypothetical)$");
		overallScore.setText("Current score: " + + totalScoreInt + " (hypothetical)$");
	}
}
