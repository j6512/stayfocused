let workCountdown;
let breakCountdown;
const timerDisplay = document.querySelector('.display-time-left');
const buttons = document.querySelectorAll('[data-time]');


function timer(workSeconds, breakSeconds) {
    clearInterval(workCountdown);
    clearInterval(breakCountdown);

    if (workSeconds == 0) {
        document.getElementById('background').style.backgroundColor = 'white';
        displayTimeRemaining(0);
        return;
    }

    const workNow = Date.now();
    const workThen = workNow + workSeconds * 1000;
    displayTimeRemaining(workSeconds);

    document.getElementById('background').style.backgroundColor = 'yellow';

    workCountdown = setInterval(() => {


        const workSecondsRemaining = Math.round((workThen - Date.now()) / 1000);

        if (workSecondsRemaining != 0) {
//            localStorage.timeRemaining = workSecondsRemaining;
            displayTimeRemaining(workSecondsRemaining);
        } else if (workSecondsRemaining == 0) {
//            localStorage.timeRemaining = workSecondsRemaining;
            displayTimeRemaining(workSecondsRemaining);
            clearInterval(workCountdown);
//            localStorage.removeItem("timeRemaining");

            // starts the break timer once the working timer reaches 0
            const breakNow = Date.now();
            const breakThen = breakNow + breakSeconds * 1000;
            breakCountdown = setInterval(() => {
            document.getElementById('background').style.backgroundColor = 'blue';
                const breakSecondsRemaining = Math.round((breakThen - Date.now()) / 1000) + 1;
                if (breakSecondsRemaining != 0) {
//                    localStorage.timeRemaining = breakSecondsRemaining;
                    displayTimeRemaining(breakSecondsRemaining);
                } else if (breakSecondsRemaining == 0) {
//                    localStorage.timeRemaining = breakSecondsRemaining;
                    displayTimeRemaining(breakSecondsRemaining);
                    clearInterval(breakCountdown);
//                    localStorage.removeItem("timeRemaining");
                    timer(workSeconds, breakSeconds);
                    return;
                } else if (breakSecondsRemaining < 0) {
                    clearInterval(workCountdown);
                    clearInterval(breakCountdown);
                    return;
                }
            }, 1000);

        } else if (workSecondsRemaining < 0) {
            clearInterval(workCountdown);
            clearInterval(breakCountdown);
            return;
        }
    }, 1000);
}

function displayTimeRemaining(seconds) {
    const minutes = Math.floor(seconds / 60);
    const remainderSeconds = seconds % 60;
    const display = `${minutes < 10 ? '0' : ''}${minutes}:${remainderSeconds < 10 ? '0' : ''}${remainderSeconds}`;
    timerDisplay.textContent = display;
    document.title = display;

    console.log({minutes, remainderSeconds});
}

function startTimer() {
    const seconds = parseInt(this.dataset.time);

    timer(seconds);
}

buttons.forEach(button => button.addEventListener('click', startTimer));

document.customInput.addEventListener("submit", function(e) {
    e.preventDefault();

    const workMinutes = this.workMinutes.value;
    const breakMinutes = this.breakMinutes.value;

    timer(workMinutes * 60, breakMinutes * 60);
    this.reset();
})

//if (localStorage.timeRemaining) {
//    timer(localStorage.timeRemaining);
//}
//
