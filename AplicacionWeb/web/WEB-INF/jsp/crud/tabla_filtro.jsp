<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<!DOCTYPE html>
<div id="div_tabla_filtro">

    <div id="campoSeleccion" hidden><c:out value="${campoSeleccion}"/></div>
    <div id="campoSeleccion" hidden><c:out value="${campoSeleccion}"/></div>

    <h3 ><c:out value="${consulta.getNombre()}"/></h3>

    <div class="row separador"></div>

    <div id="div_contenedor_filtro" class="container">
        <%@include file="filtro.jsp" %>
    </div>

    <div class="row spacer"></div>

    <div id="div_contenedor_tabla" class="container">
        <%@include file="tabla.jsp" %>
    </div>
    
</div>