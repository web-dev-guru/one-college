$(function() {

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
/* data: data,
      columns: [
              { data: 'name' },
              { data: 'position' }
          ]
          */
    $('#table_id').DataTable({
            /*data: data,
             columns: [
                          { data: 'name' },
                          { data: 'position.display' }
                      ]*/
        /*ajax: {
            url: '/college/api/chapterexam',
            dataSrc:''
        },
        columns: [
                  { data: 'questionTitie' },
                  { data: 'questionType' }
              ]*/
        ajax: {
            url: '/college/api/chapterexam',
            dataSrc:''
        },
        columns: [
                  { data: 'question',
                  render: function ( data, type, row, meta ) {
                        var i;
                        var opt='';
                        for(i=0; i<data.questionOptionDTOs.length;i++){
                            if(data.questionType=='SN'){
                                opt=opt+ '<tr><td><input type="radio" name="rg" id="radio-'+i+'"></td>'
                            }
                            opt=opt+'<td>'+data.questionOptionDTOs[i].questionMsg+'</td></tr>'

                        }

                        var result='<div><table width="100%"><colgroup><col span="1" style="width: 6%;"><col span="1" style="width: 94%;"></colgroup><tbody id="treview"><tr><td colspan="2">'+data.questionTitle+'</td></tr>'
                        var tail='</tbody></table></div>';
                        result=result+opt;
                        result=result+tail;
                        console.log(result);
                        return result;
                      }}
              ]

    });

});