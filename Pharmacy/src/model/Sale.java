package model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Sale {

	private LongProperty saleId;
	private StringProperty name;
	private StringProperty manufacturer;
	private IntegerProperty quantity;
	private IntegerProperty totalPrice;

	public Sale() {
		this(0, null, null, 0, 0);
	}

	public Sale(long saleId, String name, String manufacturer, int quantity, int totalPrice) {
		super();
		this.saleId = new SimpleLongProperty(saleId);
		this.name = new SimpleStringProperty(name);
		this.manufacturer = new SimpleStringProperty(manufacturer);
		this.quantity = new SimpleIntegerProperty(quantity);
		this.totalPrice = new SimpleIntegerProperty(totalPrice);
	}

	public long getSaleId() {
		return saleId.get();
	}

	public void setSaleId(long saleId) {
		this.saleId.set(saleId);
	}

	public LongProperty saleIdProperty() {
		return saleId;
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

	public int getTotalPrice() {
		return totalPrice.get();
	}

	public void setTotalPrice(int totalPrice) {
		this.totalPrice.set(totalPrice);
	}

	public IntegerProperty totalPriceProperty() {
		return totalPrice;
	}

}
