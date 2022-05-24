package model.assets.gameBlocks;

import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

public class DestructibleBlock extends BlockType{
	private int health;
	private final String ONE_HIT_BRICK_IMG 	=  "/model/assets/gameBlocks/blue_button10.png";
	private final String TWO_HIT_BRICK_IMG= "/model/assets/gameBlocks/red_button09.png";
	private final String THREE_HIT_BRICK_IMG= "/model/assets/gameBlocks/yellow_button12.png";

	public DestructibleBlock(int hp) {
		health=hp;
		changeImage();	
	}
	
	public BlockType hit(AnchorPane gamePane)
	{
		if(health>1)
		{
			health--;
			double x =image.getLayoutX();
			double y = image.getLayoutY();
			gamePane.getChildren().remove(image);
			changeImage();
			image.setLayoutX(x);
			image.setLayoutY(y);
			gamePane.getChildren().add(image);
			return this;
		}
		else
		{
			gamePane.getChildren().remove(image);
			return new EmptyBlock();
		}
	}
	
	private void changeImage()
	{
		switch(health) {
		case 1:
			image = new ImageView(ONE_HIT_BRICK_IMG);
			break;
		case 2:
			image = new ImageView(TWO_HIT_BRICK_IMG);
			break;
		case 3:
			image = new ImageView(THREE_HIT_BRICK_IMG);
			break;
		default:
				break;


	}
		
	}
}
