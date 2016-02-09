<%-- Te-Hao Huang
	 ID: tehaoh
	 Task #7 --%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<jsp:include page="top_customer.jsp" />
<jsp:include page="left_customer.jsp" />

<div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
	<h1 class="page-header">Sell Fund</h1>
	<div class="table-responsive">
		<form class="form-signin" method="post" action="sellfund.do">

			<table class="table table-striped">
				<tbody>
					<tr style='text-align: center;'>
						<td><div align="left">Fund Symbol:</div></td>
						<td><div align="left">
								<input type="text" name="symbol" readonly
									value="${sessionScope.customerFund.fundSymbol}" />
							</div></td>
					</tr>
					<tr style='text-align: center;'>
						<td><div align="left">Number of Shares You Own:</div></td>



						<td><div align="left">
								<fmt:formatNumber type="number" minFractionDigits="3"
									value="${sessionScope.customerFund.readShares()}" />
							</div></td>
					</tr>

					<tr style='text-align: center;'>
						<td><div align="left">Number of Shares You Want to
								Sell:</div></td>
						<td><div align="left">

								<input type="text" name="shares" value="${sellfundform.share}" />
							</div></td>
					</tr>
					<tr style='text-align: center;'>
						<td colspan="2" align="center"><button type="submit"
								name="sellbutton" value="Place Order" class="btn btn-primary">Place
								Order</button></td>
					</tr>
					<jsp:include page="error_list.jsp" />
				</tbody>
			</table>
		</form>
	</div>
</div>
<jsp:include page="bottom.jsp" />