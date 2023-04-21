
//$(document).ready(function() {
//    $.ajax({
//        //url: "http://rest-service.guides.spring.io/greeting"
//        url: "localhost:8080/date"
//    }).then(function(data) {
//       $('.calculatedDate').append(data.content);
//    });
//});
var timeZoneSelection = ["Europe/Berlin","America/Marigot","Asia/Tokyo"] // TODO request dynamically, fix

const defaultFromTimeZone = timeZoneSelection[0];
const defaultToTimeZone = timeZoneSelection[0];


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
//$( "#city" )
//  .selectmenu()
//  .selectmenu( "menuWidget" )
//    .addClass( "overflow" );

//$( "#toTimeZoneSelection" )
//  .selectmenu()
//  .selectmenu( "menuWidget" )
//    .addClass( "overflow" );

$( "#hourSelection" )
 .selectmenu()
 .selectmenu( "menuWidget" )
   .addClass( "overflow" );

$( "#minuteSelection" )
 .selectmenu()
 .selectmenu( "menuWidget" )
   .addClass( "overflow" );


$("#hourSelection-menu").css("height","calc(0.4*100vh)");

$("#minuteSelection-menu").css("height","calc(0.4*100vh)");

//$("#fromTimeZoneSelection")
//            .selectmenu()
//            .selectmenu("menuWidget")
//            .addClass("overflow");



$("#fromTimeZoneSelection")
            .autocomplete({source: timeZoneSelection})

$( "#toTimeZoneSelection" )
            .autocomplete({source: timeZoneSelection})

$("#clearAll").button()
$("#clearAll").on("click", function(event,ui){
                           $("#resultDates >").remove()
})

//
//console.log($("#fromTimeZoneSelection").selectmenu())

$("#hourSelection").on("selectmenuselect",
                        function(event,ui){
                          let currentSelection = ui.item.value;
                          if (currentSelection == "currentTime"){
                            currentDate = new Date();
                          }
                          else{
                            currentDate.setHours(currentSelection);
                          }
                          updateUIDate();
                                    });

$("#minuteSelection").on("selectmenuselect",
                        function(event,ui){
                        let currentSelection = ui.item.value;
                        currentDate.setMinutes(currentSelection);
                        updateUIDate();
                                    });


//$("#fromTimeZoneSelection").on("selectmenuselect",
//                        function(event,ui){
//                              selectedFromTimezone = ui.item.value;
//                                    });

$("#fromTimeZoneSelection").on("autocompleteselect",
                        function(event,ui){
                              selectedFromTimezone = ui.item.value;
                                    });

$("#toTimeZoneSelection").on("autocompleteselect",
                        function(event,ui){
                              selectedToTimezone = ui.item.value;
                              requestSubmission();

                                    });

//function addDefaultOnRefresh($jSelectmenu){
//    const $evtChange = $jSelectmenu.on("change", function(event,ui){
//    $jSelectmenu.selectmenu("refresh")
//    });
//    $evtChange.val(defaultFromTimeZone).change()
//}

function addDefaultOnRefresh($jSelectmenu){
    //const $evtChange = $jSelectmenu.on("change", function(event,ui){
    //$jSelectmenu.autocomplete("refresh")
    //});
    $jSelectmenu.val(defaultFromTimeZone);//.change()
}




// update the selected values on refresh to the defaults

//$("#toTimeZoneSelection").on("selectmenuselect",
//                        function(event,ui){
//                              selectedToTimezone = ui.item.value;
//                                    });

addDefaultOnRefresh($("#fromTimeZoneSelection"))
addDefaultOnRefresh($("#toTimeZoneSelection"))


function updateUIDate(){
    $("#selectedDate").text(currentDate.toLocaleDateString("de-DE",options));
}

function requestDate(){
    return $.ajax({method: "GET",
            url: "http://localhost:8080/date",
            data: {currentDateString:currentDate.toISOString(),toTimeZone:selectedToTimezone,fromTimeZone:selectedFromTimezone,
                                fromUTCOffset:currentDate.getTimezoneOffset()
                        }
            })
}

function requestTimeZones(typedLetters){
    return $.ajax({method:"GET",
                    url: "http://localhost:8080/timezones",
                    data: {typedLetters:typedLetters}
                    }
    )
}


//function postDate(){
//    return $.ajax({method: "POST",
//            url: "http://localhost:8080/date",
//            data: {currentDateString:currentDate.toISOString(),toTimeZone:selectedToTimezone,fromTimeZone:selectedFromTimezone,
//                    fromUTCOffset:currentDate.getTimezoneOffset()
//                        } //  sends the time with zero utc offset!
//            })
//}
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


function requestSubmission(){
            if (selectedToTimezone === selectedFromTimezone){
                console.log("the time stays the same") // TODO show dialog of some kind
                return;
            }
            $.ajax({method: "GET",
                        url: "http://localhost:8080/date",
                        data: {currentDateString:currentDate.toISOString(),toTimeZone:selectedToTimezone,fromTimeZone:selectedFromTimezone,
                                            fromUTCOffset:currentDate.getTimezoneOffset()
                            }
                        }).then(
                        function(data) {
                            let receivedDate = new Date(data.date);
                            optionsTrDate.timeZone = "UTC";//set the value to UTC bc of possible missing implementations for other timezone
                            if (data.date.length != 0){
                                createDate(currentDate,receivedDate,currentDate.toLocaleDateString("de-DE",options),receivedDate.toLocaleString("de-DE",optionsTrDate),data.fromTimeZone,data.toTimeZone)
                            }
                            console.log("received object");
                            console.log(data)
                            },function(failData){
                            console.log("error getting the request through")
                        })
}

//$()

$(document).on("click","#requestButton",requestSubmission);



var dateId = 0;


function createDate(fromDate,toDate,originalDate,time,fromZone,toZone){
    let newId = "date" + dateId
    let timeId = "time" + dateId
    let zoneId = "zone" + dateId
    let buttonId = dateId;
    let timeGoalClass = "class=timeGoalClass";
    let zoneGoalClass = "class=zoneGoalClass";
    console.log(fromDate.toLocale)
    console.log(toDate)
    $("#resultDates").append("<div class=\"timeUnit\">\n" +
                                 "<div id=timeId " + timeGoalClass + ">" + time + "</div>\n" +
                                 "<div id=zoneId " + zoneGoalClass + "> from " + fromZone + " to " + toZone + "</div>" + "\n" +
                                 "<div> from Time: " + originalDate + " </div>" + "\n" +
                                 "<div>\n" +
                                        "<span style=\"display:flex\">" +
                                         "<div>"+
                                             "<div" + " id=\"clockFrom" + dateId +"\" class=\"clockJo\">\n" +
                                                 "<div class=\"clockJoCenter\"></div>" +
                                                 "<div class=\"pointerHand longPointer\"></div>" +
                                                 "<div class=\"pointerHand shortPointer\"></div>" +
                                             "</div>" +
                                         "</div>" +
                                         "<div>"+
                                              "<div" + " id=\"clockTo" + dateId +"\" class=\"clockJo\">\n" +
                                                  "<div class=\"clockJoCenter\"></div>" +
                                                  "<div class=\"pointerHand longPointer\"></div>" +
                                                  "<div class=\"pointerHand shortPointer\"></div>" +
                                              "</div>" +
                                          "</div>" +
                                          "</span>" +
                                "<button" + " class=\"closeButton\"> X </button>\n" +
                                 "</div>\n" +
                             "</div>");
    createClock($("#clockFrom" + dateId))
    // TODO maybe remove whitespaces
    let optionsShort = { hour: 'numeric',minute: 'numeric'};
    let cutOffFromDate = fromDate.toLocaleDateString("de-DE",optionsShort)
    cutOffFromDate = cutOffFromDate.substring(cutOffFromDate.length - 5,cutOffFromDate.length)
    optionsShort.timeZone = "UTC" // set the timezone only for the received date(otherwise the from time is to early)
    let cutOffToDate = toDate.toLocaleString("de-DE",optionsShort)
    cutOffToDate = cutOffToDate.substring(cutOffToDate.length - 5,cutOffToDate.length)
    console.log(cutOffToDate,toDate.toLocaleString("de-DE",optionsShort))

    let localeDateFromHours = cutOffFromDate.substring(0,2)
    let localeDateFromMinutes = cutOffFromDate.substring(3,5)
    let localDateToHours = cutOffToDate.substring(0,2)
    let localeDateToMinutes =  cutOffToDate.substring(3,5)
    setClockTime($("#clockFrom" + dateId), localeDateFromHours,localeDateFromMinutes);
    createClock($("#clockTo" + dateId))
    setClockTime($("#clockTo" + dateId), localDateToHours,localeDateToMinutes);
    $(".closeButton").button()
    $(".closeButton").on("click", function(event,ui){
           // remove parent div -> the whole element
          $(event.currentTarget).parent().parent().remove()
//         $(event.currentTarget).parent().remove(); // remove the ui element on click
    })
    dateId += 1;
    // todo use format
}

requestTimeZones().then(function(data) {
    console.log(data);
    if (data.length != 0){
        timeZoneSelection = data;
        $("#fromTimeZoneSelection")
                    .autocomplete("option","source", timeZoneSelection)

        $( "#toTimeZoneSelection" )
                    .autocomplete("option","source", timeZoneSelection)
    }
                    }, function(data){console.log("error in request")})



const hourAmount = 12;
const minuteAmount = 60;

const amountOfLines = 12;
const circleDeg = 360;

function createClock(clockJQElement){
    for (let curDeg = circleDeg; curDeg > 0; curDeg-=circleDeg/amountOfLines){
        let div = document.createElement("div")
        div.classList.add("timeLine")
        div.style.rotate = curDeg + "deg"
        clockJQElement.append(div)
    }
    setClockTime(clockJQElement,0,0)

}


// the clock element should be the html element with the basic clockJo class
function setClockTime(clockElement,timeHours,timeMinutes){
    // 180 deg added to set the rotation to the start 0:00
    let hourDeg = 180 + (360 / hourAmount) * timeHours
    let minuteDeg = 180 + (360 / minuteAmount) * timeMinutes
    clockElement.get(0).style.setProperty("--shortPointerDeg",hourDeg +"deg");
    clockElement.get(0).style.setProperty("--longPointerDeg",minuteDeg +"deg");

}
