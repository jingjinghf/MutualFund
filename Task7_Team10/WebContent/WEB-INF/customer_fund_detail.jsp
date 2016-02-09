<%-- Te-Hao Huang
	 ID: tehaoh
	 Task #7 --%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<jsp:include page="top_customer.jsp" />
<jsp:include page="left_customer.jsp" />

<div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
	<h1 class="page-header">Fund Detail: ${sessionScope.modifyFund.symbol}</h1>
	<div class="row placeholders">
		<div class="col-xs-6 col-sm-3 placeholder"></div>
	</div>
	<div class="table-responsive">
		<table width="700" class="table table-striped" border="1">
			<thead>
				<tr>
					<th height="25">Date</th>
					<th>Trading Price</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="detail" items="${details}">
					<tr>
						<td><fmt:formatDate value="${detail.priceDate}" pattern="MM/dd/yyyy" /></td>

						<td>
						<c:choose>
									<c:when test="${detail.price == 0}">
                                      --
                                    </c:when>
									<c:otherwise>
                                    <fmt:setLocale value="en_US" />
							        <fmt:formatNumber value="${detail.price}" type="currency" />
                                    </c:otherwise>
						</c:choose>  
						</td>					
					
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>
</div>

<jsp:include page="bottom.jsp" />