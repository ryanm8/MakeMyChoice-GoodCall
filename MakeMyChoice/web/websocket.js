/* 
 * Created by Chris Hoffman on 2014.12.06  * 
 * Copyright © 2014 Chris Hoffman. All rights reserved. * 
 */
var wsUri = 'ws://' + document.location.host
        + document.location.pathname.substr(0,
                document.location.pathname.indexOf("/faces"))
        + '/websocket';
console.log(wsUri);
var websocket = new WebSocket(wsUri);
var textField = document.getElementById("textField");
//var users = document.getElementById("users");
var chatlog = document.getElementById("chatlog");
var canSend = false;
var sent = false;
var username;
var pre = document.createElement("p");
websocket.onopen = function(evt) {
    onOpen(evt);
};
websocket.onmessage = function(evt) {
    onMessage(evt);
};
websocket.onerror = function(evt) {
    onError(evt);
};
websocket.onclose = function(evt) {
    onClose(evt);
};
var output = document.getElementById("output");
function join(name) {
    username = name;
    if(!canSend)
    {
        websocket.send(username + " has joined the chat.");
        canSend = true;
        pre.innerHTML = "";
    }
}
function send_message() {
    if (canSend)
    {
        websocket.send(username + ": " + textField.value); 
    }
    else
    {
        if(!sent)
        {
            writeToScreen("Please Join Chat First.");
            sent = true;
        } 
    }
}
function onOpen() {
    //writeToScreen("CONNECTED");
}
function onClose() {
    websocket.send(username + " has left the chat.");
    canSend = false;
    sent = false;
}
function onMessage(evt) {
        chatlog.innerHTML = evt.data + "\n" + chatlog.innerHTML;
}

function onError(evt) {
    writeToScreen('<span style="color: red;">ERROR:</span> ' +
            evt.data);
}
function disconnect() {
    websocket.close();
}

function writeToScreen(message) {  
    pre.style.wordWrap = "break-word";
    pre.innerHTML = message;
    output.appendChild(pre);
}
