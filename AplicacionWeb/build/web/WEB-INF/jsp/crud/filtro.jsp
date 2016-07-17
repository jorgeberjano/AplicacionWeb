<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>

<div>
    <form:form method="POST" cssClass="form-signin" commandName="filtro" role="form" >
        <div id="portador_form" class="container">

<%--
            <div class="row show-grid"> 
                <div class="col-md-2 bold  text-right">
                    <spring:message code="portador.nombre" />
                </div>
                <div class="col-md-4">
                    <form:input id="nombre" tabindex="1" maxlength="30" path="nombre" type="name" cssClass="form-control" />
                </div>
            </div>  
            <div class="row show-grid">
                <div class="col-md-2 bold  text-right">
                    <spring:message code="portador.apellido1" />
                </div>
                <div class="col-md-4">
                    <form:input id="apellido1" tabindex="2" maxlength="30" path="apellido1" type="ap1" cssClass="form-control" />
                </div>
            </div>
            <div class="row show-grid">
                <div class="col-md-2 bold  text-right"><spring:message code="portador.apellido2" /></div>
                <div class="col-md-4">
                    <form:input id="apellido2" tabindex="3" maxlength="30" path="apellido2" type="ap2" cssClass="form-control" />
                </div>
            </div>

            <div class="row spacer"></div>
            <div class="pull-right">
                <button type="button" id="limpiar" onclick="limpiarFormulario()" class="btn btn-default"><spring:message code="boton.limpiar" /></button>
            </div>
            <script>
                $("input").keypress(function(e) { keypressFiltro(e, 'filtrar'); });
            </script>
--%>
        </div> 
    </form:form>
</div>






