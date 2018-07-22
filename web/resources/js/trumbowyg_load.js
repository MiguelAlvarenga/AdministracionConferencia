
$(document).ready(function () {

	$.trumbowyg.svgPath = rutaIconos;

	$('#rtfContenido').trumbowyg({
            
            
            btnsDef: {
                nombrePrograma: {
                    fn: function() {
                        document.getElementById('rtfContenido').focus(); 
                        pasteHtmlAtCaret('');
                        document.getElementById('rtfContenido').focus(); 
                        pasteHtmlAtCaret('<span class="textoImportante">{NombrePrograma}</span>&nbsp;'); 
                    },
                    ico: 'uploadc'
                },
                nombreConferencia: {
                    fn: function() {
                        document.getElementById('rtfContenido').focus(); 
                        pasteHtmlAtCaret('');
                        document.getElementById('rtfContenido').focus(); 
                        pasteHtmlAtCaret('<span class="textoImportante">{NombreConferencia}</span>&nbsp;'); 
                    },
                    ico: 'uploadb'
                },
                ponentes: {
                    fn: function() {
                        document.getElementById('rtfContenido').focus(); 
                        pasteHtmlAtCaret('');
                        pasteHtmlAtCaret('<span class="textoImportante">{Ponentes}</span>&nbsp;'); 
                    },
                    ico: 'uploadd'
                },
                moderadores: {
                    fn: function() {
                        document.getElementById('rtfContenido').focus(); 
                        pasteHtmlAtCaret('');
                        document.getElementById('rtfContenido').focus(); 
                        pasteHtmlAtCaret('<span class="textoImportante">{Moderadores}</span>&nbsp;'); 
                    },
                    ico: 'uploade'
                },
                duracion: {
                    fn: function() {
                        document.getElementById('rtfContenido').focus(); 
                        pasteHtmlAtCaret('');
                        document.getElementById('rtfContenido').focus(); 
                        pasteHtmlAtCaret('<span class="textoImportante">{Duracion}</span>&nbsp;'); 
                    },
                    ico: 'uploadf'
                },
                fechaPonencia: {
                    fn: function() {
                        document.getElementById('rtfContenido').focus(); 
                        pasteHtmlAtCaret('');
                        pasteHtmlAtCaret('<span class="textoImportante">{FechaPonencia}</span>&nbsp;'); 
                    },
                    ico: 'uploadg'
                },
                horaPonencia: {
                    fn: function() {
                        document.getElementById('rtfContenido').focus(); 
                        pasteHtmlAtCaret('');
                        document.getElementById('rtfContenido').focus(); 
                        pasteHtmlAtCaret('<span class="textoImportante">{HoraPonencia}</span>&nbsp;'); 
                    },
                    ico: 'uploadh'
                },
                enlaceModerador: {
                    fn: function() {
                        document.getElementById('rtfContenido').focus(); 
                        pasteHtmlAtCaret('');
                        document.getElementById('rtfContenido').focus(); 
                        pasteHtmlAtCaret('<span class="textoImportante">{EnlaceModerador}</span>&nbsp;'); 
                    },
                    ico: 'uploadi'
                },
                enlaceConferencia: {
                    fn: function() {
                        document.getElementById('rtfContenido').focus(); 
                        pasteHtmlAtCaret('');
                        document.getElementById('rtfContenido').focus(); 
                        pasteHtmlAtCaret('<span class="textoImportante">{EnlaceConferencia}</span>&nbsp;'); 
                    },
                    ico: 'uploadi'
                },
                contraseniaConferencia: {
                    fn: function() {
                        document.getElementById('rtfContenido').focus(); 
                        pasteHtmlAtCaret('');
                        document.getElementById('rtfContenido').focus(); 
                        pasteHtmlAtCaret('<span class="textoImportante">{ContraseñaConferenciaPrivada}</span>&nbsp;'); 
                    },
                    ico: 'uploadj'
                },
                
                tags: {
                    dropdown: ['nombrePrograma', 'nombreConferencia', 'ponentes', 'moderadores', 'duracion', 'fechaPonencia', 'horaPonencia', 'enlaceModerador', 'enlaceConferencia', 'contraseniaConferencia'],
                    ico: 'uploada'
                }
            },
            btns: [
                ['viewHTML'],
                ['undo', 'redo'], // Only supported in Blink browsers
                ['formatting'],
                ['strong', 'em', 'del'],
                ['superscript', 'subscript'],
                ['insertImage'],
                ['justifyLeft', 'justifyCenter', 'justifyRight', 'justifyFull'],
                ['unorderedList', 'orderedList'],
                ['horizontalRule'],
                ['removeformat'],
                ['tags'],
                ['fullscreen']
                
            ]
	});

	$('#rtfContenido2').trumbowyg({
	});

	$('#rtfContenido2').trumbowyg('disable');

});


function pasteHtmlAtCaret(html) {
    var sel, range;
    if (window.getSelection) {
        // IE9 and non-IE
        sel = window.getSelection();
        if (sel.getRangeAt && sel.rangeCount) {
            range = sel.getRangeAt(0);
            range.deleteContents();

            // Range.createContextualFragment() would be useful here but is
            // non-standard and not supported in all browsers (IE9, for one)
            var el = document.createElement("div");
            el.innerHTML = html;
            var frag = document.createDocumentFragment(), node, lastNode;
            while ( (node = el.firstChild) ) {
                lastNode = frag.appendChild(node);
            }
            range.insertNode(frag);
            
            // Preserve the selection
            if (lastNode) {
                range = range.cloneRange();
                range.setStartAfter(lastNode);
                range.collapse(true);
                sel.removeAllRanges();
                sel.addRange(range);
            }
        }
    } else if (document.selection && document.selection.type != "Control") {
        // IE < 9
        document.selection.createRange().pasteHTML(html);
    }
}