$(function() {


    $( "#searchuser" ).on( "click", function( event ) {
                        event.preventDefault();
        var data = [
            {  "name":"Tiger Nixon",
               "position":{
                    "display" : "director",
                    "salary" : "1000"
                   }
            },
            {"name":"Garrett Winters",
               "position": {
                   "display" : "manager",
                   "salary" : "800"
                  }
            }
        ]
    var userDTO = {}
    userDTO["firstname"] = $("#firstname").val();
    userDTO["lastname"] = $("#lastname").val();
    console.log(userDTO);

    $.ajax({
             type: "POST",
             contentType: "application/json",
             url: "/college/api/searchuser",
             data: JSON.stringify(userDTO),
             dataType: 'json',
             cache: false,
             timeout: 600000,
             success: function (json) {
                console.log(json);
                if(json.msg=='success'){
                    console.log("json: "+json.result);
                     $('#user_table').DataTable({
                                     data:json.result,
                                     searching: false,
                                     ordering:  false,
                                     select: true,
                                     pageLength: 1,
                                     lengthMenu: [ 1 ],
                                     paging: true,
                                     columnDefs:[
                                            { title: "First Name",   targets: 0 },
                                            { title: "Last Name",   targets: 1 },
                                            { title: "Status",   targets: 2 },
                                            { title: "Role",   targets: 3 },
                                            { title: "Start Date",   targets: 4 },
                                            { title: "Password",   targets: 5 },
                                            { title: "Email",   targets: 6 },

                                     ],
                                     columns: [

                                               { data: 'firstname' },
                                               { data: 'lastname' },
                                               { data: 'status' },
                                               { data: 'role' },
                                               { data: 'startDate'},
                                               { data: 'password' },
                                               { data: 'email' },
                                               {
                                                   data: null,
                                                   className: "dt-center editor-edit",
                                                   defaultContent: '<i class="fa fa-pencil"/>',
                                                   orderable: false
                                               },
                                               {
                                                      data: null,
                                                      className: "dt-center editor-delete",
                                                      defaultContent: '<i class="fa fa-trash"/>',
                                                      orderable: false
                                                  },
                                           ]




                            });

                }else{
                    $("#useradd-warning-text").html(json.msg)
                    $("#useradd-warning-dialog").dialog("open");
                }
             },
             error: function (e) {

                window.open('/college/logout','_self')

             }
         });


        });

    $('#user_table').on('click', 'td.editor-edit', function (e) {
        e.preventDefault();
        var email = $(this).closest('td').prev().html();//get the previous td(email)


    } );

});