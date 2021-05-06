let countdown;
const timerDisplay = document.querySelector('.display-time-left');
const endTime = document.querySelector('.display-end-time');
const buttons = document.querySelectorAll('[data-time]');

function timer(seconds) {
    clearInterval(countdown);
    const now = Date.now();
    const then = now + seconds * 1000;
    displayTimeRemaining(seconds);
    displayEndTime(then);

    countdown = setInterval(() => {

        const secondsRemaining = Math.round((then - Date.now()) / 1000);

        if (secondsRemaining < 0) {
            clearInterval(countdown);

            return;
        }

        displayTimeRemaining(secondsRemaining);
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

function displayEndTime(timestamp) {
    const end = new Date(timestamp);
    const hours = end.getHours();
    const minutes = end.getMinutes();

    endTime.textContent = `Be Back At ${hours > 12 ? hours - 12 : hours}:${minutes < 10 ? '0' : ''}${minutes}`;

}

function startTimer() {
    const seconds = parseInt(this.dataset.time);

    timer(seconds);
}

buttons.forEach(button => button.addEventListener('click', startTimer));

document.customInput.addEventListener("submit", function(e) {
    e.preventDefault();

    const min = this.minutes.value;


    timer(min * 60);
    this.reset();
})