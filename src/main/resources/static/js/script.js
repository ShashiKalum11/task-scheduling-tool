document.addEventListener('DOMContentLoaded', function() {
    const addTaskForm = document.getElementById('addTaskForm');
    const taskTableBody = document.getElementById('taskTableBody');
    const sortByPriorityBtn = document.getElementById('sortByPriority');
    const sortByDeadlineBtn = document.getElementById('sortByDeadline');
    const sortByJobTimeBtn = document.getElementById('sortByJobTime');
    const showTaskCountBtn = document.getElementById('showTaskCount');
    const showDaysLeftBtn = document.getElementById('showDaysLeft');
    const taskCountDiv = document.getElementById('taskCount');
    const daysLeftDiv = document.getElementById('daysLeft');

    function fetchTasks(url = '/') {
        fetch(url)
            .then(response => response.text())
            .then(html => {
                const parser = new DOMParser();
                const doc = parser.parseFromString(html, 'text/html');
                taskTableBody.innerHTML = doc.querySelector('#taskTableBody').innerHTML;
            });
    }

    addTaskForm.addEventListener('submit', function(e) {
        e.preventDefault();
        const formData = new FormData(addTaskForm);
        fetch('/add', {
            method: 'POST',
            body: formData
        }).then(() => {
            addTaskForm.reset();
            fetchTasks();
        });
    });

    taskTableBody.addEventListener('click', function(e) {
        if (e.target.classList.contains('remove-task')) {
            const taskName = e.target.dataset.taskName;
            fetch('/remove?taskName=' + encodeURIComponent(taskName), {
                method: 'POST'
            }).then(() => fetchTasks());
        }
        if (e.target.classList.contains('update-status')) {
            const taskName = e.target.dataset.taskName;
            const newStatus = prompt('Enter new status:');
            if (newStatus) {
                fetch('/update-status?taskName=' + encodeURIComponent(taskName) + '&newStatus=' + encodeURIComponent(newStatus), {
                    method: 'POST'
                }).then(() => fetchTasks());
            }
        }
    });

    sortByPriorityBtn.addEventListener('click', () => fetchTasks('/priority'));
    sortByDeadlineBtn.addEventListener('click', () => fetchTasks('/deadline'));
    sortByJobTimeBtn.addEventListener('click', () => fetchTasks('/jobtime'));

    showTaskCountBtn.addEventListener('click', function() {
        fetch('/count')
            .then(response => response.text())
            .then(count => {
                taskCountDiv.textContent = 'Total tasks: ' + count;
            });
    });

    showDaysLeftBtn.addEventListener('click', function() {
        fetch('/days-left')
            .then(response => response.json())
            .then(daysLeft => {
                let html = '<ul>';
                for (let [task, days] of Object.entries(daysLeft)) {
                    html += `<li>${task}: ${days} days left</li>`;
                }
                html += '</ul>';
                daysLeftDiv.innerHTML = html;
            });
    });

    // Initial fetch of tasks
    fetchTasks();
});