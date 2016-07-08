<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<nav>      
    <c:if test="${numeroPaginas > 0}"> 
        <ul class="pagination pull-right">
            <li class="disabled">
                <a href="#">
                    <spring:message code="tabla.pagina" />
                    <c:out value="${paginaActual}" />
                    <spring:message code="tabla.de" />
                    <c:out value="${numeroPaginas}" />
                </a>
            </li>
            <c:choose>
                <c:when test="${paginaActual == 1}">
                    <li class="disabled">
                    </c:when>
                    <c:otherwise>
                    <li>
                    </c:otherwise>
                </c:choose>
                <a href="javascript:paginar('first')">
                    <span class="glyphicon glyphicon-fast-backward"></span>
                </a>
            </li>
            <c:choose>
                <c:when test="${paginaActual == 1}">
                    <li class="disabled">
                    </c:when>
                    <c:otherwise>
                    <li>
                    </c:otherwise>
                </c:choose>
                <a href="javascript:paginar('previous')">
                    <span class="glyphicon glyphicon-step-backward"></span>
                </a>
            </li> 
            <c:choose>
                <c:when test="${paginaActual == numeroPaginas}">
                    <li class="disabled">
                    </c:when>
                    <c:otherwise>
                    <li>
                    </c:otherwise>
                </c:choose>
                <a href="javascript:paginar('next')">
                    <span class="glyphicon glyphicon-step-forward"></span>
                </a>
            </li>
            <c:choose>
                <c:when test="${paginaActual == numeroPaginas}">
                    <li class="disabled">
                    </c:when>
                    <c:otherwise>
                    <li>
                    </c:otherwise>
                </c:choose>
                <a href="javascript:paginar('last')">
                    <span class="glyphicon glyphicon-fast-forward"></span>
                </a>
            </li>                         
        </ul>
    </c:if>
</nav>
