$(function(){
	$("#signin").click(function(){
		var name=$('input[name="user"]').val();
		var pwd=$('input[name="pwd"]').val();
		$.post('/login', { 'user': name, 'pwd': pwd } , function(data) {
			if(data[0]=="1"){
				window.location.replace("/EaTime");
			}else{
				alert('Wrong');
			}
		});
	});	
});