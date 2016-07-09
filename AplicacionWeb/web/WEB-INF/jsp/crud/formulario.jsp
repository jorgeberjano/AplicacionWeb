<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@taglib prefix="spring"uri="http://www.springframework.org/tags" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>

<%-- <form:form modelAttribute="elemento" method="POST" enctype="multipart/form-data" id="formulario" cssClass="form-signin" role="form" > --%>
<form:form commandName="elemento"
           method="POST"
           enctype="multipart/form-data"
           id="formulario"
           cssClass="form-signin"
           role="form" 
           acceptCharset="UTF-8">
    
    <%--<form:hidden id="pk_elememto" path="pk" />--%>
    <%--<input type="hidden" value="${mensaje}" id="mensaje_server" />--%>

    <c:forEach var="campo" items="${consulta.listaCampos}">
        <div class="form-group">            
            <form:label path="mapa['${campo.getNombre()}'].valor" for="${campo.getNombre()}"><c:out value='${campo.getTitulo()}' /></form:label>
            <c:choose>
                <c:when test="${campo.tipoDato == 'BOOLEANO'}">
                    BOOL
                    <form:select path="mapa['${campo.getNombre()}'].valor"
                                 items="${campo.opciones}"
                                 multiple="false"                                 
                                 tabindex="${campo.getIndice()}"
                                 cssClass="form-control"
                                 id="${campo.getNombre()}" />
                </c:when>
                <c:when test="${campo.tipoDato == 'FECHA'}">
                    FECHA
                    <form:input path="mapa['${campo.getNombre()}'].valor"
                                tabindex="${campo.getIndice()}"
                                class="form-control"
                                id="${campo.getNombre()}" />
                </c:when>
                <c:otherwise>
                    <form:input path="mapa['${campo.getNombre()}'].valor" tabindex="${campo.getIndice()}" class="form-control" id="${campo.getNombre()}" />
                </c:otherwise>
            </c:choose>            
        </div>
    </c:forEach>

    <%--<form:input path="a" class="form-control" id="a" />--%>
    <%--
        <c:set var="error"><form:errors path="nombre"/></c:set>
        <div class="row show-grid <c:if test="${not empty error}"> form-main-error has-error </c:if>">
            <div class="col-md-2 bold text-right">
                <spring:message code="portador.nombre" />
            </div>                
            <div class="col-md-4">
                <form:input tabindex="1" maxlength="30" path="nombre" cssClass="form-control" id="name" />
            </div>
            <div class="col-md-4">
                ${error}
            </div>
        </div>                                                

        <c:set var="error"><form:errors path="apellido1"/></c:set>
        <div class="row show-grid <c:if test="${not empty error}"> form-main-error has-error </c:if>">
            <div class="col-md-2 bold text-right">
                <spring:message code="portador.apellido1" />
            </div>
            <div class="col-md-4">
                <form:input tabindex="2" path="apellido1" maxlength="30" cssClass="form-control"/>
            </div>  
            <div class="col-md-4">
                ${error}
            </div>
        </div>

        <c:set var="error"><form:errors path="apellido2"/></c:set>
        <div class="row show-grid <c:if test="${not empty error}"> form-main-error has-error </c:if>">
            <div class="col-md-2 bold text-right">
                <spring:message code="portador.apellido2" />
            </div>
            <div class="col-md-4">
                <form:input tabindex="3" path="apellido2" maxlength="30" cssClass="form-control"/>
            </div>
            <div class="col-md-4">
                ${error}
            </div>
        </div>

        <c:set var="error"><form:errors path="nif"/></c:set>
        <div class="row show-grid <c:if test="${not empty error}"> form-main-error has-error </c:if>">
            <div class="col-md-2 bold text-right">
                <spring:message code="portador.nif" />
            </div>                
            <div class="col-md-4">
                <form:input tabindex="4" maxlength="20" path="nif" cssClass="form-control" />
            </div>
            <div class="col-md-4">
                ${error}
            </div>
        </div>

        <c:set var="error"><form:errors path="baja"/></c:set>
        <div class="row show-grid <c:if test="${not empty error}"> form-main-error has-error </c:if>">
            <div class="col-md-2 bold text-right">
                <spring:message code="portador.baja" />
            </div>                
            <div class="col-md-4">
                <form:select tabindex="5" cssClass="form-control" path="baja.texto" items="${elemento.baja.opciones}" itemValue="texto" itemLabel="texto" multiple="false" />
            </div>
            <div class="col-md-4">
                ${error}
            </div>
        </div>
    </div>
    --%>
    <script>
        $("#formulario").submit(function (event) {
            guardarElemento(this);
            event.preventDefault();
        });
    </script>

</form:form> 


