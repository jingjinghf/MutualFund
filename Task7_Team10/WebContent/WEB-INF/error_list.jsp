<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<br/>
<c:forEach var="error" items="${errors}">
<div class="alert alert-danger" role="alert">
${error}
</div>
</c:forEach>
<br/>

