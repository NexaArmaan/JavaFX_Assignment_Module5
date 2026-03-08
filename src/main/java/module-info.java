module org.example.javafx_assignment_module5 {
    requires javafx.controls;
    requires javafx.fxml;


    opens org.example.javafx_assignment_module5 to javafx.fxml;
    exports org.example.javafx_assignment_module5;
}