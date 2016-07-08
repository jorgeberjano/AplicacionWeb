<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>

<div id="editor">

    <form:form method="POST" enctype="multipart/form-data" id="formulario" cssClass="form-signin" commandName="elemento" role="form" >

        <form:hidden path="id" id="id_elemento" />

        <div class="container">
            <h3>
                <c:out value="${titulo}" />
            </h3>

            <div class="row spacer separador"/>

            <div class="container">
                <div class="row show-grid">
                    <div class="col-md-4"></div>
                    <div class="col-md-2">
                        <c:choose>                                        
                            <c:when test="${not empty elemento.foto.base64}">
                                <p> FOTO </p>
                                <img id="foto_visible" src="data:image/jpg;base64,${elemento.foto.base64}" class="img-rounded img-size"/>
                            </c:when>
                            <c:otherwise>
                                <p> SIN FOTO </p>
                                <img id="foto_visible" src="<c:url value='/resources/images/unknown-member.gif' />" class="img-rounded img-size"/>
                            </c:otherwise>
                        </c:choose>
                    </div>
                    <div class="col-md-6">
                        <input type="file" name="file" id="input_foto" title="Seleccionar foto" />
                    </div>
                </div>
            </div>
            
            <div class="row spacer separador"/>
            
            <!-- botones -->
            <div class="pull-right">              
                <input type="submit" value="<spring:message code="boton.guardar" />" class="btn btn-default"/>
                <button id="boton_cancelar" type="button" class="btn btn-default"><spring:message code="boton.cancelar" /></button>
                <script>
                    $("#boton_cancelar").click(function () {
                        var id = $("#id_elemento").val();
                        $.ajax({
                            type: "GET",
                            url: "mostrarPortador/" + id,
                            success: function (result) {
                                $("#contenedor_crud").html(result);
                            },
                            error: function () {
                                alert("Error al cancelar");
                            }
                        });
                    });

                    $("#formulario").submit(function (event) {
                        $.ajax({
                            type: "POST",
                            url: "guardarFoto",
                            contentType: false,
                            processData: false,
                            data: new FormData(this),
                            success: function (result) {
                                $("#contenedor_crud").html(result);
                            },
                            error: function (xhr, errorType, exception) {
                                alert("errorl : " + exception + " " + errorType + " " + xhr.statusText);
                            }
                        });
                        event.preventDefault();
                    });

                    $("#input_foto").change(function () {
                        var input = this;
                        if (input.files && input.files[0]) {
                            var reader = new FileReader();
                            reader.onload = function (e) {
                                $('#foto_visible').attr('src', e.target.result);
                            }
                            reader.readAsDataURL(input.files[0]);
                        }
                    });
                </script>
            </div>
        </div>
    </form:form> 
</div>

