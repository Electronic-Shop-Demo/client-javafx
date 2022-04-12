module application.ui.lib {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.controlsfx.controls;
    requires org.jetbrains.annotations;
    requires lombok;
    requires com.google.guice;
    requires application.ui.annotations;
    requires application.ui.commons;

    opens uilib.assets;
    opens uilib;
    opens com.mairwunnx.ui.lib;
    exports com.mairwunnx.ui.lib;
    exports com.mairwunnx.ui.lib.apis;
}
