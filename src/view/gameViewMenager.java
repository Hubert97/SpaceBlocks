package view;


import controller.AbsCon;
import controller.gameController;
import controller.gamePadController;
import javafx.animation.AnimationTimer;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.stage.Stage;
import model.Model;
import model.gameModel;


public class gameViewMenager {
	//view
	private AnchorPane gamePane;
	private Scene gameScene;
	private Stage gameStage;
	private Stage menuStage;
	private AnimationTimer gameTimer;
	
	private static final int GAME_WIDTH = 1200;
	private static final int GAME_HEGHT = 1000;
	
	private boolean start=false;
	private AbsCon controller;
	
	//model
	private gameModel gModel;
	ScoreLabel scoreLabel ;
	

	

	
	
	
	public gameViewMenager() {
		initStage();
		initListeners();
		createBackground();
	}

	
	/**
	 * creates stage and initiates it with def vals.
	 */
	private void initStage() 
	{

		gamePane = new AnchorPane();
		gameScene = new Scene(gamePane,GAME_WIDTH, GAME_HEGHT);
		gameStage = new Stage();
		gameStage.setScene(gameScene);
		
		initTimer();

	}
	/**
	 * initiates game timer.
	 * has loose condition, each frame calls update from controller and model (indepent)
	 */
	private void initTimer() 
	{
		gameTimer = new AnimationTimer()
				{
					@Override
					public void handle(long now)
					{
						controller.updatePlatformPosition();
						if(start)
						{
						if(gModel.update(now)==true)//game lost
						{
							gameTimer.stop();
							gameStage.close();
							menuStage.show();
							controller.close();
						}
						}
						
					}
				};
				try {
					controller.updatePlatformPosition();
				}
				catch (Exception e)
				{
					e.fillInStackTrace();
				}
		gameTimer.start();
	}
	/**
	 * creates left/right key listeners
	 */
	private void initListeners()
	{

		
		gameScene.setOnKeyPressed(new EventHandler<javafx.scene.input.KeyEvent>() {

			@Override
			public void handle(javafx.scene.input.KeyEvent event) {
				
				if(event.getCode()==KeyCode.RIGHT)
				{
					gameController.isRightKeyPressed=true;
				}
				
				if(event.getCode()==KeyCode.LEFT)
				{
				
					gameController.isLeftkeyPressed=true;
				}
				if(event.getCode()==KeyCode.SPACE)
				{
					start=true;
				}
				
			}	
		});
		
		gameScene.setOnKeyReleased(new EventHandler<javafx.scene.input.KeyEvent>() {

			@Override
			public void handle(javafx.scene.input.KeyEvent event) {
				if(event.getCode()==KeyCode.LEFT)
				{
					gameController.isLeftkeyPressed=false;
				}
				if(event.getCode()==KeyCode.RIGHT)
				{
					gameController.isRightKeyPressed=false;
				}
			}
			
		});
	}
	/**
	 * call this to run game
	 * 
	 * creates game model
	 * creates controller
	 * creates view class (game pane)
	 * gives pointers to each class 
	 * 
	 * 
	 * @param menuStage	Pointer to menu stage, used to open start menu after game is over.
	 * @param ForceBlocks flag that tells if Force Blocks will be generated
	 * @param MHblocks flag that tells if Multi Hit Blocks are to be generated
	 * @param useExCon Bool chooses which controller class to use (interchangable controller classes(
	 */
	public void initGame(Stage menuStage, Model settingsModel)	
	{
		this.menuStage=menuStage;
		this.menuStage.hide();
		
		gModel = new gameModel(gamePane,GAME_WIDTH,GAME_HEGHT,settingsModel.getForceBlocks(),settingsModel.getMHblocks());
		if(settingsModel.getUseExCon())
			controller = new gamePadController(gModel,GAME_WIDTH);
		else
			controller = new gameController(gModel,GAME_WIDTH);
		
		gModel.FillPane(gamePane);
		gameStage.show();
		
	}

	/**
	 * creates bachgroud
	 */
	private void createBackground() //sets background
	{
		Image bImage = new Image("/view/resources/nebula.jpg",GAME_WIDTH,GAME_HEGHT,false,true);
		BackgroundImage background=new BackgroundImage(bImage,BackgroundRepeat.REPEAT,BackgroundRepeat.REPEAT, BackgroundPosition.DEFAULT,null );
		gamePane.setBackground(new Background(background));
	}
	
	
}
