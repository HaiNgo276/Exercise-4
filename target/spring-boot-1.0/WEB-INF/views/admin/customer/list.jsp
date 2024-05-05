<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/common/taglib.jsp" %>
<%@ taglib prefix="display" uri="http://displaytag.sf.net"%>
<c:url var="customerListURL" value="/admin/customer-list"/>
<c:url var="customerAPI" value="/api/customer"/>

<html>
<head>
    <title>Danh sách khách hàng</title>
</head>

<body>
<div class="main-content">
    <div class="main-content-inner">
        <div class="breadcrumbs" id="breadcrumbs">
            <script type="text/javascript">
                try{ace.settings.check('breadcrumbs' , 'fixed')}catch(e){}
            </script>
            <ul class="breadcrumb">
                <li>
                    <i class="ace-icon fa fa-home home-icon"></i>
                    <a href="#">Trang chủ</a>
                </li>
                <li class="active">
                    Danh sách khách hàng
                </li>
            </ul><!-- /.breadcrumb -->
        </div>
        <div class="page-content">
            <div class="col-xs-12">
                <div class="widget-box ui-sortable-handle">
                    <div class="widget-header" >
                        <h5 class="widget-title">Tìm kiếm</h5>

                        <div class="widget-toolbar">
                            <a href="#" data-action="collapse">
                                <i class="ace-icon fa fa-chevron-up"></i>
                            </a>
                        </div>
                    </div>

                    <div class="widget-body" style="font-family: 'Times New Roman', Times, serif;">
                        <div class="widget-main">
                            <form:form id="listFormCustomer" modelAttribute="modelSearch" action="${customerListURL}" method="GET">
                                <div class="row">
                                    <div class="form-group">
                                        <div class="col-xs-12">
                                            <div class="col-xs-4">
                                                <label  class="name" >Tên khách hàng </label>
                                                <form:input class="form-control" path="fullname"/>
                                            </div>
                                            <div class="col-xs-4">
                                                <label  class="name">Di động</label>
                                                <form:input class="form-control"  path="phone"/>
                                            </div>
                                            <div class="col-xs-4">
                                                <label  class="name">Email</label>
                                                <form:input class="form-control"  path="email"/>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <div class="col-xs-12">
                                            <div class="col-xs-4">
                                                <security:authorize access="hasRole('MANAGER')">
                                                    <div >
                                                        <label class="name">Chọn nhân viên phụ trách</label>
                                                        <form:select class="form-control" path="staffId" >
                                                            <form:option value="">---Chọn nhân viên---</form:option>
                                                            <form:options items="${staffs}"/>
                                                        </form:select>
                                                    </div>
                                                </security:authorize>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <div class="col-xs-12">
                                            <div class="col-xs-6">
                                                <button type="button" class="btn btn-xs btn-danger" id="btnSearchCustomer">
                                                    <i class="ace-icon glyphicon glyphicon-search"></i>
                                                    Tìm kiếm
                                                </button>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </form:form>
                        </div>
                    </div>

                    <security:authorize access="hasRole('MANAGER')">
                        <div class="pull-right">
                            <a href="/admin/customer-edit">
                                <button class="btn btn-white btn-info btn-bold" title="Thêm khách hàng">
                                    <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-person-add" viewBox="0 0 16 16">
                                        <path d="M12.5 16a3.5 3.5 0 1 0 0-7 3.5 3.5 0 0 0 0 7m.5-5v1h1a.5.5 0 0 1 0 1h-1v1a.5.5 0 0 1-1 0v-1h-1a.5.5 0 0 1 0-1h1v-1a.5.5 0 0 1 1 0m-2-6a3 3 0 1 1-6 0 3 3 0 0 1 6 0M8 7a2 2 0 1 0 0-4 2 2 0 0 0 0 4"/>
                                        <path d="M8.256 14a4.5 4.5 0 0 1-.229-1.004H3c.001-.246.154-.986.832-1.664C4.484 10.68 5.711 10 8 10q.39 0 .74.025c.226-.341.496-.65.804-.918Q8.844 9.002 8 9c-5 0-6 3-6 4s1 1 1 1z"/>
                                    </svg>
                                </button>
                            </a>

                            <button class="btn btn-white btn-danger btn-bold" title="Xóa khách hàng" id="deleteCustomersBtn">
                                <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-person-dash" viewBox="0 0 16 16">
                                    <path d="M12.5 16a3.5 3.5 0 1 0 0-7 3.5 3.5 0 0 0 0 7M11 12h3a.5.5 0 0 1 0 1h-3a.5.5 0 0 1 0-1m0-7a3 3 0 1 1-6 0 3 3 0 0 1 6 0M8 7a2 2 0 1 0 0-4 2 2 0 0 0 0 4"/>
                                    <path d="M8.256 14a4.5 4.5 0 0 1-.229-1.004H3c.001-.246.154-.986.832-1.664C4.484 10.68 5.711 10 8 10q.39 0 .74.025c.226-.341.496-.65.804-.918Q8.844 9.002 8 9c-5 0-6 3-6 4s1 1 1 1z"/>
                                </svg>
                            </button>
                        </div>
                    </security:authorize>

                </div>
            </div>

            <div class="row">
                <div class="col-xs-12"  style="font-family: 'Times New Roman', Times, serif;">
                    <div class="table-responsive">
                        <display:table name="customerList" requestURI="${customerListURL}" id="tableList" pagesize="${model.maxPageItems}"
                                       class="table table-striped table-bordered table-hover"
                                       style="text-align: center;">
                            <security:authorize access="hasRole('MANAGER')">
                                <display:column title="<fieldset class='form-group' style='display: block; margin: 0' >
                                                           <input type='checkbox' id='checkAll' class='check-box-element'>
                                                       </fieldset>"
                                                class="center select-cell"
                                                headerClass="center select-cell no-select">
                                    <fieldset class="no-select">
                                        <input type="checkbox" name="checkList" value="${tableList.id}"
                                               id="checkbox_${tableList.id}" class="check-box-element"/>
                                    </fieldset>
                                </display:column>
                            </security:authorize>

                            <display:column property="fullname" title="Tên khách hàng" headerClass="center"/>
                            <display:column property="phone" title="Di động" headerClass="center"/>
                            <display:column property="email" title="Email" headerClass="center"/>
                            <display:column property="demand" title="Nhu cầu" headerClass="center"/>
                            <display:column property="createdBy" title="Người thêm" headerClass="center"/>
                            <display:column property="createdDate" title="Ngày thêm" headerClass="center"/>
                            <display:column property="status" title="Tình trạng" headerClass="center"/>
                            <display:column title="Thao tác" headerClass="center">
                                <div class="hidden-sm hidden-xs btn-group">
                                    <security:authorize access="hasRole('MANAGER')">
                                        <button class="btn btn-xs btn-success" title="Giao khách hàng" onclick="assignmentCustomer(${tableList.id})" name="customerId">
                                            <i class="ace-icon glyphicon glyphicon-list"></i>
                                        </button>
                                    </security:authorize>

                                    <a class="btn btn-xs btn-info" title="Sửa thông tin khách hàng" href="/admin/customer-edit-${tableList.id}">
                                        <i class="ace-icon fa fa-pencil bigger-120"></i>
                                    </a>
                                    <security:authorize access="hasRole('MANAGER')">
                                        <button class="btn btn-xs btn-danger" title="Xóa khách hàng" onclick="deleteCustomer(${tableList.id})">
                                            <i class="ace-icon fa fa-trash-o bigger-120"></i>
                                        </button>
                                    </security:authorize>
                                </div>
                            </display:column>
                        </display:table>
                    </div>
                </div>
            </div>

        </div>
    </div><!-- /.page-content -->
</div>

<%--model show staffList--%>
<div class="modal fade" role="dialog" id="assignmentCustomerModal">
    <div class="modal-dialog">
        <!-- Modal content-->
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal">&times;</button>
                <h4 class="modal-title">Danh Sách Nhân Viên</h4>
            </div>

            <div class="modal-body">
                <table class="table table-striped table-bordered table-hover" id="staffList">
                    <thead>
                    <tr>
                        <th style="text-align: center;">Chọn</th>
                        <th>Tên nhân viên</th>
                    </tr>
                    </thead>
                    <tbody>
                    </tbody>
                </table>
                <input type="hidden" id="customerId" name= "customerId" value="">
            </div>

            <div class="modal-footer">
                <button type="button" class="btn btn-default" id="btnAssignmentCustomer" >Giao Khách Hàng</button>
                <button type="button" class="btn btn-default" data-dismiss="modal" >Đóng</button>
            </div>
        </div>
    </div>
</div>


<script src="assets/js/jquery.2.1.1.min.js"></script>

<script>
    function assignmentCustomer(customerId){
        $('#assignmentCustomerModal').modal();
        loadStaff(customerId);
        $('#customerId').val(customerId);
    }
    function loadStaff(customerId){
        $.ajax({
            type: "GET",
            url: "${customerAPI}/" + customerId + '/staffs',
            dataType: "JSON",
            success: function(response) {
                var row = '';
                $.each(response.data, function (index, item){
                    row += '<tr>';
                    row += '<td class="text-center"><input type="checkbox" value=' + item.staffId + ' id="checkbox_' + item.staffId + '" class = "check-box-element"' + item.checked + '/></td>';
                    row += '<td class="text-center">' + item.fullName + '</td>';
                    row += '</tr>';
                });
                $('#staffList tbody').html(row);
            },
            error: function(response){
                window.location.href = "<c:url value="/admin/customer-list?message=error" />"
            }
        });
    }
    $('#btnAssignmentCustomer').click(function (e){
        e.preventDefault();
        var data = {};
        data["id"] = $('#customerId').val();
        var staffs = $("#staffList").find('tbody input[type = checkbox]:checked').map(function(){
            return $(this).val();
        }).get();
        data['staffs'] = staffs;
        updateAssignmentCustomer(data);
        console.log("OK");
    })
    function updateAssignmentCustomer(data){
        $.ajax({
            type: "POST",
            url: "${customerAPI}" + '/assignment',
            data: JSON.stringify(data),
            contentType: "application/json",
            // dataType: "JSON",
            success: function(response) {
                window.location.href = "<c:url value= "/admin/customer-list?message=success"/>";
            },
            error: function(response){
                window.location.href = "<c:url value="/admin/customer-list?message=error"/>";
            }
        });
    }


    $('#btnSearchCustomer').click(function (e){
        e.preventDefault();
        $('#listFormCustomer').submit();
    })

    function deleteCustomer(data){
        var customerId = [data];
        deleteCustomers(customerId);
    }
    $('#deleteCustomersBtn').click(function (e){
        e.preventDefault();
        var customerIds = $('#tableList').find('tbody input[type = checkbox]:checked').map(function (){
            return $(this).val();
        }).get();
        deleteCustomers(customerIds);
    });

    function deleteCustomers(data){
        $.ajax({
            type: "POST",
            url: "${customerAPI}/" + data,
            data: JSON.stringify(data),
            contentType: "application/json",
            // dataType: "JSON",
            success: function (respond) {
                window.location.href = "<c:url value= "/admin/customer-list?message=success"/>";
            },
            error: function (respond){
                window.location.href = "<c:url value="/admin/customer-list?message=error"/>";
            }
        });
    }
</script>
</body>
</html>

