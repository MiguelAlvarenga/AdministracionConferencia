function filtrar(numero){
            
            $('tbody').attr('class','searchable');
            $('#tablePaging').hide();     
            
            var rex = new RegExp($('#filter').val(), 'i');
            $('.searchable tr').hide();
            $('.searchable tr').filter(function () {
                return rex.test($(this).text());
            }).show();
           
            if($('#filter').val() === ""){
                $('#tablePaging').show();
                load(numero);
            }
};


function filtrarP(numero){
            
            $('tbody').attr('class','searchable');
          
            var rex = new RegExp($('#filterP').val(), 'i');
            $('.tablaP .searchable tr').hide();
            $('.tablaP .searchable tr').filter(function () {
                return rex.test($(this).text());
            }).show();
           
           
};



function filtrarPl(numero){
            
            $('tbody').attr('class','searchable');
           $('#tablePaging').hide();   
           
            var rex = new RegExp($('#filterPl').val(), 'i');
            $('.tablaPl .searchable tr').hide();
            $('.tablaPl .searchable tr').filter(function () {
                return rex.test($(this).text());
            }).show();
            
           if($('#filterPl').val() === ""){
                $('#tablePaging').show();
                load(numero);
            }
           
};

function filtrarTPl(numero){
            
            $('tbody').attr('class','searchable');
          
            var rex = new RegExp($('#filterTPl').val(), 'i');
            $('.tablaTPl .searchable tr').hide();
            $('.tablaTPl .searchable tr').filter(function () {
                return rex.test($(this).text());
            }).show();
           
           
};

function filtrarUsuNo(numero){
            
            $('tbody').attr('class','searchable');
          
            var rex = new RegExp($('#filterUsuNo').val(), 'i');
            $('.tablaUsuNo .searchable tr').hide();
            $('.tablaUsuNo .searchable tr').filter(function () {
                return rex.test($(this).text());
            }).show();
           
           
};


function filtrarUsuSi(numero){
            
            $('tbody').attr('class','searchable');
          
            var rex = new RegExp($('#filterUsuSi').val(), 'i');
            $('.tablaUsuSi .searchable tr').hide();
            $('.tablaUsuSi .searchable tr').filter(function () {
                return rex.test($(this).text());
            }).show();
           
           
};


function load(numero){
    $('tbody').attr('class','searchable');
    $('.searchable tr').attr('class','data');
    
window.tp = new Pagination('#tablePaging', {
					itemsCount: numero,
					
					onPageChange: function (paging) {
						//custom paging logic here
					
						var start = paging.pageSize * (paging.currentPage - 1),
							end = start + paging.pageSize,
							$rows = $('.paginacion').find('.data');

						$rows.hide();

						for (var i = start; i < end; i++) {
							$rows.eq(i).show();
						}
						
							}
				});
};


function loadInsert(data,numero){
    $('tbody').attr('class','searchable');
    $('.searchable tr').attr('class','data');
    
window.tp = new Pagination('#tablePaging', {
					itemsCount: numero+1,
					
					onPageChange: function (paging) {
						//custom paging logic here
					
						var start = paging.pageSize * (paging.currentPage - 1),
							end = start + paging.pageSize,
							$rows = $('.paginacion').find('.data');

						$rows.hide();

						for (var i = start; i < end; i++) {
							$rows.eq(i).show();
						}
						
							}
				});
};

function loadDelete(data,numero){
    $('tbody').attr('class','searchable');
    $('.searchable tr').attr('class','data');
    
window.tp = new Pagination('#tablePaging', {
					itemsCount: numero-1,
					
					onPageChange: function (paging) {
						//custom paging logic here
					
						var start = paging.pageSize * (paging.currentPage - 1),
							end = start + paging.pageSize,
							$rows = $('.paginacion').find('.data');

						$rows.hide();

						for (var i = start; i < end; i++) {
							$rows.eq(i).show();
						}
						
							}
				});
};
