<%--
  Created by IntelliJ IDEA.
  User: Mr.Diao
  Date: 2021/05/06
  Time: 17:54
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":"
            + request.getServerPort() + path + "/";
%>
<html>
<head>
    <title>员工列表</title>
    <base href="<%=basePath%>">
    <script src="static/js/jquery-3.0.0.min.js"></script>
    <link href="static/bootstrap-3.3.7-dist/css/bootstrap.css" rel="stylesheet" />
    <script src="static/bootstrap-3.3.7-dist/js/bootstrap.js"></script>
</head>
<body>
    <div class="container">
        <%-- 标题行 --%>
        <div class="row">
            <div class="col-md-12">
                <h1>SSM-CRUD</h1>
            </div>
        </div>
        <%-- 按钮 --%>
        <div class="row">
            <div class="col-md-4">
                <button class="btn btn-primary">新增</button>
                <button class="btn btn-danger">删除</button>
            </div>
        </div>
        <%-- 显示查询列表的表格 --%>
        <div class="row">
            <div class="col-md-12">
                <table class="table table-hover">
                    <tr>
                        <th>#</th>
                        <th>Name</th>
                        <th>Gender</th>
                        <th>Email</th>
                        <th>DeptName</th>
                        <th>操作</th>
                    </tr>
                    <c:forEach items="${requestScope.pageInfo.list}" var="emp" varStatus="i">
                    <tr>
                        <td>${i.count}</td>
                        <td>${emp.name}</td>
                        <td>${emp.gender == 'M' ? '男' : '女'}</td>
                        <td>${emp.email}</td>
                        <td>${emp.dept.name}</td>
                        <td>
                            <button class="btn btn-primary btn-sm"><span class="glyphicon glyphicon-pencil"></span>
                                编辑
                            </button>
                            <button class="btn btn-danger btn-sm"><span class="glyphicon glyphicon-trash"></span>
                                删除
                            </button>
                        </td>
                    </tr>
                    </c:forEach>
                </table>
            </div>
        </div>
        <div class="row">
            <div class="col-md-6">
                当前第【${requestScope.pageInfo.pageNum}】页，总【${requestScope.pageInfo.pages}】页，
                总【${requestScope.pageInfo.total}】条记录。
            </div>
            <div class="col-md-6">
                <nav>
                    <ul class="pagination">

                        <li <c:if test="${!requestScope.pageInfo.hasPreviousPage}">class="disabled"</c:if>><a href="emps">首页</a></li>
                        <li <c:if test="${!requestScope.pageInfo.hasPreviousPage}">class="disabled"</c:if>><a href="emps?pn=${requestScope.pageInfo.pageNum - 1}">上一页</a></li>
                        <c:forEach items="${requestScope.pageInfo.navigatepageNums}" var="i">

                            <li  <c:if test="${i == requestScope.pageInfo.pageNum}">class="active"</c:if>><a href="emps?pn=${i}">${i}</a></li>
                        </c:forEach>


                        <li <c:if test="${!requestScope.pageInfo.hasNextPage}">class="disabled"</c:if>><a href="emps?pn=${requestScope.pageInfo.pageNum + 1}">下一页</a></li>
                        <li <c:if test="${!requestScope.pageInfo.hasNextPage}">class="disabled"</c:if>><a href="emps?pn=${requestScope.pageInfo.pages}">尾页</a></li>
                    </ul>
                </nav>
            </div>
        </div>
    </div>
</body>
</html>
