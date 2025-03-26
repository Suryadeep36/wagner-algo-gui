package org.example.spellcheckalgorithm;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.util.Duration;

public class PopupController {
    @FXML
    private GridPane dpGrid;  // GridPane to visualize the DP table

    private String correctWord;
    private String givenWord;

    @FXML
    protected void setStrings(String correctWord, String givenWord) {
        this.correctWord = correctWord;
        this.givenWord = givenWord;

        animateDPTable();
    }

    private void animateDPTable() {
        int n = correctWord.length();
        int m = givenWord.length();
        int[][] dp = new int[m + 1][n + 1];

        // Initialize the DP table for edit distance
        for (int i = 0; i <= m; i++) {
            for (int j = 0; j <= n; j++) {
                if (i == 0) {
                    dp[i][j] = j;
                } else if (j == 0) {
                    dp[i][j] = i;
                } else if (givenWord.charAt(i - 1) == correctWord.charAt(j - 1)) {
                    dp[i][j] = dp[i - 1][j - 1];
                } else {
                    dp[i][j] = 1 + Math.min(dp[i - 1][j - 1], Math.min(dp[i - 1][j], dp[i][j - 1]));
                }
            }
        }

        // Adding word characters in the grid's top row and left column
        for (int j = 1; j <= n; j++) {
            Label charLabel = new Label(String.valueOf(correctWord.charAt(j - 1)));
            styleCharLabel(charLabel);
            dpGrid.add(charLabel, j, 0);
        }
        for (int i = 1; i <= m; i++) {
            Label charLabel = new Label(String.valueOf(givenWord.charAt(i - 1)));
            styleCharLabel(charLabel);
            dpGrid.add(charLabel, 0, i);
        }

        // Timeline to animate each DP cell fill
        Timeline timeline = new Timeline();
        int animationSpeed = 300;  // milliseconds per cell animation

        // Animate filling of each DP cell with delay using KeyFrames
        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                final int row = i, col = j;
                KeyFrame keyFrame = new KeyFrame(Duration.millis(animationSpeed * (i * n + j)),
                        event -> {
                            Label cellLabel = new Label(String.valueOf(dp[row][col]));
                            styleDPLabel(cellLabel);
                            dpGrid.add(cellLabel, col, row);
                        });
                timeline.getKeyFrames().add(keyFrame);
            }
        }

        timeline.play();  // Play the animation
    }

    // Method to style the character labels
    private void styleCharLabel(Label label) {
        label.setStyle("-fx-border-color: black; -fx-alignment: center; -fx-padding: 10;");
        label.setFont(new Font("Arial", 20));  // Larger font for visibility
        label.setMinSize(40, 40);  // Increase cell size
        label.setMaxSize(40, 40);
    }

    // Method to style DP cell labels
    private void styleDPLabel(Label label) {
        label.setStyle("-fx-font-size: 16px; -fx-padding: 5px; -fx-border-color: black; -fx-background-color: lightblue;");
        label.setMinSize(40, 40);  // Increase DP cell size
        label.setMaxSize(40, 40);
    }
}
