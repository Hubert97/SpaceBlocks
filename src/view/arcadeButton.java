package view;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Font;

/**
 * class which creates custom button 
 * @author huber
 *
 */
public class arcadeButton extends Button
{
	private final String FONT_PATH = "src/view/resources/kenvector_future.ttf";
	private final String BUTTON_PRESSED_IMG = "-fx-background-color: transparent; -fx-background-image: url('/view/resources/grey_buttonPressed.png');";
	private final String BUTTON_IMG = "-fx-background-color: transparent; -fx-background-image: url('/view/resources/grey_button01.png');";
	/**
	 * initiates button with string and def size vals
	 * */
	public arcadeButton(String text)
	{
		setText(text);
		setPrefHeight(49);
		setPrefWidth(190);
		setStyle(BUTTON_IMG);
		setButtonFont();
		arcadeButton_INIT();
		
	}
	/**
	 * tryes to load font from url :FONT_PATH if not succesful uses Arial and prints "Failded loading font file\n" to console
	 */
	private void setButtonFont()
	{
		try {
			setFont(Font.loadFont(new FileInputStream(FONT_PATH),12));
		}catch (FileNotFoundException e)
		{
			setFont(Font.font("Arial", 25));	
			System.out.print("Failded loading font file\n");
		}
	
		
	}
	/**
	 * changes pressed button img from BUTTON_PRESSED_IMG url
	 */
	private void setButtonPressedImg()
	{
		setStyle(BUTTON_PRESSED_IMG);
		setPrefHeight(49);
		setLayoutX(getLayoutX());
		setLayoutY(getLayoutY());
	}
	/**
	 * changes pressed button img from BUTTON_IMG url
	 */
	private void setButtonReleasedImg()
	{
		setStyle(BUTTON_IMG);
		setPrefHeight(49);
		setLayoutX(getLayoutX());
		setLayoutY(getLayoutY());
	}
	/**
	 * chagnes button image when mouse over it
	 */
	private void arcadeButton_INIT()
	{
		setOnMousePressed(new EventHandler<MouseEvent>() {//button pressed
			@Override
			public void handle(MouseEvent event) {
				if(event.getButton().equals(MouseButton.PRIMARY)) {
					setButtonPressedImg();
				}
			}
		});
		setOnMouseReleased(new EventHandler<MouseEvent>() {//button realeased
			@Override
			public void handle(MouseEvent event) {
				if(event.getButton().equals(MouseButton.PRIMARY)) {
					setButtonReleasedImg();
				}
			}
		});
		
		setOnMouseEntered(new EventHandler<MouseEvent>() {//mouse entered on button
			@Override
			public void handle(MouseEvent event) {
				setEffect(new DropShadow());
			}
		});
		
		setOnMouseExited(new EventHandler<MouseEvent>() {//mouse exitted button
			@Override
			public void handle(MouseEvent event) {
				setEffect(null);
			}
		});
	}
}
