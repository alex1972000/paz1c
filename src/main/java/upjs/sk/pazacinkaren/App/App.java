package upjs.sk.pazacinkaren.App;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application{

	public static Stage stage;

	@Override
	public void start(Stage primaryStage) throws Exception {
		stage = primaryStage;
		SignInPageController controller = new SignInPageController();
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("SignInPage.fxml"));
		fxmlLoader.setController(controller);
		Parent parent = fxmlLoader.load();
		Scene scene = new Scene(parent);
		scene.getStylesheets().add(getClass().getResource("button.css").toExternalForm());
		stage.setMinHeight(580);
		stage.setMinWidth(980);
		stage.setTitle("PAZacinkareò");
		stage.setScene(scene);
		stage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}
}
