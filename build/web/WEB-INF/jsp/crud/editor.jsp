<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>

<div>
    <div id="editor" class="container">
        
        <span id="pk">${pk}</span>
        
        <h3>
            <c:out value="${titulo}" />
        </h3>
        <div class="row separador"></div>

        <div class="container">

            <div id="div_formulario">
                <%@include file="formulario.jsp" %>
            </div>
        </div>

        <div class="row spacer separador"></div>

        <!-- botones -->
        <div class="pull-right">              
            <c:if test='${"mostrar".equals(modo)}'>
                <button id="boton_atras" type="button" class="btn btn-default"><spring:message code="boton.atras" /></button>
                <button id="boton_modificar" type="button" class="btn btn-default"><spring:message code="boton.modificar" /></button>
                <button id="boton_borrar" type="button" class="btn btn-default"><spring:message code="boton.borrar" /></button>
            </c:if>
            <c:if test='${"editar".equals(modo)}'>
                <button id="boton_guardar" type="button" class="btn btn-default"><spring:message code="boton.guardar" /></button>
                <%-- <input type="submit" value="<spring:message code="boton.guardar" />" class="btn btn-default"/> --%>
                <button id="boton_cancelar" type="button" class="btn btn-default"><spring:message code="boton.cancelar" /></button>
            </c:if>                
            <script>
                $("#boton_atras").click(function () {
                    mostrarTablaElementos();                    
                });

                $("#boton_modificar").click(function () {
                    modificarElemento();
                });

                $("#boton_cancelar").click(function () {
                    var pk = $("#pk").val();
                    if (pk === undefined || pk < 0) {
                        mostrarTablaElementos();
                    } else {
                        mostrarElemento();
                    }
                });

                $("#boton_borrar").click(function () {
                    borrarElemento();
                });

                $("#boton_guardar").click(function () {
                    $("#formulario").submit();
                });

            </script>
        </div>
    </div>

    <c:if test='${"mostrar".equals(modo)}'>
        <script>
            soloLectura("#div_formulario");
        </script>
    </c:if>

</div>

