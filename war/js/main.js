var isModalOn = false;

$(function() {

	// $('.timepick').timepicker({
	// 	altField: '.timepick',
	// 	defaultTime: '9:20'
	// });

	// $("#event-time").timePicker({
	// 	startTime: "12.00",
	// 	show24Hours: false,
	// 	separator: '.',
	// 	step: 15
	// });
	
	$("#event-time").timePicker();

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

	$('a[data-toggle="modal"]').click(function(event) {
		isModalOn = true;
	});

	$('#new-event-modal').on('hidden', function() {
		isModalOn = false;
	});

});