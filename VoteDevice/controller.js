"use strict";

const divPoll = document.getElementById("divPoll");
const pollCodeInp = document.getElementById("pollCode");
const connectButton = document.getElementById("connectButton");
const status = document.getElementById("status");

const yesVotes = document.getElementById("yesVotes");
const noVotes = document.getElementById("noVotes");

const question = document.getElementById("question");
const theme = document.getElementById("theme");

let id;
let pollId;

const votes = {
    yes: 0,
    no: 0
}

connectButton.onclick = async function () {
    const code = encodeURIComponent(pollCodeInp.value);
    if (code.length === 0) return;

    const resp = await fetch(`https://localhost:8443/api/poll/connect/${code}`);

    if (!resp.ok) {
        divPoll.hidden = true;
        status.innerText = "Not connected";
    }

    divPoll.hidden = false;

    const json = await resp.json();

    id = json.identifier;
    pollId = json.pollId;
    question.innerText = json.question;
    theme.innerText = json.theme;
    status.innerText = "Connected";
}

function sendVotes() {
    fetch("https://localhost:8443/api/poll/vote/device", {
        method: "POST",
        headers:{'Content-Type': 'application/json'},
        body: JSON.stringify({
            deviceId: id,
            pollId: pollId,
            yes: votes.yes,
            no: votes.no
        })
    }).then((resp) => {
        if(resp.ok){
            votes.yes = 0;
            votes.no = 0;
            yesVotes.innerText = votes.yes;
            noVotes.innerText = votes.no;
        }
    })
}

function incYes() {
    votes.yes++;
    yesVotes.innerText = votes.yes;
}

function incNo() {
    votes.no++;
    noVotes.innerText = votes.no;
}

function reset() {
    votes.no = 0;
    votes.yes = 0;

    noVotes.innerText = votes.no;
    yesVotes.innerText = votes.yes;
}
