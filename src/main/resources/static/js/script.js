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

    // Function to show tasks with updated priority
    window.showPriorityUpdatedTasks = function() {
        window.location.href = '/priority-updated';
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

    document.getElementById('statusForm').addEventListener('submit', function(event) {
        event.preventDefault();
        const taskName = document.getElementById('taskName').value;
        const newStatus = document.getElementById('newStatus').value;

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
            Swal.fire(
                'Updated!',
                `Task "${taskName}" status has been updated to "${newStatus}".`,
                'success'
            ).then(() => {
                $('#statusModal').modal('hide');
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

    // Function to show task status window
    window.showTaskStatusWindow = function() {
        const statusWindow = document.getElementById('statusWindow');
        if (statusWindow) {
            statusWindow.showModal();
        }
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
