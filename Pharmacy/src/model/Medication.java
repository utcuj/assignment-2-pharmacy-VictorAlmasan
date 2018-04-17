package model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Medication {

	private LongProperty medicationId;
	private StringProperty name;
	private StringProperty ingredients;
	private StringProperty manufacturer;
	private IntegerProperty quantity;
	private IntegerProperty price;

	public Medication() {
		this(0, null, null, null, 0, 0);
	}

	public Medication(long medicationId, String name, String ingredients, String manufacturer, int quantity,
			int price) {
		super();
		this.medicationId = new SimpleLongProperty(medicationId);
		this.name = new SimpleStringProperty(name);
		this.ingredients = new SimpleStringProperty(ingredients);
		this.manufacturer = new SimpleStringProperty(manufacturer);
		this.quantity = new SimpleIntegerProperty(quantity);
		this.price = new SimpleIntegerProperty(price);
	}

	public long getMedicationId() {
		return medicationId.get();
	}

	public void setMedicationId(long medicationId) {
		this.medicationId.set(medicationId);
	}

	public LongProperty medicationIdProperty() {
		return medicationId;
	}

	public String getName() {
		return name.get();
	}

	public void setName(String name) {
		this.name.set(name);
	}

	public StringProperty nameProperty() {
		return name;
	}

	public String getIngredients() {
		return ingredients.get();
	}

	public void setIngredients(String ingredients) {
		this.ingredients.set(ingredients);
	}

	public StringProperty ingredientsProperty() {
		return ingredients;
	}

	public String getManufacturer() {
		return manufacturer.get();
	}

	public void setManufacturer(String manufacturer) {
		this.manufacturer.set(manufacturer);
	}

	public StringProperty manufacturerProperty() {
		return manufacturer;
	}

	public int getQuantity() {
		return quantity.get();
	}

	public void setQuantity(int quantity) {
		this.quantity.set(quantity);
	}

	public IntegerProperty quantityProperty() {
		return quantity;
	}

	public int getPrice() {
		return price.get();
	}

	public void setPrice(int price) {
		this.price.set(price);
	}

	public IntegerProperty priceProperty() {
		return price;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((ingredients == null) ? 0 : ingredients.getValue().hashCode());
		result = prime * result + ((manufacturer == null) ? 0 : manufacturer.getValue().hashCode());
		result = prime * result + ((name == null) ? 0 : name.getValue().hashCode());
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
		Medication other = (Medication) obj;
		if (ingredients == null) {
			if (other.ingredients != null)
				return false;
		} else if (!ingredients.getValue().equals(other.ingredients.getValue()))
			return false;
		if (manufacturer == null) {
			if (other.manufacturer != null)
				return false;
		} else if (!manufacturer.getValue().equals(other.manufacturer.getValue()))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.getValue().equals(other.name.getValue()))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Medication [name=" + name + ", ingredients=" + ingredients + ", manufacturer=" + manufacturer
				+ ", quantity=" + quantity + ", price=" + price + "]";
	}

}
