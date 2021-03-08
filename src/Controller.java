import java.net.URL;

import java.sql.*;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class Controller implements Initializable {

    @FXML
    private TextField tfId;

    @FXML
    private TextField tfTitle;

    @FXML
    private TextField tfAuthor;

    @FXML
    private TextField tfYear;

    @FXML
    private TextField tfPages;

    @FXML
    private TableView<Books> tvBooks;

    @FXML
    private TableColumn<Books, Integer> colId;

    @FXML
    private TableColumn<Books, String> colTitle;

    @FXML
    private TableColumn<Books, String> colAuthor;

    @FXML
    private TableColumn<Books, Integer> colYear;

    @FXML
    private TableColumn<Books, Integer> colPages;

    @FXML
    private Button btnInsert;

    @FXML
    private Button btnUpdate;

    @FXML
    private Button btnDelete;

    @FXML
    public void handleButtonAction(ActionEvent event) {
        System.out.println("Yooo what the fuck");
        if (event.getSource() == btnInsert) {
            insertBook();
        } else if (event.getSource() == btnUpdate) {
            updateBook();
        } else if (event.getSource() == btnDelete) {
            deleteBook();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        showBooks();
    }

    public Connection getConnection() {
        Connection conn;
        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/library", "root", "admin");
            return conn;
        } catch (Exception err) {
            System.out.println(err.getMessage());
            return null;
        }
    }

    public ObservableList<Books> getBooksList() {
        ObservableList<Books> bookList = FXCollections.observableArrayList();
        Connection conn = getConnection();
        String query = "SELECT * FROM books";
        Statement st;
        ResultSet rs;

        try {
            st = conn.createStatement();
            rs = st.executeQuery(query);

            while (rs.next()) {
                Books books = new Books(rs.getInt("id"), rs.getString("title"), rs.getString("author"),
                        rs.getInt("year"), rs.getInt("pages"));
                bookList.add(books);
                System.out.println("yeah its fetching");
            }
            conn.close();
        } catch (Exception err) {
            System.out.println(err.getMessage());
        }

        return bookList;
    }

    public void showBooks() {
        ObservableList<Books> list = getBooksList();
        colId.setCellValueFactory(new PropertyValueFactory<Books, Integer>("id"));
        colTitle.setCellValueFactory(new PropertyValueFactory<Books, String>("title"));
        colAuthor.setCellValueFactory(new PropertyValueFactory<Books, String>("author"));
        colYear.setCellValueFactory(new PropertyValueFactory<Books, Integer>("year"));
        colPages.setCellValueFactory(new PropertyValueFactory<Books, Integer>("pages"));

        tvBooks.setItems(list);
    }

    private void insertBook() {
        Connection connection = getConnection();
        PreparedStatement stmt;
        try {
            stmt = connection.prepareStatement("INSERT INTO books VALUES(?,?,?,?,?)");
            stmt.setInt(1, Integer.parseInt(tfId.getText()));
            stmt.setString(2, tfTitle.getText());
            stmt.setString(3, tfAuthor.getText());
            stmt.setInt(4, Integer.parseInt(tfYear.getText()));
            stmt.setInt(5, Integer.parseInt(tfPages.getText()));
            int i = stmt.executeUpdate();
            System.out.println(i);
            connection.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        showBooks();
        clearTextFields();
    }

    private void deleteBook() {
        Connection connection = getConnection();
        PreparedStatement stmt;

        try {
            stmt = connection.prepareStatement("delete from books where id=?");
            stmt.setInt(1, Integer.parseInt(tfId.getText()));

            int i = stmt.executeUpdate();
            System.out.println(i + " records deleted");
        } catch (Exception e) {
            e.printStackTrace();
        }
        showBooks();
        clearTextFields();
    }

    private void updateBook() {
        Connection connection = getConnection();
        PreparedStatement stmt;

        try {
            stmt = connection.prepareStatement("UPDATE books SET title=?, author=?, year=?, pages=? WHERE id=?");
            stmt.setInt(5, Integer.parseInt(tfId.getText()));
            stmt.setString(1, tfTitle.getText());
            stmt.setString(2, tfAuthor.getText());
            stmt.setInt(3, Integer.parseInt(tfYear.getText()));
            stmt.setInt(4, Integer.parseInt(tfPages.getText()));
            int i = stmt.executeUpdate();

            System.out.println(i);
            connection.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        showBooks();
        clearTextFields();
    }

    public void clearTextFields() {
        tfId.setText("");
        tfTitle.setText("");
        tfAuthor.setText("");
        tfYear.setText("");
        tfPages.setText("");
    }

}
