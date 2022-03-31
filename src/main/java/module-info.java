module com.mairwunnx.application {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.jetbrains.annotations;
    requires lombok;

    opens com.mairwunnx.application to javafx.fxml;
    exports com.mairwunnx.application;
}