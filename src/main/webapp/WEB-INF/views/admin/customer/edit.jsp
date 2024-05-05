<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/common/taglib.jsp" %>
<c:url var="customerEditURL" value="/admin/customer-edit"></c:url>
<c:url var="customerAPI" value="/api/customer"></c:url>
<c:url var="transactionAPI" value="/api/transaction"></c:url>
<html>
<head>
    <title>Thêm khách hàng</title>
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
                    <a href="/admin/home" style="text-decoration: none">Trang chủ</a>
                </li>
                <li class="active" modelAttribute ="customerEdit">
                    <c:if test="${not empty customerEdit.id}">
                        Chỉnh sửa khách hàng
                    </c:if>
                    <c:if test="${empty customerEdit.id}">
                        Thêm khách hàng
                    </c:if>
                </li>
                </li>
            </ul><!-- /.breadcrumb -->
        </div>

        <div class="page-content">
            <div class="page-header">
                <h1>
                    Thông tin khách hàng
                </h1>
            </div><!-- /.page-header -->

            <div class="row" >
                <div class="col-xs-12">
                    <form:form action="${customerEditURL}" modelAttribute="customerEdit" id="form-edit" method="GET">
                        <div class="col-xs-12">
                            <div class="form-horizontal" role="form">
                                <div class="form-group" >
                                    <label class="col-xs-3">Tên khách hàng</label>
                                    <div class="col-xs-9">
                                        <form:input class="form-control" name="name" path="fullname"/>
                                    </div>
                                </div>
                                <div class="form-group" >
                                    <label class="col-xs-3">Số điện thoại</label>
                                    <div class="col-xs-9">
                                        <form:input class="form-control" name="phone" path="phone"/>
                                    </div>
                                </div>
                                <div class="form-group" >
                                    <label class="col-xs-3">Email</label>
                                    <div class="col-xs-9">
                                        <form:input class="form-control" name="email" path="email"/>
                                    </div>
                                </div>
                                <div class="form-group" >
                                    <label class="col-xs-3">Tên công ty</label>
                                    <div class="col-xs-9">
                                        <form:input class="form-control" name="companyname" path="companyname"/>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-xs-3">Nhu cầu</label>
                                    <div class="col-xs-9">
                                        <form:input class="form-control" name="demand" path="demand"/>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-xs-3">Tình trạng</label>
                                    <div class="col-xs-2">
                                        <form:select class="form-control" path="status">
                                            <form:option value="">--- Chọn Tình Trạng ---</form:option>
                                            <form:options items="${statuses}"/>
                                        </form:select>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-xs-3"></label>
                                    <div class="col-xs-9">
                                        <c:if test="${not empty customerEdit.id}">
                                            <button type="button" class="btn btn-primary" id="btnAddOrUpdateCustomer">Cập nhật khách hàng</button>
                                            <button type="button" class="btn btn-primary" id="btnCancel">Hủy thao tác</button>
                                        </c:if>
                                        <c:if test="${empty customerEdit.id}">
                                            <button type="button" class="btn btn-primary" id="btnAddOrUpdateCustomer">Thêm khách hàng</button>
                                            <button type="button" class="btn btn-primary" id="btnCancel">Hủy thao tác</button>
                                        </c:if>

                                    </div>
                                </div>
                            </div>
                        </div>
                        <form:hidden path="id" id = ""/>
                    </form:form>
                </div><!-- /.col -->
            </div>
        </div><!-- /.page-content -->

        <c:if test="${not empty customerEdit.id}">
            <c:forEach var="item" items="${TransactionList}">
                <div class="col-xs-12">
                    <div class="col-sm-12">
                        <h1 class="header smaller lighter blue">${item.value}</h1>
                        <button class="btn btn-lg btn-primary" title="Thêm giao dịch" style="font-family: 'Times New Roman', Times, serif;"
                                onclick="transactionType('${item.key}', ${customerEdit.id})">
                            <i class = "orange ace-icon fa fa-location-arrow bigger-130"></i>
                            Add
                        </button>
                    </div>

                    <c:if test="${item.key == 'CSKH'}">
                        <div class="col-xs-12">
                            <table id="tableList" class="table table-striped table-bordered table-hover">
                                <thead>
                                <tr>
                                    <th>Ngày tạo</th>
                                    <th>Người tạo</th>
                                    <th>Ngày sửa</th>
                                    <th>Người sửa</th>
                                    <th>Chi tiết giao dịch</th>
                                    <th class="center">Thao tác</th>
                                </tr>
                                </thead>

                                <tbody>
                                    <%--duyet tat ca cac toa nha duoc tra ra tu controler--%>
                                <c:forEach var="it" items="${CSKH}">
                                    <tr class="">
                                        <td>${it.createdDate}</td>
                                        <td>${it.createdBy}</td>
                                        <td>${it.modifiedDate}</td>
                                        <td>${it.modifiedBy}</td>
                                        <td>${it.note}</td>
                                        <td class="center">
                                            <div class="hidden-sm hidden-xs btn-group">
                                                <button class="btn btn-xs btn-info" title="sửa giao dịch" onclick="UpdateTransaction('CSKH', ${customerEdit.id}, ${it.id})">
                                                    <i class="ace-icon fa fa-pencil bigger-120"></i>
                                                </button>
                                            </div>
                                        </td>
                                    </tr>
                                </c:forEach>
                                </tbody>
                            </table>
                        </div>
                    </c:if>

                    <c:if test="${item.key == 'DDX'}">
                        <div class="col-xs-12">
                            <table id="tableList1" class="table table-striped table-bordered table-hover">
                                <thead>
                                <tr>
                                    <th>Ngày tạo</th>
                                    <th>Người tạo</th>
                                    <th>Ngày sửa</th>
                                    <th>Người sửa</th>
                                    <th>Chi tiết giao dịch</th>
                                    <th class="center">Thao tác</th>
                                </tr>
                                </thead>

                                <tbody>
                                    <%--duyet tat ca cac toa nha duoc tra ra tu controler--%>
                                <c:forEach var="it" items="${DDX}">
                                    <tr class="">
                                        <td>${it.createdDate}</td>
                                        <td>${it.createdBy}</td>
                                        <td>${it.modifiedDate}</td>
                                        <td>${it.modifiedBy}</td>
                                        <td>${it.note}</td>
                                        <td class="center">
                                            <div class="hidden-sm hidden-xs btn-group">
                                                <button class="btn btn-xs btn-info" title="sửa giao dịch" onclick="UpdateTransaction('DDX', ${customerEdit.id}, ${it.id})">
                                                    <i class="ace-icon fa fa-pencil bigger-120"></i>
                                                </button>
                                            </div>
                                        </td>
                                    </tr>
                                </c:forEach>
                                </tbody>
                            </table>
                        </div>
                    </c:if>
                </div>

            </c:forEach>
        </c:if>


    </div>
</div>

<div class="modal fade" role="dialog" id="transactionTypeModel">
    <div class="modal-dialog">
        <!-- Modal content-->
        <div class="modal-content">
            <div class="modal-header">
                <h4 class="modal-title">Nhập giao dịch</h4>
            </div>
            <div class="modal-body">
                <div class="form-group has-success">
                    <label for= "transactionDetail" class="col-xs-12 col-sm-3 control-label no-padding-right">Chi tiết giao dịch</label>
                    <div class="col-xs-12 col-sm-9">
                        <span class="block input-icon input-icon-right">
                            <input type="text" id="transactionDetail" class="width-100">
                        </span>
                    </div>
                </div>
            </div>
            <input type="hidden" id="customerId" value=""/>
            <input type="hidden" id="code" value=""/>
            <input type="hidden" id="transactionId" value=""/>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" id="btnAddOrUpdateTransaction" >Thêm giao dịch</button>
                <button type="button" class="btn btn-default" data-dismiss="modal">Đóng</button>
            </div>
        </div>
    </div>
</div>


<script>

    function transactionType(code, customerId){
        $('#transactionDetail').val('');
        $('#transactionTypeModel').modal();
        $('#customerId').val(customerId);
        $('#code').val(code);
    }

    function UpdateTransaction(code, customerId, transactionId){
        $('#transactionTypeModel').modal();
        loadTransactionDetail(transactionId);
        $('#customerId').val(customerId);
        $('#code').val(code);
        $('#transactionId').val(transactionId);
    }

    function loadTransactionDetail(transactionId){
        $.ajax({
            type: "GET",
            url: "${transactionAPI}/" + transactionId,
            dataType: "JSON",
            success: function(response) {
                console.log(response);
                $('#transactionDetail').val(response['data']);
            },
            error: function(response){
                console.log("Fail!")
            }
        });
    }


    $('#btnAddOrUpdateTransaction').click(function (e){
        e.preventDefault();
        var data = {};
        data['id'] = $('#transactionId').val();
        data['code'] = $('#code').val();
        data['note'] = $('#transactionDetail').val().trim();
        data['customerId'] = $('#customerId').val();
        addOrUpdateTransaction(data);

    });

    function addOrUpdateTransaction(data){
        $.ajax({
            type: "POST",
            url: "${transactionAPI}",
            data: JSON.stringify(data),
            contentType: "application/json",
            // dataType: "JSON",
            success: function(response) {
                window.location.reload();
            },
            error: function(response){
                window.location.reload();

            }
        });
    }

    $(document).ready(function(){
        const urlParams = new URLSearchParams(window.location.search);
        const formDataSent = urlParams.has('formDataSent');
        $('#btnAddOrUpdateCustomer').click(function(){
            var data = {};
            var formData = $('#form-edit').serializeArray();
            $.each(formData, function(i, v){
                data["" + v.name + ""] = v.value.trim();
            })
            if(data['fullname'] != '' && data['phone'] != ''){
                addOrUpdateCustomer(data);
            }
            else{
                localStorage.setItem('formData', JSON.stringify(data));
                var formData = localStorage.getItem('formData');
                window.location.href = "<c:url value='/admin/customer-edit'/>?formDataSent=true";

            }
        });
        if(formDataSent){
            var formData = localStorage.getItem('formData');
            if(formData){
                formData = JSON.parse(formData);
                $.each(formData, function(key, value){
                    $('[name="' + key + '"]').val(value);
                    if (value === "") {
                        if(key === "fullname"){
                            var inputField = $('#fullname');
                            var errorSpan = $('<span class="input-error">Cần nhập tên khách hàng</span>');
                            inputField.after(errorSpan);
                        }
                        else if(key === "phone"){
                            var inputField = $('#phone');
                            var errorSpan = $('<span class="input-error">Cần nhập số điện thoại khách hàng</span>');
                            inputField.after(errorSpan);
                        }
                    }
                });
            }
        }
    });

    function addOrUpdateCustomer(data){
        $.ajax({
            type: "POST",
            url: "${customerAPI}",
            data: JSON.stringify(data),
            contentType: "application/json",
            // dataType: "JSON",
            success: function () {
                window.location.href = "<c:url value="/admin/customer-list?message=success"/>";

            },
            error: function (){
                window.location.href = "<c:url value="/admin/customer-edit?message=error_system"/>";
            }
        })
    }

    $('#btnCancel').click(function (){
        window.location.href = "/admin/customer-list";
    });

</script>
</body>
</html>