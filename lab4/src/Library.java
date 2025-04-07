import java.util.*;

public class Library {

    private List<Book> books;
    private Set<String> uniqueAuthors;
    private Map<String, Integer> authorBookCount;

    public Library() {
        books = new ArrayList<>();
        uniqueAuthors = new HashSet<>();
        authorBookCount = new HashMap<>();
    }

    public void addBook(Book book) {
        books.add(book);
        uniqueAuthors.add(book.getAuthor());
        authorBookCount.merge(book.getAuthor(), 1, Integer::sum);
    }

    public void removeBook(Book book) {
        books.remove(book);
        boolean isThere = false;
        for (Book item : books) {
            if (item.getAuthor().equals(book.getAuthor())) {
                isThere = true;
                break;
            }
        }
        if (!isThere) { uniqueAuthors.remove(book.getAuthor()); }
        authorBookCount.merge(book.getAuthor(), -1, Integer::sum);
    }

    public List<Book> findBooksByAuthor(String author) {
        List<Book> authorBooks = new ArrayList<>();
        for (Book book : books) {
            if (book.getAuthor().equals(author)) {
                authorBooks.add(book);
            }
        }
        return authorBooks;
    }

    public List<Book> findBooksByYear(int year) {
        List<Book> yearBooks = new ArrayList<>();
        for (Book book : books) {
            if (book.getYear() == year) {
                yearBooks.add(book);
            }
        }
        return yearBooks;
    }

    public void printAllBooks() {
        for (Book book : books) {
            System.out.println(book.toString());
        }
    }

    public void printUniqueAuthors() {
        for (String author : uniqueAuthors) {
            System.out.println(author);
        }
    }

    public void printAuthorStatistics() {
        authorBookCount.forEach((author, count) ->
                System.out.printf("Author %s - Book count %s%n", author, count));
    }
}
