<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>

<div>
    <div id="editor" class="container">
        
        <span class="hidden" id="pk">${pk}</span>
        
        <h3>
            <c:out value="${titulo}" />
        </h3>
        <div class="row separador"></div>

        <%@include file="editor_botones.jsp" %>
        
        <div class="row separador"></div>
        
        <div class="container">

            <div id="div_formulario">
                <%@include file="editor_formulario.jsp" %>
            </div>
        </div>

        <div class="row spacer separador"></div>

        <%@include file="editor_botones.jsp" %>
        
        <div class="row spacer separador"></div>
    </div>

    <c:if test='${"mostrar".equals(modo)}'>
        <script>
            soloLectura("#div_formulario");
        </script>
    </c:if>

</div>

