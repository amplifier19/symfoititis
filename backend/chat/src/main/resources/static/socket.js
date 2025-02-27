let stompClient = null;
const message = {
    room: "a61b49c5-ecdb-3634-af92-1b2570a2e159",
    dep_id: 1,
    c_id: 2,
    is_teacher: false,
    sender_id: "c08746ce-8e13-411b-8420-b437161fad94",
    recipient_id: "cb62f508-4788-4432-a8ad-94cd13b5c2bc",
    type: "TEXT",
    content: "HELLO"
}

function onConnected() {
  console.log('connected')
  stompClient.send("/app/chat/send", {}, JSON.stringify(message));
}

function onError() {
    console.log('Could not connect to WebSocket server. Please refresh this page to try again!');
}

function connect() {
        const socket = new SockJS('http://localhost:8084/ws');
        stompClient = Stomp.over(socket);
        stompClient.connect({}, onConnected, onError);
}

connect();

