module com.schfr.virtualpet {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.schfr.virtual_pet to javafx.fxml;
    exports com.schfr.virtual_pet;
    exports com.schfr.virtual_pet.view;
    opens com.schfr.virtual_pet.view to javafx.fxml;
}