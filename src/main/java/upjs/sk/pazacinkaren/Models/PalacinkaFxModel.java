package upjs.sk.pazacinkaren.Models;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class PalacinkaFxModel {
	
	private Long id;
	private StringProperty name = new SimpleStringProperty();
	private StringProperty description = new SimpleStringProperty();
	private IntegerProperty weight = new SimpleIntegerProperty();
	private StringProperty weightType = new SimpleStringProperty();
	private DoubleProperty price = new SimpleDoubleProperty();
	private StringProperty currency = new SimpleStringProperty();

	public String getName() {
		return name.get();
	}

	public void setName(String name) {
		this.name.set(name);
	}

	public StringProperty nameProperty() {
		return name;
	}

	public String getDescription() {
		return description.get();
	}

	public void setDescription(String description) {
		this.description.set(description);
	}

	public StringProperty descriptionProperty() {
		return description;
	}

	public int getWeight() {
		return weight.get();
	}

	public void setWeight(int weight) {
		this.weight.set(weight);
	}

	public IntegerProperty weightProperty() {
		return weight;
	}

	public String getWeightType() {
		return weightType.get();
	}

	public void setWeightType(String weightType) {
		this.weightType.set(weightType);
	}

	public StringProperty weightTypeProperty() {
		return weightType;
	}

	public double getPrice() {
		return price.get();
	}

	public void setPrice(double price) {
		this.price.set(price);
	}

	public DoubleProperty priceProperty() {
		return price;
	}

	public String getCurrency() {
		return currency.get();
	}

	public void setCurrency(String currency) {
		this.currency.set(currency);
	}

	public StringProperty currencyProperty() {
		return currency;
	}

	public void setPalacinka(Palacinka palacinka) {
		this.id = palacinka.getId();
		setName(palacinka.getName());
		setDescription(palacinka.getDescription());
		setWeight(palacinka.getWeight());
		setWeightType(palacinka.getWeightType());
		setPrice(palacinka.getPrice());
		setCurrency(palacinka.getCurrency());
	}

	public Palacinka getPalacinka() {
		Palacinka palacinka = new Palacinka();
		palacinka.setId(id);
		palacinka.setName(getName());
		palacinka.setDescription(getDescription());
		palacinka.setWeight(getWeight());
		palacinka.setWeightType(getWeightType());
		palacinka.setPrice(getPrice());
		palacinka.setCurrency(getCurrency());
		return palacinka;
	}

}
