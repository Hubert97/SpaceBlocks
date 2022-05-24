package model.assets.gameBlocks;

import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

public abstract class BlockType {	
	

	//TO DO different collors depending on type
	
	protected ImageView image;
	

	
	public ImageView getImage()
	{
		return image;
	}
	public void setX(int X)
	{
		image.setLayoutX(X);
		
	}
	public void setY(int Y)
	{
		image.setLayoutY(Y);
	}
	public int getX()
	{
		return (int) image.getLayoutX();
	}
	public int getY()
	{
		return (int) image.getLayoutY();
	}
	public abstract BlockType hit(AnchorPane gamePane);
}
