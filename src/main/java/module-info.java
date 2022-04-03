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

    requires org.apache.commons.lang3;
    requires org.apache.logging.log4j;

    requires javax.inject;
    requires com.google.guice;

    opens com.mairwunnx.application to javafx.fxml, com.google.guice;
    opens com.mairwunnx.application.application.di.modules to com.google.guice;

    exports com.mairwunnx.application.application.router;
    exports com.mairwunnx.application;
    exports com.mairwunnx.application.application;
    opens com.mairwunnx.application.application to com.google.guice, javafx.fxml;
    exports com.mairwunnx.application.application.controllers;
    opens com.mairwunnx.application.application.controllers to com.google.guice, javafx.fxml;
    exports com.mairwunnx.application.application.components;
    opens com.mairwunnx.application.application.components to com.google.guice, javafx.fxml;
}
