module org.example.spellcheckalgorithm {
    requires javafx.controls;
    requires javafx.fxml;


    opens org.example.spellcheckalgorithm to javafx.fxml;
    exports org.example.spellcheckalgorithm;
}