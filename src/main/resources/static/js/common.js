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