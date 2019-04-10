let stompClient = null;

const setConnected = (connected) => {
    $("#connect").prop("disabled", connected);
    $("#disconnect").prop("disabled", !connected);
    if (connected) {
        $("#chatLine").show();
    }
    else {
        $("#chatLine").hide();
    }
    $("#message").html("");
}

const connect = () => {
    stompClient = Stomp.over(new SockJS('/user'));
    stompClient.connect({}, (frame) => {
        setConnected(true);
        console.log('Connected: ' + frame);
        stompClient.subscribe('/topic/response', (response) => showUser(JSON.parse(response.body)));
    });
}

const disconnect = () => {
    if (stompClient !== null) {
        stompClient.disconnect();
    }
    setConnected(false);
    console.log("Disconnected");
}

const sendNewUser = () => stompClient.send("/user/SaveUser", {}, JSON.stringify({'login': $("#username").val(),'password':$("#password").val()}))

const showUser = (user) =>  $("#UserRow").append("<tr><td>" +user.id + "</td><td>+user.login+</td></tr>")

const showUsers = (Users) =>Users.forEach(user,i,Users)
{ showUser(user)}

$(function () {
    $("form").on('submit', (event) => {
        event.preventDefault();
    });
    $("#connect").click(connect);
    $("#disconnect").click(disconnect);
    $("#send").click(sendNewUser);
});



