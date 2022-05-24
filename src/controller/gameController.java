package controller;

import model.gameModel;

public class gameController extends AbsCon
{
	private final int MOVE_VALUE = 7;
 
	
	public gameController(gameModel gModel, int windowWith) 
	{
		this.windowWidth=windowWith;
		this.gModel=gModel;
	}

/**
 * Moves platform if right/left key is pressed
 */
	public void updatePlatformPosition()
	{
		if(isLeftkeyPressed && !isRightKeyPressed)
		{
			if(gModel.getPlatformPosition()<=0)
				gModel.setPlatformPosition(0);
			else
				gModel.setPlatformPosition(gModel.getPlatformPosition()-MOVE_VALUE);
			
		}
		if(!isLeftkeyPressed && isRightKeyPressed)
		{
			
			if(gModel.getPlatformPosition()>=windowWidth-190)
				gModel.setPlatformPosition(windowWidth-190);
			else
				gModel.setPlatformPosition(gModel.getPlatformPosition()+MOVE_VALUE);
			
		}
	}
	public void close()
	{	
	}
	

}
