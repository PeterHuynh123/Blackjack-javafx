module com.example.blackjack_javafx {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.blackjack_javafx to javafx.fxml;
    exports com.example.blackjack_javafx;
}