module com.mycompany.irr00_group_project {
    requires java.desktop;
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;

    opens com.mycompany.irr00_group_project.controller to javafx.fxml;
    opens com.mycompany.irr00_group_project.controller.components to javafx.fxml;
    exports com.mycompany.irr00_group_project;
}