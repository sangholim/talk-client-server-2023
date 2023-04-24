const friend = {
    insert: {
        messageId: 'friend-insert-message',
        defaultMessage: '이메일을 입력하세요',
        addEvents : function() {
            var modal = document.getElementById('friend-insert-modal');
            modal.addEventListener('hide.bs.modal', function () {
                friend.insert.modalDataClear(modal);
            });
            var insertButton = document.getElementById('friend-insert-button');
            insertButton.addEventListener('click', function () {
                friend.insert.request(modal);
            });
        },
        modalDataClear: function(modal) {
            const nodes = document.querySelectorAll(`#${modal.id} .modal-body input[type='text']`);
            nodes.forEach(x=> {
                x.value = '';
            });
            friend.insert.modifyMessage(friend.insert.defaultMessage);
        },
        request: function(modal) {
            const body = {};
            const nodes = document.querySelectorAll(`#${modal.id} .modal-body input`);
            nodes.forEach(x=> {
                body[x.name] = x.value;
            });
            var xhr = new XMLHttpRequest();
            xhr.addEventListener('loadstart', friend.insert.loading);
            xhr.addEventListener('load', friend.insert.response);
            xhr.open('POST', '/api/friends');
            xhr.setRequestHeader('Content-type', 'application/json');
            xhr.send(JSON.stringify(body));

        },
        response: function(event) {
            spinner.hide();
            var response = event.target;
            var responseBody = JSON.parse(response.responseText);
            if(response.status !== 201) {
                friend.insert.modifyMessage(responseBody.message);
                return
            }
            location.replace(location.href);
        },
        modifyMessage: function(text) {
            var message = document.getElementById(`${this.messageId}`);
            message.innerText = text;
        },
        loading: function() {
            spinner.show();
        }
    }
}