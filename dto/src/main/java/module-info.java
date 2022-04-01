module application.dto.main {
    requires org.jetbrains.annotations;
    requires lombok;
    requires com.fasterxml.jackson.annotation;

    exports com.mairwunnx.dto.request.products;

    exports com.mairwunnx.dto.response.products;

    opens com.mairwunnx.dto.response.products to com.fasterxml.jackson.databind;
}