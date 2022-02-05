package upjs.sk.pazacinkaren.App;

import java.io.IOException;
import java.util.List;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.util.converter.NumberStringConverter;
import upjs.sk.pazacinkaren.Dao.DaoFactory;
import upjs.sk.pazacinkaren.Models.Palacinka;
import upjs.sk.pazacinkaren.Models.PalacinkaFxModel;

public class EditPalacinkaPageController {

	private static Palacinka selectedPalacinka;
	private PalacinkaFxModel palacinkaModel;

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
	private Button deleteButton;

	@FXML
	private Button editButton;

	@FXML
	private Button backButton;

	@FXML
	private ComboBox<String> palacinkyComboBox;

	public EditPalacinkaPageController() {
		palacinkaModel = new PalacinkaFxModel();
		selectedPalacinka = new Palacinka();
	}

	// https://stackoverflow.com/questions/22622636/javafx-8-how-to-bind-textfield-text-property-to-tableview-integer-property
	@FXML
	void initialize() {
		palacinkaNameTextField.textProperty().bindBidirectional(palacinkaModel.nameProperty());
		descriptionTextField.textProperty().bindBidirectional(palacinkaModel.descriptionProperty());
		weightTextField.textProperty().bindBidirectional(palacinkaModel.weightProperty(), new NumberStringConverter());
		typeTextField.textProperty().bindBidirectional(palacinkaModel.weightTypeProperty());
		priceTextField.textProperty().bindBidirectional(palacinkaModel.priceProperty(), new NumberStringConverter());
		currencyTextField.textProperty().bindBidirectional(palacinkaModel.currencyProperty());

		List<Palacinka> palacinkaList = DaoFactory.INSTANCE.getPalacinkaDao().getAll();
		for (Palacinka palacinka : palacinkaList) {
			palacinkyComboBox.getItems().add(palacinka.getId() + " " + palacinka.getName());
		}

		palacinkyComboBox.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				String palacinka = palacinkyComboBox.getSelectionModel().getSelectedItem();
				String array[] = palacinka.split(" ", 0);
				Long id = Long.parseLong(array[0]);
				selectedPalacinka = DaoFactory.INSTANCE.getPalacinkaDao().getPalacinkaById(id);
				palacinkaModel.setPalacinka(selectedPalacinka);
			}
		});

		deleteButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				if (selectedPalacinka == null) {
					new Alert(Alert.AlertType.INFORMATION, "Choose palacinka from the ComboBox!").showAndWait();
				} else {
					@SuppressWarnings("unused")
					Palacinka palacinka = DaoFactory.INSTANCE.getPalacinkaDao().deletePalacinka(selectedPalacinka);
				}
			}
		});

		editButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				@SuppressWarnings("unused")
				Palacinka palacinka = DaoFactory.INSTANCE.getPalacinkaDao()
						.savePalacinka(palacinkaModel.getPalacinka());
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
