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
                console.log('Days left for tasks:', data);
            });
    }

    // Function to open task status update modal
    window.showUpdateStatusModal = function(taskName) {
        document.getElementById('taskName').value = taskName;
        $('#statusModal').modal('show');
    }

    // Function to handle status update form submission
    document.getElementById('statusForm').addEventListener('submit', function(event) {
        event.preventDefault();
        const taskName = document.getElementById('taskName').value;
        const newStatus = document.getElementById('newStatus').value;

        fetch(`/update-status?taskName=${taskName}&newStatus=${newStatus}`, {
            method: 'POST'
        }).then(() => {
            Swal.fire(
                'Updated!',
                `Task ${taskName} status has been updated.`,
                'success'
            ).then(() => {
                $('#statusModal').modal('hide');
                window.location.reload();
            });
        });
    });

    // Function to remove a task
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
                fetch(`/remove?taskName=${taskName}`, {
                    method: 'POST'
                }).then(() => {
                    Swal.fire(
                        'Removed!',
                        `Task ${taskName} has been removed.`,
                        'success'
                    ).then(() => {
                        window.location.reload();
                    });
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
