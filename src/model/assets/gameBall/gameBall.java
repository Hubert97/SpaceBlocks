package model.assets.gameBall;

import javafx.scene.image.ImageView;
import model.assets.gameBlocks.ForceBlock;
/**
 *this class creates ball, and has logic of moving it around board
 * @author huber
 *
 */
public class gameBall 
{
	public double posX,posY;//current position of ball
	public double vX,vY;//speed of ball
	public double forceX,forceY;//forces acting on ball;
	public double mass; //ball mass
	public double vMax;	//max speed of ball
	
	private ImageView image;
	private final String BALL_IMG 	=  "/model/assets/gameBall/red_circle.png";
	private final int maxSpeed=400;
	
	public gameBall(int posX,int posY) {
		this.posX=posX;
		this.posY=posY;
		image = new ImageView(BALL_IMG);
		image.setLayoutX(this.posX);
		image.setLayoutY(this.posY);

		//velocity in px/s
		vX=200;
		vY=-200;
	}
	
	public ImageView getImage()
	{
		return image;
	}
	
	public void setPositionX(double posX)
	{
		this.posX=posX-10;
		image.setLayoutX(this.posX);

	}
	public double getPositionFromCenterX()
	{
		return image.getLayoutX()+10;
	}
	public void setPositionY(double posY)
	{
		this.posY=posY-10;
		image.setLayoutY(this.posY);
	}
	public double getPositionFromCenterY()
	{
		return image.getLayoutY()+10;
	}
	/**
	 * updates vX and vY based on Force block distance to ball , position and angle from it.
	 * mass tempers the acceleration.
	 * @param fBlock
	 * @param dTime
	 */
	public void updateSpeed(ForceBlock fBlock, double dTime)
	{
		if(fBlock !=null)
		{
			double distanceFromBlock=Math.pow(Math.pow(Math.abs(fBlock.getX()+25-getPositionFromCenterX()),2)
					+Math.pow(Math.abs(fBlock.getY()+22-getPositionFromCenterY()), 2),
					0.5);

			if(distanceFromBlock<800)
			{
				double alpha=Math.atan(Math.abs(fBlock.getY()+22-getPositionFromCenterY())/Math.abs(fBlock.getX()+25-getPositionFromCenterX()));
				double forceAtBallX=Math.sin(alpha) * fBlock.getForce()/Math.pow(distanceFromBlock, 2);
				double forceAtBallY=Math.cos(alpha) * fBlock.getForce()/Math.pow(distanceFromBlock, 2);
				
				
				if(vX>maxSpeed)
					vX=maxSpeed;
				else if (vX<-maxSpeed)
					vX=-maxSpeed;
				else
				{
					if(fBlock.getX()+25-getPositionFromCenterX()>0)
					{
						vX=vX+forceAtBallX*dTime;
					}
					else
					{
						vX=vX-forceAtBallX*dTime;
					}	
				
				}
				
				
				if(vY>maxSpeed)
					vY=maxSpeed;
				else if (vY<-maxSpeed)
					vY=-maxSpeed;
				else {
					if(fBlock.getY()+25-getPositionFromCenterY()>0)
					{
						vY=vY+forceAtBallY*dTime;
					}
					else
					{
						vY=vY-forceAtBallY*dTime;
					}
				
					
				}
				
			}
			
		}
		
	}

}
