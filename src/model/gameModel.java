package model;

import java.util.Random;

import javafx.scene.layout.AnchorPane;
import model.assets.aPlatform;
import model.assets.gameBall.gameBall;
import model.assets.gameBlocks.BlockType;
import model.assets.gameBlocks.DestructibleBlock;
import model.assets.gameBlocks.EmptyBlock;
import model.assets.gameBlocks.ForceBlock;
import view.ScoreLabel;


/**
 * Model of the game,
 * Menages all blocks and comunicates with view class by setting params of objects.
 * recieves info from controll class
 * 
 * @author huber
 *
 */
public class gameModel {

	private AnchorPane gamePane;
	
	private aPlatform Platform;
	private gameBall Ball;
	private BlockType blockArray[][];
	private ForceBlock ForceBlocks[]; 
	private double prevTime;
	private boolean gameOver=false;
	
	private final int BLOCK_SIZE_X = 49;
	private final int BLOCK_SIZE_Y = 45;
	private final int BALL_RADIOUS = 10;
	private final int FREE_SPACE_SIZE =5;	//beetween blocks
	private final int divider=50;			//thinss ckecking border of block smaller the more acurate collisions but too big resaults in deadzone
	
	private int windowHeight;
	private int windowWidth;
	private boolean ForceBlocksFlag;	//are force blocks generated?
	private boolean MHblocksFlag;		//are Multi Hit Blocks generated?
	private int blockArraySizeX;
	private int blockArraySizeY;
	private int forceBlockListSize;		
	
	private boolean isFirstUpdate=true;
	private int Score=0;
	private ScoreLabel scoreLabel;
	
		public gameModel(AnchorPane gamePane,int windowSizeX,int windowSizeY,boolean ForceBlocks, boolean MHblocks)
	{
		scoreLabel = new ScoreLabel();
		scoreLabel.updateScore(10);
		gamePane.getChildren().add(scoreLabel);
		
		this.ForceBlocksFlag=ForceBlocks;
		this.MHblocksFlag=MHblocks;
		
		
		this.gamePane=gamePane;
		windowWidth=windowSizeX;
		windowHeight=windowSizeY; 
		blockArraySizeX=(windowSizeX-60)/(BLOCK_SIZE_X+FREE_SPACE_SIZE);
		blockArraySizeY=(windowSizeY/(BLOCK_SIZE_Y+FREE_SPACE_SIZE))/2;
		createBlocks();
		createPlatform();
		createBall();

	}
	/**
	 * creates array of force blocks, places them on board and fills it with randomly generates blocks
	 * 
	 */
	private void createBlocks()
	{
		blockArray=new BlockType[blockArraySizeX][];
		 Random generator = new Random();
		 int randNum;
		forceBlockListSize=0;
		for(int i=0 ; i<blockArraySizeX ; i++)
		{
			blockArray[i]=new BlockType[blockArraySizeY];
			
			for(int j=0 ; j<blockArraySizeY ; j++)
			{
				if (i==0||i==blockArraySizeX-1)
				{
					blockArray[i][j]= new EmptyBlock();
				}
				else if(j==0||j==blockArraySizeX-1)
				{
					blockArray[i][j]= new EmptyBlock();
				}
				else
				{
					randNum=generator.nextInt(20);
					if(randNum==5&&ForceBlocksFlag) {
						blockArray[i][j] = new ForceBlock((generator.nextDouble()-0.2));
						forceBlockListSize++;
					}
					
					else {
						if(MHblocksFlag)
							blockArray[i][j] = new DestructibleBlock((randNum%3)+1);	
						else
							blockArray[i][j] = new DestructibleBlock(1);
					}
					blockArray[i][j].setX((i*(BLOCK_SIZE_X+FREE_SPACE_SIZE))+BLOCK_SIZE_X);
					blockArray[i][j].setY((j*(BLOCK_SIZE_Y+FREE_SPACE_SIZE))+BLOCK_SIZE_Y);
				}
				

			}
		}
		ForceBlocks=new ForceBlock[forceBlockListSize];
		int counter=0;
		for(int i=0 ; i<blockArraySizeX ; i++)
		{
			for(int j=0 ; j<blockArraySizeY ; j++)
			{
				if(blockArray[i][j] instanceof ForceBlock && counter<forceBlockListSize)
				{
					ForceBlocks[counter]=(ForceBlock) blockArray[i][j];
					ForceBlocks[counter].setListPos(counter);
					counter++;
				}
			}
		}
		
	}

	private void createBall()
	{
		Ball=new gameBall(windowWidth/2,windowHeight-130);
		
	}
	
	private void createPlatform()
	{
		Platform=new aPlatform(windowHeight);
		Platform.setPosition(windowWidth/2-100);
	}

	private void getPlatform(AnchorPane gamePane) 
	{
		// TODO Auto-generated method stub
		gamePane.getChildren().add(Platform.getImage());
		
	}

	private void getBall(AnchorPane gamePane)
	{
		gamePane.getChildren().add(Ball.getImage());
	}
	
	private void getBlockArray(AnchorPane gamePane) {
		
		for(int i=0;i<blockArraySizeX;i++)
		{
			for(int j=0; j<blockArraySizeY;j++)
			{
				
				if(!(blockArray[i][j] instanceof EmptyBlock))
				{
				gamePane.getChildren().add(blockArray[i][j].getImage());
				}
		
			}
		}
		
	}
/**
 * fills pane with assets
 * @param gamePane AnchorPane
 */
	public void FillPane(AnchorPane gamePane)
	{
		getPlatform(gamePane);
		getBlockArray(gamePane);
		getBall(gamePane);
	}

	public int getPlatformPosition()
	{
		return Platform.getPosition();
	}
	
	public void setPlatformPosition(int posX)
	{
		Platform.setPosition(posX);
	}
	
	public void updateBall(long longnow)
	{
		long divider =1000000000L;
		double now=(double)longnow/divider;
			if(isFirstUpdate)
			{
				prevTime=now;
				isFirstUpdate=false;
			}
			
			ballSpeedLogic(now-prevTime);
			borderColisionDetection();
			platformColisionLogic();
			blockColisionLogic();
			
			System.out.println(Ball.vX+"   "+Ball.vY);
			
			Ball.setPositionX((Ball.vX*(now-prevTime))+Ball.getPositionFromCenterX());
			Ball.setPositionY((Ball.vY*(now-prevTime))+Ball.getPositionFromCenterY());
			
	// v*t=s
			prevTime=now;
			
	}

	private void ballSpeedLogic(double dTime) 
	{
		for (int i=0; i<forceBlockListSize; i++)
		{
			if(ForceBlocks[i]!=null) {
			Ball.updateSpeed(ForceBlocks[i], dTime);
		//	System.out.println("Forceblock["+i+"]");
			}
		}
		
	}
/**
 * updates ball
 * @param longnow Time differenc since last update
 * @return
 */
	public boolean update(long longnow)
	{
		updateBall(longnow);
		return gameOver;
	}
	/**
	 * checks if ball is withing board
	 */
	private void borderColisionDetection()
	{
		//Checking if Ball is in Playing space
		if(Ball.getPositionFromCenterX()<=36 ) //Bouncing from left part
		{
			Ball.setPositionX(37);
			Ball.vX=-Ball.vX;
		}
		
		if( Ball.getPositionFromCenterX()>=windowWidth-36) //Bouncing from right part
		{
			Ball.setPositionX(windowWidth-37);
			Ball.vX=-Ball.vX;	
		}
		
		
		if(Ball.getPositionFromCenterY()<=36 ) //bouncing of top part
		{
			Ball.setPositionY(37);
			Ball.vY=-Ball.vY;
		}		
		if (Ball.getPositionFromCenterY()>=windowHeight-70)
		{
			//loose condition
		//	Ball.vY=-Ball.vY;
			gameOver=true;
		}

	}
	/**
	 * If chekcks if ball colided with platform, if yes. it sets speed speed of ball acording to place platform was hit. max vals of colision is -200 VY 
	 * and 240 VX (proportional to colision place) 
	 */
	private void platformColisionLogic()
	{
		//Colision Checking with Platform
		if(Ball.getPositionFromCenterX()<Platform.getPosition()+190 &&
				Ball.getPositionFromCenterX()>Platform.getPosition() &&
				Ball.getPositionFromCenterY()>=windowHeight-100)
		{
		Ball.setPositionY(windowHeight-101);
		Ball.vY=-(200+(int)Score/10);
		Ball.vX=(((Ball.getPositionFromCenterX()-Platform.getPosition())-95)/95*240);
		}
	}
	
/**
 * This class detects colisions with blocks surrodunding ball. Chechs nearest blocks near ball which results in 3x3 area of blocks being checked
 */
	
	private void blockColisionLogic()
	{
		int xOcupiedSector=(int) (Ball.getPositionFromCenterX())/(BLOCK_SIZE_X+FREE_SPACE_SIZE);
		int yOcupiedSector=(int)(Ball.getPositionFromCenterY())/(BLOCK_SIZE_Y+FREE_SPACE_SIZE);
	
		
		if(yOcupiedSector<=blockArraySizeY)
		{
			
			//bounce Logic
			//bouncing from right
		int i =0;
		int j =0;
		boolean L,R,D,U;

		
		
				for (int a= xOcupiedSector-1; a<=xOcupiedSector+1;a++)	//making 3x3 chunk in which ball checks for collisions
				{
					for (int b = yOcupiedSector-1; b<=yOcupiedSector+1; b++)
					{
						
						if(a>=blockArraySizeX)
							j=blockArraySizeX-1;
						else if(a<0)
							i=0;
						else
							i=a;
						
						if(b>=blockArraySizeY)
							j=blockArraySizeY-1;
						else if (b<0)
							j=0;
						else
							j=b;
						
						
						if(!(blockArray[i][j] instanceof EmptyBlock)) 
						{				
	
		if(isBallInsideBox(Ball.getPositionFromCenterX(), Ball.getPositionFromCenterY(), blockArray[i][j].getX(),
				blockArray[i][j].getY(), BLOCK_SIZE_X, BLOCK_SIZE_Y))
			{
		L=false; R=false; U=false; D=false;
			
//Each block is devided to make 4 smaller blocks.
if(isBallInsideBox(Ball.getPositionFromCenterX(), Ball.getPositionFromCenterY(), blockArray[i][j].getX()+BLOCK_SIZE_X-BLOCK_SIZE_X/divider,
		blockArray[i][j].getY()+5, BLOCK_SIZE_X/divider, BLOCK_SIZE_Y-10) ) 
{//block is hit from right
	Ball.vX=Math.abs(Ball.vX);
	R=true;

}
if(isBallInsideBox(Ball.getPositionFromCenterX(), Ball.getPositionFromCenterY(), blockArray[i][j].getX(),
		blockArray[i][j].getY()+5, BLOCK_SIZE_X/divider, BLOCK_SIZE_Y-10) ) 
{//block is hit from left
	Ball.vX=-Math.abs(Ball.vX);
	L=true;

}

if(isBallInsideBox(Ball.getPositionFromCenterX(), Ball.getPositionFromCenterY(), blockArray[i][j].getX()+5,
		blockArray[i][j].getY(), BLOCK_SIZE_X-10, BLOCK_SIZE_Y/divider) ) 
{//block is hit from top
	Ball.vY=-Math.abs(Ball.vY);
	U=true;

}

if(	isBallInsideBox(Ball.getPositionFromCenterX(), Ball.getPositionFromCenterY(), blockArray[i][j].getX()+5,
		blockArray[i][j].getY()+BLOCK_SIZE_Y-BLOCK_SIZE_Y/divider, BLOCK_SIZE_X-10, BLOCK_SIZE_Y/divider))
{//block is hit from bottom
	Ball.vY=Math.abs(Ball.vY);
	D=true;
	
}
exlusionZone(L,R,D,U,i,j);
if(blockArray[i][j] instanceof ForceBlock)
{
	ForceBlocks[((ForceBlock)blockArray[i][j]).getListPos()]=null;
	Score=Score+90;
	
}
	
blockArray[i][j]=blockArray[i][j].hit(gamePane);
Score=Score+10;
scoreLabel.updateScore(Score);
			}

							
								
}}}}
		}


	private void exlusionZone(boolean l, boolean r, boolean d, boolean u, int i, int j) {
	if(l)
		Ball.setPositionX(blockArray[i][j].getX()-BALL_RADIOUS);
	if(r)
		Ball.setPositionX(blockArray[i][j].getX()+BLOCK_SIZE_X+BALL_RADIOUS);
	if(d)
		Ball.setPositionY(blockArray[i][j].getY()+BLOCK_SIZE_Y+BALL_RADIOUS);
	if(u)
		Ball.setPositionY(blockArray[i][j].getY()-BALL_RADIOUS);
}
	/**
	 * This method cheksk if hitbox of discribed by Params block and ball colides
	 * if yes it returns true if not false
	 * @param ballMidleX - X coordinate of ball
	 * @param ballMidelY - Y coordinate of ball
	 * @param boxRootX - X root coordinate of box
	 * @param box RootY - Y root coordinate of box
	 * @param width 
	 * @param height
	 * @return true/false
			*/
	private boolean isBallInsideBox(double ballMidleX, double ballMidleY,double boxRootX, double boxRootY, double width, double height)
	{
		if(
				(boxRootX+width+BALL_RADIOUS)>ballMidleX &&
				(boxRootX-BALL_RADIOUS)<ballMidleX&&
				(boxRootY-BALL_RADIOUS)<ballMidleY &&
				boxRootY+height+BALL_RADIOUS>ballMidleY
				
				)
			return true;
		else 
			return false;
		
	}
	
	
	
}
