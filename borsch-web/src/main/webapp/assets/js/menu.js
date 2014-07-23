var daysCount = 0;
var activeDays = {
    0: false, 1: false, 2: false, 3: false, 4: false, 5: false, 6: false
};
var weekDeviationFromCurrent = 0;

var addMenuItemFormSubmit = function () {
    var dishName = $(this).children('input[placeholder="dish name"]');
    var price = $(this).children('input[placeholder="price"]');
    var day = $(this).parent().parent().attr('name');

    if(!$.isNumeric(price.val())) {
        $(this).siblings('strong').show();
        return false;
    }

    $.ajax({
        url: 'menu/ajax/addMenuItem',
        data: {
            weekDeviation: weekDeviationFromCurrent,
            dishName: dishName.val(),
            price: price.val(),
            day: day
        },
        type: 'POST'
    });

    $(this).parent().parent().children('ul').append('<li>' + dishName.val() + ':' + price.val() + '</li>'); // HORRIBLE CODE !!!
    dishName.val('');
    price.val('');

    $(this).siblings('strong').fadeOut();
    return false;
}

var clearWeek = function () {
    var menuItems = $('#menuItems>div');
    $.each(menuItems, function () {
        activeDays[$(this).attr('name')] = false;
        if ($(this).attr('name') != undefined) {
            $(this).remove();
        }


    });
    daysCount = 0;
    var daysMenu = $('#daysMenu>ul>li');
    $.each(daysMenu, function () {
        $(this).fadeIn();
    });
}

var showCurrentWeek = function() {
    $.ajax({
        url: 'menu/ajax/showCurrentWeek',
        data: {
            weekDeviation: weekDeviationFromCurrent
        },
        dataType: 'text',
        statusCode: {
            200: function(date){
                $('#menuWeekChooser>div').text(date);
            }
        }
    });
}

$(document).ready(function () {
    getMenuItems();
    showCurrentWeek();
});

$('#menuWeekChooser>button').click(function () {
    weekDeviationFromCurrent += +$(this).val();
    showCurrentWeek();
    clearWeek();
    getMenuItems();
});

var addDay = function (itemInDayList) {
    if (activeDays[itemInDayList.val()]) {
        return;
    }
    var skeleton = $('#daySkeleton').clone();
    skeleton.removeAttr('id');
    skeleton.attr('name', itemInDayList.val());
    skeleton.children('h3').html(itemInDayList.text());
    if (daysCount % 2 === 0) {
        skeleton.addClass('reddish-background');
    } else {
        skeleton.addClass('yellowish-background');
    }
    skeleton.appendTo('#menuItems');
    daysCount++;
    if (daysCount === 7) {
        $('#daysMenu').fadeOut('fast');
    }
    itemInDayList.slideUp('slow');
    activeDays[itemInDayList.val()] = true;
    $('form[name="addDish"]').on('submit', addMenuItemFormSubmit);
}

var addMenuItemInDay = function (menuItem) {
    $.each($('#menuItems>div'), function () {
        var name = this.getAttribute('name');
        if (name == menuItem.day) {
            $(this).children('ul').append('<li>' + menuItem.name + ':' + menuItem.price + '</li>')
        }

    });
}

var getMenuItems = function () {
    $.getJSON('/menu/ajax/menuItems', {
        weekDeviation: weekDeviationFromCurrent
    }, function (data) {
        $.each(data, function () {
            var menuItem = this;
            if (activeDays[menuItem.day] == false) {
                $.each($('#daysMenu>ul>li'), function () {
                    if ($(this).val() === menuItem.day) {
                        addDay($(this));
                        return;
                    }
                });
            }

            addMenuItemInDay(menuItem);
        });
    });
}

$('#daysMenu>ul>li').click(function () {
    addDay($(this));
});
