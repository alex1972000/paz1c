package upjs.sk.pazacinkaren.Models;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/*
 * Entita, ktorá v sebe uchováva inštatné premenne, getre, 
 * setre, toString metodu, hashcode a equals. 
 */

public class User {

	private Long id;
	private String name;
	private String surname;
	private String email;
	private int telNumber;
	private String login;
	private String password;
	private int isicCardNumber;
	private int role;
	private String nazovRole;
	private List<User> userList = new ArrayList<User>();

	public User(long id, String name, String surname, String email, int telNumber, String login, String password,
			int isicCardNumber, int role) {
		this.id = id;
		this.name = name;
		this.surname = surname;
		this.email = email;
		this.telNumber = telNumber;
		this.login = login;
		this.password = password;
		this.isicCardNumber = isicCardNumber;
		this.role = role;
	}

	public User() {

	}

	public User(long id, String name, String surname, String email, int telNumber, String login, String password,
			int isicCardNumber, int role, String menoRoli) {
		this.id = id;
		this.name = name;
		this.surname = surname;
		this.email = email;
		this.telNumber = telNumber;
		this.login = login;
		this.password = password;
		this.isicCardNumber = isicCardNumber;
		this.role = role;
		this.nazovRole = menoRoli;
	}

	public User(String name, String surname, String email, int telNumber, String login, String password,
			int isicCardNumber, int role) {
		this.name = name;
		this.surname = surname;
		this.email = email;
		this.telNumber = telNumber;
		this.login = login;
		this.password = password;
		this.isicCardNumber = isicCardNumber;
		this.role = role;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public int getTelNumber() {
		return telNumber;
	}

	public void setTelNumber(int tel_number) {
		this.telNumber = tel_number;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getIsicCardNumber() {
		return isicCardNumber;
	}

	public void setIsicCardNumber(int isicCardNumber) {
		this.isicCardNumber = isicCardNumber;
	}

	public int getRole() {
		return role;
	}

	public void setRole(int role) {
		this.role = role;
	}

	public List<User> getUserList() {
		return userList;
	}

	public void setUserList(List<User> userList) {
		this.userList = userList;
	}

	public String getNazovRole() {
		return nazovRole;
	}

	public void setNazovRole(String nazovRole) {
		this.nazovRole = nazovRole;
	}

	@Override
	public String toString() {
		return "User " + id + " | " + name + " " + surname + " | " + email + " | "
				+ telNumber + " | login=" + login + " | password=" + password + " | isic=" + isicCardNumber
				+ " | role=" + role + " | " + nazovRole;
	}

	@Override
	public int hashCode() {
		return Objects.hash(email, id, isicCardNumber, login, name, nazovRole, password, role, surname, telNumber,
				userList);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		return Objects.equals(email, other.email) && Objects.equals(id, other.id)
				&& isicCardNumber == other.isicCardNumber && Objects.equals(login, other.login)
				&& Objects.equals(name, other.name) && Objects.equals(nazovRole, other.nazovRole)
				&& Objects.equals(password, other.password) && role == other.role
				&& Objects.equals(surname, other.surname) && telNumber == other.telNumber
				&& Objects.equals(userList, other.userList);
	}

}
