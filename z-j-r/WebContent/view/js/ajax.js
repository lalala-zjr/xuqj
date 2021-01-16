// JavaScript Document
function ajax(json){
	var method = json && json.method || "get";
	var url = json && json.url || "";
	var data = json && json.data || "";
	var fnSucc = json && json.fnSucc || function(){};
	var fnFaild = json && json.fnFaild || function(){};
	var xhr = window.XMLHttpRequest ? new XMLHttpRequest() : new ActiveXObjext("Microsoft.XMLHTTP");
	if(method=="get"){
		xhr.open(method,url+"?"+data,true);
		xhr.send();
	}else{
		xhr.open(method,url,true);
		xhr.setRequestHeader("content-type","application/x-www-form-urlencoded");
		xhr.send(data);
	}
	console.log("666");
	xhr.onreadystatechange = function(){
		(xhr.readyState == 4 && xhr.status == 200) ? fnSucc(xhr.responseText) : fnFaild();
	}
}