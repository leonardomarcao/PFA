
package application;

import org.hibernate.SessionFactory;

import dao.BuildSessionFactory;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class PFAMain extends Application {
	// define your offsets here
	private double xOffset = 0;
	private double yOffset = 0;

	@Override
	public void start(Stage primaryStage) {
		try {
			Parent root = FXMLLoader.load(getClass().getResource("/view/SignIn.fxml"));

			Scene scene = new Scene(root);

			primaryStage.setScene(scene);
			primaryStage.resizableProperty().set(false);
			primaryStage.initStyle(StageStyle.DECORATED);
			primaryStage.setTitle("PFA - LOGIN");

			primaryStage.show();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		SessionFactory sessionFactory = new BuildSessionFactory().getSessionFactory();
		launch(args);
	}
}