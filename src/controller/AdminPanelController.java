package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.util.Duration;

public class AdminPanelController  implements Initializable{
	
	@FXML private BorderPane borderPaneMain;
	@FXML private Parent temp;
    @FXML private HBox btnDashboard;
    @FXML private HBox btnReceita;
    @FXML private HBox btnDespesa;
    @FXML private HBox btnRelatorio;
	
    //load dashboard on mouse click
    @FXML
    void btnDashboardOnMouseClick(MouseEvent event) {
    	loadUI("/view/Dashboard");
    }
    
    //load despesa on mouse click
    @FXML
    void btnDespesaOnMouseClick(MouseEvent event) {
    	loadUI("/view/Despesa");
    }

    //load receita on mouse click
    @FXML
    void btnReceitaOnMouseClick(MouseEvent event) {
    	loadUI("/view/Receita");
    }
        
    //load user interface with fade transition
	private void loadUI(String ui) {			
		try {
			temp = FXMLLoader.load(getClass().getResource(ui+".fxml"));				
		}catch (IOException e) {
			System.out.println(e);
		}						
		FadeTransition ft = new FadeTransition(Duration.millis(300), borderPaneMain.getCenter());		
		ft.setFromValue(1);
		ft.setToValue(0);								
		ft.setAutoReverse(false);
		ft.setCycleCount(1);
		ft.setOnFinished((ActionEvent e) -> borderPaneMain.setCenter(temp));		
		ft.play();				
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		//load dash in initialize
		loadUI("/view/Dashboard");
		
	}
		
}
