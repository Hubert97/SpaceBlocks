package view;

import java.util.ArrayList;
import view.gameViewMenager;


import view.arcadeButton;
import view.arcadeInfoPanel;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.VBox;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import model.Model;


/**
 * Menu class, creates Menu screen, manages settings and panels
 * Launches gameManager which creates MVC model for the game.
 * This class doesn't use MVC because its visual only.
 * @author huber
 *
 */
public class viewMenager {
	//windows size consts
	private static final int WINDOW_HEIGHT=720;
	private static final int WINDOW_WIDTH=1200;
	
	//button size consts
	private static final int MENU_BUTTONS_START_X = 100;
	private static final int MENU_BUTTTONS_START_Y = 149;
	
	//private classes
	private AnchorPane mainPane;
	private Scene mainScene;
	private Stage mainStage;
	private ArrayList<arcadeButton> menuButtons;
	private ArrayList<CheckBox> SettingsCheckBoxList;
	
	private Model model;
	
	private arcadeInfoPanel startPanel;
	private arcadeInfoPanel settingsPanel;
	private arcadeInfoPanel creditsPanel;


	private arcadeInfoPanel lastPanel;

	public viewMenager(Model model)					//contructor of viemMenager
	{
		this.model = model;
		
		mainPane = new AnchorPane();
		mainScene = new Scene(mainPane,WINDOW_WIDTH,WINDOW_HEIGHT);
		mainStage = new Stage();
		
		mainStage.setScene(mainScene);
		createBackground();
		createPaanels();
		menuButtons = new ArrayList<arcadeButton>();
		createeButtons();
	
	}

	private void createPaanels() {
//Creating Pannels
		startPanel = new arcadeInfoPanel();
		settingsPanel = new arcadeInfoPanel();
		creditsPanel = new arcadeInfoPanel();
//Adding panels do pane
		mainPane.getChildren().add(startPanel);
		mainPane.getChildren().add(settingsPanel);
		mainPane.getChildren().add(settingsPanel.setPanelText("   Force Blocks\n\n   Multi-Hit Blocks\n\n   External\n   Controller"));
		VBox box=createSettings();
		settingsPanel.getPanel(box);	
		mainPane.getChildren().add(box);
		
		mainPane.getChildren().add(creditsPanel);
		mainPane.getChildren().add(creditsPanel.setPanelText("Created by:\nHubert Kowalski\n\n\n\n\n                    PROZA 2019"));

		
		
		
	}
	/**
	 * Shows Panel, If another is already visible it toggles it and show chosen one.
	 * @param Panel
	 */
	private void showPanel(arcadeInfoPanel Panel)	
	{
		if(lastPanel!=null)
		{
			lastPanel.movePanel();
		}
		Panel.movePanel();
		lastPanel=Panel;
	}
	public Stage getMainStage()
	{
		return mainStage;
	}
	
	
	//BUTTONS
	
	/**
	 * adds button to pane, and positions it
	 * @param btn arcade button to add 
	 */
	private void createMenuButton(arcadeButton btn)	
	{	
		btn.setLayoutX(MENU_BUTTONS_START_X);
		btn.setLayoutY(MENU_BUTTTONS_START_Y + menuButtons.size()*100);
		menuButtons.add(btn);
		mainPane.getChildren().add(btn);
	}
	/**
	 * creates button. Has event handler which does something when clicked
	 */
	private void createStartButton() 
	{
		arcadeButton startButton = new arcadeButton("START");
		createMenuButton(startButton);
		startButton.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				//code which is executed after klicking it
				//showPanel(startPanel);
				gameViewMenager gameVM= new gameViewMenager();
				gameVM.initGame(mainStage, model);
				
				
				
			}
		});
	}
	/**
	 * creates button. Has event handler which shows settings
	 */
	private void createSettingsButton()
	{
		arcadeButton settingsButton = new arcadeButton("SETTINGS");
		createMenuButton(settingsButton);
		settingsButton.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				//code which is executed after klicking it
				showPanel(settingsPanel);
				
			}
		});
	}
	/**
	 * creates button. Has event handler which shows credits screen
	 */
	private void createCreditsButton()
	{
		arcadeButton creditsButton = new arcadeButton("CREDITS");
		createMenuButton(creditsButton);
		creditsButton.setOnAction(new EventHandler<ActionEvent>() {	
			@Override
			public void handle(ActionEvent event) {
				//code which is executed after klicking it
				showPanel(creditsPanel);
			}
		});
		
	}
	/**
	 * creates button. Has event handler which turns proggram off
	 */
	private void createQuitButton()
	{
		arcadeButton quitButton = new arcadeButton("QUIT");
		createMenuButton(quitButton);
		quitButton.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				//code which is executed after klicking it
				mainStage.close();
			}
		});
	}
	
	/**
	 * calls functions which create buttons
	 */
	private void createeButtons()   
	
	{
		
		createStartButton();
		createSettingsButton();
		createCreditsButton();
		createQuitButton();
		
	}
	/**
	 * sets background
	 */
	private void createBackground() 
	{
		Image bImage = new Image("view/resources/Galaxy Center.png",WINDOW_WIDTH,WINDOW_HEIGHT,false,true);
		BackgroundImage background=new BackgroundImage(bImage,BackgroundRepeat.REPEAT,BackgroundRepeat.REPEAT, BackgroundPosition.DEFAULT,null );
		mainPane.setBackground(new Background(background));
	}
	/**
	 *creates settings screen
	 */
	private VBox createSettings() {
		
		SettingsCheckBoxList = new ArrayList<CheckBox>();
		SettingsCheckBoxList.add(new CheckBox());
		SettingsCheckBoxList.add(new CheckBox());
		SettingsCheckBoxList.add(new CheckBox());
		VBox box=new VBox();
		box.getChildren().add(SettingsCheckBoxList.get(0));
		box.getChildren().add(SettingsCheckBoxList.get(1));
		box.getChildren().add(SettingsCheckBoxList.get(2));
		
		
		SettingsCheckBoxList.get(0).selectedProperty().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				model.setForceBlocks(newValue);
		    }
		});
		
		SettingsCheckBoxList.get(1).selectedProperty().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				model.setMHblocks(newValue);
		    }
		});
		
		SettingsCheckBoxList.get(2).selectedProperty().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				model.setUseExCon(newValue);
		    }
		});
		return box;
		
	}
}
