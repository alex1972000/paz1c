package upjs.sk.pazacinkaren.Models;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;


public class UserFxModel {
	
	private Long id;
	private StringProperty name = new SimpleStringProperty();
	private StringProperty surname = new SimpleStringProperty();
	private StringProperty email = new SimpleStringProperty();
	private IntegerProperty telNumber = new SimpleIntegerProperty();
	private StringProperty login = new SimpleStringProperty();
	private StringProperty password = new SimpleStringProperty();
	private IntegerProperty isicCardNumber = new SimpleIntegerProperty();
	private IntegerProperty role = new SimpleIntegerProperty();
	
	public String getName() {
		return name.get();
	}
	public void setName(String name) {
		this.name.set(name);
	}
	public StringProperty nameProperty() {
		return name;
	}
	
	public String getSurname() {
		return surname.get();
	}
	public void setSurname(String surname) {
		this.surname.set(surname);
	}
	public StringProperty surnameProperty() {
		return surname;
	}
	
	public String getLogin() {
		return login.get();
	}
	public void setLogin(String login) {
		this.login.set(login);
	}
	public StringProperty loginProperty() {
		return login;
	}
	
	public String getPassword() {
		return password.get();
	}
	public void setPassword(String password) {
		this.password.set(password);
	}
	public StringProperty passwordProperty() {
		return password;
	}
	
	public String getEmail() {
		return email.get();
	}
	public void setEmail(String email) {
		this.email.set(email);
	}
	public StringProperty emailProperty() {
		return email;
	}
	
	public int getTelNumber() {
		return telNumber.get();
	}
	public void setTelNumber(int telNumber) {
		this.telNumber.set(telNumber);
	}
	public IntegerProperty telNumberProperty() {
		return telNumber;
	}
	
	public int getIsicCardNumber() {
		return isicCardNumber.get();
	}
	public void setIsicCardNumber(int isicCardNumber) {
		this.isicCardNumber.set(isicCardNumber);
	}
	public IntegerProperty isicCardNumberProperty() {
		return isicCardNumber;
	}
	
	public int getRole() {
		return role.get();
	}
	public void setRole(int role) {
		this.role.set(role);
	}
	public IntegerProperty roleProperty() {
		return role;
	}
	
	public void setUser(User user) {
		this.id = user.getId();
		setName(user.getName());
		setSurname(user.getSurname());
		setLogin(user.getLogin());
		setPassword(user.getPassword());
		setEmail(user.getEmail());
		setTelNumber(user.getTelNumber());
		setIsicCardNumber(user.getIsicCardNumber());
		setRole(user.getRole());
	}
	
	public User getUser() {
		User user = new User();
		user.setId(id);
		user.setName(getName());
		user.setSurname(getSurname());
		user.setLogin(getLogin());
		user.setPassword(getPassword());
		user.setEmail(getEmail());
		user.setTelNumber(getTelNumber());
		user.setIsicCardNumber(getIsicCardNumber());
		user.setRole(getRole());
		return user;
	}
}
