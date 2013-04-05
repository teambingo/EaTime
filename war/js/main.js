var isModalOn = false;


function createEvent(restaurant,hour,min,eventname,eventID){
	var br='<hr>';
	if($("*[value=" + "'" + restaurant + "'" + "]").next().children('.event').length === 0){
		br='';
	}
	var newEvent=$(br+'<div class="row-fluid event" eventid="'+eventID+'"><div class="span2 headDiv"><img src="'+ userImg +'" class="img-circle head"></div><div class="span2 orgDiv"><div class="label label-info">Organizer</div><div class="display">'+fullname+'</div></div><div class="span2 eNameDiv"><div class="label label-info">Event Name</div><div class="display">'+eventname+'</div></div><div class="span2 timeDiv"><div class="label label-info">Time</div><br><div class="hourNum">'+hour+'</div> : <div class="minNum">'+min+'</div></div><div class="span2 countDiv"><div class="label label-info">Attendants</div><div class="display">0</div></div><div class="span2 joinDiv"><button type="submit" class="btn btn-info join" onclick="invite(this)">Invite!</button></div>');
	//newEvent.hide();
	$("*[value=" + "'" + restaurant + "'" + "]").next().each(function(){
		$(this).append(newEvent.clone(true));
	});
	//alert('success');
	//newEvent.show(1000);
}

// function testClick(){
// 	createEvent('happy-china',11,10);
// }
function setCurrentDate(){
	$( "#datepicker" ).datepicker();
	var d=new Date();
	var month=d.getMonth()+1;
	var day=d.getDate();
	var year=d.getFullYear();
	if(month<10)
		month='0'+month.toString();
	if(day<10)
		day='0'+day.toString();
	year=year.toString();
	var date=month+'/'+day+'/'+year;
	$( "#datepicker" ).val(date);
}



//Invite button initialized
$(function(){
	$('#inviteBtn').click(function() {
		var invites = $('#inviteContent').val().split(/[\s,]+/);
		var eventId = $(this).data('eventid');
		var restaurant = $(this).data('restaurant');
		
		var url = "/event?";
			url += "action=invite";
			url += "&id=" + encodeURIComponent(eventId);
			url += "&restaurant=" + encodeURIComponent(restaurant);

			if ($("#inviteContent").val() !== '') {
				for (var i = 0;  i < invites.length; i++) {
					if (invites[i] !== '') {
						url += "&invite=" + encodeURIComponent(invites[i]);
					}
				}
			}

		console.log('invite url', url);

		//TODO AJAX TO CONNECT SERVER
		$.getJSON(url, function(data) {
			var status = data['status'];

			if (status === 0) {
				// succeed
				$('#new-invite-modal').modal('hide');
				setTimeout(function() {
					joinMsgPrompt('Invite success!');
				}, 500);

			} else {
				// failed
				var reason = data['reason'];
				inviteMsgPrompt(reason);
				console.log('invite failed', reason);
			}
		})
		.error(function() {
			// Error
			console.log('invite request failed.');
			inviteMsgPrompt('invite request failed.');
		});
	});
});

$(function() {

	//Initailize error for event, msg for success and failure
	$(".error").css("visibility","hidden");
	$(".msg").hide();
	$('.inviteMsg').hide();
	$('.createMsg').hide();
	//Initialize timepick and current date
	$('.timepick').timepicker({
		altField: '.timepick',
		defaultTime: '12:00'
	});
	setCurrentDate();


	//Initail tabs
	$('#cattabs a').click(function (e) {
		e.preventDefault();
		$(this).tab('show');
	});


	// accordion setup

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


	// modal function when clicking modal

	$('a[data-toggle="modal"]').click(function(obj){
		$("#create").data("restaurant",$(this).parent().attr('value'));
		isModalOn = true;
	});

	$('#new-event-modal').on('hidden', function() {
		isModalOn = false;
	});

	// Create Event click function
	$("#create").click(function(){
		var name = $("#event-name").val();
		var restaurant = $(this).data("restaurant");
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
			url += "&name=" + encodeURIComponent(name);
			url += "&restaurant=" + encodeURIComponent(restaurant);
			url += "&date=" + encodeURIComponent(date);

			if (typeof username !== 'undefined') {
				url += '&username=' + encodeURIComponent(username);
			}

			if ($("#event-invite").val() !== '') {
				for (var i = 0;  i < invites.length; i++) {
					if (invites[i] !== '') {
						url += "&invite=" + encodeURIComponent(invites[i]);
					}
				}
			}

			console.log('create url', url);

			$.getJSON(url, function(data) {
				var status = data['status'];

				if (status === 0) {
					// succeed
					var eventID = data['id'];
					console.log('created event id', eventID);

					// Add new event html using javascript
					createEvent(restaurant, hourandmin[0], hourandmin[1], name,eventID);
					$('#new-event-modal').modal('hide');
					setTimeout(function() {
						joinMsgPrompt("Successfully created!");
					}, 500);
				} else {
					// failed
					var reason = data['reason'];
					console.log('create failed', reason);
					createMsgPrompt(reason);
				}
			})
			.error(function() {
				// Error
				console.log('create request failed.');
				createMsgPrompt('create request failed.');
			});
		}
	});

});


//Join
function join(obj) {
	if($(obj).hasClass('disabled')){
		return;
	}
	var restaurant = $(obj).parent().parent().parent().prev().attr('value');
	var eventID = $(obj).parent().parent().attr('eventid');

	var url = "/event?";
		url += "action=join";
		url += "&id=" + encodeURIComponent(eventID);
		url += '&restaurant=' + encodeURIComponent(restaurant);
		url += "&username=" + encodeURIComponent(username);

	console.log('join url', url);

	$.getJSON(url, function(data) {
		var status = data['status'];

		if (status === 0) {
			// succeed
			console.log('join success');
			joinMsgPrompt('Join success!');
			$('*[eventid='+ eventID +']').children('.joinDiv').children().addClass('disabled');
			// TODO add attendents
			var attendents_count = $(obj).parent().prev().children('.display').text();
			attendents_count = parseInt(attendents_count, 10);
			attendents_count += 1;
			$(obj).parent().prev().children('.display').text(attendents_count);
		} else {
			// failed
			var reason = data['reason'];
			console.log('join failed', reason);
			joinMsgPrompt(reason);
		}
	})
	.error(function() {
		// Error
		console.log('join request failed.');
		joinMsgPrompt('join request failed.');
	});
}


//Join Reload
function joinReload(obj) {
	if($(obj).hasClass('disabled')){
		return;
	}
	var restaurant = $(obj).parent().parent().parent().prev().attr('value');
	var eventID = $(obj).parent().parent().attr('eventid');

	var url = "/event?";
		url += "action=join";
		url += "&id=" + encodeURIComponent(eventID);
		url += '&restaurant=' + encodeURIComponent(restaurant);
		url += "&username=" + encodeURIComponent(username);

	console.log('join url', url);

	$.getJSON(url, function(data) {
		var status = data['status'];

		if (status === 0) {
			// succeed
			console.log('join success');
			joinMsgPrompt('Join success!');
			// $('*[eventid='+ eventID +']').children('.joinDiv').children().addClass('disabled');
			// // TODO add attendents
			// var attendents_count = $(obj).parent().prev().children('.display').text();
			// attendents_count = parseInt(attendents_count, 10);
			// attendents_count += 1;
			// $(obj).parent().prev().children('.display').text(attendents_count);
			location.reload();
		} else {
			// failed
			var reason = data['reason'];
			console.log('join failed', reason);
			joinMsgPrompt(reason);
		}
	})
	.error(function() {
		// Error
		console.log('join request failed.');
		joinMsgPrompt('join request failed.');
	});
}


//success msg display
function joinMsgPrompt(msg){
	$('#msg').text(msg);
	$('.msg').show();
	setTimeout(function(){
		$('.msg').hide('fade',1000);
	},500);

}
//failure msg display
function inviteMsgPrompt(msg){
	$('#inviteMsg').text(msg);
	$('.inviteMsg').show();
	setTimeout(function(){
		$('.inviteMsg').hide('fade',1000);
	},500);
}

//failure msg display for creation
function createMsgPrompt(msg){
	$('#createMsg').text(msg);
	$('.createMsg').show();
	setTimeout(function(){
		$('.createMsg').hide('fade',1000);
	},500);
}

function invite(obj) {
	$('#new-invite-modal').modal('show');
	var restaurant = $(obj).parent().parent().parent().prev().attr('value');
	var eventID = $(obj).parent().parent().attr('eventid');
	$('#inviteBtn').data('eventid',eventID);
	$('#inviteBtn').data('restaurant',restaurant);
}


// Add attendants inspect function
$(function(){
	$('.countDiv').children('.display').popover({'placement':'bottom','trigger':'hover'});
});