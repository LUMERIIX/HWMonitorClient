module org.example {


    requires java.base;
    requires java.logging;

    // Java-FX
    requires javafx.base;
    requires javafx.graphics;
    requires javafx.web;
    requires javafx.controls;

    // 3rd party
    requires eu.hansolo.medusa;
    requires eu.hansolo.tilesfx;

    exports org.example;
}