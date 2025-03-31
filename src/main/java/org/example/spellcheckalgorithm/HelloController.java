package org.example.spellcheckalgorithm;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;


public class HelloController {
    @FXML
    private Label welcomeText;
    @FXML
    private TextField input;
    @FXML
    private ListView<String> output;
    @FXML
    protected void onHelloButtonClick() throws Exception {
        output.getItems().clear();
        String s = input.getText().toLowerCase();
        List<String> topSuggestions = WagnerFischerAlgo.spellCheck(s);
        output.getItems().addAll(topSuggestions);
    }
    @FXML
    public void initialize() {
        welcomeText.setText("Enter a word to check if it is correct");
        output.setCellFactory(listView -> new CustomListCell());
    }

    protected class CustomListCell extends ListCell<String> {
        private final HBox hBox = new HBox();
        private final Label label = new Label();
        private final Button button = new Button("How?");
        public CustomListCell() {
            super();
            hBox.setSpacing(10);
            hBox.getChildren().addAll(label, button);

            button.setOnAction(event -> {
                try{
                    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("popup.fxml"));
                    Scene scene = new Scene(fxmlLoader.load(), 320, 240);
                    PopupController popupController = fxmlLoader.getController();
                    popupController.setStrings(label.getText().split(" ")[0], input.getText());
                    Stage stage = new Stage();
                    stage.setTitle("How does it work??");
                    stage.setScene(scene);
                    stage.show();
                }
                catch (Exception e){
                    System.out.println(e.getMessage());
                }

            });
        }
        protected void updateItem(String item, boolean empty) {
            super.updateItem(item, empty);
            if (empty || item == null) {
                setGraphic(null); // Empty cell
            } else {
                label.setText(item); // Set text from the ListView item
                setGraphic(hBox);   // Show the HBox with the label and button
            }
        }
    }


}