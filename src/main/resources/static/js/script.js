document.addEventListener('DOMContentLoaded', function() {
    // Function to search tasks
    window.searchTask = function() {
        const keyword = document.getElementById('searchKeyword').value;
        window.location.href = `/search?keyword=${keyword}`;
    }

    // Function to show tasks sorted by priority
    window.showTasksByPriority = function() {
        window.location.href = '/priority';
    }

    // Function to show tasks sorted by deadline (EDF)
    window.showTasksByEDF = function() {
        window.location.href = '/deadline';
    }

    // Function to show tasks sorted by shortest job first (SJF)
    window.showTasksBySJF = function() {
        window.location.href = '/jobtime';
    }

    window.showPriorityUpdatedTasks = function() {
        fetch('/priority-updated')
            .then(response => response.json())
            .then(tasks => {
                let taskList = tasks.map(task =>
                    `<li>Task: ${task.name}, Priority: ${task.priority}</li>`
                ).join('');

                Swal.fire({
                    title: 'Priority Updated Tasks',
                    html: `<ul>${taskList}</ul>`,
                    icon: 'info',
                    width: '600px'
                });
            })
            .catch(error => {
                console.error('Error:', error);
                Swal.fire(
                    'Error!',
                    'Failed to fetch priority updated tasks.',
                    'error'
                );
            });
    }

    // Function to show days left for tasks
    window.showDaysLeft = function() {
        fetch('/days-left')
            .then(response => response.json())
            .then(data => {
                let daysLeftHtml = '<ul>';
                for (const [taskName, daysLeft] of Object.entries(data)) {
                    daysLeftHtml += `<li>${taskName}: ${daysLeft} day(s) left</li>`;
                }
                daysLeftHtml += '</ul>';

                Swal.fire({
                    title: 'Days Left for Tasks',
                    html: daysLeftHtml,
                    icon: 'info'
                });
            })
            .catch(error => {
                console.error('Error:', error);
                Swal.fire(
                    'Error!',
                    'An error occurred while fetching days left for tasks.',
                    'error'
                );
            });
    }

    // Function to open task status update modal
    window.showUpdateStatusModal = function(taskName) {
        document.getElementById('taskName').value = taskName;
        $('#statusModal').modal('show');
    }

    // Event listener for the update status button
    document.getElementById('updateStatusBtn').addEventListener('click', function() {
        const taskName = document.getElementById('taskName').value;
        const newStatus = document.getElementById('newStatus').value;

        if (!newStatus) {
            Swal.fire('Error', 'Please enter a new status', 'error');
            return;
        }

        fetch(`/update-status?taskName=${encodeURIComponent(taskName)}&newStatus=${encodeURIComponent(newStatus)}`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded',
            }
        }).then(response => {
            if (response.ok) {
                return response.text();
            }
            throw new Error('Network response was not ok.');
        }).then(data => {
            console.log('Server response:', data);
            $('#statusModal').modal('hide');
            Swal.fire(
                'Updated!',
                `Task "${taskName}" status has been updated to "${newStatus}".`,
                'success'
            ).then(() => {
                window.location.reload();
            });
        }).catch(error => {
            console.error('Error:', error);
            Swal.fire(
                'Error!',
                'An error occurred while updating the task status.',
                'error'
            );
        });
    });

    // Ensure the modal is properly initialized
    $(document).ready(function() {
        $('#statusModal').modal({
            show: false
        });
    });

    window.removeTask = function(taskName) {
        Swal.fire({
            title: 'Are you sure?',
            text: `You are about to remove the task: ${taskName}`,
            icon: 'warning',
            showCancelButton: true,
            confirmButtonColor: '#3085d6',
            cancelButtonColor: '#d33',
            confirmButtonText: 'Yes, remove it!'
        }).then((result) => {
            if (result.isConfirmed) {
                fetch(`/remove?taskName=${encodeURIComponent(taskName)}`, {
                    method: 'POST',
                    headers: {
                        'Content-Type': 'application/x-www-form-urlencoded',
                    }
                }).then(response => {
                    if (response.ok) {
                        return response.text();
                    }
                    throw new Error('Network response was not ok.');
                }).then(data => {
                    console.log('Server response:', data);
                    Swal.fire(
                        'Removed!',
                        `Task "${taskName}" has been removed.`,
                        'success'
                    ).then(() => {
                        window.location.reload();
                    });
                }).catch(error => {
                    console.error('Error:', error);
                    Swal.fire(
                        'Error!',
                        'An error occurred while removing the task.',
                        'error'
                    );
                });
            }
        });
    }

    window.showTaskStatusWindow = function() {
        fetch('/status-window')
            .then(response => {
                if (!response.ok) {
                    throw new Error('Server responded with status: ' + response.status);
                }
                return response.json();
            })
            .then(statuses => {
                let statusList = statuses.map(task => `<li>${task.name}: ${task.status}</li>`).join('');

                Swal.fire({
                    title: 'Task Statuses',
                    html: statusList ? `<ul>${statusList}</ul>` : 'No tasks found.',
                    icon: 'info',
                    width: '600px'
                });
            })
            .catch(error => {
                console.error('Error:', error);
                Swal.fire(
                    'Error!',
                    'Failed to fetch task statuses: ' + error.message,
                    'error'
                );
            });
    }

    // Function to show task count
    window.showTaskCount = function() {
        fetch('/count')
            .then(response => response.text())
            .then(count => {
                Swal.fire({
                    title: 'Task Count',
                    text: `Total Tasks: ${count}`,
                    icon: 'info'
                });
            });
    }
});

function clearSearch() {
    window.location.href = `/search?keyword=`;
}