module es.jeremy.ejeh {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens es.jeremy.ejeh to javafx.fxml;
    exports es.jeremy.ejeh;
}