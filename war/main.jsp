<!DOCTYPE html>
<%@page import="com.bingo.eatime.core.CategoryManager"%>
<html>
<head>

<%@ page import="com.bingo.eatime.core.Category"%>
<%@ page import="com.bingo.eatime.core.CategoryManager"%>
<%@ page import="com.bingo.eatime.core.Restaurant"%>
<%@ page import="com.google.appengine.api.datastore.*,java.util.*"%>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>EaTime</title>
<link rel="stylesheet"
	href="./css/ui-lightness/jquery-ui-1.10.1.custom.css" />
<link rel="stylesheet" href="css/main.css" />
<link rel="stylesheet" href="bootstrap/css/bootstrap.css" />

<script src="jQuery/js/jquery-1.9.1.js"></script>
<script src="jQuery/js/jquery-ui-1.10.1.custom.js"></script>
<script src="bootstrap/js/bootstrap.js"></script>
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
								TreeSet<Restaurant> restaurants = CategoryManager
									.getRestaurantsFromCategory(cat.getKey());
								if (restaurants != null) {
									for (Restaurant restaurant : restaurants) {
							%>
							<div class="test">
								<p class="restaurant"><%=restaurant.getName()%></p>
								<a href="#myModal" role="button" class="btn" data-toggle="modal">Create
									New Event</a>
							</div>
							<div></div>
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
	<div id="myModal" class="modal hide fade" tabindex="-1" role="dialog"
		aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-header">
			<button type="button" class="close" data-dismiss="modal"
				aria-hidden="true">×</button>
			<h3 id="myModalLabel">Create New Event!</h3>
		</div>
		<div class="modal-body">
			<p>**************TO DO************</p>
		</div>
		<div class="modal-footer">
			<button class="btn" data-dismiss="modal" aria-hidden="true">Close</button>
			<button class="btn btn-primary">Save changes</button>
		</div>
	</div>

</body>
</html>
