package model;

import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class User {

	private LongProperty userId;
	private StringProperty username;
	private StringProperty password;
	private StringProperty type;

	public User() {
		this(0, null, null, null);
	}

	public User(long userId, String username, String password, String type) {
		super();
		this.userId = new SimpleLongProperty(userId);
		this.username = new SimpleStringProperty(username);
		this.password = new SimpleStringProperty(password);
		this.type = new SimpleStringProperty(type);
	}

	public long getUserId() {
		return userId.get();
	}

	public void setUserId(long userId) {
		this.userId.set(userId);
	}

	public LongProperty userIdProperty() {
		return userId;
	}

	public String getUsername() {
		return username.get();
	}

	public void setUsername(String username) {
		this.username.set(username);
	}

	public StringProperty usernameProperty() {
		return username;
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

	public String getType() {
		return type.get();
	}

	public void setType(String type) {
		this.type.set(type);
	}

	public StringProperty typeProperty() {
		return type;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((password == null) ? 0 : password.getValue().hashCode());
		result = prime * result + ((username == null) ? 0 : username.getValue().hashCode());
		result = prime * result + ((type == null) ? 0 : type.getValue().hashCode());
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
		User other = (User) obj;
		if (password == null) {
			if (other.password != null)
				return false;
		} else if (!password.getValue().equals(other.password.getValue()))
			return false;
		if (username == null) {
			if (other.username != null)
				return false;
		} else if (!username.getValue().equals(other.username.getValue()))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "User [userId=" + userId + ", username=" + username + ", password=" + password + ", type=" + type + "]";
	}

}