<%@ page language="java" contentType="text/html; charset=euc-kr"
    pageEncoding="EUC-KR"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<script type="text/javascript">
function sendChildValue(cnum,location,name,feature){

	opener.setChildValue(cnum,location,name,feature);

	window.close();

	}
 </script>
<div align="center">
<h3>고 양 이 찾 기</h3>
<form name="f" action="cat_find_ok.do">
<table border="1">
 <tr>
 <td>
 <input type="text" name="location" width="300" value="${location}">
 <input type="submit" value="찾기">
 </td>
 </tr>
</table>
<br>

<table border="1" width="100%" align="center">
<tr>
<td>
위치
</td>
<td>
이름
</td>
<td>
특징
</td>
</tr>
<c:forEach var="dto" items="${cat_list}">
<tr>
<td>
${dto.location}
</td>
<td>
<a href="javascript:sendChildValue('${dto.cnum}','${dto.location}','${dto.name}','${dto.feature }')">
${dto.name}
</a>
</td>
<td>
${dto.feature}
</td>
</tr>
</c:forEach>
</table>
<c:if test="${cat_list==null}">
<h3>등록된 고양이가 없습니다</h3>
</c:if>
</form>
</div>