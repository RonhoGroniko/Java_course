package com.example.lab_5;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.sql.*;

public class AuthorDialogController {
    @FXML private TextField surnameField;
    @FXML private TextField nameField;
    @FXML private TextField patronymicField;
    @FXML private TextField birthDateField;
    @FXML private TextField countryField;

    private Author author;
    private DatabaseHandler dbHandler;

    public void setAuthor(Author author) {
        this.author = author;
        if (author != null) {
            surnameField.setText(author.getSurname());
            nameField.setText(author.getName());
            patronymicField.setText(author.getPatronymic());
            birthDateField.setText(author.getBirthDate());
            countryField.setText(author.getCountry());
        }
    }

    public void setDbHandler(DatabaseHandler dbHandler) {
        this.dbHandler = dbHandler;
    }

    @FXML
    private void save() {
        if (validateInput()) {
            try {
                if (author == null) {
                    try (Connection conn = dbHandler.getConnection();
                         PreparedStatement stmt = conn.prepareStatement(
                                 "INSERT INTO authors (surname, name, patronymic, birth_date, country) VALUES (?, ?, ?, ?, ?)",
                                 Statement.RETURN_GENERATED_KEYS)) {

                        stmt.setString(1, surnameField.getText());
                        stmt.setString(2, nameField.getText());
                        stmt.setString(3, patronymicField.getText());
                        stmt.setString(4, birthDateField.getText());
                        stmt.setString(5, countryField.getText());
                        stmt.executeUpdate();
                    }
                } else {
                    try (Connection conn = dbHandler.getConnection();
                         PreparedStatement stmt = conn.prepareStatement(
                                 "UPDATE authors SET surname = ?, name = ?, patronymic = ?, birth_date = ?, country = ? WHERE id = ?")) {

                        stmt.setString(1, surnameField.getText());
                        stmt.setString(2, nameField.getText());
                        stmt.setString(3, patronymicField.getText());
                        stmt.setString(4, birthDateField.getText());
                        stmt.setString(5, countryField.getText());
                        stmt.setInt(6, author.getId());
                        stmt.executeUpdate();
                    }
                }
                closeDialog();
            } catch (SQLException e) {
                e.printStackTrace();
                showAlert("Ошибка", "Ошибка при сохранении автора: " + e.getMessage(), Alert.AlertType.ERROR);
            }
        }
    }

    @FXML
    private void cancel() {
        closeDialog();
    }

    private boolean validateInput() {
        if (surnameField.getText().trim().isEmpty()) {
            showAlert("Ошибка", "Фамилия автора обязательна для заполнения", Alert.AlertType.ERROR);
            return false;
        }
        if (nameField.getText().trim().isEmpty()) {
            showAlert("Ошибка", "Имя автора обязательно для заполнения", Alert.AlertType.ERROR);
            return false;
        }
        return true;
    }

    private void closeDialog() {
        Stage stage = (Stage) surnameField.getScene().getWindow();
        stage.close();
    }

    private void showAlert(String title, String message, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}