
//$(document).ready(function() {
//    $.ajax({
//        //url: "http://rest-service.guides.spring.io/greeting"
//        url: "localhost:8080/date"
//    }).then(function(data) {
//       $('.calculatedDate').append(data.content);
//    });
//});

var defaultFromTimeZone = "UTC" // TODO calculate it
var defaultToTimeZone = "UTC"

var optionsTrDate = { hour: 'numeric',minute: 'numeric', weekday: 'long', year: 'numeric', month: 'long', day: 'numeric',timeZone :"UTC"};
var options = { hour: 'numeric',minute: 'numeric', weekday: 'long', year: 'numeric', month: 'long', day: 'numeric' };


var currentDate = new Date();

var selectedFromTimezone = defaultFromTimeZone;
var selectedToTimezone = defaultToTimeZone;

console.log("the date in utc zero format",currentDate.toISOString())
$("#selectedDate").text(currentDate.toLocaleDateString("de-DE",options));
$( "#datepicker" ).datepicker();
$( "#datepicker" ).datepicker("option","onSelect",function(date,obj) {

        currentDate = $( "#datepicker" ).datepicker("getDate");
        let timeAndHours = new Date(); // TODO when hours are selected only then take the old current date?,maybe add an event listener
        currentDate.setHours(timeAndHours.getHours())
        currentDate.setMinutes(timeAndHours.getMinutes())
        $("#selectedDate").text(currentDate.toLocaleDateString("de-DE",options));
        })

    // select menu code
$( "#city" )
  .selectmenu()
  .selectmenu( "menuWidget" )
    .addClass( "overflow" );

$( "#toTimeZoneSelection" )
  .selectmenu()
  .selectmenu( "menuWidget" )
    .addClass( "overflow" );

$( "#hourSelection" )
 .selectmenu()
 .selectmenu( "menuWidget" )
   .addClass( "overflow" );

$("#fromTimeZoneSelection")
            .selectmenu()
            .selectmenu("menuWidget")
            .addClass("overflow");

console.log($("#fromTimeZoneSelection").selectmenu())

$("#hourSelection").on("selectmenuselect",
                        function(event,ui){
                              currentDate.setHours(ui.item.value);
                              updateUIDate();
                                    });

$("#fromTimeZoneSelection").on("selectmenuselect",
                        function(event,ui){
                              selectedFromTimezone = ui.item.value;
                                    });

function addDefaultOnRefresh($jSelectmenu){
    const $evtChange = $jSelectmenu.on("change", function(event,ui){
    $jSelectmenu.selectmenu("refresh")
    });
    $evtChange.val(defaultFromTimeZone).change()
}




// update the selected values on refresh to the defaults

$("#toTimeZoneSelection").on("selectmenuselect",
                        function(event,ui){
                              selectedToTimezone = ui.item.value;
                                    });

addDefaultOnRefresh($("#fromTimeZoneSelection"))
addDefaultOnRefresh($("#toTimeZoneSelection"))


function updateUIDate(){
    $("#selectedDate").text(currentDate.toLocaleDateString("de-DE",options));
}

function requestDate(){
    return $.ajax({method: "GET",
            url: "http://localhost:8080/date"
            })
}

function postDate(){
    return $.ajax({method: "POST",
            url: "http://localhost:8080/date",
            data: {currentDateString:currentDate.toISOString(),toTimeZone:selectedToTimezone,fromTimeZone:selectedFromTimezone,
                    fromUTCOffset:currentDate.getTimezoneOffset()
                        } //  sends the time with zero utc offset!
            })
}
// new idea instead of sending the from time zone we send an offset
// so the utc date, the offset and the goal date will be send
// the the goal date is calculated by adding the offset on the server to the utc date
// finally the goal date is calculated!
// this way we don't have to use for the from offset


//$(document).on("click","#requestButton",function() {
//            requestDate().then(function(data) {
//               $('.calculatedDate').append(data.content);
//
//               console.log(data), function(failData){
//                    console.log("error getting the request through")
//               }
//            });
//})
$(document).on("click","#requestButton",function() {
            postDate().then(function(data) {
                let receivedDate = new Date(data.date);
                optionsTrDate.timeZone = "UTC"//set the value to UTC bc of possible missing implementations for other timezone
//                receivedDate.setUTCHours(data.utcoffsetHours); // TODO set time zone
                $("#time1").text(receivedDate.toLocaleDateString("de-DE",optionsTrDate));
                $("#zone1").text(data.timeZone);
                console.log("received object");
               console.log(data)
               , function(failData){
                    console.log("error getting the request through")
               }
            });
})

