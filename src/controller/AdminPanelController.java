package controller;

import java.io.IOException;

import javafx.animation.FadeTransition;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

public class AdminPanelController {
	
	@FXML private BorderPane borderPaneMain;
    @FXML private HBox btnDashboard;
    @FXML private HBox btnReceita;
    @FXML private HBox btnDespesa;
    @FXML private HBox btnRelatorio;
	
    @FXML
    void btnDashboardOnMouseClick(MouseEvent event) {
    	loadUI("/view/Dashboard");
    }
    
    @FXML
    void btnDespesaOnMouseClick(MouseEvent event) {
    	loadUI("/view/Despesa");
    }
    
    @FXML
    void btnReceitaOnMouseClick(MouseEvent event) {
    	loadUI("/view/Receita");
    }
    
    @FXML
    void btnRelatorioOnMouseClick(MouseEvent event) {
    	loadUI("/view/Relatorio");
    }
    

	private void loadUI(String ui) {
		Parent root = null;
		try {
			root = FXMLLoader.load(getClass().getResource(ui+".fxml"));			
		}catch (IOException e) {
			System.out.println(e);
		}		
		borderPaneMain.setCenter(root);
	}
	
	

	

}
