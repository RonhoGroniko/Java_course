public class LibraryTest {

    public static void main(String[] args) {
        Book book1 = new Book("Гамлет", "Шекспир", 1603);
        Book book2 = new Book("Капитанская дочка", "Пушкин", 1836);
        Book book3 = new Book("Пиковая дама", "Пушкин", 1834);
        Book book4 = new Book("Обломов", "Гончаров", 1859);

        Library lib = new Library();
        lib.addBook(book1);
        lib.addBook(book2);
        lib.addBook(book3);
        lib.addBook(book4);

        System.out.println("Список книг:");
        lib.printAllBooks();

        System.out.println("\nУникальные авторы:");
        lib.printUniqueAuthors();

        System.out.println("\nСтатистика авторов:");
        lib.printAuthorStatistics();

        System.out.println("\nКниги Пушкина:");
        System.out.println(lib.findBooksByAuthor("Пушкин"));

        System.out.println("\nКниги 1859 года:");
        System.out.println(lib.findBooksByYear(1859));

        lib.removeBook(book1);
        System.out.println("\nСписок книг после удаления:");
        lib.printAllBooks();

    }
}
