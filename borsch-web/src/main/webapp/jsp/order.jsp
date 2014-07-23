<link href="assets/css/order.css" rel="stylesheet">
<div class="orders-content">
    <div class="tabbed_area">
        <ul class="tabs">
            <li class="active"><a href="#">All</a></li>
            <li><a href="#">Paid</a></li>
            <li><a href="#">Unpaid</a></li>
        </ul>


        <div class="tabs-content">
            <div class="top-search-wrap">
                <form class="navbar-search pull-left">
                    <input type="text" id="search-order" class="search-query input-xxlarge" placeholder="Search">
                </form>
            </div>

            <div id="orders-main-panel">

            </div>
        </div>
    </div>
    <div class="orders-side-panel">
        <ul id="week-nav" class="nav-list">
            <li class="nav-header">Week:<br><span id="startDate"></span> - <span id="endDate"></span></li>
            <li><a href="#set">Set week</a></li>
            <li><a href="#next">Next week</a></li>
            <li><a href="#prev">Prew week</a></li>
        </ul>
        <ul id="export-nav" class="nav-list">
            <li class="nav-header">Export order</li>
            <li><a href="#day">On day</a></li>
            <li><a href="#week">On week</a></li>
        </ul>
    </div>
</div>
<div id="date-picker"></div>
<script>

    $(function(){
        createMass();
        readd();
    });

</script>