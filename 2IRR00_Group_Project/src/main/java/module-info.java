module com.mycompany.irr00_group_project {
    requires java.desktop;
    requires javafx.controls;
    requires javafx.fxml;

    opens com.mycompany.irr00_group_project.controller to javafx.fxml;
    exports com.mycompany.irr00_group_project;
}