
var veivParam = "All";

var dateFormat="yy-mm-dd";
var startDate;
var endDate;
var date = new Date();

$(function(){

    startDate = new Date(date.getFullYear(), date.getMonth(), date.getDate() - (date.getDay()?(date.getDay()-1):6));
    changeWeek(startDate);
});

var allOrders = [];
var paidOrders = [];
var unpaidOrders = [];


function createOrder(order) {
    var container = document.createElement('div');

    container.innerHTML = '<div class="order-element order-'+(order.paid?'':'un')+'paid" name='+order.orderId+'>'+
        '<div class="order-info">'+
        '<strong class="order-name">'+ order.name + '</strong>'+
        '<div class="order-login">'+order.orderCost+'</div>'+
        '</div>'+
        '<div class="order-button-bloc">'+
        '<button name="message">Message</button><br>'+
        (order.paid?'':'<button name="payment">Order payment</button>')+
        '</div></div>';

    return container.firstChild;
};

function addOrder(order){
    var $newOrder =$(createOrder(order));
    $("#orders-main-panel").append($newOrder);
    $newOrder.css({display:"none"}).fadeIn(350);
};

function createMass(){
    paidOrders = [];
    unpaidOrders = [];
    for(var or in allOrders){
        if(allOrders[or].paid){
            paidOrders.push(allOrders[or]);
        }
        else{
            unpaidOrders.push(allOrders[or]);
        }
    }
}

function veivOrders(){
    var now;
    switch (veivParam)
    {
        case "Paid":now = paidOrders;break;
        case "Unpaid":now = unpaidOrders;break;
        default :now = allOrders;break;
    }
    return now;
}

function createMessageDialog(id, name) {
    var container = document.createElement('div');

    container.innerHTML = '<div class="message-dialog" name='+id+'>'+
        '<strong>Message to '+name+'</strong><br>'+
        '<textarea class="dialog-message-aera" rows="4"></textarea><br>'+
        '<button name="sendMessage">Send</button><button name="closeMessageDialog">Close Message</button>'
    '</div>';

    return container.firstChild;
};

function showCover(mess) {
    var coverDiv = document.createElement('div');
    coverDiv.id = 'cover-div';
    coverDiv.innerHTML = mess||"";
    document.body.appendChild(coverDiv);
}

function messageButton(){
    showCover();
    var $orderDiv =  $(this).parent().parent();
    var $dialog=$(createMessageDialog($orderDiv.attr("name"),$orderDiv.find(".order-name").text()));
    $dialog.appendTo("body");
    $("button[name='closeMessageDialog']").click(closeMessageDialog);
    $(".message-dialog button[name='sendMessage']").click(sendMessage);
};


function sendMessage(){
    $(".message-dialog").remove();
    $.ajax({
        type: "POST",
        url:  "dashboard/sendMessage",

        data: {
            "receiverID" : $(".message-dialog").attr('name'),
            "messageText" : $("dialog-message-aera").val(),
            "ajax" : true
        },

        statusCode:{
            200: function(){
                $("#createMessage").css("display", "none");
                alert("Message was successfully sent.");
                $("#cover-div").remove();

            }
        },
        error:
            function(){
                alert("Something got wrong!");
                $("#cover-div").remove();
        }
    });
    showCover('Wait request');
};

function closeMessageDialog(){
    $("#cover-div").remove();
    $(".message-dialog").remove();
}

function orderPayment(){
    var ts = $(this);
    $.ajax({
        type: "Get",
        url:  "order/ajax/orderPayment",

        data: {
            "id" : ts.parent().parent().attr('name'),
            "ajax" : true
        },

        statusCode:{
            200: function(){
                ts.parent().parent().addClass("order-paid");
                ts.remove();
                $("#cover-div").remove();
            }
        },
        error:
            function(){
                alert("Something got wrong!");
                $("#cover-div").remove();
            }
    });
    showCover("Wait request");
}

function readd()
{
    $("#orders-main-panel").empty();
    var now = veivOrders();
    for(var i=0;i<now.length; i++ ){
        addOrder(now[i]);
    }
    if(!now.length)
    noElements();
    $(".order-element button[name='message']").click(messageButton);
    $(".order-element button[name='payment']").click(orderPayment);
}


function noElements(mess){
    if($("#orders-status").length == 0)
        $("<div>").text(mess||"No orders").attr("id","orders-status")
            .css({display:"none"}).appendTo("#orders-main-panel").fadeIn(500);

};

function startSearch(){
    var notFound = true;
    var searchText = $(".search-query")[0].value.toLowerCase();
    var info = $(".order-info").each(function(){
        var $this = $(this);
        if(!$this.text().toLowerCase().search(searchText)){
            $this.parent().fadeIn(350);
            notFound = false;
        }
        else
        {
            $this.parent().fadeOut(350);
        }
    });
    if(notFound){
        noElements("Not found");
    }
    else{
        $("#orders-status").remove();
    }
}

$("#search-order").keyup(startSearch);

$(".orders-content .tabs li").click(function(){
    $(".tabs li.active").removeClass("active");
    $(this).addClass("active");
    veivParam = $(this).text().trim();
    readd();
    $("#search-order").focus();
    startSearch();
});


function changeWeek(date){
    startDate = new Date(date.getFullYear(), date.getMonth(), date.getDate() - (date.getDay()?(date.getDay()-1):6));
    endDate = new Date(startDate.getFullYear(), startDate.getMonth(), startDate.getDate()  + 6);
    $('#startDate').text($.datepicker.formatDate( dateFormat, startDate));
    $('#endDate').text($.datepicker.formatDate( dateFormat, endDate));
    $.getJSON('/order/ajax/orders', {
        date: $('#startDate').text()
    }, function(json){
        allOrders = json;
        createMass();
        readd();
    });
};

function createDataPicker(func){
    showCover();
    $('#date-picker').datepicker( {
        showOtherMonths: true,
        selectOtherMonths: true,
        firstDay:1,
        onSelect: function() {
            var date = $(this).datepicker('getDate');
            func(date);
            $("#cover-div").remove();
            $('#date-picker').datepicker('destroy') ;
        }
    });
};

$("#week-nav a[href='#set']").click(function(){
    createDataPicker(changeWeek);
});

$("#week-nav a[href='#next']").click(function(){
    changeWeek(new Date(startDate.getFullYear(), startDate.getMonth(), startDate.getDate()  + 7));
});

$("#week-nav a[href='#prev']").click(function(){
    changeWeek(new Date(startDate.getFullYear(), startDate.getMonth(), startDate.getDate()  - 7));
});

