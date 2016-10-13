function limpiarFormulario() {
    var elementos = $("input");
    elementos.val("");
    aplicarFiltro();
}

function keypressFiltro(e) {
    if (e.keyCode === 13) {
        aplicarFiltro();
    } else {
        filtrar();
    }
}

function filtrar() {
    if (typeof (timerFiltro) !== "undefined") {
        clearTimeout(timerFiltro);
    }
    timerFiltro = setTimeout(function() { aplicarFiltro() }, 3000);
}

function aplicarFiltro() {
    var filtro = $('#filtro').serialize();
    var idConsulta = $("#idConsulta").html();
    $.ajax({
        type: "POST",
        url: "filtrar/" + idConsulta,
        data: {
            filtro: filtro
            //campoSeleccion: campoSeleccion
        },
        success: function (result) {
            $("#div_contenedor_tabla").html(result);
        },
        error: manejarError()
    });
}

function ordenar(campoOrden, campoSeleccion ) {
    var idConsulta = $("#idConsulta").html();
    $.ajax({
        type: "POST",
        url: completarUrl("ordenar/" + idConsulta),
        data: {
            campoOrden: campoOrden,
            campoSeleccion: campoSeleccion
        },
        success: function (result) {
            $("#div_contenedor_tabla").html(result);
        },
        error: manejarError()
    });
}

function completarUrl(url) {
    var contextPath = $("#contextPath").html();
    return contextPath + "/" + url;
}

function paginar(desplazamiento) {
    
    var idConsulta = $("#idConsulta").html();
    $.ajax({
        type: "GET",
        data: {
            accion: desplazamiento
        },
        url: completarUrl("/paginar/" + idConsulta),
        success: function (result) {
            $('#div_contenedor_tabla').html(result);
        },
        error: manejarError
    });
}

function volver() {
    var url = $("#urlRetorno").html();  
    $.ajax({
        type: "GET",
        url: completarUrl(url),
        success: function (result) {
            $("#contenedor_crud").html(result);
        }
    });
}

function mostrarTablaElementos() {
    var idConsulta = $("#idConsulta").html();
    $.ajax({
        type: "GET",
        url: completarUrl("tabla/" + idConsulta),
        success: function (result) {
            $("#contenedor_crud").html(result);
        },
        error: manejarError()
    });
}

function mostrarElemento(pk) {
    var idConsulta = $("#idConsulta").html();
    $.ajax({
        type: "GET",
        url: completarUrl("mostrar/" + idConsulta + "/" + pk + "/"),
        success: function (result) {
            $("#contenedor_crud").html(result);
            $("#pk").html(pk);
        },
        error: manejarError()
    });
}

function crearElemento() {
    var idConsulta = $("#idConsulta").html();
    $.ajax({
        type: "GET",
        url: completarUrl("crear/" + idConsulta),
        success: function (result) {
            $("#contenedor_crud").html(result);
        },
        error: manejarError()
    });
}

function modificarElemento() {
    var idConsulta = $("#idConsulta").html();
    var pk = $("#pk").html();
    $.ajax({
        type: "GET",
        url: completarUrl("modificar/" + idConsulta + "/" + pk + "/"),
        success: function (result) {
            $("#contenedor_crud").html(result);
        },
        error: manejarError()
    });
}

function borrarElemento() {
    var idConsulta = $("#idConsulta").html();
    var pk = $("#pk").html();
    $.ajax({
        type: "GET",
        url: completarUrl("borrar/" + idConsulta + "/" + pk + "/"),
        success: function (result) {
            $("#contenedor_crud").html(result);
        },
        error: manejarError()
    });
}

function guardarElemento(form) {
    var idConsulta = $("#idConsulta").html();
    var pk = $("#pk").html();
    var datos = new FormData(form);
    var url;
    if (pk) {
        url = "guardar/" + idConsulta + "/" + pk + "/";
    } else {
        url = "guardar/" + idConsulta + "/";
    }
    $.ajax({
        type: "POST",
        url: completarUrl(url),
        contentType: false,
        processData: false,
        data: datos,
        success: function (result) {
            var html = $.parseHTML(result);
            if ($(html).find('#editor').length ||
                    $(html).find('#error').length) {
                $("#contenedor_crud").html(result);
            } else {
                $("#div_formulario").html(result);
            }
        },
        error: manejarError
    });
}

function manejarError(xhr, errorType, exception) {
    if (xhr) {
        alert(errorType + ": " + xhr.statusText);
    }
}

function soloLectura(s) {
    $(s + " :input").prop("disabled", true);
}

function mostrarPagina(pagina) {
    $.ajax({
        type: "GET",
        url: completarUrl(pagina),
        success: function (result) {
            mostrarEnPanelPrincipal(result);
        },
        error: manejarError
    });
}

function mostrarEnPanelPrincipal(contenido) {
    if (contenido) {
        $("#div_contenido").removeClass("hidden");
    } else {
        $("#div_contenido").addClass("hidden");
    }
    $("#div_contenido").html(contenido);
}

function clearDateInput(name) {
    $("#" + name).val("");   
}

function seleccionar() {
    var idConsulta = $("#idConsulta").html();
    $.ajax({
        type: "GET",
        data: { campoSeleccion: "idPortador"},
        url: completarUrl("seleccionar/" + idConsulta),
        success: function (result) {
            $('#panel_seleccion').removeClass("hidden");
            $('#div_seleccion').html(result);
        },
        error: manejarError
    });
}

function seleccionado(campoSeleccion, id) {
    $("#" + campoSeleccion).val(id);
    $('#panel_seleccion').addClass("hidden");
    $('#div_seleccion').html("");
}

//function serializarFormulario(formulario) {    
//    var elementos = $(formulario).find("input");
//    var texto = "";
//    for (var i = 0; i < elementos.length; i++) {
//        var e = elementos[i];
//        var nombre = e.name;
//        var valor = e.value;
//        if (texto !== "") {
//            texto += "%";
//        }
//        texto += nombre + "=" + valor;
//    }
//    return texto;
//}

function serializarCampos(elementos) {
    var pk = "";
    for (var i = 0; i < elementos.length; i++) {
        var e = elementos[i];
        var nombre = e.title;
        var valor = e.textContent.trim();
        if (pk !== "") {
            pk += "%";
        }
        pk += nombre + "=" + valor;
    }
    return pk;
}


