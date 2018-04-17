package controller;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import mainapp.MainApp;
import model.User;
import handler.UserHandler;

public class LoginLayoutController {

	private MainApp mainApp;

	@FXML
	private TextField username;

	@FXML
	private TextField password;

	public void setMainApp(MainApp mainApp) {
		this.mainApp = mainApp;
	}

	@FXML
	public void login(ActionEvent event) throws IOException {

		User user = UserHandler.loginUser(username.getText(), password.getText());

		if (user != null) {
			if ("administrator".equals(user.getType())) {
				FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/AdminLayout.fxml"));
				Parent root1 = (Parent) fxmlLoader.load();

				mainApp.getPrimaryStage().close();
				Stage stage = new Stage();
				stage.setScene(new Scene(root1));
				stage.show();
			} else if ("regularUser".equals(user.getType())) {
				FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/UserLayout.fxml"));
				Parent root1 = (Parent) fxmlLoader.load();

				mainApp.getPrimaryStage().close();
				Stage stage = new Stage();
				stage.setScene(new Scene(root1));
				stage.show();
			}

		} else {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("Login error");
			alert.setHeaderText("Bad Input");
			alert.setContentText("Please insert a valid username and password.");
			alert.showAndWait();
		}

	}

}
