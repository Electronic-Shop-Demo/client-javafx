module application.ui.navigation {
    requires org.jetbrains.annotations;
    requires javafx.fxml;
    requires javafx.graphics;
    requires lombok;

    exports com.mairwunnx.ui.navigation;
    exports com.mairwunnx.ui.navigation.configurators;
    exports com.mairwunnx.ui.navigation.graph;
    exports com.mairwunnx.ui.navigation.builders;
    exports com.mairwunnx.ui.navigation.types;
    exports com.mairwunnx.ui.navigation.contracts;
    exports com.mairwunnx.ui.navigation.controller;
    exports com.mairwunnx.ui.navigation.impl;
}
