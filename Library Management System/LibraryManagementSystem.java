import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

public class LibraryManagementSystem extends JFrame {

    private static class Book {
        private int id;
        private String title;
        private String author;
        private boolean isIssued;

        public Book(int id, String title, String author) {
            this.id = id;
            this.title = title;
            this.author = author;
            this.isIssued = false;
        }

        public int getId() {
            return id;
        }

        public String getTitle() {
            return title;
        }

        public String getAuthor() {
            return author;
        }

        public boolean isIssued() {
            return isIssued;
        }

        public void issueBook() {
            isIssued = true;
        }

        public void returnBook() {
            isIssued = false;
        }

        @Override
        public String toString() {
            return "ID: " + id + ", Title: " + title + ", Author: " + author + ", Issued: " + isIssued;
        }
    }

    private Map<Integer, Book> books = new HashMap<>();
    private int bookIdCounter = 1;

    private JTextField bookTitleField;
    private JTextField bookAuthorField;
    private JTextField bookIdField;
    private JTextArea displayArea;

    public LibraryManagementSystem() {
        setTitle("Library Management System");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridLayout(6, 2));

        inputPanel.add(new JLabel("Book Title:"));
        bookTitleField = new JTextField();
        inputPanel.add(bookTitleField);

        inputPanel.add(new JLabel("Book Author:"));
        bookAuthorField = new JTextField();
        inputPanel.add(bookAuthorField);

        JButton addBookButton = new JButton("Add Book");
        inputPanel.add(addBookButton);
        inputPanel.add(new JLabel(""));

        inputPanel.add(new JLabel("Book ID:"));
        bookIdField = new JTextField();
        inputPanel.add(bookIdField);

        JButton issueBookButton = new JButton("Issue Book");
        inputPanel.add(issueBookButton);

        JButton returnBookButton = new JButton("Return Book");
        inputPanel.add(returnBookButton);

        displayArea = new JTextArea();
        displayArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(displayArea);

        add(inputPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);

        addBookButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addBook();
            }
        });

        issueBookButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                issueBook();
            }
        });

        returnBookButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                returnBook();
            }
        });
    }

    private void addBook() {
        String title = bookTitleField.getText();
        String author = bookAuthorField.getText();
        if (!title.isEmpty() && !author.isEmpty()) {
            Book book = new Book(bookIdCounter, title, author);
            books.put(bookIdCounter, book);
            bookIdCounter++;
            bookTitleField.setText("");
            bookAuthorField.setText("");
            updateDisplay();
        }
    }

    private void issueBook() {
        try {
            int bookId = Integer.parseInt(bookIdField.getText());
            if (books.containsKey(bookId) && !books.get(bookId).isIssued()) {
                books.get(bookId).issueBook();
                bookIdField.setText("");
                updateDisplay();
            } else {
                JOptionPane.showMessageDialog(this, "Book ID not found or already issued.");
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Invalid input.");
        }
    }

    private void returnBook() {
        try {
            int bookId = Integer.parseInt(bookIdField.getText());
            if (books.containsKey(bookId) && books.get(bookId).isIssued()) {
                books.get(bookId).returnBook();
                bookIdField.setText("");
                updateDisplay();
            } else {
                JOptionPane.showMessageDialog(this, "Book ID not found or not issued.");
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Invalid input.");
        }
    }

    private void updateDisplay() {
        StringBuilder displayText = new StringBuilder();
        for (Book book : books.values()) {
            displayText.append(book).append("\n");
        }
        displayArea.setText(displayText.toString());
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new LibraryManagementSystem().setVisible(true);
            }
        });
    }
}
