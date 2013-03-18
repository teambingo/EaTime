$(function(){
	$('.alert').hide();
	$("#signin").click(function(){
		error();
		var name=$('input[name="user"]').val();
		var pwd=$('input[name="pwd"]').val();
		$.post('/login', { 'user': name, 'pwd': pwd } , function(data) {
			if(data[0]=="1"){
				window.location.replace("/eatime");
			}else{
				error();
			}
		});
	});	
});

function error(){
		$('.alert').show("slide", {direction: "up" }, 1000);
		setTimeout('$(\'.alert\').hide("fade", 1000);', 2000);
}