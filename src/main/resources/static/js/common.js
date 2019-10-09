function  aa() {
    getName();
}

function getName(){
    $.ajax({
        type : "POST",
        dataType: "json",
        url : "login/getName",
        success : function(data) {
            $("#name").append(data.id);
        }
    });
}
//jq设置全局ajax参数，401是自己设置的状态，状态为401时，自动跳转到登录页重新登录。
$.ajaxSetup({
    type: "POST",
    complete: function(jqXHR, textStatus){
        switch (jqXHR.status){
            case(500):
                alert("服务器系统内部错误");
                break;
            case(401):
                window.location.href="/";
                break;
            case(403):
                alert("无权限执行此操作");
                break;
            case(408):
                alert("请求超时");
                break;
            case(200):
                break;
            default:
                alert("未知错误");
        }
    },
    success: function(data){
        alert("操作成功");
    }
});