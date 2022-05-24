package model.assets.gameBlocks;

import javafx.scene.layout.AnchorPane;

public class EmptyBlock extends BlockType 
{
	public BlockType hit(AnchorPane gamePane)
	{
		return this;
	}

	
}
