
$(document).ready(function () {

	refrescar();

	$(".body").on("click", "#btnGuardarTemp", function () {
		refrescar();
	});

});

function refrescar() {

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

	$('#rtfContenido2').trumbowyg('html', Contenido);

}
;