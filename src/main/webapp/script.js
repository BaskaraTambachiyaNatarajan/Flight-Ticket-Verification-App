/**
 * 
 */
 function loadData(){
	var content = document.getElementById("jar_frame").contentWindow.document.getElementsByTagName("pre")[0].innerHTML;
	myJSON = JSON.parse(content);
    var js = Object.keys(myJSON);
    if(js.length>0){
	    var output = document.getElementById("output");
	    var successFile = "";
	    for (var i = 0; i < js.length; i++) {
			if(js[i]==="Success" || js[i]==="Failed"){
				const a = document.createElement("a");
		        a.innerHTML=js[i];
		        a.setAttribute("href",myJSON[js[i]]);
				const p = document.createElement("p");
		        p.innerHTML="Click here to download "+js[i]+" file : ";
		        p.appendChild(a);
		        var key = js[i]+"Count";
		        p.innerHTML += " ( "+myJSON[key]+" )";
		        output.appendChild(p);
				const br = document.createElement("br");
		        output.appendChild(br);
		        if(js[i]==="Success"){
						successFile=myJSON[js[i]];
					}
	        }
		}
		var sendMail = document.getElementById("sendMail");
		sendMail.style.display = "block";
		sendMail.setAttribute("onclick","send_mail('"+successFile+"')");
	}
	else{
		alert("Please enter valid CSV file (Refer sample file)");
	}
	document.getElementById("File").value = "";
	stopLoading();
}

function send_mail(f){
	$("#loadingArea").addClass("disabledbutton");
	document.getElementById("loader").style.display = "block";
	$.post("SendMail",
        {
          file:f
        },
        function(data,status){
        alert(data);
	$("#loadingArea").removeClass("disabledbutton");
	document.getElementById("loader").style.display = "none";
        });
}

function loading(){
	var text = document.getElementById("File").value;
	if(!(text.trim().endsWith(".csv"))){
		return false;
	}
	document.getElementById("output").innerHTML="";
	$("#loadingArea").addClass("disabledbutton");
	document.getElementById("loader").style.display = "block";
	var sendMail = document.getElementById("sendMail");
	sendMail.style.display = "none";
}

function stopLoading(){
	$("#loadingArea").removeClass("disabledbutton");
	document.getElementById("loader").style.display = "none";
}