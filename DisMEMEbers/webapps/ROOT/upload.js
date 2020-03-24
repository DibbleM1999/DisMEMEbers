"use strict";

function submitForm(){
    //let name = $("#name").val();
    let imageFile = $("#image").prop("files")[0];
    let imageFilename = $("#image").val();
    
    let fdata = new FormData();

    //fdata.append("name",name);
    fdata.append("image",imageFile,imageFilename);

    $.ajax({
        type: "POST",
        url: "/srv/upload",
        data: fdata,
        contentType: false, 
        processData: false,
        success: (data) => {
            $("#statusdiv").html("Server said this: "+data);
        },
        failure: (xhr,status,err) => {
            
        },
        error: (xhr,status,err) => {
            $("#statusdiv").html("Something went wrong: status="+status+" err="+err);
        }

    });

}
