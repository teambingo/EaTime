<!DOCTYPE html>
<html>
<head>
<meta name="generator"
	content="HTML Tidy for HTML5 (experimental) for Windows https://github.com/w3c/tidy-html5/tree/c63cc39">
<%@ page import="com.bingo.eatime.core.Category"%>
<%@ page import="com.bingo.eatime.core.CategoryManager"%>
<%@ page import="com.bingo.eatime.core.Event"%>
<%@ page import="com.bingo.eatime.core.EventManager"%>
<%@ page import="com.bingo.eatime.core.Restaurant"%>
<%@ page import="com.bingo.eatime.core.RestaurantManager"%>
<%@ page import="com.bingo.eatime.core.Utilities"%>
<%@ page import="com.bingo.eatime.core.Person"%>
<%@ page import="com.bingo.eatime.core.PersonManager"%>
<%@ page import="com.google.appengine.api.datastore.*,java.util.*"%>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>EaTime</title>
<link rel="stylesheet"
	href="css/ui-lightness/jquery-ui-1.10.1.custom.css" type="text/css">
<link rel="stylesheet" href="css/bootstrap.css" type="text/css">
<!-- <link rel="stylesheet" href="css/jquery.ui.timepicker.css" /> -->
<link rel="stylesheet" href="css/timePicker.css" type="text/css">
<link rel="stylesheet" href="css/main.css" type="text/css">
<link rel="stylesheet" href="css/notify.css" type="text/css">
<script src="//ajax.googleapis.com/ajax/libs/jquery/1.9.1/jquery.js"
	type="text/javascript">
	
</script>
<script>
	// Makes session username accessible via javascript
	var username = "<%=request.getSession().getAttribute("user")%>";
	var userImg="<%=request.getSession().getAttribute("userImg")%>";
	var fullname="<%=request.getSession().getAttribute("fullname")%>";
</script>
<!-- <script src="js/jquery-1.9.1.js"></script> -->

<script src="js/jquery-ui-1.10.1.custom.js" type="text/javascript">
	
</script>
<script src="js/bootstrap.js" type="text/javascript">
	
</script>
<!-- <script src="js/jquery.mb.browser.js"></script> -->
<!-- <script src="js/jquery.timePicker.js"></script> -->

<script src="js/jquery.ui.timepicker.js" type="text/javascript">
	
</script>
<script src="js/main.js" type="text/javascript">
	
</script>
</head>
<body>
	<div class="page">
		<%
			TreeSet<Event> unreadEvents = null;
			TreeSet<Event> inviteEvents = null;
			String username = (String) request.getSession().getAttribute("user");
			Person me = null;
			if (username != null) {
				me = PersonManager.getPersonByUsername(username);
				unreadEvents = PersonManager.getInviteEvents(me.getKey(), true);
				inviteEvents = PersonManager.getInviteEvents(me.getKey(), false);
				if (unreadEvents == null)
					unreadEvents = new TreeSet<Event>();
				if (inviteEvents == null)
					inviteEvents = new TreeSet<Event>();
			} else {
				response.sendRedirect("/login.jsp");
			}
		%>
		<div class="container">
			<div class="top">
				<div class="logout">Log out</div>
				<div class="topTag">Notification</div>
				<div class="topTag">Events</div>
				<div class="topTag">Profile</div>
			</div>
			<div class="down">
				<div class="description">Here are all your invitations!</div>

				<div class="accordion" id="Notification">
					<div id='unread'>Unread</div>
					<%
						TreeSet<Key> unreadKeys = new TreeSet<Key>();
						Iterator<Event> iterUnread = unreadEvents.iterator();
						while (iterUnread.hasNext()) {
							Event event = iterUnread.next();
							unreadKeys.add(event.getKey());
						}
						Iterator<Event> iter = inviteEvents.iterator();
						while (iter.hasNext()) {
							Event event = iter.next();
					%>
					<div class='unreadEvents'>
					<div class="restaurant-header"
						value="<%=event.getRestaurantKey().getName()%>">
						<p class="restaurant"><%=RestaurantManager.getRestaurant(event.getRestaurantKey()).getName()%></p>
						<a href="#new-event-modal" role="button" class="btn"
							data-toggle="modal">Create New Event</a>
					</div>
					<div class="events">
						<div
							class="row-fluid event<%=unreadKeys.contains(event.getKey()) ? " unread" : ""%>"
							eventid="<%=event.getKey().getId()%>">
							<div class="span2 headDiv">
								<img src="<%=event.getCreator().getGravatarUrlString()%>"
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

								<button type="submit"
									class="btn btn-info join<%=EventManager.isJoined(me.getKey(), event.getKey()) ? " disabled" : ""%>"
									onclick="join(this)" value="join">Join!</button>
							</div>
						</div>
					</div>

					<%
						}
						PersonManager.addReadEvents(unreadKeys, me.getKey());
					%>
				</div>
				</div>

			</div>
		</div>
	</div>
</body>
</html>