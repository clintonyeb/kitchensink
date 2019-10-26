// Web Sockets

const url = "ws://localhost:8080/kitchensink/kitchen-socket";
const title = document.getElementById('team-title');
const state = document.getElementById('team-state');

const websocket = new WebSocket(url);

websocket.onopen = function (evt) {
    console.log("web socket connected created");
};
websocket.onclose = function (evt) {
    console.log("closed")
};
websocket.onmessage = function (evt) {
    console.log("message " + evt.data);
    const data = evt.data;
    if(data == null || data.indexOf("::") === -1) return;
    const team = data.split("::");
    const teamId = team[0];
    const s = team[1];

    if(selectedTeamId && selectedTeamId === teamId) {
        console.log(state);
        state.textContent = s;
    }
};
websocket.onerror = function (evt) {
    console.log("error " + evt);
};


// HTTP Requests

// const axios = axios.default;

async function getUser(url, param, cb) {
    try {
        const response = await axios.get(url + param);
        cb(null, response);
    } catch (error) {
        cb(error);
    }
}

let selectedTeamId;
let selEl;

function setCurrentTeam(event) {
    const el = event.target;
    if(selEl === el) return;

    const v =  el.getAttribute('data-value');
    if(!selectedTeamId && v && selectedTeamId !== v) {
        document.querySelector('.team-button').classList.remove('invisible');
    }
    selectedTeamId = v;

    if(selEl) {
        selEl.classList.remove('active');
    }

    el.classList.add('active');
    selEl = el;
}


function setTeam() {
    const url = "/kitchensink/rest/people/teams/";
    getUser(url, selectedTeamId, (err, res) => {
        if(err) return;
        const team = res.data;
        updateTeamFields(team);
    })
}

function updateTeamFields(team) {
    title.textContent = team.name;
    state.textContent = team.state;
}

function teamStateChanged (event) {
    console.log('changed');
    const el = event.target;
    const teamId = el.getAttribute('data-value');
    const value = el.value;
    const message = teamId + "::" + value;

    if(websocket.readyState === WebSocket.OPEN){
        console.log('sending message ' + message);
        websocket.send(message);
    }
}