
$(document).ready(function () {

    $("body").on("click", ".btnGuardarCorreoTemp", function () {
        cargarCorreoAGuardar();
        $(".btnGuardarCorreo").click();
    });

    $("body").on("click", ".btnGuardarYEnviarCorreoTemp", function () {
        cargarCorreoAGuardar();
        $(".btnGuardarCorreo2").click();
    });

    $("body").on("click", ".btnSubirArchivoTemp", function () {
        $(".btnSubirArchivo").click();
    });
    
});

function cargarCorreoAGuardar() {
    $(".txtContenidoOculto").html($("#rtfContenido").html());
    $(".txtContenidoOculto").click(); //Ejecuto el evento Ajax de txtContenidoOculto, para que almacene en el Bean en contenido que acabo de setearle.
    $("#btnCancelar").focus(); //Solo le doy el focus a algo.
}

function cargarCorreoEnTrumbowyg() {
    var Contenido = $(".txtContenidoOculto").html().toString();
    var Bandera = true;

    while (Bandera) {
        if (Contenido.search("&lt;") >= 0) {
            Contenido = Contenido.replace("&lt;", "<");
            Contenido = Contenido.replace("&gt;", ">");
        } else {
            Bandera = false;
        }
    }

    var e = document.getElementById("frmNavigator:slcIdm");
    var strUser = e.options[e.selectedIndex].text;



    if (strUser === 'English') {
        $('.btnGuardarCorreoTemp').attr("value", "Update");
        $('.btnGuardarYEnviarCorreoTemp').attr("value", "Update and Send");
    } else {
        $('.btnGuardarCorreoTemp').attr("value", "Actualizar");
        $('.btnGuardarYEnviarCorreoTemp').attr("value", "Actualizar y Enviar");
    }


    $('#rtfContenido').trumbowyg('html', Contenido);

}

function cargarPlantillaEnTrumbowyg() {
    var Contenido = $(".txtContenidoOculto2").html().toString();
    var Bandera = true;

    while (Bandera) {
        if (Contenido.search("&lt;") >= 0) {
            Contenido = Contenido.replace("&lt;", "<");
            Contenido = Contenido.replace("&gt;", ">");
        } else {
            Bandera = false;
        }
    }

    var e = document.getElementById("frmNavigator:slcIdm");
    var strUser = e.options[e.selectedIndex].text;



    if (strUser === 'English') {
        $('.btnGuardarCorreoTemp').attr("value", "Save");
        $('.btnGuardarYEnviarCorreoTemp').attr("value", "Save and Send");
    } else {
        $('.btnGuardarCorreoTemp').attr("value", "Guardar");
        $('.btnGuardarYEnviarCorreoTemp').attr("value", "Guardar y Enviar");
    }


    $('#rtfContenido').trumbowyg('html', Contenido);
}

function mostrarModalDisenarCorreo() {
    $('#rtfContenido').trumbowyg('html', '');
    $('#modalDisenarCorreo').modal('show');
}

function mostrarModalSubirArchivo() {
    $('#modalArchivos').modal('show');
}

function redireccionarEnviarCorreo() {
    $(".btnEnviarCorreo").click();
}