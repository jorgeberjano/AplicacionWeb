<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<div id="div_tabla" class="row show-grid">   
    <div class="container show-top-margin separate-rows">
        <div class="row show-grid">
            <div class="col-md-8">
                <c:if test='${empty campoSeleccion}'>
                    <button id="boton_crear" type="button" class="btn btn-default"><spring:message code="boton.crear" /></button>
                </c:if>
            </div>
            <div class="col-md-4">
                <%@include file="paginacion.jsp" %>
            </div>
        </div>
    </div>
    
    <div class="row spacer"/>
    
    <table id="tabla" class="table table-striped table-bordered table-hover">
        <thead>
            <tr>
                <c:forEach var="campo" items="${consulta.listaCampos}">                
                    <%-- <th class="text-center" data-field="<c:out value="${campo.nombre}" />" data-align="center" ><c:out value="${campo.titulo}" /></th>
                    --%>
                    <th class="text-center " data-field="${campo.nombre}" data-align="center" ><c:out value="${campo.titulo}" /></th>
                </c:forEach>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="elemento" items="${listaElementos}">
                <tr id="row">             
                    <c:forEach var="campo" items="${consulta.listaCampos}">
                        <td title="${campo.nombre}" class="${campo.clave ? "pk" : ""}">
                            <c:out value="${elemento.get(campo.nombre)}" />
                        </td>
                    </c:forEach>
                </tr>
            </c:forEach>
        </tbody>
    </table>                  
    <c:choose>
        <c:when test="${not empty mensaje_error}">
            <div class="row show-grid">
                <div class="col-sm-12 text-center">
                    <c:out value="${mensaje_error}" />
                </div>
            </div>
        </c:when>
        <c:when test="${empty listaElementos || listaElementos.size() == 0}" >
            <div class="row show-grid">
                <div class="col-sm-12 text-center">
                    <spring:message code="tabla.vacia" />
                </div>
            </div>                                
        </c:when>
    </c:choose>

    

    <script>        
        $("tr#row").click(function () {
            var pk = serializarCampos($(this).find("td.pk"));
            
            var campoSeleccion = $("#campoSeleccion").html();
            if (campoSeleccion) {
                seleccionado(campoSeleccion, pk);
            } else {
                mostrarElemento(pk);
            }
        });

        $("#boton_crear").click(function() {
            crearElemento();
        });

        $("th").click(function() {
            var campoOrden = this.getAttribute("data-field");
            var campoSeleccion = $("#campoSeleccion").html();
            ordenar(campoOrden, campoSeleccion);
        });
    </script>
</div>
