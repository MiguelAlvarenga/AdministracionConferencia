
var origenContenido = -1;

$(document).ready(function () {

	$(".container-fluid").on("click", "#liPlantilla", function () {
		$("#liTipoPlantilla").removeClass("active");
		$("#divTipoPlantilla").addClass("hidden");

		$("#liPlantilla").addClass("active");
		$("#divPlantilla").removeClass("hidden");
	});

	$(".container-fluid").on("click", "#liTipoPlantilla", function () {
		$("#liPlantilla").removeClass("active");
		$("#divPlantilla").addClass("hidden");

		$("#liTipoPlantilla").addClass("active");
		$("#divTipoPlantilla").removeClass("hidden");
	});

	//Usado en plantillasCorreo.xhtml
	$(".body").on("click", "#btnGuardarTemp", function () {
		$(".txtContenidoOculto").html($("#rtfContenido").html());
		$(".cmbTipoPlantilla").focus();
		$(".txtContenidoOculto").click();
		$("#btnGuardarTemp").focus();
		$(".btnGuardarPlantilla").click();
	});

	//Usado en conferencias.xhtml
	$(".body").on("click", ".btnGuardarTemp2", function () {
		$(".txtContenidoOculto").html($("#rtfContenido").html());
		$(".txtContenidoOculto").click(); //Ejecuto el evento Ajax de txtContenidoOculto, para que almacene en el Bean en contenido que acabo de setearle.
		$(".btnGuardarTemp2").focus(); //Solo le doy el focus a algo
		$(".btnGuardarCorreo").click();
	});
        
        

});


function mostrarModalEditar() {

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

	$('#rtfContenido').trumbowyg('html', Contenido);

	$('#modalEditarPlantilla').modal('show');
}

function mostrarModalVer() {

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

	$('#rtfContenido2').trumbowyg('html', Contenido);
	$('#modalVerPlantilla').modal('show');

}

function mostrarCorreo() {

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

	origenContenido = 0;

	$('#btnGuardarTemp2').attr("value", "Actualizar");
	$('.btnEnviarCorreo').attr("value", "Actualizar y Enviar");
	$('#rtfContenido').trumbowyg('html', Contenido);
}

function mostrarPlantillaCorreo() {

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

	origenContenido = 1;
	$('#btnGuardarTemp2').attr("value", "Guardara");
	$('.btnEnviarCorreo').attr("value", "Guardar y Enviar");
	$('#rtfContenido').trumbowyg('html', Contenido);
}

function mostrarModalCrearCorreo() {
	$('#modalCrearCorreo').modal('show');
}

function guardarCorreo() {
	$(".txtContenidoOculto").html($("#rtfContenido").html());
	$(".txtContenidoOculto").click(); //Ejecuto el evento Ajax de txtContenidoOculto, para que almacene en el Bean en contenido que acabo de setearle.
	$(".btnGuardarTemp2").focus(); //Solo le doy el focus a algo
}

function redireccionarEnviarCorreo() {
alert('csd llega');
	$(".btnGuardarYEnviar").click();
}
