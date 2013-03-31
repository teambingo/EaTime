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
			$("#event-name").css("border", "1px solid #C83135");
			$(".error").css("visibility", "visible");
		} else {
			$('#event-name').css('border', '');
			$('.error').css('visibility', 'hidden');

			var url = "/event?";
			url += "action=add";
			url += "&name=" + name;
			url += "&restaurant=" + $(this).data("restaurant");
			url += "&date=" + date;

			if (typeof username !== 'undefined') {
				url += '&username=' + username;
			}

			if ($("#event-invite").val() !== '') {
				for (var i = 0;  i < invites.length; i++) {
					if (invites[i] !== '') {
						url += "&invite=" + invites[i];
					}
				}
			}

			console.log('url', url);

			var req = new XMLHttpRequest();
			req.onreadystatechange = function() {
				if (req.readyState == 4 && req.status == 200) {
					var response = $.parseJSON(req.responseText);
					var status = response['status'];
					console.log('response', response);

					if (status > 0) {
						// succeed
						location.reload();
					} else {
						// failed
						// TODO display error message
						var reason = response['reason'];
						console.log('create event failed', reason);
					}
				} else if (req.readyState == 4 && req.status == 500) {
					// Internal Server Error
					// TODO deal with it
				}
			};
			req.open('GET', url);
			req.send();
		}
	});

});