function searchTask() {
    const keyword = document.getElementById('searchKeyword').value;
    fetch(`/search?keyword=${keyword}`)
        .then(response => response.json())
        .then(tasks => {
            displayTasks(tasks);
        });
}

function showTasksByEDF() {
    fetch('/sort/edf')
        .then(response => response.json())
        .then(tasks => {
            displayTasks(tasks);
        });
}

function showTasksBySJF() {
    fetch('/sort/sjf')
        .then(response => response.json())
        .then(tasks => {
            displayTasks(tasks);
        });
}

function displayTasks(tasks) {
    const taskList = document.getElementById('taskList');
    taskList.innerHTML = '';
    tasks.forEach(task => {
        const taskItem = document.createElement('li');
        taskItem.innerHTML = `
            <span>${task.name}</span>
            <span>${task.priority}</span>
            <span>${task.deadline}</span>
            <span>${task.jobTime}</span>
            <span>${task.status}</span>
        `;
        taskList.appendChild(taskItem);
    });
}
