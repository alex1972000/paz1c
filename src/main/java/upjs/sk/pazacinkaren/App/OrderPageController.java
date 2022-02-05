package upjs.sk.pazacinkaren.App;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import upjs.sk.pazacinkaren.Dao.DaoFactory;
import upjs.sk.pazacinkaren.Models.Order;
import upjs.sk.pazacinkaren.Models.Palacinka;

public class OrderPageController {

	public static Stage orderStage = new Stage();

	@FXML
	private ComboBox<String> addressComboBox;

	@FXML
	private Button confirmButton;

	@FXML
	private Button orderButton;

	@FXML
	private Button backButton;

	@FXML
	private ListView<Palacinka> orderListView;

	@FXML
	private Label totalLabel;

	@FXML
	void initialize() {
		confirmButton.setDisable(true);
		orderButton.setDisable(true);

		orderListView.setItems(PalacinkaListPageController.newOrder);
		orderListView.setStyle("-fx-font-weight: bold;" + "");

		addressComboBox.getItems().addAll("Popradská 66", "Popradská 76", "Kysucká 16", "Pražská 4", "Medická 4",
				"Medická 6");
		final boolean isMyComboBoxEmpty = addressComboBox.getSelectionModel().isEmpty();
		addressComboBox.valueProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				if (isMyComboBoxEmpty) {
					confirmButton.setDisable(false);
				}
			}
		});

		confirmButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				totalLabel.setText(getTotalPrice() + "");
				orderButton.setDisable(false);
			}
		});

		orderButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				Order addOrder = new Order();
				Map<Palacinka, Integer> mapCount = new HashMap<>();
				for (Palacinka palacinka : PalacinkaListPageController.newOrder) {
					if (mapCount.containsKey(palacinka))
						mapCount.put(palacinka, mapCount.get(palacinka) + 1);
					else
						mapCount.put(palacinka, 1);

					addOrder.setAddress(addressComboBox.getValue());
					addOrder.setIdPalacinka(palacinka.getId());
					addOrder.setIdUser(SignInPageController.signedInUser.getId());
					addOrder.setDate(LocalDateTime.now());
					addOrder.setKusyPalaciniek(mapCount);
				}
				@SuppressWarnings("unused")
				Order order = DaoFactory.INSTANCE.getOrderDao().saveOrder(addOrder);
				MailSender.send();
				PalacinkaListPageController.newOrder = FXCollections.observableArrayList();
				openOutroPage();
			}
		});

		orderListView.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				if (event.getClickCount() == 2) {
					@SuppressWarnings("unused")
					Palacinka selectedPalacinka = orderListView.getSelectionModel().getSelectedItem();
					int palacinkaOrderIndex = orderListView.getSelectionModel().getSelectedIndex();
					PalacinkaListPageController.newOrder.remove(palacinkaOrderIndex);
					orderListView.setItems(PalacinkaListPageController.newOrder);
					orderListView.setStyle("-fx-font-weight: bold;" + "");
				}
			}
		});

		backButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				openPalacinkaListPage();
			}
		});
	}

	public double getTotalPrice() {
		double price = 0;
		for (Palacinka palacinka : PalacinkaListPageController.newOrder) {
			price += palacinka.getPrice();
		}
		return price;
	}

	private void openOutroPage() {
		OutroPageController controller = new OutroPageController();
		FXMLLoader loader = new FXMLLoader(getClass().getResource("OutroPage.fxml"));
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

	private void openPalacinkaListPage() {
		PalacinkaListPageController controller = new PalacinkaListPageController();
		FXMLLoader loader = new FXMLLoader(getClass().getResource("PalacinkaListPage.fxml"));
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

	public static String mailMessage() {
		String result = "";
		String currency = "";
		for (Palacinka palacinka : PalacinkaListPageController.newOrder) {
			result += palacinka.getName() + " | " + palacinka.getDescription() + " | " + palacinka.getWeight() + "g | "
					+ palacinka.getWeightType() + "\n\n";
		}
		double price = 0;
		for (Palacinka palacinka : PalacinkaListPageController.newOrder) {
			price += palacinka.getPrice();
			currency = palacinka.getCurrency();
		}
		result += "Total price: " + price + " " + currency;
		return result;
	}

}
