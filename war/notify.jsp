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
<%@ page import="com.google.appengine.labs.repackaged.org.json.JSONArray"%>
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
<script src="js/jquery-1.9.1.js"></script>

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
		boolean isLogin=true;
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
				isLogin=false;
			}
		%>
		<div class="alert alert-block msg">
			<h1 id='msg'>test</h1>
		</div>
		<div class="container">
			<div class="top">
				<div class="logout">
					<a href="logout">Log out</a>
				</div>
				<div class="topTag">
					<a href="notify.jsp">Notification</a>
				</div>
				<div class="topTag" id="events">
					<a href="events.jsp">Events</a>
				</div>
				<div class="topTag" id="profile">
					<a href="eatime">Profile</a>
				</div>
				<div class="topTag" id="home">
					<a href="eatime">Home</a>
				</div>
			</div>
			<div class="down">
				<div class="description">Here are all your invitations!</div>

				<%
				if(isLogin){
					HashSet<Key> unreadKeys = new HashSet<Key>();
					for (Event event : unreadEvents) {
						unreadKeys.add(event.getKey());
					}

					for (Event event : inviteEvents) {
				%>
				<div class='unreadEvents'>
					<div class="restaurantName"
						value="<%=event.getRestaurantKey().getName()%>">
						<p class="restaurant"><%=RestaurantManager.getRestaurant(event.getRestaurantKey()).getName()%></p>

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
								<%
										TreeSet<Person> joins = event.getJoins();
										JSONArray joinsArray = new JSONArray();
										if (joins != null) {
											for (Person person : joins) {
												joinsArray.put(person.getFullName(true));
											}
										}
								%>
								<div class="display" data-content='<%= joinsArray %>'><%=event.getJoins() != null ? event.getJoins().size() : 0%></div>
							</div>
							<div class="span2 joinDiv">

								<button type="submit"
									class="btn btn-info join<%=EventManager.isJoined(me.getKey(), event.getKey()) ? " disabled" : ""%>"
									onclick="join(this)" value="join">Join!</button>
							</div>
						</div>
					</div>
				</div>
				<%
					}
					PersonManager.addReadEvents(unreadKeys, me.getKey());
				}
				%>



			</div>
		</div>
	</div>
</body>
</html>