<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<!DOCTYPE html>
<div>    
    <span id="idConsulta" class="hidden"><c:out value="${consulta.getIdConsulta()}"/></span>
    <div id="contenedor_crud" class="container">
        <%@include file="tabla_filtro.jsp" %>
    </div>
</div>
