package it.polito.tdp.crimes;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import it.polito.tdp.model.ArcoPeso;
import it.polito.tdp.model.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;

public class CrimesController {
	
	Model model;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ComboBox<String> boxCategoria;

    @FXML
    private ComboBox<Integer> boxMese;

    @FXML
    private Button btnCreaGrafo;

    @FXML
    private TextArea txtResult;

    @FXML
    void doCreaGrafo(ActionEvent event) {
    	String categoria = boxCategoria.getValue();
    	
    	Integer mese = boxMese.getValue();
    	if(categoria == null || mese == null) {
    		txtResult.appendText("Si devono selezionare un mese e una categoria\n");
    		return;
    	}
    	
    	List<ArcoPeso> result = this.model.creaGrafo(categoria, mese);
    	txtResult.clear();
    	txtResult.appendText(String.format("Grafo creato di %d vertici e %d archi\n", this.model.getVertici(), this.model.getArchi()));
    	if(result.isEmpty()) {
    		txtResult.appendText("Non ci sono archi\n");
    	}
    	for(ArcoPeso tmp: result) {
    		txtResult.appendText(tmp.toString() + "\n");
    	}
    }

    @FXML
    void initialize() {
        assert boxCategoria != null : "fx:id=\"boxCategoria\" was not injected: check your FXML file 'Crimes.fxml'.";
        assert boxMese != null : "fx:id=\"boxMese\" was not injected: check your FXML file 'Crimes.fxml'.";
        assert btnCreaGrafo != null : "fx:id=\"btnCreaGrafo\" was not injected: check your FXML file 'Crimes.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Crimes.fxml'.";

    }
    public void setModel(Model model) {
    	this.model=model;
    	boxCategoria.getItems().addAll(this.model.listCategorie());
    	boxMese.getItems().addAll(this.model.listMesi());
    }
}
