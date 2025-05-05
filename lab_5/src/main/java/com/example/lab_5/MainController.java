package com.example.lab_5;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;
import java.util.Optional;

public class MainController {
    @FXML private TableView<Book> booksTable;
    @FXML private TableColumn<Book, Integer> idColumn;
    @FXML private TableColumn<Book, String> titleColumn;
    @FXML private TableColumn<Book, String> authorColumn;
    @FXML private TableColumn<Book, Integer> yearColumn;
    @FXML private TableColumn<Book, String> genreColumn;
    @FXML private TableColumn<Book, Integer> quantityColumn;

    @FXML private TableView<Author> authorsTable;
    @FXML private TableColumn<Author, Integer> authorIdColumn;
    @FXML private TableColumn<Author, String> authorSurnameColumn;
    @FXML private TableColumn<Author, String> authorNameColumn;
    @FXML private TableColumn<Author, String> authorPatronymicColumn;
    @FXML private TableColumn<Author, String> birthDateColumn;
    @FXML private TableColumn<Author, String> countryColumn;

    @FXML private TextField searchField;

    private DatabaseHandler dbHandler;
    private ObservableList<Book> books = FXCollections.observableArrayList();
    private ObservableList<Author> authors = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        dbHandler = new DatabaseHandler();

        // Настройка таблицы книг
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        authorColumn.setCellValueFactory(new PropertyValueFactory<>("authorName"));
        yearColumn.setCellValueFactory(new PropertyValueFactory<>("year"));
        genreColumn.setCellValueFactory(new PropertyValueFactory<>("genre"));
        quantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        booksTable.setItems(books);

        // Настройка таблицы авторов
        authorIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        authorSurnameColumn.setCellValueFactory(new PropertyValueFactory<>("surname"));
        authorNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        authorPatronymicColumn.setCellValueFactory(new PropertyValueFactory<>("patronymic"));
        birthDateColumn.setCellValueFactory(new PropertyValueFactory<>("birthDate"));
        countryColumn.setCellValueFactory(new PropertyValueFactory<>("country"));
        authorsTable.setItems(authors);

        loadAuthors();
        loadBooks();
    }

    private void loadBooks() {
        books.clear();
        try (Connection conn = dbHandler.getConnection();
             PreparedStatement stmt = conn.prepareStatement("""
             SELECT b.id, b.title, b.year, b.genre, b.quantity,
                    a.id as author_id, a.surname, a.name, a.patronymic
             FROM books b
             JOIN authors a ON b.author_id = a.id
             ORDER BY b.title""");
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Author author = new Author(
                        rs.getInt("author_id"),
                        rs.getString("surname"),
                        rs.getString("name"),
                        rs.getString("patronymic"),
                        null, null
                );

                books.add(new Book(
                        rs.getInt("id"),
                        rs.getString("title"),
                        author.getFullName(),
                        rs.getInt("year"),
                        rs.getString("genre"),
                        rs.getInt("quantity")
                ));
            }
        } catch (SQLException e) {
            showAlert("Database Error", "Failed to load books: " + e.getMessage(), Alert.AlertType.ERROR);
            e.printStackTrace();
        }
    }


    private void loadAuthors() {
        authors.clear();
        try (Connection conn = dbHandler.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM authors ORDER BY name")) {

            while (rs.next()) {
                authors.add(new Author(
                        rs.getInt("id"),
                        rs.getString("surname"),
                        rs.getString("name"),
                        rs.getString("patronymic"),
                        rs.getString("birth_date"),
                        rs.getString("country")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void showAddBookDialog() {
        showBookDialog(null);
    }

    @FXML
    private void showEditBookDialog() {
        Book selected = booksTable.getSelectionModel().getSelectedItem();
        if (selected != null) {
            showBookDialog(selected);
        } else {
            showAlert("Ошибка", "Выберите книгу для редактирования", Alert.AlertType.WARNING);
        }
    }

    @FXML
    private void showBookDialog(Book book) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/lab_5/book_dialog.fxml"));
            Parent root = loader.load();

            BookDialogController controller = loader.getController();
            controller.setAuthors(authors); // Передаем список авторов перед установкой книги
            controller.setBook(book);
            controller.setDbHandler(dbHandler);

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();

            loadBooks();
        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Ошибка", "Не удалось загрузить форму редактирования", Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void deleteSelectedBook() {
        Book selected = booksTable.getSelectionModel().getSelectedItem();
        if (selected != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Подтверждение удаления");
            alert.setHeaderText("Удаление книги");
            alert.setContentText("Вы уверены, что хотите удалить книгу: " + selected.getTitle() + "?");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                try (Connection conn = dbHandler.getConnection();
                     PreparedStatement stmt = conn.prepareStatement("DELETE FROM books WHERE id = ?")) {

                    stmt.setInt(1, selected.getId());
                    stmt.executeUpdate();
                    loadBooks();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        } else {
            showAlert("Ошибка", "Выберите книгу для удаления", Alert.AlertType.WARNING);
        }
    }

    @FXML
    private void searchBooks() {
        String searchText = searchField.getText().trim();
        if (searchText.isEmpty()) {
            loadBooks();
            return;
        }

        books.clear();
        try (Connection conn = dbHandler.getConnection();
             PreparedStatement stmt = conn.prepareStatement("""
                 SELECT b.id, b.title, 
                        a.surname || ' ' || a.name || ' ' || COALESCE(a.patronymic, '') as author_full_name,
                        b.year, b.genre, b.quantity 
                 FROM books b 
                 JOIN authors a ON b.author_id = a.id
                 WHERE b.title LIKE ? 
                    OR (a.surname || ' ' || a.name || ' ' || COALESCE(a.patronymic, '')) LIKE ?
                    OR b.genre LIKE ?
                 ORDER BY b.title""")) {

            String searchPattern = "%" + searchText + "%";
            stmt.setString(1, searchPattern);
            stmt.setString(2, searchPattern);
            stmt.setString(3, searchPattern);

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                books.add(new Book(
                        rs.getInt("id"),
                        rs.getString("title"),
                        rs.getString("author_full_name"),
                        rs.getInt("year"),
                        rs.getString("genre"),
                        rs.getInt("quantity")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert("Ошибка", "Ошибка при поиске книг: " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void showAddAuthorDialog() {
        showAuthorDialog(null);
    }

    @FXML
    private void showEditAuthorDialog() {
        Author selected = authorsTable.getSelectionModel().getSelectedItem();
        if (selected != null) {
            showAuthorDialog(selected);
        } else {
            showAlert("Ошибка", "Выберите автора для редактирования", Alert.AlertType.WARNING);
        }
    }

    private void showAuthorDialog(Author author) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/lab_5/author_dialog.fxml"));
            Stage stage = new Stage();
            stage.setScene(new Scene(loader.load()));
            stage.initModality(Modality.APPLICATION_MODAL);

            AuthorDialogController controller = loader.getController();
            controller.setAuthor(author);
            controller.setDbHandler(dbHandler);

            stage.showAndWait();
            loadAuthors();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void deleteSelectedAuthor() {
        Author selected = authorsTable.getSelectionModel().getSelectedItem();
        if (selected != null) {
            // Есть ли книги у этого автора
            try (Connection conn = dbHandler.getConnection();
                 PreparedStatement stmt = conn.prepareStatement("SELECT COUNT(*) FROM books WHERE author_id = ?")) {

                stmt.setInt(1, selected.getId());
                ResultSet rs = stmt.executeQuery();
                if (rs.next() && rs.getInt(1) > 0) {
                    showAlert("Ошибка", "Нельзя удалить автора, у которого есть книги", Alert.AlertType.ERROR);
                    return;
                }
            } catch (SQLException e) {
                e.printStackTrace();
                return;
            }

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Подтверждение удаления");
            alert.setHeaderText("Удаление автора");
            alert.setContentText("Вы уверены, что хотите удалить автора: " + selected.getFullName() + "?");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                try (Connection conn = dbHandler.getConnection();
                     PreparedStatement stmt = conn.prepareStatement("DELETE FROM authors WHERE id = ?")) {

                    stmt.setInt(1, selected.getId());
                    stmt.executeUpdate();
                    loadAuthors();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        } else {
            showAlert("Ошибка", "Выберите автора для удаления", Alert.AlertType.WARNING);
        }
    }

    private void showAlert(String title, String message, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}