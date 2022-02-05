package upjs.sk.pazacinkaren.App;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;

public class OutroPageController {

	@FXML
	private Button backButton;

	@FXML
	void initialize() {
		backButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				openSignInPage();
			}
		});
	}

	private void openSignInPage() {
		SignInPageController controller = new SignInPageController();
		FXMLLoader loader = new FXMLLoader(getClass().getResource("SignInPage.fxml"));
		loader.setController(controller);
		try {
			Parent parent = loader.load();
			Scene scene = new Scene(parent);
			scene.getStylesheets().add(getClass().getResource("button.css").toExternalForm());
			App.stage.setScene(scene);
			App.stage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
