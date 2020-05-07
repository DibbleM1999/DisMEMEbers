"use strict";

function submitForm(){
    let imageFile = $("#image").prop("files")[0];
    let imageFilename = $("#image").val();
    
    //validation code, prevents submitting improper files
    if( imageFile.length === 0 ){   //checks if the file has data
    $("#image").addClass("invalid");
    $("#statusdiv").html("Please submit a file.");
    return;
    } else {
    $("#image").removeClass("invalid");
    }
    let fileTypeIndex = imageFilename.lastIndexOf('.');
    let fileType = imageFilename.substring(fileTypeIndex + 1);
    if( fileType === "png" || fileType === "jpg" ){   //checks if the filename ends with png or jpg
    $("#image").addClass("invalid");
    $("#statusdiv").html("File is not of valid type, pleasure submit a png or jpg.");
    return;
    } else {
    $("#image").removeClass("invalid");
    }
    
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
            //System.out.println("success test line");
            $("#statusdiv").html("Server said this: "+data);
            //get the username, time, and date to go along the image data
            //take the data and put it in the database somehow
        },
        error: (xhr,status,err) => {
            //System.out.println("error test line");
            $("#statusdiv").html("Something went wrong: status="+status+" err="+err);
        }

    });

}
