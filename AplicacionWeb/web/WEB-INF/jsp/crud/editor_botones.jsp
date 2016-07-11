<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>


<div class="pull-right">              
    <c:if test='${"mostrar".equals(modo)}'>
        <button type="button" class="boton_atras btn btn-default"><spring:message code="boton.atras" /></button>
        <button type="button" class="boton_modificar btn btn-default"><spring:message code="boton.modificar" /></button>
        <button type="button" class="boton_borrar btn btn-default"><spring:message code="boton.borrar" /></button>
    </c:if>
    <c:if test='${"editar".equals(modo)}'>
        <button type="button" class="boton_guardar btn btn-default"><spring:message code="boton.guardar" /></button>
        <button type="button" class="boton_cancelar btn btn-default"><spring:message code="boton.cancelar" /></button>
    </c:if>                
    <script>
        $(".boton_atras").click(function () {
            mostrarTablaElementos();                    
        });

        $(".boton_modificar").click(function () {
            modificarElemento();
        });

        $(".boton_cancelar").click(function () {
            var pk = $("#pk").val();
            if (pk === undefined || pk < 0) {
                mostrarTablaElementos();
            } else {
                mostrarElemento();
            }
        });

        $(".boton_borrar").click(function () {
            borrarElemento();
        });

        $(".boton_guardar").click(function () {
            $("#formulario").submit();
        });

    </script>
</div>
