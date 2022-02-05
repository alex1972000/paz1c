package upjs.sk.pazacinkaren.Models;

import java.util.ArrayList;
import java.util.List;

/*
 * Entita, ktorá v sebe uchováva inštatné premenne, getre, 
 * setre, toString metodu, hashcode a equals. 
 */

public class Palacinka {

	private Long id;
	private String name;
	private String description;
	private int weight;
	private String weightType;
	private double price;
	private String currency;
	private List<Palacinka> palacinkaList = new ArrayList<Palacinka>();

	public Palacinka(Long id, String name, String description, int weight, String weightType, double price,
			String currency) {
		super();
		this.id = id;
		this.name = name;
		this.description = description;
		this.weight = weight;
		this.weightType = weightType;
		this.price = price;
		this.currency = currency;
	}

	public Palacinka() {

	}

	public Palacinka(String name, String description, int weight, String weightType, double price, String currency) {
		super();
		this.name = name;
		this.description = description;
		this.weight = weight;
		this.weightType = weightType;
		this.price = price;
		this.currency = currency;
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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getWeight() {
		return weight;
	}

	public void setWeight(int weight) {
		this.weight = weight;
	}

	public String getWeightType() {
		return weightType;
	}

	public void setWeightType(String weightType) {
		this.weightType = weightType;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public List<Palacinka> getPalacinkaList() {
		return palacinkaList;
	}

	public void setPalacinkaList(List<Palacinka> palacinkaList) {
		this.palacinkaList = palacinkaList;
	}

	@Override
	public String toString() {
		return "Palacinka " + id + " | " + name + " | " + description + " | " + weight + "g" + " | " + weightType
				+ " | price= " + price + " " + currency;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((currency == null) ? 0 : currency.hashCode());
		result = prime * result + ((description == null) ? 0 : description.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((palacinkaList == null) ? 0 : palacinkaList.hashCode());
		long temp;
		temp = Double.doubleToLongBits(price);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + weight;
		result = prime * result + ((weightType == null) ? 0 : weightType.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Palacinka other = (Palacinka) obj;
		if (currency == null) {
			if (other.currency != null)
				return false;
		} else if (!currency.equals(other.currency))
			return false;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (palacinkaList == null) {
			if (other.palacinkaList != null)
				return false;
		} else if (!palacinkaList.equals(other.palacinkaList))
			return false;
		if (Double.doubleToLongBits(price) != Double.doubleToLongBits(other.price))
			return false;
		if (weight != other.weight)
			return false;
		if (weightType == null) {
			if (other.weightType != null)
				return false;
		} else if (!weightType.equals(other.weightType))
			return false;
		return true;
	}

}
