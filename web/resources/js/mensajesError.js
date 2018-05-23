
var MutationObserver = window.MutationObserver || window.WebKitMutationObserver || window.MozMutationObserver;

// Seleccionar el nodo que deseamos observar
var target = document.querySelector('#contenedorMensajes');

// Creamos una instacia de un Observador
var observer = new MutationObserver(function (mutations) {
	mutations.forEach(function (mutation) {
		$(".globalAlert").delay(8000).fadeOut(3000).delay(3000).queue(function () {
			$(this).remove();
		});

	});
        
});

// Configuramos lo que va estar pendiente el observador
var config = {attributes: false, childList: true, characterData: false, subtree: true};

// Iniciamos la observación
observer.observe(target, config);
observer.observe(target2,config);
// Si se desea, esta función detiene el Observador y ya noesta pendiente del objeto.
//observer.disconnect();