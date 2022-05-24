package view;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javafx.scene.control.Label;
import javafx.scene.text.Font;
/**
 * clas displays score at game screen
 * @author huber
 *
 */
public class ScoreLabel extends Label
{
	private final static String FONT_PATH = "src/view/resources/kenvector_future.ttf";
	
	public ScoreLabel()
	{
		try {
			setFont(Font.loadFont(new FileInputStream(FONT_PATH),30));
		}catch (FileNotFoundException e)
		{
			setFont(Font.font("verdana",30));	
			System.out.print("Failded loading font file\n");
		}
		setLayoutX(10);
		setLayoutY(5);
	}

	public void updateScore(int score)
	{
		setText("Score: "+Integer.toString(score));
	}
}
