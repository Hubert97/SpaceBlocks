package model.assets;

import javafx.scene.image.ImageView;

public class aPlatform {

	private final String PLATFORM_URL 	=  "/view/resources/grey_buttonPressed.png";
	private ImageView image;
	
	public aPlatform(int windowHeight) {
		image = new ImageView(PLATFORM_URL);
		image.setLayoutY(windowHeight-90);
	}
	
	public ImageView getImage()
	{
		return image;
	}
	public void setPosition(int posX)
	{
		image.setLayoutX(posX);
	}

	public int getPosition() {
		// TODO Auto-generated method stub
		return (int) image.getLayoutX();
	}

}
