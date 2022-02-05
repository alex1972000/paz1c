package upjs.sk.pazacinkaren.App;

// https://stackoverflow.com/questions/26563390/detect-doubleclick-on-row-of-tableview-javafx

import java.io.IOException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.util.Callback;
import upjs.sk.pazacinkaren.Dao.DaoFactory;
import upjs.sk.pazacinkaren.Models.Palacinka;

public class PalacinkaListPageController {

	public static ObservableList<Palacinka> newOrder = FXCollections.observableArrayList();
	public static Stage orderStage = new Stage();

	@FXML
	private Button nextButton;

	@FXML
	private Button backButton;

	@FXML
	private TableView<Palacinka> palacinkyTableView;

	@FXML
	void initialize() {
		palacinkyTableView.setItems(FXCollections.observableArrayList(DaoFactory.INSTANCE.getPalacinkaDao().getAll()));

		TableColumn<Palacinka, String> name = new TableColumn<Palacinka, String>("Name");
		name.setCellValueFactory(new PropertyValueFactory<Palacinka, String>("name"));
		name.setStyle("-fx-alignment: CENTER; -fx-font-weight: bold;");
		palacinkyTableView.getColumns().add(name);

		TableColumn<Palacinka, String> description = new TableColumn<Palacinka, String>("Description");
		description.setCellValueFactory(new PropertyValueFactory<Palacinka, String>("description"));
		description.setStyle("-fx-alignment: CENTER; -fx-font-weight: bold;");
		palacinkyTableView.getColumns().add(description);

		TableColumn<Palacinka, Integer> weight = new TableColumn<Palacinka, Integer>("Weight");
		weight.setCellValueFactory(new PropertyValueFactory<Palacinka, Integer>("weight"));
		weight.setStyle("-fx-alignment: CENTER; -fx-font-weight: bold;");
		palacinkyTableView.getColumns().add(weight);

		TableColumn<Palacinka, String> weightType = new TableColumn<Palacinka, String>("Weight Type");
		weightType.setCellValueFactory(new PropertyValueFactory<Palacinka, String>("weightType"));
		weightType.setStyle("-fx-alignment: CENTER; -fx-font-weight: bold;");
		palacinkyTableView.getColumns().add(weightType);

		TableColumn<Palacinka, Double> price = new TableColumn<Palacinka, Double>("Price");
		price.setCellValueFactory(new PropertyValueFactory<Palacinka, Double>("price"));
		price.setStyle("-fx-alignment: CENTER; -fx-font-weight: bold;");
		palacinkyTableView.getColumns().add(price);

		TableColumn<Palacinka, String> currency = new TableColumn<Palacinka, String>("Currency");
		currency.setCellValueFactory(new PropertyValueFactory<Palacinka, String>("currency"));
		currency.setStyle("-fx-alignment: CENTER; -fx-font-weight: bold;");
		palacinkyTableView.getColumns().add(currency);

		palacinkyTableView.setRowFactory(new Callback<TableView<Palacinka>, TableRow<Palacinka>>() {
			@Override
			public TableRow<Palacinka> call(TableView<Palacinka> param) {
				final TableRow<Palacinka> row = new TableRow<Palacinka>();
				row.setOnMouseClicked(new EventHandler<MouseEvent>() {
					@Override
					public void handle(MouseEvent event) {
						if (event.getClickCount() == 2 && (!row.isEmpty())) {
							Palacinka rowInfo = DaoFactory.INSTANCE.getPalacinkaDao()
									.getByName(row.getItem().getName());
							newOrder.add(rowInfo);
							new Alert(Alert.AlertType.INFORMATION,
									"You selected this palacinka into your order: " + rowInfo.getName()).showAndWait();
						}
					}
				});
				return row;
			}
		});

		nextButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				openOrderPage();
			}
		});

		backButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				openSignInPage();
			}
		});
	}

	private void openOrderPage() {
		OrderPageController controller = new OrderPageController();
		FXMLLoader loader = new FXMLLoader(getClass().getResource("OrderPage.fxml"));
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
