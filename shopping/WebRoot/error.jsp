<%@ page contentType="text/html;charset=utf-8"%>
<%@ include file = "user/header.jsp" %>
		<center>
		    看看你运气怎么样啊？<br>
		    <%
		        if (request.getAttribute("op_error") != null) {
		        //出现错误的时候
		    %>
		        <font color="red"><%=request.getAttribute("op_error")%></font>
		    <%
		        }
		    %>
		</center>
<%@ include file = "user/footer.jsp" %>