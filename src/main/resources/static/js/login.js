function login() {

    var username = document.getElementById("username");
    var pass = document.getElementById("password");

    if (username.value == "") {

        alert("请输入用户名");

    } else if (pass.value  == "") {

        alert("请输入密码");

    }
    login2(username.value,pass.value);
}

function login2(name,value){
    $.ajax({
        type : "POST",
        dataType: "json",
        url : "login/login2",
        data:{'username':name,'password':value},
        success : function(data) {
            var s = data.status;
            if(s=='200'){
                window.location.href="/index";
            }else{
                alert(data.msg);
            }
        }
    });
}