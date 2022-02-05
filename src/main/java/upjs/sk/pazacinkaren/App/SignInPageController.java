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
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import upjs.sk.pazacinkaren.Dao.Constants;
import upjs.sk.pazacinkaren.Dao.DaoFactory;
import upjs.sk.pazacinkaren.Dao.EntityNotFoundException;
import upjs.sk.pazacinkaren.Interfaces.RoleDao;
import upjs.sk.pazacinkaren.Models.User;

public class SignInPageController {

	private RoleDao roleDao;
	public static User signedInUser;
	public static Stage signUpStage = new Stage();

	public SignInPageController() {
		roleDao = DaoFactory.INSTANCE.getRoleDao();
	}

	@FXML
	private TextField loginTextField;

	@FXML
	private PasswordField passwordPasswordField;

	@FXML
	private Button signInButton;

	@FXML
	private Button signUpButton;

	@FXML
	void initialize() {
		
		signInButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				getUserData();
			}
		});
		signUpButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				openSignUpPage();
			}
		});
	}

	private void getUserData() {
		String login = loginTextField.getText();
		String password = passwordPasswordField.getText();
		try {
			User user = DaoFactory.INSTANCE.getUserDao().getUserByLogin(login, password);
			signedInUser = user;
			App.stage.close();
			if (user.getRole() == roleDao.getRoleByName(Constants.admin))
				openAdminPage();
			else
				openPalacinkaListPage();
		} catch (EntityNotFoundException e) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Account Sign In");
			alert.setContentText("Invalid username or password.");
			alert.show();
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

	private void openAdminPage() {
		AdminPageController controller = new AdminPageController();
		FXMLLoader loader = new FXMLLoader(getClass().getResource("AdminPage.fxml"));
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

	private void openSignUpPage() {
		SignUpPageController controller = new SignUpPageController();
		FXMLLoader loader = new FXMLLoader(getClass().getResource("SignUpPage.fxml"));
		loader.setController(controller);
		try {
			Parent parent = loader.load();
			Scene scene = new Scene(parent);
			scene.getStylesheets().add(getClass().getResource("button.css").toExternalForm());
			signUpStage.setTitle("Sign Up");
			signUpStage.setScene(scene);
			signUpStage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
