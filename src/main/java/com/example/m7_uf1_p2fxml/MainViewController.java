package com.example.m7_uf1_p2fxml;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

import java.net.URL;
import java.text.Normalizer;
import java.util.ResourceBundle;

public class MainViewController implements Initializable {

    @FXML
    private GraphicalComponentController graphicalComponentController;

    @FXML
    private TextField generatedDni;

    @FXML
    private Text errorText;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }

    private final char[] alphabet = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'Ñ', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};

    private int extractSevenLetters(String name, String surname, char[] alphabet) {
        if (!name.isEmpty() || !surname.isEmpty()) {
            int nameLength = Math.min(name.length(), 7);
            int surnameLength = Math.min(surname.length(), 7 - nameLength);

            String result = name.substring(0, nameLength) + surname.substring(0, surnameLength);

            return convertLettersToNumbers(result, alphabet);
        } else {
            return -1;
        }
    }

    private int convertLettersToNumbers(String letters, char[] alphabet) {
        int result = 0;

        for (char letter : Normalizer.normalize(letters, Normalizer.Form.NFD)
                .replaceAll("\\p{InCombiningDiacriticalMarks}+", "")
                .toUpperCase()
                .toCharArray()) {
            if (!Character.isLetter(letter)) {
                return -1;
            }
            int position = getPositionInAlphabet(letter, alphabet);
            int singleDigitNumber = position % 10;
            result = result * 10 + singleDigitNumber;
        }

        return result;
    }

    private int getPositionInAlphabet(char letter, char[] alphabet) {
        for (int i = 0; i < alphabet.length; i++) {
            if (alphabet[i] == letter) {
                return i + 1;
            }
        }
        return -1;
    }

    @FXML
    private void onDeleteGeneratedDNIButtonClick() {
        generatedDni.setText("");
    }

    @FXML
    private void onGenerateDNIButtonClick() {
        String name = graphicalComponentController.getName();
        String surnames = graphicalComponentController.getSurname();
        String multiplierString = graphicalComponentController.getMultiplier();

        if (name.isEmpty() && surnames.isEmpty()) {
            errorText.setText("The name/surname fields cannot be empty. Fill at least 1.");
            return;
        }

        if (multiplierString.isEmpty()) {
            errorText.setText("The multiplier can't be empty");
            return;
        }

        if (!multiplierString.matches("\\d+")) {
            errorText.setText("Invalid character/s in the multiplier.");
            return;
        }

        int multiplier = Integer.parseInt(multiplierString);

        if (multiplier >= 1 && multiplier <= 9) {
            int generatedNumbers = extractSevenLetters(name, surnames, alphabet);

            if (generatedNumbers != -1) {
                int multipliedNumber = generatedNumbers * multiplier;

                String resultString = String.valueOf(multipliedNumber);
                int resultLength = resultString.length();

                if (resultLength > 8) {
                    resultString = resultString.substring(0, 8);
                } else if (resultLength < 8) {
                    resultString = String.format("%0" + (8 - resultLength) + "d%s", 0, resultString);
                }

                char letter = calculateLetter(Integer.parseInt(resultString) % 23);

                String finalDNI = resultString + letter;

                System.out.println("Generated numbers: " + generatedNumbers + ", Multiplier: " + multiplier);
                System.out.println("Multiplied number: " + resultString);
                System.out.println("Final DNI: " + finalDNI);

                generatedDni.setText(finalDNI);
                errorText.setText("");
            } else {
                errorText.setText("Introduce a valid name/surname, without special characters and no numbers.");
            }
        } else {
            errorText.setText("The multiplier's value has to range between 1 and 9");
        }
    }

    private char calculateLetter(int remainder) {
        char[] letterTable = {'T', 'R', 'W', 'A', 'G', 'M', 'Y', 'F', 'P', 'D', 'X', 'B',
                'N', 'J', 'Z', 'S', 'Q', 'V', 'H', 'L', 'C', 'K', 'E'};

        return letterTable[remainder];
    }
}
