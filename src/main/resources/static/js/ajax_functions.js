$(function() {


    $( "#useradd-warning-dialog" ).dialog({
                title: "Dialog",
                modal: true,
                draggable: false,
                resizable: false,
                autoOpen: false,
                width: 500,
                height: 200
            });

    $( "#warning-dialog" ).dialog({
                title: "Dialog",
                modal: true,
                draggable: false,
                resizable: false,
                autoOpen: false,
                width: 500,
                height: 200
            });
    $( "#success-dialog" ).dialog({
                    title: "Dialog",
                    modal: true,
                    draggable: false,
                    resizable: false,
                    autoOpen: false,
                    width: 500,
                    height: 200,
                    buttons: {
                            "Next Question": function() {
                              window.open('/college/question','_self');
                            }/*,
                            Cancel: function() {
                                window.open('/college/question','_self');
                              $( this ).dialog( "close" );
                            }*/
                    }
                });
    $( "#useradd-success-dialog" ).dialog({
                        title: "Dialog",
                        modal: true,
                        draggable: false,
                        resizable: false,
                        autoOpen: false,
                        width: 500,
                        height: 200,
                        buttons: {
                                "Next User": function() {
                                  window.open('/college/adduser','_self');
                                }/*,
                                Cancel: function() {
                                    window.open('/college/question','_self');
                                  $( this ).dialog( "close" );
                                }*/
                        }
                    });
    $( "#useradd-warning-dialog" ).hide();
    $( "#warning-dialog" ).hide();
    $( "#success-dialog" ).hide();
    $( "#useradd-success-dialog" ).hide();
    $('input:text')
      .button()
      .css({
              'font' : 'inherit',
             'color' : 'inherit',
        'text-align' : 'left',
           'outline' : 'none',
            'cursor' : 'text'
      });
  $("#roleSelection" ).selectmenu();
  $("#statusSelection" ).selectmenu();

  $("#main-exam-question-category" ).selectmenu();
  $("#main-exam-question-category").on('selectmenuchange', function() {
                                      console.log($(this).val());
                                       $("#hdchapter").val($(this).val());
                                   });
     $("#main-exam-question-difficulty" ).selectmenu();
     $("#main-exam-question-difficulty").on('selectmenuchange', function() {
                                         console.log($(this).val());
                                         $("#hddifclt").val($(this).val());
                                      });


    $("#main-exam-question-type" ).selectmenu();
    $("#main-exam-question-type").on('selectmenuchange', function() {
                                        console.log($(this).val());
                                        $("#hdtype").val($(this).val());
                                     });
    $( "button" ).button();

    initmain();
    $( "#tboy" ).on( 'click' ,'.dc', function( event ) {
        console.log("hello"+event.target.id);
        $(this).closest('tr').remove();
    });

    $( "#adduser" ).on( "click", function( event ) {
                    event.preventDefault();
                    if($("#password").val() != $("#cfmpassword").val()){
                         $("#useradd-warning-text").html("Password and Confirm Password are not matched")
                         $("#useradd-warning-dialog").dialog("open");
                    }
                    var userDTO = {}
                    userDTO["email"] = $( "#email").val();
                    userDTO["firstname"] = $("#firstName").val();
                    userDTO["lastname"] = $("#lastName").val();
                    userDTO["password"] = $("#password").val()
                    userDTO["startDate"] =  $("#startTimeDatePicker").val();
                    userDTO["role"] =  $("#roleSelection").val();
                    userDTO["status"] =  $("#statusSelection").val();
                    console.log(userDTO);
                    $.ajax({
                             type: "POST",
                             contentType: "application/json",
                             url: "/college/api/adduser",
                             data: JSON.stringify(userDTO),
                             dataType: 'json',
                             cache: false,
                             timeout: 600000,
                             success: function (data) {
                                console.log(data);
                                if(data.msg=='success'){
                                    resetUserForm();
                                    $("#useradd-success-text").html("user add successfully")
                                    $("#useradd-success-dialog").dialog("open");
                                }else{
                                    $("#useradd-warning-text").html(data.msg)
                                    $("#useradd-warning-dialog").dialog("open");
                                }
                             },
                             error: function (e) {

                                window.open('/college/logout','_self')

                             }
                         });



    } );




    $( "#submitquestion" ).on( "click", function( event ) {
                        event.preventDefault();

                        var questionDTO = {}
                        questionDTO["questionTitle"] = $( "#questiontitle").val();
                        questionDTO["chapter"] = $("#hdchapter").val();
                        questionDTO["groupId"] = $("#question-group").val();
                        questionDTO["difficulty"] = $("#hddifclt").val()
                        questionDTO["questionType"] =  $("#hdtype").val();
                        console.log(questionDTO);

                        var questionOptionDTOs =[];
                        $('.rwopt').each(function(i, obj) {
                            var questionOptionDTO={}
                            console.log($(this).find("td.optxt").html());
                            questionOptionDTO["questionMsg"]=$(this).find("td.optxt").html();
                            console.log($(this).find("td.awr").html());
                            if($(this).find("td.awr").html()=='*'){
                                questionOptionDTO["isAnswer"] = true;
                            }else{
                                questionOptionDTO["isAnswer"] = false;
                            }
                            questionOptionDTOs.push(questionOptionDTO);
                         });
                         questionDTO["questionOptionDTOs"]=questionOptionDTOs
                         console.log(questionDTO);

                         $.ajax({
                                     type: "POST",
                                     contentType: "application/json",
                                     url: "/college/api/question",
                                     data: JSON.stringify(questionDTO),
                                     dataType: 'json',
                                     cache: false,
                                     timeout: 600000,
                                     success: function (data) {
                                        $("#success-text").html("question add successfully")
                                        $("#success-dialog").dialog("open");

                                        //console.log("print out the data");
                                        //var result=JSON.stringify(data);
                                        //console.log("data"+result);

                                     },
                                     error: function (e) {
                                        window.open('/college/logout','_self')


                                     }
                                 });


        } );


    var rowIdx = 1;
    $( "#addoptions" ).on( "click", function( event ) {
  // Adding a row inside the tbody.
        console.log(rowIdx++)
        var str= "<tr class='opt'><td></td><td><input type='checkbox' id=answr"+rowIdx+"></td><td><textarea id='o"+rowIdx+"' rows='5' cols='20' style='width:600px;' class='ui-widget ui-state-default ui-corner-all' ></textarea></td><td><button id='rem"+rowIdx+"' class='dc ui-button ui-widget ui-corner-all'>del</button></td></tr>";
        $('#tboy').append(str);
    } );

    $( "#review" ).on( "click", function( event ) {
        $("#review-container").css('display','block');

        $('#treview .rwopt').remove();
        var difficulty = $("#hddifclt").val();
        var type = $("#hdtype").val();
        var size=$('.opt').length;
         $("#qstn").html($( "#questiontitle").val())
         $("#chapt").html( $("#hdchapter").val())
         $("#dfct").html( $("#hddifclt").val())
         $("#gp").html( $("#question-group").val())
         var error=false;
        var checkedcount = 0;

        if(size==0){
            error =true;
            $("#warning-text").html("No answer provided, please choose at least one answer.")
            $("#warning-dialog").dialog("open");
            return;
        }



            $('.opt').each(function(i, obj) {

                var op=""
                //console.log("i:"+i);
                //console.log($(this).find("textarea").val());
                //console.log($(this).find("input:checkbox").prop('checked'))

                switch(i) {
                  case 0:
                     if($(this).find("input:checkbox").prop('checked')){
                        op ="<tr class='rwopt'><td class='awr'>*</td><td> A:</td><td class='optxt'>" + $(this).find("textarea").val()+"</td></tr>";
                     }else{
                        op ="<tr class='rwopt'><td></td><td class='awr'> A:</td><td class='optxt'>" + $(this).find("textarea").val()+"</td></tr>";
                     }
                     $('#treview').append(op);
                    break;
                  case 1:
                     if($(this).find("input:checkbox").prop('checked')){
                        op ="<tr class='rwopt'><td class='awr'>*</td><td> B:</td><td class='optxt'>" + $(this).find("textarea").val()+"</td></tr>";
                     }else{
                        op ="<tr class='rwopt'><td class='awr'></td><td> B:</td><td class='optxt'>" + $(this).find("textarea").val()+"</td></tr>";
                     }
                     $('#treview').append(op);
                    break;
                  case 2:
                       if($(this).find("input:checkbox").prop('checked')){
                          op ="<tr class='rwopt'><td class='awr'>*</td><td> C:</td><td class='optxt'>" + $(this).find("textarea").val()+"</td></tr>";
                       }else{
                          op ="<tr class='rwopt'><td class='awr'></td><td> C:</td><td class='optxt'>" + $(this).find("textarea").val()+"</td></tr>";
                       }
                       $('#treview').append(op);
                      break;
                  case 3:
                     if($(this).find("input:checkbox").prop('checked')){
                        op ="<tr class='rwopt'><td class='awr'>*</td><td> D:</td><td class='optxt'>" + $(this).find("textarea").val()+"</td></tr>";
                     }else{
                        op ="<tr class='rwopt'><td class='awr'></td><td> D:</td><td class='optxt'>" + $(this).find("textarea").val()+"</td></tr>";
                     }
                     $('#treview').append(op);
                    break;
                  default:
                    // code block
                }


                if($(this).find("input:checkbox").prop('checked')){
                    checkedcount++;
                }
            });

        console.log("checkedcount"+checkedcount);
        if(checkedcount==0){
            error =true;
            $("#warning-text").html("No answer provided, please choose at least one answer.")
            $("#warning-dialog").dialog("open");
            return false;
        }
        if(type=='SG' || type =='YN'){
            if(checkedcount>1){
                    error =true;
                    $("#warning-text").html("Too many answers provided, only need one answers.")
                    $("#warning-dialog").dialog("open");
                    return false;
            }
        }
        console.log("error "+error)
        if(error==false){
        console.log("show button")
           $("#submitquestion").show();
        }else{
            console.log("hide button")
            $("#submitquestion").hide();
        }
     });
    $( "#startTimeDatePicker" ).datepicker();
    $("#login-form").submit(function (event) {

        //stop submit the form event. Do this manually using ajax post function
        event.preventDefault();

        var loginForm = {}
        loginForm["userId"] = $("#userId").val();
        loginForm["password"] = $("#password").val();
        console.log(loginForm);
        $("#btn-login").prop("disabled", true);
        $.ajax({
            type: "POST",
            contentType: "application/json",
            url: "/college/api/login",
            data: JSON.stringify(loginForm),
            dataType: 'json',
            cache: false,
            timeout: 600000,
            success: function (data) {
                var user = data.userResult;
                console.log("user role:"+user.role);
                //$("#btn-login").prop("disabled", false);
                if(user.role=='admin'){
                    window.open('/college/main','_self')

                }else{
                    window.open('/college/question','_self')
                }
            },
            error: function (e) {

                var json = "<h4>Response Error</h4><pre>"
                    + e.responseText + "</pre>";
                var errorMsg = JSON.parse(e.responseText);
                $('#feedback').html(errorMsg.msg);

                console.log("ERROR : ", e);
                $("#btn-login").prop("disabled", false);

            }
        });


    });

    function initmain(){
        $( "#main-exam-question-display-area" ).hide();
        $( "#main-user-management-display-area" ).hide();
        $("#hddifclt").val($("#main-exam-question-difficulty").val());
        $("#hdtype").val($("#main-exam-question-type").val());
        $("#hdchapter").val($("#main-exam-question-category").val());
        $("#submitquestion").hide();

        $("#review-container").css('display','none');
        console.log("hide all working bench area333")
    }

    $( "#main-exam-question-btn" ).click(function() {
      $( "#main-exam-question-display-area" ).show();
      $( "#main-user-management-display-area" ).hide();
      console.log("show exam display area")
    });

    $( "#main-user-btn" ).click(function() {
          $( "#main-exam-question-display-area" ).hide();
          $( "#main-user-management-display-area" ).show();
          console.log("show user display area")
    });

    function resetUserForm(){
        $( "#email").val("");
        $("#firstName").val("");
        $("#lastName").val("");
        $("#password").val("")
        $("#startTimeDatePicker").val("");
    }
});