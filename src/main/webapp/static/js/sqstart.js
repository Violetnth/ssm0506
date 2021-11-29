//定义一个成员变量，用来添加后跳转到最后一页
var  totalRecord ,currentPage;

$(function(){
    $("#emp_delete_all_btn").click(function(){
        var names = "";
        var ids = "";
        $.each($(".check_item:checked"),function(){
            names += $(this).parents("tr").find("td:eq(2)").text() + ",";

            ids += $(this).parents("tr").find("td:eq(1)").text() + "-";
        });
        // 去除names和ids多余的符号
        names = names.substring(0,names.length - 1);
        ids = ids.substring(0,ids.length - 1);
        if(confirm("是否确认删除【" + names +"】的信息吗？")){
            // 发送ajax请求
            $.ajax({
                url : "emp/" + ids,
                type:"delete",
                dataType : "json",
                success:function(result){
                    alert(result.msg);

                    to_page(currentPage);
                }
            });
        }
    });

    // 全选/全不选
    $("#check_all").click(function(){
        // 让所有的项和当前的复选框选中状态相同
        $(".check_item").prop("checked",$(this).prop("checked"));
    });

    $("#emp_update_modal_btn").click(function () {
        //验证邮箱是否合法
        var email = $("#email_update_input").val();
        var regEmail = /^[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+(\.[a-zA-Z0-9_-]+)+$/;
        if (! regEmail.test(email)){
            show_validate_msg("#email_update_input","error","邮箱格式不合法");
            return false;
        }else {
            show_validate_msg("#email_update_input","success","");
        }

        //发送Ajax请求保存更新的数据
        $.ajax({
            url:"emp/" + $(this).attr("edit-id"),
            data:$("#empUpdateModal form").serialize() + "&_method=PUT",
            type:"POST",
            dataType:"json",
            success:function (result) {
                $("#empUpdateModal").modal("hide");
                alert(result.msg);
                to_page(currentPage);

            }
        });
    });

    $("#name_add_input").change(function (){
        //发送ajax请求校验用户名是否可用
        var name = $("#name_add_input").val();
        $.ajax({
            url:"checkUser",
            data:{"name":name},
            type:"POST",
            dataType:"json",
            success:function (result){
                if(result.code == 100){
                    show_validate_msg("#name_add_input","success","用户名可用")
                    //做标记，给提交按钮添加一个属性，没有实际的作用
                    $("#emp_add_modal_btn").attr("ajax-va","success");
                }else if (result.code == 200){
                    //从后台取值，取验证结果
                    show_validate_msg("#name_add_input","error",result.extend.va_msg);
                    $("#emp_add_modal_btn").attr("ajax-va","error");
                }
            }
        });
    });

    $("#emp_add_modal_btn").click(function () {
        //1、模态框中填写的表单数据提交给服务器进行保存
        //1.1先对要保存的数据进行校验
        if (! validate_add_form()){
            return false;
        }
        //通过提交按钮的ajax-va属性，验证当前用户名已存在时，不再提交数据
        if ($("#emp_add_modal_btn").attr("ajax-va") == "error"){
            return false;
        }
        //2、发送ajax请求保存员工
        //把form表单中的数据序列化：
        // alert($("#empAddModal form").serialize());
        $.ajax({
            url:"emp",
            type:"post",
            data:$("#empAddModal form").serialize(),
            dataType:"json",
            success:function (result) {
                if (result.code == 100){
                    //员工保存成功
                    alert(result.msg)
                    //1、关闭模态框
                    $("#empAddModal").modal("hide");
                    //2、来到最后一页，显示刚才添加的
                    to_page(totalRecord);
                }else {
                    //显示失败信息
                    if (undefined != result.extend.errorFileds.email){
                        show_validate_msg("#email_add_input","error",result.extend.errorFileds.email);
                    }
                    if (undefined != result.extend.errorFileds.name){
                        show_validate_msg("#name_add_input","error",result.extend.errorFileds.name);
                    }
                }

            }
        });

    });




    //打开模态框
    $("#emp_add_btn").click(function () {
        //清空表单数据
        reset_form("#empAddModal form");
        //发送ajax请求，查出部门信息，显示在下拉列表中
        getDepts("#dept_add_select");
        $("#empAddModal").modal({
            //让模态框显示出来
            backdrop :"static"
        });
    });
    to_page(1);
})

/*
    click单击事件添加失败
    给修改和删除按钮添加单击事件有两种：
    1、在创建时添加
    2、使用live()。Jquery3.0以后改名为on()
*/
$(document).on("click",".edit_btn",function () {
    //加载部门
    getDepts("#dept_update_select");
    //查出员工信息，回显员工信息
    //$(this)代表当前点击的那个按钮的选择器
    getEmp($(this).attr("edit-id"));
    //把员工id传递给模态框的更新按钮
    $("#emp_update_modal_btn").attr("edit-id",$(this).attr("edit-id"));
    $("#empUpdateModal").modal({
        //让模态框显示出来
        backdrop :"static"
    });
});

$(document).on("click",".check_item",function(){
    // 判断当前训中的元素和页面上总元素数量是否相等
    var flag = $(".check_item:checked").length == $(".check_item").length;
    $("#check_all").prop("checked",flag);
});

$(document).on("click",".delete_btn",function () {
    //1、弹出是否确认删除的对话框
    var name = $(this).parents("tr").find("td:eq(2)").text();
    var id = $(this).attr("del-id");
    if (confirm("确认删除【" + name +"】的信息吗？")){
        $.ajax({
            url:"emp/" +id,
            type:"delete",
            dataType:"json",
            success:function (result) {
                alert(result.msg);
                to_page(currentPage);
            }
        });
    }
});



//清空表单及内容
function reset_form(ele){
    //清空表单内容
    $(ele)[0].reset();
    //清空表单样式
    $(ele).find("*").removeClass("has-error has-success");
    $(ele).find(".help-block").text("");

}

//校验表单数据
function validate_add_form(){
    //1、拿到要校验的数据，使用正则表达式
    var name = $("#name_add_input").val();
    //姓名正则表达式
    var regName = /^[\u4E00-\u9FA5\uf900-\ufa2d·s]{2,20}$/;
    if (! regName.test(name)){
        show_validate_msg("#name_add_input","error","姓名格式不合法");
        return false;
    }else {
        show_validate_msg("#name_add_input","success","");
    }

    //校验邮箱的信息
    var email = $("#email_add_input").val();
    var regEmail = /^[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+(\.[a-zA-Z0-9_-]+)+$/;
    if (! regEmail.test(email)){
        show_validate_msg("#email_add_input","error","邮箱格式不合法");
        return false;
    }else {
        show_validate_msg("#email_add_input","success","");
    }
    return true;
}

//清除当前元素的校验状态
function show_validate_msg(ele,status,msg){

    $(ele).parent().removeClass("has-error has-success");
    $(ele).next("span").text("");
    if ("success" == status){
        $(ele).parent().addClass("has-success");
    }else if ("error" == status){
        $(ele).parent().addClass("has-error")
    }
    $(ele).next("span").text(msg);
}

function getEmp(id){
    $.ajax({
        url:"emp/" + id,
        type:"get",
        dataType:"json",
        success:function (result) {
            var emp = result.extend.emp;
            $("#name_update_input").val(emp.name);
            $("#email_update_input").val(emp.email);
            // Jquery操作选择框同样使用val属性
            $("#empUpdateModal input[name=gender]").val([emp.gender]);
            $("#dept_update_select").val([emp.did]);
        }
    });
}

function getDepts(ele) {
    //清空下拉菜单
    $(ele).empty();
    $.ajax({
        url:"depts",
        type:"get",
        dataType: "json",
        success:function (result){
            console.log(result);
            $.each(result.extend.depts,function (index,item) {
                var optionEle = $("<option></option>").append(item.name).attr("value",item.id);
                optionEle.appendTo(ele);
            });
        }
    });
}

function to_page(pn){
    $.ajax({
        url: "emps",
        type :"get",
        data: {"pn":pn},
        dataType:"json",
        success:function(result){
            console.log(result);
            // 1.解析并构建员工列表
            build_emps_table(result);
            //2.解析并构建分页信息
            build_page_info(result);
            //3.解析并构建分页导航
            build_page_nav(result);
        }
    });
}

function  build_emps_table(result){
    //清空table
    $("#emps_table tbody").empty();
    var emps = result.extend.pageInfo.list;
    $.each(emps,function(index,item){
        var checkBoxTd = $("<td></td>").append("<input type='checkbox' class='check_item' />");
        var empIdTd = $("<td></td>").append(item.id);
        var empNameTd = $("<td></td>").append(item.name);
        var genderTd = $("<td></td>").append(item.gender == 'M' ? '男' : '女');
        var emailTd = $("<td></td>").append(item.email);
        var deptNameTd = $("<td></td>").append(item.dept.name);

        var editBtn = $("<button></button>").addClass("btn btn-primary btn-sm edit_btn")
            .append($("<span></span>").addClass("glyphicon glyphicon-pencil")).append("修改");
        //给按钮添加一个属性。button标签原则上有没有一个属性叫edit-id?
        // edit-id这个属性名是我乱写的。实际上这个属性是我们自定义的，没有任何意义。只是为了存储id
        editBtn.attr("edit-id",item.id);
        // editBtn.click(function () {
        //     alert("edit_btn");
        // });
        var delBtn = $("<button></button>").addClass("btn btn-danger btn-sm delete_btn")
            .append($("<span></span>").addClass("glyphicon glyphicon-trash")).append("删除");
        delBtn.attr("del-id",item.id);
        var btnTd = $("<td></td>").append(editBtn).append(" ").append(delBtn);
        $("<tr></tr>")
            .append(checkBoxTd)
            .append(empIdTd)
            .append(empNameTd)
            .append(genderTd)
            .append(emailTd)
            .append(deptNameTd)
            .append(btnTd)
            .appendTo("#emps_table tbody");
    });
}

function build_page_info(result){
    $("#page_info_area").empty();
    $("#page_info_area").append("当前第【" + result.extend.pageInfo.pageNum + "】页，总【" + result.extend.pageInfo.pages
        + "】页，共【" + result.extend.pageInfo.total + "】条记录。");
    totalRecord = result.extend.pageInfo.total;
    currentPage = result.extend.pageInfo.pageNum;
}

function build_page_nav(result){
    $("#page_nav_area").empty();
    var ul = $("<ul></ul>").addClass("pagination");

    var firstPageLi = $("<li></li>").append($("<a></a>").append("首页"));
    var prePageLi = $("<li></li>").append($("<a></a>").append("上一页"));

    if(!result.extend.pageInfo.hasPreviousPage){
        firstPageLi.addClass("disabled");
        prePageLi.addClass("disabled");
    }else{
        firstPageLi.click(function(){
            to_page(1);
        });
        prePageLi.click(function(){
            to_page(result.extend.pageInfo.pageNum - 1);
        });
    }

    ul.append(firstPageLi).append(prePageLi);

    $.each(result.extend.pageInfo.navigatepageNums,function(index,item){
        var numLi = $("<li></li>").append($("<a></a>").append(item));
        if(result.extend.pageInfo.pageNum == item){
            numLi.addClass("active");
        }
        numLi.click(function(){
            to_page(item);
        });
        ul.append(numLi);
    });

    var nextPageLi = $("<li></li>").append($("<a></a>").append("下一页"))
    var lastPageLi = $("<li></li>").append($("<a></a>").append("尾页"))
    if(!result.extend.pageInfo.hasNextPage){
        nextPageLi.addClass("disabled");
        lastPageLi.addClass("disabled");
    }else{
        nextPageLi.click(function(){
            to_page(result.extend.pageInfo.pageNum + 1);
        });
        lastPageLi.click(function(){
            to_page(result.extend.pageInfo.pages);
        });
    }

    ul.append(nextPageLi).append(lastPageLi);
    var navEle = $("<nav></nav>").append(ul);
    navEle.appendTo("#page_nav_area");
}

