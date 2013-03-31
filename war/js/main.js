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
		console.log('invites', invites);

		if (name === '') {
			$("#event-name").css("border","1px solid #C83135");
			$(".error").css("visibility","visible");
		} else {
			var url = "/event?";
			url += "action=add";
			url += "&name=" + name;
			url += "&restaurant=" + $(this).data("restaurant");
			url += "&date=" + date;

			if (typeof username !== 'undefined') {
				url += '&username=' + username;
			}

			if (invites) {
				for (var i = 0;  i < invites.length; i++) {
					url += "&invite=" + invites[i];
				}
			}

			console.log('url', url);

			var req = new XMLHttpRequest();

			req.onreadystatechange = function() {
				if (req.readyState == 4 && req.status == 200) {
					var response = parseInt(req.responseText, 10);
					console.log('response', response);

					if (response > 0) {
						// succeed

						location.reload();
					} else {
						// failed

						console.log('create event failed.');
					}
				}
			};
			req.open('GET', url);
			req.send();
		}
	});

});