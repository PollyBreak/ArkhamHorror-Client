module kz.stargazer.arkhamhorror_client {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;

    opens kz.stargazer.arkhamhorror_client to javafx.fxml;
    exports kz.stargazer.arkhamhorror_client;
    exports kz.stargazer.arkhamhorror_client.view_controllers;
    opens kz.stargazer.arkhamhorror_client.view_controllers to javafx.fxml;
}