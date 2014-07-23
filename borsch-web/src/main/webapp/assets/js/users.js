$(".inputTextSearch").focus(function(){
    $(this).attr('placeholder','');
});
$(".inputTextSearch").focusout(function(){
    $(this).attr('placeholder','Search for...');
});

$("input:checkbox").on('click',function(){
    var userId = $(this).attr("id");
    var role;
    if($(this).is(":checked")){
        role="ADMIN";
    }else{
        role="USER";
    }

    $.ajax({
        type:"POST",
        url:"users/changeRole",
        data:{
            "userId" : userId,
            "role": role
        },
        statusCode:{
             230: function(){
                alert("Updating was failed");
             }
        }
    })
});

$(".inputTextSearch").keyup(function(){
    var searchValue = $(this).val().trim().toUpperCase();
    var count=0;

    if($(".notFound").is(":visible")){
        $(".notFound").css("display","none");
    }

    $(".nameAdmin").each(function(index,currentElement){
        var result = $(currentElement).text().trim().toUpperCase().indexOf(searchValue);
        if(result ==-1){
            $(currentElement).parent().css("display","none");
        }else{
            $(currentElement).parent().css("display","block");
            count++;
        }
    });
    if(count==0){
        $(".notFound").show();
    }
});



