package application;
	
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;


public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			
			Class.forName("com.mysql.jdbc.Driver");
			
			FXMLLoader loader = new FXMLLoader(getClass().getResource("Login.fxml"));
			
        	AnchorPane root = new AnchorPane(); 
			
			//BorderPane root = new BorderPane();
			Scene scene = new Scene(root,497,287);
			scene.setRoot((Parent) loader.load());
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.setTitle("工资管理系统");
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
