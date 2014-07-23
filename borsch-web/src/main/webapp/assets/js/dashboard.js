var setMessageRead = function(target, messageID) {
    var targetObject = $(target);
    $.ajax({
        type: "POST",
        url:  "dashboard/readMessage",
        data: {
            "messageID" : messageID,
            "ajax" : true
        },

        statusCode:{
            200: function(){
                targetObject.parent().removeClass("new-message").addClass("read-message");
                targetObject.css("display","none");
            },

            500: function(){
                alert("Something got wrong");
            }
        }
    });
};

$(".new-message-to-admins-button").on("click", function() {
    if ($("#createMessage").css("display") != "none") {
        $("#createMessage").css("display", "none");
    } else {
        var $this = $(this);
        var receiverName = "Admin";
        $("#receiverID").val(-1);
        $("#receiverName").text("To: " + receiverName);
        $("#createMessage").css("display", "inline");
    }
});

$(".new-message-to-all-button").on("click", function() {
    if ($("#createMessage").css("display") != "none") {
        $("#createMessage").css("display", "none");
    } else {
        var $this = $(this);
        var receiverName = "All";
        $("#receiverID").val(0);
        $("#receiverName").text("To: " + receiverName);
        $("#createMessage").css("display", "inline");
    }
});

$(".new-message-button").on("click", function() {
    if ($("#createMessage").css("display") != "none") {
        $("#createMessage").css("display", "none");
    } else {
        var $this = $(this);
        var receiverName = $($this.siblings(".user-name")[0]).text();
        $("#receiverID").val($($this.siblings(".id-input")[0]).val());
        $("#receiverName").text("To: " + receiverName);
        $("#createMessage").css("display", "inline");
    }
});

var getMessage = function(target, messageID) {
    $.ajax({
        type: "POST",
        url:  "dashboard/getMessage",
        data: {
            "messageID" : messageID,
            "ajax" : true
        },
        success: function(data) {
            showMessage(target, data, messageID);
        }
    });
};


var showMessage = function(target, message, messageID) {
    var $target = $(target);
    var shortMessage = message.substr(0, 30);
    if (message.length > 30) {
        shortMessage += "...";
    }

    if ($target.parent().hasClass("new-message")) {
        setMessageRead($target.next("button"), messageID);
        $target.parent().removeClass("new-message").addClass("read-message");
        $target.next("button").css("display","none");
    }

    if ($target.text() == message) {
        $target.text(shortMessage);
    } else {
        $target.text(message);
    }
}

$("#search").on("keyup", function() {
    var text = $(this).val();
    $(".user-cell").each(function() {
        var $this = $(this);
        if ($($this.children(".user-name")[0]).text().search(text) == -1) {
            $this.css("display", "none");
        } else {
            $this.css("display", "block");
        }
    });

});

$("#cancelButton").on("click", function() {
    $("#createMessage").css("display", "none");
    return false;
})

$("#sendMessageButton").on("click", function() {
    var message = $("#new-message-textarea").val();
    var receiverID = $("#receiverID").val();
    $.ajax({
        type: "POST",
        url:  "dashboard/sendMessage",

        data: {
            "receiverID" : receiverID,
            "messageText" : message,
            "ajax" : true
        },

        statusCode:{
            200: function(){
                $("#createMessage").css("display", "none");
                $("#new-message-textarea").val("");
                alert("Message was successfully sent.");
            },

            500: function(){
                alert("Something got wrong!");
            }
        }
    });
    return false;
});


function startGettingMessages() {
    if ($("#messages-page").length != 0) {
        setInterval(function() {
            $.ajax({
                type: "POST",
                url: "dashboard/getMessages",
                data: {
                    oldMessagesCount: $("#messages-board").children().length
                },
                success: function(data) {
                    parseMessages(data);
                },
                error: function(jqXHR, textStatus, error){
                    console.log(textStatus);
                    console.log(error);
                }
            });
        }, 5000);
    };
};

window.onload = startGettingMessages;

var parseMessages = function(messages) {
    var messagesHTML = "";
    messages.forEach(function(message) {
        if (message.readStatus == false) {

            messagesHTML += '<div class="cell new-message bottom-bordered">'+
                            '<div class="pull-left author-cell full-height">'+
                            'From: ' + message.senderName +
                            '</div>'+
                            '<div class = "pull-left margined-sides message-area" onclick='+
                            "'getMessage(this," + message.id + ")'>" +
                            message.shortText + "</div>" + '<button onclick="setMessageRead(this,'+ message.id+ ')"' +
                            "title = 'Mark as read'"+ 'class = "action-button"></button> </div>';
        } else {
            messagesHTML += '<div class="cell old-message right-bordered bottom-bordered">' +
                            '<div class="pull-left author-cell full-height">' +
                            "From: " + message.senderName +
                            "</div>" +
                            '<div class = "pull-left margined-sides message-area" onclick=' +
                            "'getMessage(this," + message.id + ")'>" +
                            message.shortText + "</div> </div>";

        };
    });

    document.getElementById("messages-board").innerHTML += messagesHTML;
};

