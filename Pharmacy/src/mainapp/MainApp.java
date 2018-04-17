package mainapp;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import controller.LoginLayoutController;

public class MainApp extends Application {

	private Stage primaryStage;
	private AnchorPane rootLayout;

	/**
	 * Constructor
	 */
	public MainApp() {
	}

	@Override
	public void start(Stage primaryStage) {
		this.primaryStage = primaryStage;
		this.primaryStage.setResizable(false);
		this.primaryStage.setTitle("Assignment2");
		initMainLayout();
	}

	/**
	 * Initializes the root layout.
	 */
	public void initMainLayout() {
		try {
			// Load root layout from fxml file.
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("/view/LoginLayout.fxml"));
			rootLayout = (AnchorPane) loader.load();

			// Show the scene containing the root layout.
			Scene scene = new Scene(rootLayout);
			primaryStage.setScene(scene);
			primaryStage.show();

			LoginLayoutController loginLayoutController = loader.getController();
			loginLayoutController.setMainApp(this);
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(0);
		}
	}

	/**
	 * Returns the main stage.
	 *
	 * @return
	 */
	public Stage getPrimaryStage() {
		return primaryStage;
	}

	public AnchorPane getRootLayout() {
		return rootLayout;
	}

	public static void main(String[] args) {
		try {
			launch(args);
		} catch (Exception e) {
			System.out.println("Eroare la deschidere: " + e.getMessage());
			e.printStackTrace();
		}
	}
}
