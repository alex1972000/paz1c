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
import upjs.sk.pazacinkaren.Models.User;

public class UsersPromotionPageController {

	private static User selectedUser = null;

	@FXML
	private Button deleteButton;

	@FXML
	private Button demoteButton;

	@FXML
	private Button promoteButton;
	
	@FXML
	private Button backButton;

	@FXML
	private ListView<User> usersListView;

	@FXML
	void initialize() {
		deleteButton.setDisable(true);
		demoteButton.setDisable(true);
		promoteButton.setDisable(true);
		
		usersListView.setItems(FXCollections.observableArrayList(DaoFactory.INSTANCE.getUserDao().getAll()));
		usersListView.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				if (event.getClickCount() == 2) {
					deleteButton.setDisable(false);
					demoteButton.setDisable(false);
					promoteButton.setDisable(false);
					selectedUser = DaoFactory.INSTANCE.getUserDao()
							.getUserById(usersListView.getSelectionModel().getSelectedItem().getId());
				}
			}
		});

		deleteButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				if (selectedUser != null) {
					@SuppressWarnings("unused")
					User user = DaoFactory.INSTANCE.getUserDao().deleteUser(selectedUser.getId());
					usersListView
							.setItems(FXCollections.observableArrayList(DaoFactory.INSTANCE.getUserDao().getAll()));
				} else {
					new Alert(Alert.AlertType.INFORMATION, "Select user from the list!").showAndWait();
				}
			}
		});

		demoteButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				if (selectedUser.getRole() == 2) {
					@SuppressWarnings("unused")
					User user = DaoFactory.INSTANCE.getUserDao().demoteUser(selectedUser);
					usersListView
							.setItems(FXCollections.observableArrayList(DaoFactory.INSTANCE.getUserDao().getAll()));
				} else {
					new Alert(Alert.AlertType.ERROR, "Select admin from the list to demote!").showAndWait();
				}
			}
		});

		promoteButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				if (selectedUser.getRole() == 1) {
					@SuppressWarnings("unused")
					User user = DaoFactory.INSTANCE.getUserDao().promoteUser(selectedUser);
					usersListView
							.setItems(FXCollections.observableArrayList(DaoFactory.INSTANCE.getUserDao().getAll()));
				} else {
					new Alert(Alert.AlertType.ERROR, "Select normal user from the list to promote!").showAndWait();
				}
			}
		});
		
		backButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				AdminPageController.usersPromotionStage.getScene().getWindow().hide();
			}
		});
	}
}
