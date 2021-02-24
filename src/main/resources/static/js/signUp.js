layui.use(['layer', 'form'], function () {
    var layer = layui.layer,
        form = layui.form,
        $ = layui.jquery;
    getStoreList();
    form.render('radio');
    form.on('submit(signUp)', function (data) {
        var data = {
            "username": data.field.username,
            "password": md5(data.field.password),
            "realName": data.field.realName,
            "email": data.field.email,
            "storeId": data.field.storeId
        }
        $.ajax({
            type: "post", //指定提交的类型
            url: "/user/addUser",    //传输地址的URL
            data: JSON.stringify(data), //data代表我们的数据  key -value类型的数据
            dataType: 'JSON',
            contentType: "application/json",
            async: false, //success和error代表是否返回成功，既后台给前台传输数据是否成功
            success: function (res)//success、error代表是否返回成功
            {
                if (res.code == 200) {
                    layer.msg("注册成功！！");
                    window.location.href = "/signIn";
                } else if (res.code == 20001) {
                    layer.msg("已存在相同用户名的用户");
                } else {
                    layer.msg("操作失败!");
                }
            },
            error: function (XMLHttpRequest, textStatus, errorThrown) {
                layer.msg("网络错误！");
            }
        });
        return false;
    });

    function getStoreList() {
        $.ajax({
            type: "get", //指定提交的类型 get对应 doGet()方法，post--->doPost()犯法
            url: "/Store/getStoreIdAndNameList",    //传输地址的URL
            data: {}, //data代表我们的数据  key -value类型的数据
            dataType: 'JSON',
            async: false, //success和error代表是否返回成功，既后台给前台传输数据是否成功
            success: function (res)//success、error代表是否返回成功
            {
                if (res.code == 200) {
                    var html = '<option value="">请选择一个门店</option>';
                    for (var i = 0; i < res.data.length; i++) {
                        html += '<option value="' + res.data[i].id + '">' + res.data[i].name + '</option>';
                    }
                    $("#storeSelect").html(html);
                    form.render("select");
                } else {
                    layer.msg("获取门店列表失败，请检查网络连接");
                }
            },
            error: function (XMLHttpRequest, textStatus, errorThrown) {
                layer.msg("网络错误！");
            }
        });
    }
});