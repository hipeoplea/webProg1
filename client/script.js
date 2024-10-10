const ANSWERS = {
    X_ERROR: "Неверный X или X не введён!",
    Y_ERROR: "Пожалуйста, выберите Y",
    R_ERROR: "Пожалуйста, выберите R",
    HIT_RESULT: {
        true: "Да",
        false: "Нет"
    },
    API: '/api/',
    HIT_COLOUR: {
        true: "green",
        false: "red"
    }
};

function getValidatedX() {
    const xInput = document.querySelector('#X');
    let x = parseFloat(xInput.value);

    let validationResult = !isNaN(x) && x <= 3 && x >= -3;
    if (validationResult === false) xInput.value = "";
    return validationResult ? x : null;
}

function getValidatedYR(n) {
    const Inp = document.getElementsByName(n);
    for (let i = 0; i < Inp.length; i++) {
        if (Inp[i].type === "radio" && Inp[i].checked) {
            return parseInt(Inp[i].value);
        }
    }
    return null;
}

async function validateInput(event) {
    event.preventDefault();
    const xRes = getValidatedX();
    const yRes = getValidatedYR('Y');
    const rRes = getValidatedYR('R');

    if (xRes == null || yRes == null || rRes == null) {
        if (xRes == null) alert(ANSWERS.X_ERROR);
        if (yRes == null) alert(ANSWERS.Y_ERROR);
        if (rRes == null) alert(ANSWERS.R_ERROR);
        return;
    }
    const request = {
        method: "POST",
        headers: {
            "Content-Type": "application/json",
        },
        body: JSON.stringify({
            x: xRes,
            y: yRes,
            r: rRes
        })
    };
    fetch('/api/', request)
        .then(response => response.json())
        .then(data => {
            handleResponse(xRes, yRes, rRes, data["isHit"], data["time"]);
        });
}

async function handleResponse(xResult, yResult, rResult, hit, time) {
    let body = document.querySelector('tbody');
    let passPoint = document.getElementById('no-data');
    if (passPoint) {
        passPoint.remove();
    }
    let answRow = document.createElement("tr");
    answRow.innerHTML = `
        <td>${xResult}</td>
    <td>${yResult}</td>
    <td>${rResult}</td>
    <td>${ANSWERS.HIT_RESULT[hit]}</td>
    <td>${time}</td>
    <td>${new Date().toLocaleString()}</td>`
    ;
    body.appendChild(answRow);
    writeDotResult(xResult, yResult, rResult, hit)

}

async function writeDotResult(xResult, yResult, rResult, hit){
    let passDot = document.getElementById('answerDot');
    let rSize = 400 / (rResult * 4)

    passDot.setAttribute("cx", 200 + xResult * rSize);
    passDot.setAttribute("cy", 200 - yResult * rSize);
    passDot.setAttribute("r", 3);
    passDot.setAttribute("fill", ANSWERS.HIT_COLOUR[hit]);

}
const submitButton = document.querySelector("#accept-button");
submitButton.addEventListener("click", (event) => validateInput(event));

