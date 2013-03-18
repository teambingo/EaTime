var isModalOn=false;
$(function(){
	$('#myTab a').click(function (e) {
  		e.preventDefault();
  		$(this).tab('show');
	});
	$('.accordion').accordion({ 
		header: ".test" ,
		collapsible: true ,
		heightStyle: "content" ,
		active: false ,
		beforeActivate: function(event, ui){
			if(isModalOn){
				event.preventDefault();
			}
		}
	});
	$('a[data-toggle="modal"]').click(function(event){
		isModalOn=true;
	});
	$('#myModal').on('hidden', function () {
  		isModalOn=false;
	});
});