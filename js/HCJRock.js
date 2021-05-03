$$$.model={
"onStartup":[],
"accordians":[],
"modals": [],
"grid": null
}

function $$$(cid){
var i,t,k;
for(i=0;i<$$$.model.modals.length;i++)
{
t=$$$.model.modals[i];
k=t.getContentId();
if(t.getContentId()==cid) return t;
}
let element=document.getElementById(cid);
if(!element) throw "Invalid id:"+cid;
return new TMJRockElement(element);
}

$$$.validateForm=function(parameter)
{
document.getElementById(parameter).isValid=function(obj)
{
var valid=true;
for(var key in obj)
{
var elements=document.getElementsByName(key);
if(elements.length==1)
{
var element=elements[0];
elementType=element.type;
if(elementType=='text')
{
value=element.value.trim();
document.getElementById(obj[key].errorPane).innerHTML="";
if(obj[key].required==true && value=="")
{
valid=false;
document.getElementById(obj[key].errorPane).innerHTML=obj[key].errors.required;
} 
if(value.length>obj[key].maxLength)
{
valid=false;
document.getElementById(obj[key].errorPane).innerHTML=obj[key].errors.maxLength;
}
}
if(elementType=='textarea')
{
value=element.value.trim();
document.getElementById(obj[key].errorPane).innerHTML="";
if(obj[key].required==true && value=="")
{
valid=false;
document.getElementById(obj[key].errorPane).innerHTML=obj[key].errors.required;
}
}
if(elementType=='select-one')
{
value=element.value;
document.getElementById(obj[key].errorPane).innerHTML="";
if(value==obj[key].invalid)
{
valid=false;
document.getElementById(obj[key].errorPane).innerHTML=obj[key].errors.invalid;
}
}
if(elementType=='checkbox')
{
if(obj[key].requiredState!=element.checked && obj[key].displayAlert==true)
{
alert(obj[key].errors.requiredState);
}
}
}// if condition for elementSize==1 ends here
else
{
var firstElement=elements[0];
var secondElement=elements[1];
if(firstElement.type=='radio' && secondElement.type=='radio')
{
document.getElementById(obj[key].errorPane).innerHTML="";
if(obj[key].required==true && firstElement.checked==false && secondElement.checked==false)
{
valid=false;
document.getElementById(obj[key].errorPane).innerHTML=obj[key].errors.required;
}
}
}
}// for loop ends here
return valid;
}// isValid function ends here
return document.getElementById(parameter)
}



class Grid
{
constructor(dataTableId,dataTablePaginationId,students,pageSize)
{
this.dataTableId=dataTableId;
this.dataTablePaginationId=dataTablePaginationId;
this.students=students;
this.pageSize=pageSize;
this.pageNumber=16;
this.numberOfPaginationControls=5;
this.update();
this.updatePagination();
}

setPage(pageNumber)
{
this.pageNumber=pageNumber;
this.update();
this.updatePagination();
return false;
}

update()
{
var dataTable=document.getElementById(this.dataTableId);
while(dataTable.rows.length>0) dataTable.deleteRow(0);
var tr,td;
var startFrom=(this.pageNumber-1)*this.pageSize;
var endAt=startFrom+this.pageSize-1;
var x;
for(x=startFrom;x<=endAt && x<this.students.length;x++)
{
tr=document.createElement("tr");
td=document.createElement("td");
td.innerHTML=(x+1);
tr.appendChild(td);
td=document.createElement("td");
td.innerHTML=this.students[x].rollNumber;
tr.appendChild(td);
td=document.createElement("td");
td.innerHTML=this.students[x].name;
tr.appendChild(td);
td=document.createElement("td");
td.innerHTML=this.students[x].nameOfMother;
tr.appendChild(td);
td=document.createElement("td");
td.innerHTML=this.students[x].nameOfFather;
tr.appendChild(td);
dataTable.appendChild(tr);
}
}

updatePagination()
{
function createPageChangeFunction(obj,pageNumber)
{
return function()
{
obj.setPage(pageNumber);
return false;
}
}
let objectAddress=this;
var startFrom=Math.floor((this.pageNumber-1)/(this.numberOfPaginationControls))*this.numberOfPaginationControls+1;
var endAt=startFrom+this.numberOfPaginationControls-1;
var numberOfPages=Math.floor(this.students.length/this.pageSize);
if(this.students.length%this.pageSize!=0) numberOfPages++;
if(endAt>numberOfPages) endAt=numberOfPages;
var x;
var dataTablePagination=document.getElementById(this.dataTablePaginationId);
while(dataTablePagination.rows.length>0) dataTablePagination.deleteRow(0);
var tr,td;
var anchor;
tr=document.createElement('tr');
if(startFrom>1)
{
td=document.createElement('td');
anchor=document.createElement("a");
anchor.text="previous";
anchor.href='javascript:void(0)';
anchor.onclick=createPageChangeFunction(this,startFrom-1);
td.appendChild(anchor);
tr.appendChild(td);
}
for(x=startFrom;x<=endAt;x++)
{
td=document.createElement('td');
if(x!=this.pageNumber)
{
anchor=document.createElement("a");
anchor.text=x;
anchor.href='javascript:void(0)';
anchor.onclick=createPageChangeFunction(this,x);
td.appendChild(anchor);
}
else
{
td.innerHTML="<b>"+x+"<b>";
}
tr.appendChild(td);
}
if(endAt<numberOfPages)
{
td=document.createElement('td');
anchor=document.createElement("a");
anchor.text="next";
anchor.href='javascript:void(0)';
anchor.onclick=createPageChangeFunction(this,endAt+1);
td.appendChild(anchor);
tr.appendChild(td);
}
dataTablePagination.appendChild(tr);
}
}








function TMJRockElement(element)
{
this.element=element;
this.html=function(content)
{
if(typeof this.element.innerHTML=="string")
{
if((typeof content)=="string")
{
this.element.innerHTML=content;
}
return this.element.innerHTML;
}
else
{
return null;
}
}// html function ends

this.value=function(content)
{
if(typeof this.element.value=="string")
{
if((typeof content)=="string")
{
this.element.value=content;
}
return this.element.value;
}
else
{
return null;
}
}// value function ends

this.fillComboBox=function(jsonObject){
if(!jsonObject["dataSource"]) throw "dataSource property missing in call to fillComboBox";
if(!jsonObject["text"]) throw "text property missing in call to fillComboBox";
if(!jsonObject["value"]) throw "value property missing in call to fillComboBox";
let dataSource=jsonObject["dataSource"];
if((typeof dataSource)!="object" || !dataSource.hasOwnProperty(length)) throw "dataSource property should be collection in call to fillComboBox";
if(dataSource.length==0) throw "The size collection against dataSource property should not be zero";
let value=jsonObject["value"];
if((typeof value)!="string") throw "value property should be of string type in call to fillComboBox";
let text=jsonObject["text"];
if((typeof text)!="string") throw "text property should be of string type in call to fillComboBox";
if(!dataSource[0].hasOwnProperty(text)) throw "Collection has no property named as :"+text;
if(!dataSource[0].hasOwnProperty(value)) throw "Collection has no propery names as :"+value;
var firstOptionText=null;
var firstOptionValue=null;
if(jsonObject["firstOption"])
{
if(!jsonObject.firstOption["text"]) throw "text property associated with the firstOption property is missing in call to fillComboBox";
if(!jsonObject.firstOption["value"]) throw "value property associated with the firstOption property is missing in call to fillComboBox";
firstOptionText=jsonObject.firstOption["text"];
if((typeof firstOptionText)!="string") throw "text property associated with the firstOption property should be of string type in call to fillComboBox";
firstOptionValue=jsonObject.firstOption["value"];
if((typeof firstOptionValue)!="string") throw "value property associated with the firstOption property should be of string type in call to fillComboBox";
}
let comboBox=this.element;
var obj;
for(var i=0;i<comboBox.length;i++) comboBox.removeChild(comboBox.childNodes[0]);
if(jsonObject["firstOption"])
{
obj=document.createElement("option");
obj.value=firstOptionValue;
obj.text=firstOptionText;
comboBox.appendChild(obj);
}

for(var i=0;i<dataSource.length;i++)
{
var t=dataSource[i];
obj=document.createElement("option");
obj.value=t[value];
obj.text=t[text];
comboBox.appendChild(obj);
}


}//fillComboBox ends here

}//TMJRockElement class ends


$$$.ajax=function(jsonObject){
if(!jsonObject["url"]) throw "url property missing in call to ajax";
let url=jsonObject["url"];
if((typeof url)!="string") throw "url property should be of string type in call to ajax";
let methodType="GET";
if(jsonObject["methodType"])
{
methodType=jsonObject["methodType"];
if((typeof methodType)!="string") throw "method property should be of string type in call to ajax";
methodType=methodType.toUpperCase();
if(["GET","POST"].includes(methodType)==false) throw "method property should be GET/POST in call to ajax";
}
let onSuccess=null;
if(jsonObject["success"])
{
onSuccess=jsonObject["success"];
if((typeof onSuccess)!="function") throw "success property should be of function type in call to ajax";
}
let onFailure=null;
if(jsonObject["failure"])
{
onFailure=jsonObject["failure"];
if((typeof onFailure)!="function") throw "failure property should be of function type in call to ajax";
}



if(methodType=="GET")
{
var xmlHttpRequest=new XMLHttpRequest();
xmlHttpRequest.onreadystatechange=function()
{
if(this.readyState==4)
{
if(this.status==200)
{
var responseData=this.responseText;
if(onSuccess) onSuccess(responseData);
}
else
{
if(onFailure) onFailure();
}
}
};
if(jsonObject["data"])
{
url=url+"?";

var count=0;
for(x in jsonObject.data)
{
if(count!=0) url=url+"&";
url=url+encodeURI(x);
url=url+"=";
url=url+encodeURI(jsonObject.data[x]);
count++;
}
}
xmlHttpRequest.open(methodType,url,true);
xmlHttpRequest.send();
}

if(methodType=="POST")
{
var xmlHttpRequest=new XMLHttpRequest();
xmlHttpRequest.onreadystatechange=function()
{
if(this.readyState==4)
{
if(this.status==200)
{
var responseData=this.responseText;
if(onSuccess) onSuccess(responseData);
}
else
{
if(onFailure) onFailure();
}
}
};
let jsonData={};
let sendJSON=jsonObject["sendJSON"];
if(!sendJSON) sendJSON=false;
if((typeof sendJSON)!="boolean") throw "sendJSON property should be of boolean type in call to ajax";
let queryString="";
if(jsonObject["data"])
{
if(sendJSON)
{
jsonData=jsonObject["data"];
}
else
{
var count=0;
for(x in jsonObject.data)
{
if(count!=0) queryString=queryString+"&";
queryString=queryString+encodeURI(x);
queryString=queryString+"=";
queryString=queryString+encodeURI(jsonObject.data[x]);
count++;
}
}
}
xmlHttpRequest.open('POST',url,true);
if(sendJSON==true)
{
xmlHttpRequest.setRequestHeader("Content-type","application/json");
xmlHttpRequest.send(JSON.stringify(jsonData));
}
else
{
xmlHttpRequest.setRequestHeader("Content-type","application/x-www-form-urlencoded");
xmlHttpRequest.send(queryString);
}
}
};	
// TMJRock part ends here


function addCSS()
{
let data="";
data=data+"<style>\n";
data=data+".modalMask\n";
data=data+"{\n";
data=data+"width: 100%;\n";
data=data+"height: 100%;\n";
data=data+"top: 0;\n";
data=data+"left: 0;\n";
data=data+"background: #000000;\n";
data=data+"position: fixed;\n";
data=data+"opacity: 85%;\n";
data=data+"}\n";
data=data+".modal\n";
data=data+"{\n";
data=data+"background-color: white;\n";
data=data+"position: fixed;\n";
data=data+"top: 0;\n";
data=data+"left: 0;\n";
data=data+"bottom: 0;\n";
data=data+"right: 0;\n";
data=data+"margin: auto;\n";
data=data+"}\n";
data=data+".closeButton\n";
data=data+"{\n";
data=data+"float: right;\n";
data=data+"padding: 2px 5px;\n";
data=data+"cursor: pointer;\n";
data=data+"background-color: black;\n";
data=data+"color:white;\n";
data=data+"}\n";
data=data+"</style>\n";

let head=document.getElementsByTagName('head')[0];
head.innerHTML=head.innerHTML+data;
}

$$$.onDocumentLoaded=function(fn)
{
addCSS();
if((typeof fn)!="function") throw "Excepted function found:"+(typeof fn)+"in call to onDocumentLoaded";
$$$.model.onStartup[$$$.model.onStartup.length]=fn;
}





$$$.initFramework=function()
{
let allTags=document.getElementsByTagName("*");
let x;
let t=null;
let a=null;
x=0;
while(x<allTags.length)
{
t=allTags[x];
if(t.hasAttribute("accordian"))
{
a=t.getAttribute("accordian");
if(a=="true")
{
$$$.toAccordian(t);
}
}
x++;
}
x=0;
while(x<$$$.model.onStartup.length)
{
let fn=$$$.model.onStartup[x];
fn();
x++;
}
}



$$$.accordianHeadingClicked=function(accordianIndex,panelIndex)
{
let expandedIndex=$$$.model.accordians[accordianIndex].expandedIndex;
let panels=$$$.model.accordians[accordianIndex].panels;
if(expandedIndex!=-1) panels[expandedIndex].style.display="none";
panels[panelIndex+1].style.display=panels[panelIndex+1].oldDisplay;
$$$.model.accordians[accordianIndex].expandedIndex=panelIndex+1;
}


$$$.toAccordian=function(accord)
{
let expandedIndex=-1;
let panels=[];
let children=accord.childNodes;
let i;
for(i=0;i<children.length;i++) 
{
if(children[i].nodeName=="H3")
{
panels[panels.length]=children[i];
}
if(children[i].nodeName=="DIV")
{
panels[panels.length]=children[i];
}
}
if(panels.length%2!=0) throw "Headings and division malformed to create accordian";
for(i=0;i<panels.length;i+=2)
{
if(panels[i].nodeName!="H3") throw "Headings and division malformed to create accordian";
if(panels[i+1].nodeName!="DIV") throw "Headings and division malformed to create accordian";
}

function createClickHandler(accordianIndex,panelIndex)
{
return function(){
$$$.accordianHeadingClicked(accordianIndex,panelIndex);
};
}

let accordianIndex=$$$.model.accordians.length;
for(i=0;i<panels.length;i+=2)
{
panels[i].onclick=createClickHandler(accordianIndex,i);
panels[i+1].oldDisplay=panels[i+1].style.display;
panels[i+1].style.display="none";
}
$$$.model.accordians[accordianIndex]={
"panels": panels,
"expandedIndex": expandedIndex
}
}

$$$.show=function(cid)
{
var i;
var t;
var k;
for(i=0;i<$$$.model.modals.length;i++)
{
t=$$$.model.modals[i];
k=t.getContentId();
if(t.getContentId()==cid) t.show();
}
}




function Modal(cref)
{
//some property should be used to decide if the modal should 
//be closed if the user clicks outside the modal area
this.afterOpening=null;
this.afterClosing=null;
this.beforeOpening=null;
this.beforeClosing=null;
var objectAddress=this;
var contentId=null;
var header=null;
var footer=null;
var height=null;
var width=null;
var displayCloseButton=null;
var maskColor=null;
var modalBackgroundColor;
var contentReference=cref;

let hcpErr="";
if(contentReference.hasAttribute('id')) contentId=contentReference.id;



if(contentReference.hasAttribute('header'))
{
let hdr=contentReference.getAttribute('header');
if((typeof hdr)!="string") throw "header property should be of string type";
if(hdr.length==0) throw "Length of header should not be zero";
header=hdr;
}

if(contentReference.hasAttribute('footer'))
{
let ftr=contentReference.getAttribute('footer');
if((typeof ftr)!="string") throw "footer property should be of string type";
if(ftr.length==0) throw "Length of footer should not be zero";
footer=ftr;
}

if(contentReference.hasAttribute('size'))
{
let size=contentReference.getAttribute('size');
if((typeof size)!="string") throw "size property should be of string type";
if(size.length==0) throw "Invalid size";
let i=size.indexOf('x');
height=size.slice(0,i);
width=size.slice(i+1);
}
else
{
width=400;
height=300;
}

if(contentReference.hasAttribute('closeButton'))
{
let cb=contentReference.getAttribute('closeButton');
if(cb!='true' && cb!='false') throw "Value of closeButton property should either true or false";
displayCloseButton=true;
}

if(contentReference.hasAttribute('maskColor'))
{
let mc=contentReference.getAttribute('maskColor');
maskColor=mc;
}


if(contentReference.hasAttribute('modalBackgroundColor'))
{
let mbc=contentReference.getAttribute('modalBackgroundColor');
if(mbc.length==0) throw "Invalid value for modalBackgroundColor property";
modalBackgroundColor=mbc;
}

if(contentReference.hasAttribute('afterClosing')) 
{
this.afterClosing=contentReference.getAttribute('afterClosing');
}
if(contentReference.hasAttribute('beforeClosing')) 
{
this.beforeClosing=contentReference.getAttribute('beforeClosing');
}
if(contentReference.hasAttribute('afterOpening')) 
{
this.afterOpening=contentReference.getAttribute('afterOpening');
}
if(contentReference.hasAttribute('beforeOpening')) 
{
this.beforeOpening=contentReference.getAttribute('beforeOpening');
}


contentReference.remove();
contentReference.style.display="block";

var modalMaskDivision=document.createElement("div");
modalMaskDivision.style.display="none";
modalMaskDivision.classList.add('modalMask');
if(maskColor) modalMaskDivision.style.backgroundColor=maskColor;

var modalDivision=document.createElement("div");
modalDivision.style.display="none";
modalDivision.classList.add('modal');
document.body.appendChild(modalMaskDivision);
document.body.appendChild(modalDivision);


if(height) modalDivision.style.height=height+"px";
if(width) modalDivision.style.width=width+"px";

if(displayCloseButton==true)
{
var closeButtonSpan=document.createElement("span");
closeButtonSpan.classList.add('closeButton');
var closeButtonMarker=document.createTextNode("X");
closeButtonSpan.appendChild(closeButtonMarker);
modalDivision.appendChild(closeButtonSpan);
closeButtonSpan.onclick=function(){
let closeModal=true;
if(objectAddress.beforeClosing)
{
closeModal=eval(objectAddress.beforeClosing);
}
if(closeModal)
{
modalDivision.style.display="none";
modalMaskDivision.style.display="none";
if(objectAddress.afterClosing!=null) setTimeout(objectAddress.afterClosing,100);
}
}//onclick function defination ends here
}



var headerDivision=document.createElement("div");
headerDivision.style.backgroundColor="#B8FAF9";
headerDivision.style.width=(width-10)+"px";
headerDivision.style.height="40px";
headerDivision.style.padding="5px";
if(header) headerDivision.innerHTML=header;

var footerDivision=document.createElement("div");
footerDivision.style.backgroundColor="#B8FAF9";
footerDivision.style.width=(width-10)+"px";
footerDivision.style.height="40px";
footerDivision.style.position="absolute";
footerDivision.style.bottom="0";
footerDivision.style.padding="5px";
if(footer) footerDivision.innerHTML=footer;


var contentDivision=document.createElement("div");
contentDivision.style.height=(modalDivision.style.height.substring(0,modalDivision.style.height.length-2)-110)+"px";
contentDivision.style.width=width-10+"px";
contentDivision.style.overflow="auto";
contentDivision.style.padding="5px";
contentDivision.appendChild(contentReference);

modalDivision.appendChild(headerDivision);
modalDivision.appendChild(contentDivision);
modalDivision.appendChild(footerDivision);
modalDivision.style.border="4px ridge";

if(modalBackgroundColor) modalDivision.style.backgroundColor=modalBackgroundColor;
if(height) modalDivision.style.height=height+"px";
if(width) modalDivision.style.width=width+"px";





this.getContentId=function()
{
return contentId;
}

this.show=function(){
let openModal=true;
if(this.beforeOpening)
{
openModal=eval(this.beforeOpening);
}
if(openModal)
{
modalMaskDivision.style.display="block";
modalDivision.style.display="block";
if(objectAddress.afterOpening!=null) setTimeout(objectAddress.afterOpening,100);
}
};//show function defination ends here
}


window.addEventListener('load',function()
{
$$$.initFramework();
});
