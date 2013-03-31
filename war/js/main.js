var isModalOn = false;

$(function() {
	$(".error").css("visibility","hidden");

	$('.timepick').timepicker({
		altField: '.timepick',
		defaultTime: '12:00'
	});

	$('#myTab a').click(function (e) {
		e.preventDefault();
		$(this).tab('show');
	});

	$('.accordion').accordion({
		header: ".restaurant-header",
		collapsible: true,
		heightStyle: "content",
		active: false,
		beforeActivate: function(event, ui) {
			if(isModalOn) {
				event.preventDefault();
			}
		}
	});

	$('a[data-toggle="modal"]').click(function(obj){
		$("#create").data("restaurant",$(this).prev().attr("id"));
		isModalOn = true;
	});

	$('#new-event-modal').on('hidden', function() {
		isModalOn = false;
	});

	$("#create").click(function(){
		var name = $("#event-name").val();

		var time = $(".timepick").timepicker('getTime');
		var hourandmin = time.split(":");
		var d = new Date();
		d.setHours(hourandmin[0]);
		d.setMinutes(hourandmin[1]);
		d.setSeconds(0);
		d.setMilliseconds(0);
		var date = d.getTime();

		var invites = $("#event-invite").val().split(/[\s,]+/);
		for (var i = 0;  i < invites.length; i++) {
			console.log(invites[i]);
		}

		if (name === '') {
			$("#event-name").css("border","1px solid #C83135");
			$(".error").css("visibility","visible");
		} else {
			var url = "event?";
			url += "action=add";
			url += "&name=" + name;
			url += "&restaurant=" + $(this).data("restaurant");
			url += "&date=" + date;
			if ($("#event-invite").val() !== '') {
				for (i = 0;  i < invites.length; i++) {
					url += "&invite=" + invites[i];
				}
			}



			window.location.href = "event";
		}
	});
});