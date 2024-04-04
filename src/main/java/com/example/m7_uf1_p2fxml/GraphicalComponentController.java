package com.example.m7_uf1_p2fxml;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
public class GraphicalComponentController {

    @FXML
    private TextField name;

    @FXML
    private TextField surname;

    @FXML
    private TextField multiplier;

    public String getName() {
        return name.getText();
    }

    public void setName(String name) {
        this.name.setText(name);
    }

    public String getSurname() {
        return surname.getText();
    }

    public void setSurname(String surname) {
        this.surname.setText(surname);
    }

    public String getMultiplier() {
        return multiplier.getText();
    }

    public void setMultiplier(String multiplier) {
        this.multiplier.setText(multiplier);
    }
}
