package tw.bill;

public interface BookRepository {
    Book getByIsbn(String isbn);
}
