$(document).ready(function(){
    $("#changeBalance").click(function(event){
    console.log("invoked");
        //stop submit the form event. Form will be manually submitted using ajax.
        event.preventDefault();

        var token = $("input[name='_csrf']").val();
        var header = "X-CSRF-TOKEN";
        $(document).ajaxSend(function(e, xhr, options) {
                xhr.setRequestHeader(header, token);
        });
        console.log(token+" "+header);

        var newBalance = {};
        newBalance["balance"] = $("#newBalance").val();
        $("#changeBalance").prop("disabled", true);

        $.ajax({
            type:"POST",
            contentType: "application/json",
            url: "/api/user/changeBalance",
            data: JSON.stringify(newBalance),
            dataType: 'json',
            cache: false,
            timeout: 600000,
            success: function(data){
                $("#balance").html(JSON.stringify(data, null, 4));
                $("#changeBalance").prop("disabled", false);
            },
            error: function(xhr, ajaxOptions, thrownError){
                $("#changeBalance").prop("disabled", false);
            }
        })
    });
});