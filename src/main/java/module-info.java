module com.mairwunnx.application {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.jetbrains.annotations;
    requires lombok;

    requires application.dto.main;
    requires java.net.http;

    requires com.fasterxml.jackson.core;
    requires com.fasterxml.jackson.annotation;
    requires com.fasterxml.jackson.databind;

    opens com.mairwunnx.application to javafx.fxml;
    opens com.mairwunnx.application.router to com.mairwunnx.application;

    exports com.mairwunnx.application;
}