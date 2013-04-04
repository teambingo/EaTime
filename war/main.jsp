<!DOCTYPE html>
<html>
<head>

<%@ page import="com.bingo.eatime.core.Category"%>
<%@ page import="com.bingo.eatime.core.CategoryManager"%>
<%@ page import="com.bingo.eatime.core.Event"%>
<%@ page import="com.bingo.eatime.core.EventManager"%>
<%@ page import="com.bingo.eatime.core.Restaurant"%>
<%@ page import="com.bingo.eatime.core.Utilities"%>
<%@ page import="com.bingo.eatime.core.Person"%>
<%@ page import="com.bingo.eatime.core.PersonManager"%>
<%@ page import="com.google.appengine.api.datastore.*,java.util.*"%>

<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0">

<title>EaTime</title>

<link rel="stylesheet"
	href="css/ui-lightness/jquery-ui-1.10.1.custom.css" />
<link rel="stylesheet" href="css/bootstrap.css" />
<!-- <link rel="stylesheet" href="css/jquery.ui.timepicker.css" /> -->
<link rel="stylesheet" href="css/timePicker.css" />
<link rel="stylesheet" href="css/main.css" />

<script src="//ajax.googleapis.com/ajax/libs/jquery/1.9.1/jquery.js"></script>
<!-- <script src="js/jquery-1.9.1.js"></script> -->
<script src="js/jquery-ui-1.10.1.custom.js"></script>
<script src="js/bootstrap.js"></script>
<!-- <script src="js/jquery.mb.browser.js"></script> -->
<!-- <script src="js/jquery.timePicker.js"></script> -->
<script src="js/jquery.ui.timepicker.js"></script>
<script src="js/main.js"></script>
<script>
	// Makes session username accessible via javascript
	var username = '${user}';
	var userImg="<%=request.getSession().getAttribute("userImg")%>";
	var fullname="<%=request.getSession().getAttribute("fullname")%>";
</script>
</head>

<body>
	<div class="page">
		<div class="alert alert-block msg">
			<h1 id='msg'>test</h1>
		</div>
		<div class="container">
			<div class="top">
				Hi,${user}!!
				<%
				String username = (String) request.getSession().getAttribute("user");
				Person me = null;
				if (username != null) {
					me = PersonManager.getPersonByUsername(username);
				}
			%>

				<div class="logout">
					<a href="logout">Log out</a>
				</div>
				<div class="topTag" id="notification">
					<a href="profile.html">Notification</a>
					<div class="alert">
						<button type="button" class="close" data-dismiss="alert">&times;</button>
						You have 3 invitations
					</div>
				</div>
				<div class="topTag" id="events">Events</div>
				<div class="topTag" id="profile">Profile</div>
			</div>
			<div class="down">
				<ul class="nav nav-tabs" id="cattabs">
					<%
						TreeSet<Category> categories = CategoryManager.getAllCategories();
						if (categories != null) {
							for (Category cat : categories) {
					%>
					<li><a href=<%="#" + cat.getName()%>><%=cat.getName()%></a></li>
					<%
						}
						}
					%>
					<div class='date'>
						Date: <input type="text" id="datepicker" value="" />
					</div>
				</ul>

				<!-- restaurant body -->
				<div class="tab-content">
					<%
						if (categories != null) {
							for (Category cat : categories) {
					%>
					<div class="tab-pane" id="<%=cat.getName()%>">
						<div class="accordion">
							<%
								TreeSet<Restaurant> restaurants = CategoryManager.getRestaurantsFromCategory(cat.getKey());
										if (restaurants != null) {
											for (Restaurant restaurant : restaurants) {
							%>
							<div class="restaurant-header"
								value="<%=restaurant.getKey().getName()%>">
								<p class="restaurant"><%=restaurant.getName()%></p>
								<a href="#new-event-modal" role="button" class="btn"
									data-toggle="modal">Create New Event</a>
							</div>
							<div class="events">
								<%
									TreeSet<Event> events = EventManager.getEventsFromRestaurant(restaurant.getKey());
													if (events != null) {
														Iterator<Event> iter = events.iterator();
														while (iter.hasNext()) {
															Event event = iter.next();
								%>
								<div class="row-fluid event" eventid=<%=event.getKey().getId()%>>
									<div class="span2 headDiv">
										<img src="<%=event.getCreator().getGravatarUrlString()%>>"
											class="img-circle head">
									</div>
									<div class="span2 orgDiv">
										<div class="label label-info">Organizer</div>
										<div class="display"><%=event.getCreator().getFullName(true)%></div>
									</div>
									<div class="span2 eNameDiv">
										<div class="label label-info">Event Name</div>
										<div class="display"><%=event.getName()%></div>
									</div>
									<div class="span2 timeDiv">
										<div class="label label-info">Time</div>
										<br>
										<div class="hourNum"><%=Utilities.getDateHourString(event.getTime())%></div>
										:
										<div class="minNum"><%=Utilities.getDateMinString(event.getTime())%></div>
									</div>
									<div class="span2 countDiv">
										<div class="label label-info">Attendants</div>
										<div class="display"><%=event.getJoins() != null ? event.getJoins().size() : 0%></div>
									</div>
									<div class="span2 joinDiv">
										<%
											if (event.getCreator().getUsername()
																			.equals(request.getSession().getAttribute("user"))) {
										%>
										<button type="submit" class="btn btn-info join"
											onclick="invite(this)" value="invite">Invite!</button>
										<%
											} else {
																		// System.out.println(me.getKey());
																		// System.out.println(event.getKey());
																		boolean isJoined = false;
																		if (me != null) {
																			isJoined = EventManager.isJoined(me.getKey(), event.getKey());
																		}

																		if (isJoined) {
										%>
										<button type="submit" class="btn btn-info disabled join"
											value="join">Join!</button>
										<%
											} else {
										%>
										<button type="submit" class="btn btn-info join"
											onclick="join(this)" value="join">Join!</button>
										<%
											}
																	}
										%>
									</div>
								</div>
								<%
									if (iter.hasNext()) {
								%>
								<hr>
								<%
									}
														}
													}
								%>
							</div>
							<%
								}
										}
							%>
						</div>
					</div>
					<%
						}
						}
					%>
					<!-- restaurant body end -->


				</div>
			</div>
		</div>
	</div>

	<!-- new event modal -->
	<div id="new-event-modal" class="modal hide fade" tabindex="-1"
		role="dialog" aria-labelledby="new-event-modal-label"
		aria-hidden="true">
		<div class="modal-header">
			<button type="button" class="close" data-dismiss="modal"
				aria-hidden="true">x</button>
			<h3 id="new-event-modal-label">New Event</h3>
		</div>
		<div class="modal-body">
			<table class="new-event-table">
				<tbody>
					<tr>
						<td><span class="table-label label">Name:</span></td>
						<td><input type="text" id="event-name" class="modal-input"
							placeholder="Event name"><span
							class="alert alert-error error">Please Enter Event Name</span></td>
					</tr>
					<tr>
						<td><span class="table-label label">Time:</span></td>
						<td><div class="timepick"></div></td>
						<!-- <td><input type="text" id="event-timepicker" class="modal-input" placeholder="Pick a time"></td> -->
					</tr>
					<tr>
						<td><span class="table-label label">Invite:</span></td>
						<td><input type="text" id="event-invite" class="modal-input"
							placeholder="Use , to separate username"></td>
					</tr>
				</tbody>
			</table>
			<!-- <div class="timepick"></div> -->
		</div>
		<div class="modal-footer">
			<button class="btn" data-dismiss="modal" aria-hidden="true">Close</button>
			<button class="btn btn-primary" id="create">Create</button>
		</div>
	</div>
	<!-- new event modal end -->

	<div id="new-invite-modal" class="modal hide fade" tabindex="-1"
		role="dialog" aria-labelledby="new-event-modal-label"
		aria-hidden="true">
		<div class="modal-header">
			<button type="button" class="close" data-dismiss="modal"
				aria-hidden="true">x</button>
			<h3 id="new-invite-modal-label">Who you want invite?</h3>
		</div>
		<div class="modal-body">
			<input type="text" id="inviteContent" class="modal-input"
				placeholder="Use , to separate username">
			<div class="alert alert-block inviteMsg">
				<h1 id='inviteMsg'>test</h1>
			</div>
		</div>
		<div class="modal-footer">
			<button class="btn" data-dismiss="modal" aria-hidden="true">Close</button>
			<button class="btn btn-primary" id="inviteBtn">Invite</button>
		</div>
	</div>
</body>
</html>
