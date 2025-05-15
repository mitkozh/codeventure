module com.mycompany.irr00_group_project {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.mycompany.irr00_group_project to javafx.fxml;
    exports com.mycompany.irr00_group_project;
}