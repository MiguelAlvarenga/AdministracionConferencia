/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


$(document).ready(function () {    
    $(".container-fluid").on("click", "#liConfPlantilla", function () {
		$("#liConfTipoPlantilla").removeClass("active");
		$("#divConfCorreo").addClass("hidden");
                
                $("#liConfPDF").removeClass("active");
		$("#idConfPDFBBB").addClass("hidden");
		
                $("#liConfPlantilla").addClass("active");
		$("#idConfBBB").removeClass("hidden");
                
                
                
	});
        

	$(".container-fluid").on("click", "#liConfTipoPlantilla", function () {
		$("#liConfPlantilla").removeClass("active");
		$("#idConfBBB").addClass("hidden");
                
                $("#liConfPDF").removeClass("active");
		$("#idConfPDFBBB").addClass("hidden");

                $("#liConfTipoPlantilla").addClass("active");
		$("#divConfCorreo").removeClass("hidden");
	});
        
        $(".container-fluid").on("click", "#liConfPDF", function () {
		$("#liConfPlantilla").removeClass("active");
		$("#idConfBBB").addClass("hidden");
                
                $("#liConfTipoPlantilla").removeClass("active");
		$("#divConfCorreo").addClass("hidden");
            
            
                $("#liConfPDF").addClass("active");
		$("#idConfPDFBBB").removeClass("hidden");

	});
    
});