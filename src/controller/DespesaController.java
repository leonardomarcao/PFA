package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.animation.FadeTransition;
import javafx.css.Style;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.MenuItem;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.Window;
import javafx.util.Duration;

public class DespesaController implements Initializable {		

	    @FXML
	    private AnchorPane anchorPaneDespesa;
	    
	    @FXML
	    private StackPane stackPaneDespesa;
	    
	    @FXML
	    private BorderPane borderPageDespesa;

	    @FXML
	    private MenuItem menuItemInserir;
	    
		@FXML 
		private Parent temp;
	    
	    

	    @FXML
	    void menuItemInserirOnAction(ActionEvent event) {
	    	loadUI("/view/DespesaRegistration");
	    }
	    
	        
	    //load user interface with fade transition
		private void loadUI(String ui) {			
			try {
				temp = FXMLLoader.load(getClass().getResource(ui+".fxml"));				
			}catch (IOException e) {
				System.out.println(e);
			}											
			Stage stage = new Stage();
			Scene scene = new Scene(temp);
			
			stage.setScene(scene);
			stage.resizableProperty().set(false);
			stage.initStyle(StageStyle.DECORATED);			
			stage.setTitle("PFA - CADASTRO DE DESPESA");
			stage.initModality(Modality.APPLICATION_MODAL);
			stage.showAndWait();	
		}

		@Override
		public void initialize(URL location, ResourceBundle resources) {
			//load dash in initialize
			
			
		}


	
}
