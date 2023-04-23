const friend = {
    insert: {
        modalId: 'friend-insert-modal',
        buttonId: 'friend-insert-button',
        messageId: 'friend-insert-message',
        defaultMessage: '이메일을 입력하세요',
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
            friend.insert.modifyMessage(friend.insert.defaultMessage);
        },
        api: function(modal) {
            const body = {};
            const nodes = document.querySelectorAll(`#${modal.id} .modal-body input`);
            nodes.forEach(x=> {
                body[x.name] = x.value;
            });
            var xhr = new XMLHttpRequest();
            xhr.open('POST', '/api/friends');
            xhr.setRequestHeader('Content-type', 'application/json');
            xhr.send(JSON.stringify(body));
            xhr.onload = function (e) {
              var responseBody = JSON.parse(xhr.responseText)
              if(xhr.status === 200) {
                console.log(responseBody);
              } else {
                friend.insert.modifyMessage(responseBody.message);
              }
            };
        },
        modifyMessage: function(text) {
            var message = document.getElementById(`${this.messageId}`);
            message.innerText = text
        }
    }
}