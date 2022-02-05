package upjs.sk.pazacinkaren.App;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import upjs.sk.pazacinkaren.Dao.DaoFactory;
import upjs.sk.pazacinkaren.Dao.EntityNotFoundException;
import upjs.sk.pazacinkaren.Models.Palacinka;

public class AddPalacinkaPageController {

	@FXML
	private Button addButton;

	@FXML
	private Button backButton;

	@FXML
	private TextField palacinkaNameTextField;

	@FXML
	private TextField descriptionTextField;

	@FXML
	private TextField weightTextField;

	@FXML
	private TextField typeTextField;

	@FXML
	private TextField priceTextField;

	@FXML
	private TextField currencyTextField;

	@FXML
	void initialize() {
		addButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				try {
					Palacinka addedPalacinka = new Palacinka();
					addedPalacinka.setName(palacinkaNameTextField.getText());
					addedPalacinka.setDescription(descriptionTextField.getText());
					addedPalacinka.setWeight(Integer.parseInt(weightTextField.getText()));
					addedPalacinka.setWeightType(typeTextField.getText());
					addedPalacinka.setPrice(Double.parseDouble(priceTextField.getText()));
					addedPalacinka.setCurrency(currencyTextField.getText());
					@SuppressWarnings("unused")
					Palacinka palacinka = DaoFactory.INSTANCE.getPalacinkaDao().savePalacinka(addedPalacinka);
				} catch (NumberFormatException e) {
					e.printStackTrace();
					Alert alert = new Alert(AlertType.ERROR);
					alert.setTitle("Add palacinka");
					alert.setContentText("Please check if all boxes are filled with valid information!");
					alert.show();
				} catch (EntityNotFoundException e) {
					e.printStackTrace();
					Alert alert = new Alert(AlertType.ERROR);
					alert.setTitle("Add palacinka");
					alert.setContentText("Please check if all boxes are filled with valid information!");
					alert.show();
				}
			}
		});

		backButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				openAdminPage();
			}
		});
	}

	private void openAdminPage() {
		AdminPageController controller = new AdminPageController();
		FXMLLoader loader = new FXMLLoader(getClass().getResource("AdminPage.fxml"));
		loader.setController(controller);
		try {
			Parent parent = loader.load();
			Scene scene = new Scene(parent);
			App.stage.setScene(scene);
			App.stage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
