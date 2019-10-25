const url = "ws://localhost:8080/kitchensink/kitchen-socket";

const websocket = new WebSocket(url);

websocket.onopen = function (evt) {
    console.log("web socket connected created ", evt);

    websocket.send("client to server");
};
websocket.onclose = function (evt) {
    console.log("closed")
};
websocket.onmessage = function (evt) {
    console.log("message " + evt)
};
websocket.onerror = function (evt) {
    console.log("error " + evt);
};