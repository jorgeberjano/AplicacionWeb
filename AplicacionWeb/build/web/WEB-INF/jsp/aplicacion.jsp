<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>        
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <script src="${pageContext.request.contextPath}/resources/js/jquery.js"></script>
        <script src="${pageContext.request.contextPath}/resources/js/bootstrap.min.js"></script>
        <script src="${pageContext.request.contextPath}/resources/js/crud.js"></script>
        <script src="${pageContext.request.contextPath}/resources/js/bootstrap-datepicker.js"></script>
        <script src="${pageContext.request.contextPath}/resources/js/bootstrap-datetimepicker.js"></script>
                
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/bootstrap-theme.css" media="screen" />
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/bootstrap.min.css" media="screen" />
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/datepicker.css" media="screen" />
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/bootstrap-datetimepicker.css" media="screen" />
        <title>Aplicacion Web</title>
    </head>

    <body>
        <span id="contextPath" class="hidden">${pageContext.request.contextPath}</span> 
        <div class="container collapse navbar-collapse">
            <ul id="menu" class="nav navbar-nav" role="menu">
                <li type="button" class="active dropdown">                   
                    <a href="#" class="dropdown-toggle" data-toggle="dropdown">General <span class="caret"></span></a>
                </li>
                <li type="button" class="active dropdown">
                    <a href="#" class="dropdown-toggle" data-toggle="dropdown">Base de datos <span class="caret"></span></a>
                    <ul class="dropdown-menu" role="menu">
                        <c:forEach var="consulta" items="${consultas}">
                            <%--<c:out value="${consulta}"/>    <li><a href="${pageContext.request.contextPath}/tabla/${consulta}"><c:out value="${consulta}"/></a></li>--%>
                            <li>
                                <a href="javascript:mostrarPagina('tabla/${consulta.getIdConsulta()}')"><c:out value="${consulta.getNombre()}"/></a>
                            </li>
                            
                        </c:forEach>
                        <li class="divider"></li>
                        <li><a href="#">Otros</a></li>

                    </ul>
                </li>
            </ul>                               
        </div>
        <div id="div_contenido">

        </div>        
        <script>
            //$("#hello").html("Hello world");
        </script>
    </body>

</html>
