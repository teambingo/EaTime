<!DOCTYPE html>
<html>
<head>

<%@ page import="com.bingo.eatime.core.Category"%>
<%@ page import="com.bingo.eatime.core.CategoryManager"%>
<%@ page import="com.bingo.eatime.core.Event" %>
<%@ page import="com.bingo.eatime.core.EventManager" %>
<%@ page import="com.bingo.eatime.core.Restaurant"%>
<%@ page import="com.bingo.eatime.core.Utilities"%>
<%@ page import="com.google.appengine.api.datastore.*,java.util.*"%>

<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0">

<title>EaTime</title>

<link rel="stylesheet" href="css/ui-lightness/jquery-ui-1.10.1.custom.css" />
<link rel="stylesheet" href="css/main.css" />
<link rel="stylesheet" href="css/bootstrap.css" />
<!-- <link rel="stylesheet" href="css/jquery.ui.timepicker.css" /> -->
<link rel="stylesheet" href="css/timePicker.css" />

<script src="js/jquery-1.9.1.js"></script>
<script src="js/jquery-ui-1.10.1.custom.js"></script>
<script src="js/bootstrap.js"></script>
<!-- <script src="js/jquery.ui.timepicker.js"></script> -->
<script src="js/jquery.timePicker.js"></script>
<script src="js/main.js"></script>

</head>

<body>
	<div class="page">
		<div class="container">
			<div class="top">Hi,${user}!!</div>
			<div class="down">
				<ul class="nav nav-tabs" id="myTab">
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
				</ul>

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
							<div class="restaurant-header">
								<p class="restaurant" id="<%=restaurant.getKey().getName()%>"><%=restaurant.getName()%></p>
								<a href="#new-event-modal" role="button" class="btn" data-toggle="modal">Create
									New Event</a>
							</div>
							<div class="events">
						        <% 
						        	TreeSet<Event> events = EventManager.getEventsFromRestaurant(restaurant.getKey());
						        	if (events != null) {
						        		Iterator<Event> iter = events.iterator();
										while(iter.hasNext()) {
											Event event = iter.next();
						        %>
						        <div class="row-fluid event">
						        	<div class="span2 headDiv"><img src="<%=event.getCreator().getGravatarUrlString()%>>" class="img-circle head"></div>
						  			<div class="span3 orgDiv">
						            	<div class="label label-info">Organizer</div>
						            	<div class="display"><%=event.getCreator().getFullName(true)%></div>
						            </div>
						  			<div class="span3 timeDiv">
						            	<div class="label label-info">Time</div>
						            	<br>
						                	<div class="hourNum"><%=Utilities.getDateHourString(event.getTime())%></div>:
						                	<div class="minNum"><%=Utilities.getDateMinString(event.getTime())%></div>
						                
						            </div>
						            <div class="span2 countDiv">
						            	<div class="label label-info">Attendants</div>
						            	<div class="display"><%=event.getInvites().size()%></div>
						            </div>
						            <div class="span2 joinDiv">
						            	<button type="submit" class="btn btn-info join">Join!</button>
						            </div>
								</div>
								<%
											if(iter.hasNext()) {
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
					<div class="tab-pane" id="profile">bbbb</div>
					<div class="tab-pane" id="messages">cccc</div>
					<div class="tab-pane" id="settings">dddd</div>
				</div>
			</div>
		</div>
	</div>
	<!-- Modal -->
	<div id="new-event-modal" class="modal hide fade" tabindex="-1" role="dialog" aria-labelledby="new-event-modal-label" aria-hidden="true">
	  <div class="modal-header">
	    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">x</button>
	    <h3 id="new-event-modal-label">Create New Event!</h3>
	  </div>
	  <div class="modal-body">
	  	<!-- <div class="timepick"></div> -->
	    <br>
	    <span class="label label-warning">Invite:</span>&nbsp;&nbsp;<input type="text" id="invite" placeholder="Use , to separate username">
	  </div>
	  <div class="modal-footer">
	    <button class="btn" data-dismiss="modal" aria-hidden="true">Close</button>
	    <button class="btn btn-primary">Save changes</button>
	  </div>
	</div>

</body>
</html>
