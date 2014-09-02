<%@ include file="include.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link href="<c:url value="/resources/style.css" />" rel="stylesheet" type="text/css" />
<link href="<c:url value="/resources/javascript/jquery/anytime.css" />" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="<c:url value='/resources/javascript/jquery/jquery-1.6.4.js' />"></script>
<script type="text/javascript" src="<c:url value='/resources/javascript/jquery/anytime.js' />"></script>
<script type="text/javascript" src="<c:url value='/resources/javascript/init.js' />"></script>
</head>
<body>
	<div id="headerContainer">
		<div id="headerLinks">
			<c:url value="/objektid" var="ob" />
			<c:url value="/muugiarved" var="ma" />
			<c:url value="/ostuarved" var="oa" />
			<a href="${ob }">Objektid</a> <a href="${ma }">Müügiarved</a> <a href="${oa }">Ostuarved</a>

		</div>
		<div id="userInfo">
			<sec:authorize access="isAuthenticated()">
				Tere, <sec:authentication property="principal.username" /> - <a href="<c:url value='/logout' />">Logi välja</a>
			</sec:authorize>
			<sec:authorize access="isAnonymous()">
				<a href="<c:url value='/login' />">Logi sisse</a>, et lehte kasutada
			</sec:authorize>
		</div>
		<div style="clear: both;"></div>
	</div>
