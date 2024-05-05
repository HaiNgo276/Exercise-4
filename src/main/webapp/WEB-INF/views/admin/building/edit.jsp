
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/common/taglib.jsp" %>
<%@ taglib prefix="display" uri="http://displaytag.sf.net"%>
<c:url var="buildingAPI" value="/api/building"/>
<c:url var="buildingEditURL" value="/admin/building-edit"/>

<html>
<head>
    <title>Title</title>
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
                    <li class="active" modelAttribute="buildingEdit">
                        <c:if test="${not empty buildingEdit.id}">
                            Chỉnh sửa tòa nhà
                        </c:if>
                        <c:if test="${empty buildingEdit.id}">
                            Thêm tòa nhà
                        </c:if>
                    </li>
                    </li>
                </ul><!-- /.breadcrumb -->
            </div>
            <div class="page-content">
            <div class="page-header">
                <h1>
                    Thông tin tòa nhà
                </h1>
            </div><!-- /.page-header -->

<%--            <div class="row">--%>
<%--                <div class="col-xs-12"></div>--%>
<%--            </div>--%>

            <!-- Bảng danh sách -->
            <div class="row" style="font-family: 'Times New Roman', Times, serif;">
                <form:form id="listForm" modelAttribute="buildingEdit" action="${buildingEditURL}"  method="POST">
                    <div class="col-xs-12">
                        <div class="form-horizontal" role="form">
                            <div class="form-group">
                                <label class="col-xs-3">Tên tòa nhà</label>
                                <div class="col-xs-9">
                                    <form:input class="form-control" name="name" path="name"/>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-xs-3">Quận</label>
                                <div class="col-xs-2">
                                    <form:select class="form-control" path="district">
                                        <form:option value="">--- Chọn Quận ---</form:option>
                                        <form:options items="${districtCode}"/>
                                    </form:select>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-xs-3">Phường</label>
                                <div class="col-xs-9">
                                    <form:input class="form-control" name="ward" path="ward"/>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-xs-3">Đường</label>
                                <div class="col-xs-9">
                                    <form:input class="form-control" name="street" path="street"/>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-xs-3">Kết cấu</label>
                                <div class="col-xs-9">
                                    <form:input class="form-control" name="structure" path="structure"/>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-xs-3">Số tầng hầm</label>
                                <div class="col-xs-9">
                                    <form:input class="form-control" name="numberOfBasement" path="numberOfBasement"/>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-xs-3">Diện tích sàn</label>
                                <div class="col-xs-9">
                                    <form:input class="form-control" name="floorArea" path="floorArea"/>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-xs-3">Hướng</label>
                                <div class="col-xs-9">
                                    <form:input class="form-control" name="direction" path="direction"/>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-xs-3">Hạng</label>
                                <div class="col-xs-9">
                                    <form:input class="form-control" name="level" path="level"/>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-xs-3">Diện tích thuê</label>
                                <div class="col-xs-9">
                                    <form:input class="form-control" name="rentArea" path="rentArea"/>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-xs-3">Giá thuê</label>
                                <div class="col-xs-9">
                                    <form:input class="form-control" name="rentPrice" path="rentPrice"/>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-xs-3">Mô tả giá</label>
                                <div class="col-xs-9">
                                    <form:input class="form-control" name="rentPriceDescription" path="rentPriceDescription"/>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-xs-3">Phí dịch vụ</label>
                                <div class="col-xs-9">
                                    <form:input class="form-control" name="serviceFee" path="serviceFee"/>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-xs-3">Phí ô tô</label>
                                <div class="col-xs-9">
                                    <form:input class="form-control" name="carFee" path="carFee"/>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-xs-3">Phí ngoài giờ</label>
                                <div class="col-xs-9">
                                    <form:input class="form-control" name="overtimeFee" path="overtimeFee"/>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-xs-3">Tiền điện</label>
                                <div class="col-xs-9">
                                    <form:input class="form-control" name="electricityFee" path="electricityFee"/>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-xs-3">Đặt cọc</label>
                                <div class="col-xs-9">
                                    <form:input class="form-control" name="deposit" path="deposit"/>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-xs-3">Thanh toán</label>
                                <div class="col-xs-9">
                                    <form:input class="form-control" name="payment" path="payment"/>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-xs-3">Thời hạn thuê</label>
                                <div class="col-xs-9">
                                    <form:input class="form-control" name="rentTime" path="rentTime"/>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-xs-3">Thời gian trang trí</label>
                                <div class="col-xs-9">
                                    <form:input class="form-control" name="decorationTime" path="decorationTime"/>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-xs-3">Tên quản lý</label>
                                <div class="col-xs-9">
                                    <form:input class="form-control" name="managerName" path="managerName"/>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-xs-3">SĐT quản lý</label>
                                <div class="col-xs-9">
                                    <form:input class="form-control" name="managerPhone" path="managerPhone"/>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-xs-3">Phí mô giới</label>
                                <div class="col-xs-9">
                                    <form:input class="form-control" name="brokerageFee" path="brokerageFee"/>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-xs-3">Loại tòa nhà</label>
                                <div class="col-xs-9">
                                    <form:checkboxes items="${buildingType}" path="typeCode"/>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-xs-3">Ghi chú</label>
                                <div class="col-xs-9">
                                    <form:input class="form-control" path="note"/>
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="col-sm-2 no-padding-right">Hình đại diện</label>
                                <input class="col-sm-2 no-padding-right" type="file" id="uploadImage"/>
                                <div class="col-sm-8">
                                    <c:if test="${not empty buildingEdit.avatar}">
                                        <c:set var="imagePath" value="/repository${buildingEdit.avatar}"/>
                                        <img src="${imagePath}" id="viewImage" width="300px" height="300px" style="overflow: hidden; object-fit: cover">
                                    </c:if>
                                    <c:if test="${empty buildingEdit.avatar}">
                                        <img src="/admin/image/default.png" id="viewImage" width="300px" height="300px">
                                    </c:if>
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="col-xs-3"></label>
                                <div class="col-xs-9">
                                    <c:if test="${not empty buildingEdit.id}">
                                        <button type="button" class="btn btn-primary" id="btnAddOrUpdateBuilding">Cập nhập tòa nhà</button>
                                        <button type="button" class="btn btn-primary" id="btnCancel">Hủy thao tác</button>
                                    </c:if>
                                    <c:if test="${empty buildingEdit.id}">
                                        <button type="button" class="btn btn-primary" id="btnAddOrUpdateBuilding">Thêm tòa nhà</button>
                                        <button type="button" class="btn btn-primary" id="btnCancel">Hủy thao tác</button>
                                    </c:if>

                                </div>
                            </div>
                        </div>
                    </div>
                    <form:hidden path="id" id="buildingId"/>
                </form:form>
            </div>
        </div><!-- /.page-content -->
    </div>
    </div><!-- /.main-content -->
    <script src="assets/js/jquery.2.1.1.min.js"></script>

    <script>
        var imageBase64 = '';
        var imageName = '';

        $('#btnAddOrUpdateBuilding').click(function() {
            var data = {};
            var typeCode = [];
            var formData = $('#listForm').serializeArray();
            $.each(formData, function(i, v) {
                if(v.name != 'typeCode') {
                    data["" + v.name + ""] = v.value;
                }
                else {
                    typeCode.push(v.value);
                }
                if ('' !== imageBase64) {
                    data['imageBase64'] = imageBase64;
                    data['imageName'] = imageName;
                }
            });
            data['typeCode'] = typeCode;
            if(typeCode != '') {
                addOrUpdateBuilding(data);
            }
            else {
                window.location.href = "<c:url value="/admin/building-edit?typeCode=require"/>";
            }
        });
        function addOrUpdateBuilding(data){
            $.ajax({
                type: "POST",
                url: "${buildingAPI}",
                data: JSON.stringify(data),
                contentType: "application/json",
                // dataType: "JSON",
                success: function(respond){
                    console.log("Success");
                    window.location.href = "<c:url value="/admin/building-list?message=success"/>";
                },
                error: function(respond){
                    console.log("failed");
                }
            })
        }

        $('#uploadImage').change(function (event) {
            var reader = new FileReader();
            var file = $(this)[0].files[0];
            reader.onload = function(e){
                imageBase64 = e.target.result;
                imageName = file.name; // ten hinh khong dau, khoang cach. Dat theo format sau: a-b-c
            };
            reader.readAsDataURL(file);
            openImage(this, "viewImage");
        });

        function openImage(input, imageView) {
            if (input.files && input.files[0]) {
                var reader = new FileReader();
                reader.onload = function (e) {
                    $('#' +imageView).attr('src', reader.result);
                }
                reader.readAsDataURL(input.files[0]);
            }
        }

        $('#btnCancel').click(function (e){
            window.location.href = "/admin/building-list";
            e.preventDefault();
        });
    </script>
</body>
</html>
