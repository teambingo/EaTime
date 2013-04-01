var isModalOn = false;


// TODO CHANGE img
// TODO display user's full name instead of username.
function createEvent(restaurant,hour,min){
	var br='<hr>';
	if($("*[value="+restaurant+"]").next().children('.event').length === 0){
		br='';
	}
	var newEvent=$(br+'<div class="row-fluid event" id=1><div class="span2 headDiv"><img src="http://www.gravatar.com/avatar/7c46aa86b25a0d1e343affd790e10700.jpg?s=100>" class="img-circle head"></div><div class="span3 orgDiv"><div class="label label-info">Organizer</div><div class="display">'+username+'</div></div><div class="span3 timeDiv"><div class="label label-info">Time</div><br><div class="hourNum">'+hour+'</div>:<div class="minNum">'+min+'</div></div><div class="span2 countDiv"><div class="label label-info">Attendants</div><div class="display">0</div></div><div class="span2 joinDiv"><button type="submit" class="btn btn-info join">Join!</button></div>');
	//newEvent.hide();
	$("*[value="+restaurant+"]").next().each(function(){
		$(this).append(newEvent.clone(true));
	});
	//alert('success');
	//newEvent.show(1000);
}

function testClick(){
	createEvent('happy-china',11,10);
}

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
		$("#create").data("restaurant",$(this).parent().attr('value'));
		isModalOn = true;
	});

	$('#new-event-modal').on('hidden', function() {
		isModalOn = false;
	});

	$("#create").click(function(){
		var name = $("#event-name").val();
		var restaurant=$(this).data("restaurant");
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
			url += "&restaurant=" + restaurant;
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

					if (status === 0) {
						// succeed
						//location.reload();
						createEvent(restaurant,hourandmin[0],hourandmin[1]);
						$('#new-event-modal').modal('hide');
					} else {
						// failed
						// TODO display error message
						var reason = response['reason'];
						console.log('create event failed', reason);
						alert("succeed");
						$('#new-event-modal').modal('hide');
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