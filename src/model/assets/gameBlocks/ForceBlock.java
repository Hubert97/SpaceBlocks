package model.assets.gameBlocks;

import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

;

public class ForceBlock extends BlockType{
	private final String FORCE_BLOCK_ATRACTOR_IMG 	=  "/model/assets/gameBlocks/grey_button07.png";
	private final String FORCE_BLOCK_REPULSOR_IMG 	=  "/model/assets/gameBlocks/green_button07.png";
	
	private double force;
	private int listPos;
	public ForceBlock(double F)
	{
	
		
		if(F>0)
		{
			image=new ImageView(FORCE_BLOCK_ATRACTOR_IMG);
			force=5000000;
		}
		else
		{
			image=new ImageView(FORCE_BLOCK_REPULSOR_IMG);
			force=-800000;
		}
			
	}
	public double getForce()
	{
		return force;
	}
	
	public BlockType hit(AnchorPane gamePane)
	{
		gamePane.getChildren().remove(image);
		return new EmptyBlock();
	}
	public void setListPos(int pos)
	{
		listPos=pos;
	}
	public int getListPos() {
		return listPos;
	}

}
