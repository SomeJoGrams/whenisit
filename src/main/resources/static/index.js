
//$(document).ready(function() {
//    $.ajax({
//        //url: "http://rest-service.guides.spring.io/greeting"
//        url: "localhost:8080/date"
//    }).then(function(data) {
//       $('.calculatedDate').append(data.content);
//    });
//});

var optionsTrDate = { hour: 'numeric',minute: 'numeric', weekday: 'long', year: 'numeric', month: 'long', day: 'numeric',timeZone :"UTC"};

$("#jDatepicker").datepicker();
var options = { hour: 'numeric',minute: 'numeric', weekday: 'long', year: 'numeric', month: 'long', day: 'numeric' };

// date picker code
var currentDate = new Date();

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
//(text,obj){ console.log(text)})
                    // $("#selectedDate").text()
//<!--    "de-DE");-->

    // select menu code
$( "#city" )
  .selectmenu()
  .selectmenu( "menuWidget" )
    .addClass( "overflow" );

$( "#timezone" )
  .selectmenu()
  .selectmenu( "menuWidget" )
    .addClass( "overflow" );

$( "#hourSelection" )
 .selectmenu()
 .selectmenu( "menuWidget" )
   .addClass( "overflow" );

function updateUIDate(){
    $("#selectedDate").text(currentDate.toLocaleDateString("de-DE",options));
}

$("#hourSelection").on("selectmenuselect",
                        function(event,ui){
                              currentDate.setHours(ui.item.value);
                              updateUIDate();
                                    });

function requestDate(){
    return $.ajax({method: "GET",
            url: "http://localhost:8080/date"
            })
}

function postDate(){
    return $.ajax({method: "POST",
            url: "http://localhost:8080/date",
            data: {currentDateString:currentDate.toISOString(),toTimeZone:"UTC",
                        } //  sends the time with zero utc offset
            })
}

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
                optionsTrDate.timeZone = data.timeZone;
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

