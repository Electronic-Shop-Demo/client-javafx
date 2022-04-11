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

    requires application.ui.navigation;
    requires application.ui.preferences;
    requires application.ui.lib;
    requires application.ui.commons;

    requires application.dto.main;
    requires application.models.main;

    exports com.mairwunnx.application;

    opens application.assets;
    opens application.bundles;
    opens application.fonts;
    opens application.layouts;
    opens application.styles;

    exports com.mairwunnx.application.application;
    exports com.mairwunnx.application.application.components;
    exports com.mairwunnx.application.application.controllers;


    opens com.mairwunnx.application to javafx.fxml, com.google.guice;
    opens com.mairwunnx.application.application to com.google.guice, javafx.fxml;
    opens com.mairwunnx.application.application.di.modules to com.google.guice;
    /*opens com.mairwunnx.application.application.views to com.google.guice, javafx.fxml, application.ui.navigation;*/
    opens com.mairwunnx.application.application.controllers to com.google.guice, javafx.fxml;
    opens com.mairwunnx.application.application.components to com.google.guice, javafx.fxml;
}
