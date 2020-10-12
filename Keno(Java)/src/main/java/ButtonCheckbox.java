import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;

public class ButtonCheckbox extends Button {
	private boolean isChecked = false;
	
	private Background grayBackground;
	private Background blueBackground;
	private Background greenBackground;
	
	public ButtonCheckbox() {
		super();
		createBackgrounds();
		this.setBackground(grayBackground);
	}
	
	public ButtonCheckbox(String text) {
		super(text);
		createBackgrounds();
		this.setBackground(grayBackground);
	}
	
	public ButtonCheckbox(String text, Node graphic) {
		super(text, graphic);
		createBackgrounds();
		this.setBackground(grayBackground);
	}
	
	private void createBackgrounds() {
		Image gray = new Image("grayBackground.png");
		Image blue = new Image("blueBackground.png");
		Image green = new Image("greenBackground.png");
		
		BackgroundSize backSize = new BackgroundSize(1, 1, true, true, true, true);
		
		BackgroundImage grayImg = new BackgroundImage(gray, BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT, BackgroundPosition.CENTER, backSize);
		BackgroundImage blueImg = new BackgroundImage(blue, BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT, BackgroundPosition.CENTER, backSize);
		BackgroundImage greenImg = new BackgroundImage(green, BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT, BackgroundPosition.CENTER, backSize);
		
		grayBackground = new Background(grayImg);
		blueBackground = new Background(blueImg);
		greenBackground = new Background(greenImg);
	}
	
	public boolean getChecked() {
		return isChecked;
	}
	
	public void setChecked(boolean expected) {
		isChecked = expected;
		updateColors();
	}
	
	public void swapCheckedStatus() {
		if(isChecked) {
			isChecked = false;
			updateColors();
		}
		else {
			isChecked = true;
			updateColors();
		}
	}
	
	public void updateColors() {
		if(isChecked) {
			this.setBackground(blueBackground);
		}
		else {
			this.setBackground(grayBackground);
		}
	}

	public void changeColorsGB() {
		if(this.getBackground().equals(blueBackground)) {
			this.setBackground(greenBackground);
		}
		else if(this.getBackground().equals(greenBackground)) {
			this.setBackground(blueBackground);
		}
	}
	
	public void resetToBlue() {
		this.setBackground(blueBackground);
	}
	
	public int getButtonStatus() {
		if(this.getBackground().equals(blueBackground)) {
			return 1;
		}
		else if (this.getBackground().equals(greenBackground)) {
			return 2;
		}
		else {
			return 0;
		}
	}
}
