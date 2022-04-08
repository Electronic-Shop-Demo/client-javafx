module com.mairwunnx.application {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;

    requires org.jetbrains.annotations;
    requires lombok;

    requires java.net.http;
    requires javax.inject;
    requires com.google.guice;

    requires com.fasterxml.jackson.core;
    requires com.fasterxml.jackson.annotation;
    requires com.fasterxml.jackson.databind;

    requires org.apache.commons.lang3;
    requires org.apache.logging.log4j;

    requires application.models.main;
    requires application.ui.navigation;
    requires application.ui.preferences;
    requires application.dto.main;

    exports com.mairwunnx.application;
    exports com.mairwunnx.application.application;
    exports com.mairwunnx.application.application.components;
    exports com.mairwunnx.application.application.controllers;

    opens com.mairwunnx.application to javafx.fxml, com.google.guice;
    opens com.mairwunnx.application.application to com.google.guice, javafx.fxml;
    opens com.mairwunnx.application.application.di.modules to com.google.guice;
    opens com.mairwunnx.application.application.controllers to com.google.guice, javafx.fxml;
    opens com.mairwunnx.application.application.controllers.home to com.google.guice, javafx.fxml;
    opens com.mairwunnx.application.application.components to com.google.guice, javafx.fxml;
    opens com.mairwunnx.application.application.views to javafx.fxml;
}
