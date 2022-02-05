package upjs.sk.pazacinkaren.App;

import java.util.List;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.util.converter.NumberStringConverter;
import upjs.sk.pazacinkaren.Dao.DaoFactory;
import upjs.sk.pazacinkaren.Models.User;
import upjs.sk.pazacinkaren.Models.UserFxModel;

public class EditUsersPageController {
	
	public static User selectedUser;
	private UserFxModel userModel;

    @FXML
    private Button editButton;
    
    @FXML
    private Button backButton;
    
    @FXML
    private ComboBox<String> usersComboBox;
    
    @FXML
    private TextField nameTextField;

    @FXML
    private TextField surnameTextField;

    @FXML
    private TextField emailTextField;

    @FXML
    private TextField numberTextField;

    @FXML
    private TextField loginTextField;
    
    @FXML
    private TextField passwordTextField;
    
    @FXML
    private TextField isicTextField;
    
    @FXML
    private TextField roleTextField;
    
    public EditUsersPageController() {
		userModel = new UserFxModel();
		selectedUser = new User();
	}
    
    @FXML
    void initialize() {
    	nameTextField.textProperty().bindBidirectional(userModel.nameProperty());
    	surnameTextField.textProperty().bindBidirectional(userModel.surnameProperty());
    	emailTextField.textProperty().bindBidirectional(userModel.emailProperty());
    	numberTextField.textProperty().bindBidirectional(userModel.telNumberProperty(), new NumberStringConverter());
    	loginTextField.textProperty().bindBidirectional(userModel.loginProperty());
    	passwordTextField.textProperty().bindBidirectional(userModel.passwordProperty());
    	isicTextField.textProperty().bindBidirectional(userModel.isicCardNumberProperty(), new NumberStringConverter());
    	roleTextField.textProperty().bindBidirectional(userModel.roleProperty(), new NumberStringConverter());
    	
    	List<User> userList = DaoFactory.INSTANCE.getUserDao().getAll();
    	for (User user : userList) {
			usersComboBox.getItems().add(user.getId() + " " + user.getName() + " " + user.getSurname());
		}
    	
    	usersComboBox.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				String user = usersComboBox.getSelectionModel().getSelectedItem();
				System.out.println(user);
				String array[] = user.split(" ", 0);
				Long id = Long.parseLong(array[0]);
				selectedUser = DaoFactory.INSTANCE.getUserDao().getUserById(id);
				userModel.setUser(selectedUser);
			}
		});
    	
    	editButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				@SuppressWarnings("unused")
				User user = DaoFactory.INSTANCE.getUserDao().saveUser(userModel.getUser());
				AdminPageController.editUsersStage.getScene().getWindow().hide();
			}
		});
    	
    	backButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				AdminPageController.editUsersStage.getScene().getWindow().hide();
			}
		});
    }

}

