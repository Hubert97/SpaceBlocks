package application;
	
import javafx.application.Application;
import javafx.stage.Stage;
import model.Model;
import view.viewMenager;

public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			Model model=new Model();
			viewMenager menager =new viewMenager(model);
			primaryStage=menager.getMainStage();
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
