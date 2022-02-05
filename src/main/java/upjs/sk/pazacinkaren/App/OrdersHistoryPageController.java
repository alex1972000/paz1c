package upjs.sk.pazacinkaren.App;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import upjs.sk.pazacinkaren.Dao.DaoFactory;
import upjs.sk.pazacinkaren.Models.Order;

public class OrdersHistoryPageController {

	private Long selectedId;

	@FXML
	private Button deleteButton;
	
	@FXML
    private Button backButton;

	@FXML
	private ListView<Order> ordersHistoryListView;

	@FXML
	void initialize() {
		ordersHistoryListView
				.setItems(FXCollections.observableList(DaoFactory.INSTANCE.getOrderDao().getAllForHistory()));
		ordersHistoryListView.setStyle("-fx-font-weight: bold;");

		ordersHistoryListView.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				if (event.getClickCount() == 2) {
					String selectedOrder = ordersHistoryListView.getSelectionModel().getSelectedItem().toString();
					String array[] = selectedOrder.split(" ");
					String array2[] = array[1].split("=");
					String array3 = array2[1].substring(0, array2[1].length() - 1);
					selectedId = Long.parseLong(array3);
					new Alert(Alert.AlertType.INFORMATION, "Order with ID: " + selectedId + " was selected")
							.showAndWait();
				}
			}
		});

		deleteButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				if (selectedId == null) {
					new Alert(Alert.AlertType.INFORMATION, "No order is selected!").showAndWait();
				} else {
					DaoFactory.INSTANCE.getOrderDao().deleteOrder(selectedId);
					ordersHistoryListView.setItems(
							FXCollections.observableList(DaoFactory.INSTANCE.getOrderDao().getAllForHistory()));
					ordersHistoryListView.setStyle("-fx-font-weight: bold;");
				}
			}
		});
		
		backButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				AdminPageController.ordersHistoryStage.getScene().getWindow().hide();
			}
		});
	}

}
