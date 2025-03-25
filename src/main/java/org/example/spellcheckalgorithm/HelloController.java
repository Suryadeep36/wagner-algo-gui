package org.example.spellcheckalgorithm;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

import java.util.List;


public class HelloController {
    @FXML
    private Label welcomeText;
    @FXML
    private TextField input;
    @FXML
    private ListView<String> output;
    @FXML
    protected void onHelloButtonClick() {
        output.getItems().clear();
        welcomeText.setText("Enter a word to check if it is correct");
        String s = input.getText().toLowerCase();
        List<String> topSuggestions = WagnerFischerAlgo.spellCheck(s);
        output.getItems().addAll(topSuggestions);
    }
}