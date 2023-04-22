const friend = {
    insert: {
        modalId: 'friend-insert-modal',
        buttonId: 'friend-insert-button',
        addEvents : function() {
            var modal = document.getElementById(`${this.modalId}`);
            modal.addEventListener('hide.bs.modal', function () {
                friend.insert.modalDataClear(modal);
            });
            var insertButton = document.getElementById(`${this.buttonId}`);
            insertButton.addEventListener('click', function () {
                friend.insert.api(modal);
            });
        },
        modalDataClear: function(modal) {
            const nodes = document.querySelectorAll(`#${modal.id} .modal-body input[type='text']`);
            nodes.forEach(x=> {
                x.value = '';
            });
        },
        api: function(modal) {
            const body = {};
            const nodes = document.querySelectorAll(`#${modal.id} .modal-body input`);
            nodes.forEach(x=> {
                body[x.name] = x.value;
            });
            var xhr = new XMLHttpRequest();
            xhr.open('POST', '/friends');
            xhr.setRequestHeader('Content-type', 'application/json');
            xhr.send(JSON.stringify(body));
            xhr.onload = function (e) {
              if(xhr.status === 200) {
                console.log(xhr.responseText);
              } else {
                console.log('Error!');
              }
            };
        }
    }
}