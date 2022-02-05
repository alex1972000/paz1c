package upjs.sk.pazacinkaren.App;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import upjs.sk.pazacinkaren.Dao.DaoFactory;
import upjs.sk.pazacinkaren.Models.User;

public class SignUpPageController {

	@FXML
	private TextField emailTextField;

	@FXML
	private TextField isicTextField;

	@FXML
	private TextField loginTextField;

	@FXML
	private TextField nameTextField;

	@FXML
	private TextField numberTextField;

	@FXML
	private TextField passwordTextField;

	@FXML
	private Button signUpButton;
	
	@FXML
    private Button backButton;

	@FXML
	private TextField surnameTextField;

	@FXML
	void initialize() {
		signUpButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				User newUser = new User();
				newUser.setName(nameTextField.getText());
				newUser.setSurname(surnameTextField.getText());
				newUser.setEmail(emailTextField.getText());
				if(numberTextField.getText().length() >= 3) {
					newUser.setTelNumber(Integer.parseInt(numberTextField.getText().substring(1, numberTextField.getLength())));
				}
				newUser.setLogin(loginTextField.getText());
				newUser.setPassword(passwordTextField.getText());
				if(numberTextField.getText().length() >= 1) {
					newUser.setIsicCardNumber(Integer.parseInt(isicTextField.getText()));
				}
				if (newUser.getName() == null || newUser.getSurname().length() == 0|| newUser.getEmail().length() == 0 || newUser.getTelNumber() == 0 || newUser.getLogin().length() == 0 || newUser.getPassword().length() == 0 || newUser.getIsicCardNumber() == 0)  {
					Alert alert = new Alert(AlertType.ERROR);
					alert.setTitle("Account Sign Up");
					alert.setContentText(
							"Invalid sign-up information, please check if all boxes are filled with valid information!");
					alert.show();
				} else {
					User user = DaoFactory.INSTANCE.getUserDao().saveUser(newUser);
					SignInPageController.signUpStage.getScene().getWindow().hide();
				}
			}
		});
		
		backButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				SignInPageController.signUpStage.getScene().getWindow().hide();
			}
		});
	}

}
