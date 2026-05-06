package com.example.library;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

public class LibraryTest {

    Book book;
    Member member;
    Library library;

    @BeforeEach
    void setup() {
        book = new Book("1984", "Orwell");
        member = new Member("Alice");
        library = new Library();
    }

    // =========================
    // 1. BORROW BOOK
    // =========================
    @Test
    void borrowBookTest() {
        book.borrow();
        assertTrue(book.isBorrowed());
    }

    // =========================
    // 2. BORROW ALREADY BORROWED
    // =========================
    @Test
    void borrowAlreadyBorrowedBookTest() {
        book.borrow();
        assertThrows(IllegalStateException.class, () -> book.borrow());
    }

    // =========================
    // 3. RETURN BOOK
    // =========================
    @Test
    void returnBookTest() {
        book.borrow();
        book.returnBook();
        assertFalse(book.isBorrowed());
    }

    // =========================
    // 4. RETURN WITHOUT BORROW
    // =========================
    @Test
    void returnWithoutBorrowTest() {
        assertThrows(IllegalStateException.class, () -> book.returnBook());
    }

    // =========================
    // 5. MEMBER BORROW LIMIT
    // =========================
    @Test
    void memberBorrowLimitTest() {
        member.borrowBook();
        member.borrowBook();
        member.borrowBook();

        assertEquals(3, member.getBorrowedBooks());
    }

    // =========================
    // 6. EXCEED BORROW LIMIT
    // =========================
    @Test
    void exceedBorrowLimitTest() {
        member.borrowBook();
        member.borrowBook();
        member.borrowBook();

        assertThrows(IllegalStateException.class, () -> member.borrowBook());
    }

    // =========================
    // 7. MEMBER RETURN BOOK
    // =========================
    @Test
    void memberReturnBookTest() {
        member.borrowBook();
        member.borrowBook();

        member.returnBook();

        assertEquals(1, member.getBorrowedBooks());
    }

    // =========================
    // 8. RETURN WHEN NONE BORROWED
    // =========================
    @Test
    void memberReturnWithoutBorrowTest() {
        assertThrows(IllegalStateException.class, () -> member.returnBook());
    }

    // =========================
    // 9. FIND BOOK
    // =========================
    @Test
    void findBookTest() {
        library.addBook(book);

        Book found = library.findBook("1984");

        assertNotNull(found);
        assertEquals("1984", found.getTitle());
    }

    // =========================
    // 10. BOOK AVAILABILITY
    // =========================
    @Test
    void bookAvailabilityTest() {
        library.addBook(book);

        assertTrue(library.isBookAvailable("1984"));

        book.borrow();

        assertFalse(library.isBookAvailable("1984"));
    }

    // =========================
    // 11. COUNT AVAILABLE BOOKS
    // =========================
    @Test
    void countAvailableBooksTest() {

        Book b1 = new Book("Book1", "A");
        Book b2 = new Book("Book2", "B");
        Book b3 = new Book("Book3", "C");

        library.addBook(b1);
        library.addBook(b2);
        library.addBook(b3);

        b1.borrow();

        int count = LibraryUtils.countAvailableBooks(library);

        assertEquals(2, count);
    }
}