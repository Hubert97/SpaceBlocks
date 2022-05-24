package controller;

import model.gameModel;

public abstract class AbsCon {
	
	public static boolean isExternalPadConnected;
	
	public static boolean isLeftkeyPressed;
	public static boolean isRightKeyPressed;
	
	protected int windowWidth;
	
	protected gameModel gModel;
	public abstract void updatePlatformPosition();

	public abstract void close();
	
}
