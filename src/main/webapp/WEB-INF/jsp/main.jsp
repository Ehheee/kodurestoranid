<%@ include file="include.jsp"%>
<jsp:include page="header.jsp"></jsp:include>
<c:if test="${not empty rootThing }">
	<jsp:include page="tree.jsp">
	</jsp:include>
</c:if>
<jsp:include page="${jspContent }"></jsp:include>
<jsp:include page="footer.jsp"></jsp:include>
