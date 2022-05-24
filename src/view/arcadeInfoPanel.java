package view;

import javafx.scene.SubScene;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.util.Duration;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javafx.animation.TranslateTransition;
/**
 * information panel for main menu
 * @author huber
 *
 */
public class arcadeInfoPanel extends SubScene

{

	private final static String FONT_PATH = "src/view/resources/kenvector_future.ttf";
	private final static String BACKGROUD_IMG = "view/resources/grey_panel.png";
	private final static int PANEL_SIZE_X = 500;
	private final static int PANEL_SIZE_Y = 300;
	private Label label=null;
	private VBox box;
	
	private boolean isVisible;
	
	public arcadeInfoPanel() 
	{
		super(new AnchorPane(), PANEL_SIZE_X, PANEL_SIZE_Y);
		prefHeight(PANEL_SIZE_Y);
		prefWidth(PANEL_SIZE_X);
		BackgroundImage image = new BackgroundImage(new Image(BACKGROUD_IMG, PANEL_SIZE_X, PANEL_SIZE_Y,false,true),
				BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, null);
		
		AnchorPane root =(AnchorPane) this.getRoot();
		
		root.setBackground(new Background(image));
		
		setLayoutX(1200);
		setLayoutY(180);
		
		isVisible=false;
		
		// TODO Auto-generated constructor stub
	}
	/**
	 * animation logic, sychronises text, image and other functions of the class
	 */
	public void movePanel() 
	{
		TranslateTransition transition = new TranslateTransition();
		TranslateTransition textTransition = new TranslateTransition();
		TranslateTransition vBoxTransition = new TranslateTransition();
		
		transition.setDuration(Duration.seconds(0.2));
		textTransition.setDuration(Duration.seconds(0.2));
		vBoxTransition.setDuration(Duration.seconds(0.2));
		
		transition.setNode(this);
		textTransition.setNode(label);
		vBoxTransition.setNode(box);
		
		if(isVisible==false) {
			transition.setToX(-800);
			textTransition.setToX(-800);
			vBoxTransition.setToX(-800);
			isVisible=true;
		}
		else 
		{
			transition.setToX(0);
			vBoxTransition.setToX(0);
			isVisible=false;	
			textTransition.setToX(0);
		}
		
		transition.play();
		textTransition.play();
		vBoxTransition.play();
	}
	

/**
 * creates text panel and sets text to be displayed, returns label which sould be added to mainPane
 * also sets font
 * @param input text
 * @return
 */
	public Label setPanelText(String input) {
		label=new Label();
		label.setText(input);
		
		
		try {
			label.setFont(Font.loadFont(new FileInputStream(FONT_PATH),30));
		}catch (FileNotFoundException e)
		{
			label.setFont(Font.font("verdana",30));	
			System.out.print("Failded loading font file\n");
		}
		
		label.setLayoutX(1250);
		label.setLayoutY(200);
		
		label.setContentDisplay(ContentDisplay.TOP);
		label.autosize();
		return label;
	}
	/**
	 * 	adds Vbox to the panel
	 */
	public void getPanel(VBox box) {
		this.box=box;
		this.box.setLayoutY(208);
		this.box.setLayoutX(1230);
		this.box.setSpacing(55);
	}
}
