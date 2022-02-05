package upjs.sk.pazacinkaren.App;

import java.io.IOException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import upjs.sk.pazacinkaren.Dao.DaoFactory;
import upjs.sk.pazacinkaren.Models.Palacinka;

public class AdminPageController {

	public static Stage ordersHistoryStage = new Stage();
	public static Stage usersPromotionStage = new Stage();
	public static Stage editUsersStage = new Stage();
	public static Stage orderStage = new Stage();
	public static Stage palacinkaListStage = new Stage();
	public static ObservableList<Palacinka> newOrder = FXCollections.observableArrayList();

	@FXML
	private MenuBar adminMenuBar;

	@FXML
	private MenuItem addMenuItem;

	@FXML
	private MenuItem editMenuItem;

	@FXML
	private MenuItem historyMenuItem;

	@FXML
	private MenuItem usersListMenuItem;

	@FXML
	private MenuItem editUsersMenuList;

	@FXML
	private Button orderButton;

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

		orderButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				openPalacinkaListPage();
			}
		});

		backButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				openSignInPage();
			}
		});

		addMenuItem.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				openAddPalacinkaPage();
			}
		});

		editMenuItem.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				openEditPalacinkaPage();
			}
		});

		historyMenuItem.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				openOrdersHistoryPage();
			}
		});

		usersListMenuItem.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				openUsersPromotionPage();
			}
		});

		editUsersMenuList.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				openEditUsersPage();
			}
		});
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

	private void openSignInPage() {
		SignInPageController controller = new SignInPageController();
		FXMLLoader loader = new FXMLLoader(getClass().getResource("SignInPage.fxml"));
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

	private void openAddPalacinkaPage() {
		AddPalacinkaPageController controller = new AddPalacinkaPageController();
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("AddPalacinkaPage.fxml"));
		fxmlLoader.setController(controller);
		try {
			Parent parent = fxmlLoader.load();
			Scene scene = new Scene(parent);
			App.stage.setTitle("Add Palacinka");
			App.stage.setScene(scene);
			App.stage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void openEditPalacinkaPage() {
		EditPalacinkaPageController controller = new EditPalacinkaPageController();
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("EditPalacinkaPage.fxml"));
		fxmlLoader.setController(controller);
		try {
			Parent parent = fxmlLoader.load();
			Scene scene = new Scene(parent);
			App.stage.setTitle("Edit Palacinka");
			App.stage.setScene(scene);
			App.stage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void openOrdersHistoryPage() {
		OrdersHistoryPageController controller = new OrdersHistoryPageController();
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("OrdersHistoryPage.fxml"));
		fxmlLoader.setController(controller);
		try {
			Parent parent = fxmlLoader.load();
			Scene scene = new Scene(parent);
			ordersHistoryStage.setTitle("Orders History");
			ordersHistoryStage.setMinHeight(580);
			ordersHistoryStage.setMinWidth(980);
			ordersHistoryStage.setScene(scene);
			ordersHistoryStage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void openUsersPromotionPage() {
		UsersPromotionPageController controller = new UsersPromotionPageController();
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("UsersPromotionPage.fxml"));
		fxmlLoader.setController(controller);
		try {
			Parent parent = fxmlLoader.load();
			Scene scene = new Scene(parent);
			usersPromotionStage.setTitle("Users Promotion");
			usersPromotionStage.setMinHeight(580);
			usersPromotionStage.setMinWidth(980);
			usersPromotionStage.setScene(scene);
			usersPromotionStage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void openEditUsersPage() {
		EditUsersPageController controller = new EditUsersPageController();
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("EditUsersPage.fxml"));
		fxmlLoader.setController(controller);
		try {
			Parent parent = fxmlLoader.load();
			Scene scene = new Scene(parent);
			editUsersStage.setTitle("Edit Users");
			editUsersStage.setMinHeight(580);
			editUsersStage.setMinWidth(980);
			editUsersStage.setScene(scene);
			editUsersStage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
