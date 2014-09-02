<%@ include file="include.jsp"%>
<h4>Objektid</h4>
<c:forEach items="${objektid}" var="objekt">
<a href="<c:url value='/objektid/${objekt.id }' />" >${objekt.nimi}</a><br />
</c:forEach>
