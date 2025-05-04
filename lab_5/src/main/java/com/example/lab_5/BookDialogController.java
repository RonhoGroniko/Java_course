package com.example.lab_5;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.util.StringConverter;

import java.sql.*;

public class BookDialogController {
    @FXML private TextField titleField;
    @FXML private ComboBox<Author> authorCombo;
    @FXML private TextField yearField;
    @FXML private ComboBox<String> genreCombo;
    @FXML private TextField quantityField;

    private Book book;
    private ObservableList<Author> authors;
    private DatabaseHandler dbHandler;

    @FXML
    public void initialize() {
        genreCombo.getItems().addAll(DatabaseHandler.GENRES);
    }

    public void setAuthors(ObservableList<Author> authors) {
        this.authors = authors;
        authorCombo.setItems(authors);

        authorCombo.setConverter(new StringConverter<Author>() {
            @Override
            public String toString(Author author) {
                return author != null ? author.getFullName() : "";
            }

            @Override
            public Author fromString(String string) {
                return authorCombo.getItems().stream()
                        .filter(a -> a.getFullName().equals(string))
                        .findFirst()
                        .orElse(null);
            }
        });
    }

    public void setBook(Book book) {
        this.book = book;
        if (book != null) {
            titleField.setText(book.getTitle());
            yearField.setText(String.valueOf(book.getYear()));
            genreCombo.setValue(book.getGenre());
            quantityField.setText(String.valueOf(book.getQuantity()));

            if (authors != null) {
                Author selectedAuthor = authors.stream()
                        .filter(a -> a.getFullName().equals(book.getAuthorName()))
                        .findFirst()
                        .orElse(null);
                authorCombo.getSelectionModel().select(selectedAuthor);
            }
        }
    }

    public void setDbHandler(DatabaseHandler dbHandler) {
        this.dbHandler = dbHandler;
    }

    @FXML
    private void save() {
        if (validateInput()) {
            try {
                if (book == null) {
                    try (Connection conn = dbHandler.getConnection();
                         PreparedStatement stmt = conn.prepareStatement(
                                 "INSERT INTO books (title, author_id, year, genre, quantity) VALUES (?, ?, ?, ?, ?)",
                                 Statement.RETURN_GENERATED_KEYS)) {

                        stmt.setString(1, titleField.getText());
                        stmt.setInt(2, authorCombo.getValue().getId());
                        stmt.setInt(3, Integer.parseInt(yearField.getText()));
                        stmt.setString(4, genreCombo.getValue());
                        stmt.setInt(5, Integer.parseInt(quantityField.getText()));
                        stmt.executeUpdate();
                    }
                } else {
                    // Обновление существующей книги
                    try (Connection conn = dbHandler.getConnection();
                         PreparedStatement stmt = conn.prepareStatement(
                                 "UPDATE books SET title = ?, author_id = ?, year = ?, genre = ?, quantity = ? WHERE id = ?")) {

                        stmt.setString(1, titleField.getText());
                        stmt.setInt(2, authorCombo.getValue().getId());
                        stmt.setInt(3, Integer.parseInt(yearField.getText()));
                        stmt.setString(4, genreCombo.getValue());
                        stmt.setInt(5, Integer.parseInt(quantityField.getText()));
                        stmt.setInt(6, book.getId());
                        stmt.executeUpdate();
                    }
                }

                closeDialog();
            } catch (SQLException e) {
                e.printStackTrace();
                showAlert("Ошибка", "Ошибка при сохранении книги: " + e.getMessage(), Alert.AlertType.ERROR);
            }
        }
    }

    @FXML
    private void cancel() {
        closeDialog();
    }

    private boolean validateInput() {
        StringBuilder errorMessage = new StringBuilder();

        if (titleField.getText() == null || titleField.getText().trim().isEmpty()) {
            errorMessage.append("Не заполнено название книги!\n");
        }

        if (authorCombo.getValue() == null) {
            errorMessage.append("Не выбран автор!\n");
        }

        if (yearField.getText() == null || yearField.getText().trim().isEmpty()) {
            errorMessage.append("Не заполнен год издания!\n");
        } else {
            try {
                int year = Integer.parseInt(yearField.getText());
                if (year < 0 || year > 2100) {
                    errorMessage.append("Год должен быть в диапазоне 0-2100!\n");
                }
            } catch (NumberFormatException e) {
                errorMessage.append("Год должен быть числом!\n");
            }
        }

        if (genreCombo.getValue() == null || genreCombo.getValue().trim().isEmpty()) {
            errorMessage.append("Выберите жанр из списка!\n");
        }

        if (quantityField.getText() == null || quantityField.getText().trim().isEmpty()) {
            errorMessage.append("Не заполнено количество!\n");
        } else {
            try {
                int quantity = Integer.parseInt(quantityField.getText());
                if (quantity < 1) {
                    errorMessage.append("Количество должно быть положительным числом!\n");
                }
            } catch (NumberFormatException e) {
                errorMessage.append("Количество должно быть числом!\n");
            }
        }

        if (errorMessage.length() == 0) {
            return true;
        } else {
            showAlert("Ошибка", errorMessage.toString(), Alert.AlertType.ERROR);
            return false;
        }
    }

    private void closeDialog() {
        Stage stage = (Stage) titleField.getScene().getWindow();
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