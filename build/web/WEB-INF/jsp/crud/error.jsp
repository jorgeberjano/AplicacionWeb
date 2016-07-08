<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>

<div>
    <div id="error" class="container">
        <h3>
            <spring:message code="error.titulo" />
        </h3>
        <div class="row spacer separador"></div>

        <div><c:out value="${mensaje_error}" /></div>

        <div class="row spacer separador"></div>

        <div id="urlRetorno" hidden><c:out value="${url_retorno}"/></div>

        <div class="pull-right">
            <button id="boton_atras" type="button" class="btn btn-default"><spring:message code="boton.atras" /></button>
            <script>
                $("#boton_atras").click(function () {
                    volver();
                });
            </script>
        </div>
    </div>
</div>
