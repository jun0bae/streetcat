<%@ page language="java" contentType="text/html; charset=euc-kr"
    pageEncoding="EUC-KR"%>
    
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    
<h2>�Խñ� �󼼺���</h2>
<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous"> <style> .carousel-inner > .carousel-item > img{ width: 50px; height: 300px;} </style>
<script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
  <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js" integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1" crossorigin="anonymous"></script>
  <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js" integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM" crossorigin="anonymous"></script>
  <script>
    $('.carousel').carousel({
          interval: 2000 //�⺻ 5�� })
  </script>
<script>
     function isLogin(){
    	 <% if(session.getAttribute("name")==null){%>
    	 alert("�α��� �� �̿����ּ���")
    	 location.href("cat_board.do")
    	 <%}%>
     }
     function goBack(){
    	 alert("ó��/�������� �Դϴ�")
    	 window.history.back()
     }
     
    </script>
    
<c:if test="${getBoard.writer==sessionScope.name}">

</c:if>
<a href="cat_board_edit.do?bnum=${getBoard.bnum}">����</a>
| <a href="cat_board_delete.do?bnum=${getBoard.bnum}"  onclick="return confirm('���� �����Ͻðڽ��ϱ�?')">
����</a> <!-- �ۼ��ڿ� �α���id�� ������ ����|���� ��� -->

<html>

<c:if test="${getBoard.bnum==null}">
<body onload="goBack()">
</c:if>
<table width="100%">

<tr>
<th align="left" width="25%">
�ۼ��� : ${getBoard.writer}<br>
�ۼ��� : ${getBoard.reg_date}
</th>
<th align="center">
${getBoard.subject}
</th>
<td align="right" width="25%">
��ȸ�� : ${getBoard.readcount}
</td>
</tr>
 <tr>
 
 <td align="right">
 </td>
 <td align="center">
 <div id="demo" class="carousel slide" data-ride="carousel">
    <div class="carousel-inner">
      <!-- �����̵� �� -->
      <div class="carousel-item active">
        <!--����--> <img class="d-block w-100" src="${upPath}/${getBoard.image1}" alt="1st slide" onerror="this.parentNode.style.display='none'">
        <div class="carousel-caption d-none d-md-block">

        </div>
      </div>
      <c:if test="${getBoard.image2!=null}">
      <div class="carousel-item"> <img class="d-block w-100" src="${upPath}/${getBoard.image2}" alt="2nd slide"> </div>
      </c:if>
      <c:if test="${getBoard.image3!=null}">
      <div class="carousel-item"> <img class="d-block w-100" src="${upPath}/${getBoard.image3}" alt="2nd slide"> </div>
      </c:if> 
      <c:if test="${getBoard.image4!=null}">
      <div class="carousel-item"> <img class="d-block w-100" src="${upPath}/${getBoard.image4}" alt="2nd slide"> </div>
      </c:if>
      <c:if test="${getBoard.image5!=null}">
      <div class="carousel-item"> <img class="d-block w-100" src="${upPath}/${getBoard.image5}" alt="2nd slide"> </div>
      </c:if>
      <!-- / �����̵� �� �� -->
      <!-- ���� ������ ȭ��ǥ ��ư --> <a class="carousel-control-prev" href="#demo" data-slide="prev"> <span class="carousel-control-prev-icon" aria-hidden="true"></span> <!-- <span>Previous</span> --> </a> <a class="carousel-control-next" href="#demo"
        data-slide="next"> <span class="carousel-control-next-icon" aria-hidden="true"></span> <!-- <span>Next</span> --> </a> <!-- / ȭ��ǥ ��ư �� -->
      <!-- �ε������� 
      <ul class="carousel-indicators">
        <li data-target="#demo" data-slide-to="0" class="active"></li>
        -0�����ͽ���
        <li data-target="#demo" data-slide-to="1"></li>
        <li data-target="#demo" data-slide-to="2"></li>
      </ul> �ε������� �� -->
    </div>
 <br>
 </td>
 <td align="left">
 </td>
 </tr>
 <tr>
 <td algin="center">
 ${getBoard.content}
 </td>
 </tr>
 </table>
 <br>
 
 <table align="center">
  <tr>
  <td>
   <button onclick="window.location('cat_board_like.do?bnum=${getBoard.bnum}&id=${sessionScope.id}')">��õ</button>
  </td>
  <td>
   <button onclick="window.location.href='cat_board.do'">��ϰ���</button>
  </td>
  <td>
  <a href="cat_board_content.do?bnum=${getBoard.bnum}&type=before">������</a>|
   <a href="cat_board_content.do?bnum=${getBoard.bnum}&type=next">������</a>
  </td>
  </tr>
 </table>
 
 <form name="f" action="cat_board_comment_write.do" method="post">
 <table width="100%">
 
 <c:forEach var="dto" items="${boardComment}">
 <tr>
 <td colspan="2">
 <c:if test="${dto.re_level>0}">
				<img src="${upPath}/resources/img/level.gif" width="${dto.re_level*10}" height="15"/>
				<img src="${upPath}/resources/img/re.gif">
				<c:if test="${dto.writer==getBoard.writer}">
				[�۾���]
				</c:if>
				${dto.writer} : ${dto.content} '${dto.reg_date}'
</c:if>	
<c:if test="${dto.re_level==0}">
<c:if test="${dto.writer==getBoard.writer}">
				[�۾���]
				</c:if>
  ${dto.writer} : ${dto.content} '${dto.reg_date}'
  <a href="#" onclick="window.open('reComment.do?comment_num=${dto.comment_num}','', 'width=500, height=250, resizable = no, scrollbars = no')">
  [��۴ޱ�]</a>
  </c:if>
 <br>
 </td>
 </tr>
 </c:forEach>
 <tr>
 <th>
 <c:if test="${count>0}">
            <c:if test="${startPage > pageBlock}">
			[<a href="cat_board_content.do?pageNum=${startPage-pageBlock}&bnum=${getBoard.bnum}&type=now">����</a>]
</c:if>
<c:forEach var="i" begin="${startPage}" end="${endPage}">
			[<a href="cat_board_content.do?pageNum=${i}&bnum=${getBoard.bnum}&type=now">${i}</a>]
</c:forEach>

<c:if test="${endPage<PageCount}">
			[<a href="cat_board_content.do?pageNum=${startPage+pageBlock}&bnum=${getBoard.bnum}&type=now">����</a>]
</c:if>
</c:if>
 </th>
 </tr>
 <tr>
 <td align="center">
 <textarea onfocus="isLogin()" placeholder='����� �Է��ϼ���' name="content" rows="3" cols="100%" required></textarea>
 </td>
 <td>
 <input type="hidden" name="bnum" value="${getBoard.bnum}">
 <input type="hidden" name="type" value="normal">
 <input type="submit" value="���">
 </td>
 </tr>
</table>
</form>

</body>

</html>