const chat = {
    insert : {
        popupButtonId: "chat-insert-popup-button",
        popupFriendContainer: "chat-insert-popup-friend-container",
        popupMessageTemplate: "chat-insert-popup-message-template",
        popupFriendTemplate: "chat-insert-popup-friend-template",
        popupButtonTemplate: "chat-insert-popup-button-template",
        cancelButton: "chat-friends-cancel-button",
        insertButton: "chat-friends-insert-button",
        addEvents: function() {
            var btn = document.getElementById(chat.insert.popupButtonId);
            chat.insert.popover(btn);
        },
        popover: function(element) {
            // Create a popover instance
            let popover = new bootstrap.Popover(element, {
                title: '<h5 class="custom-title"><i class="bi-info-circle-fill"></i> 친구 찾기</h5>',
                content: chat.insert.popoverView,
                html: true
            });
        },
        closePopup: function() {
            let btn = document.getElementById(chat.insert.popupButtonId);
            let popover = bootstrap.Popover.getInstance(btn);
            popover.hide();
        },
        showPopoverMessage: function(popover) {
            let message = popover.tip.querySelector(`#${chat.insert.popupMessageTemplate}`);
            message.classList.remove("d-none");
        },
        disableInsertButton: function(popover) {
            let btn = popover.tip.querySelector(`#${chat.insert.insertButton}`);
            btn.disabled = true;
        },
        popoverView: function() {
            function createFriendView(friends) {
                let container = document.getElementById(chat.insert.popupFriendContainer).cloneNode(true);
                let message = document.getElementById(chat.insert.popupMessageTemplate).cloneNode(true);
                container.insertBefore(message, null);

                friends.map(friend => {
                    let friendView = document.getElementById(chat.insert.popupFriendTemplate).cloneNode(true);
                    friendView.querySelector(".friend-name").textContent = friend.name;
                    friendView.querySelector("input[name='friend-id']").value = friend.id;
                    friendView.classList.remove("d-none");
                    container.appendChild(friendView);
                    container.insertBefore(friendView, null);
                    return friendView;
                });
                container.classList.remove("d-none");
                let buttonTemplate = document.getElementById(chat.insert.popupButtonTemplate).cloneNode(true);
                buttonTemplate.classList.remove("d-none");
                container.insertBefore(buttonTemplate, null);

                let cancelButton = container.querySelector(`#${chat.insert.cancelButton}`);
                cancelButton.addEventListener('click', chat.insert.closePopup);
                let insertButton = container.querySelector(`#${chat.insert.insertButton}`);
                insertButton.addEventListener('click', chat.insert.request);

                return container;
            }

            let xhr = new XMLHttpRequest();
            xhr.addEventListener('loadstart', chat.insert.loading);
            xhr.open('GET', '/api/friends', false);
            xhr.setRequestHeader('Content-type', 'application/json');
            xhr.send();
            spinner.hide();
            if (xhr.status != 200) {
                return "처리중 예상치 못한 에러가 발견되었습니다";
            }
            let friends = JSON.parse(xhr.responseText);

            return createFriendView(friends);
        },
        request: function() {
            const nodes = document.querySelectorAll("input[name='friend-id']:checked");
            const body = {
                friendIds : Array.from(nodes).map(x => x.value)
            };

            let xhr = new XMLHttpRequest();
            xhr.addEventListener('loadstart', chat.insert.loading);
            xhr.open('POST', '/api/chats', false);
            xhr.setRequestHeader('Content-type', 'application/json');
            xhr.send(JSON.stringify(body));
            spinner.hide();
            if (xhr.status != 201) {
                var btn = document.getElementById(chat.insert.popupButtonId);
                var popover = bootstrap.Popover.getInstance(btn);
                chat.insert.showPopoverMessage(popover);
                chat.insert.disableInsertButton(popover);
                return;
            }
            location.replace(location.href);
        },
        loading: function() {
            spinner.show();
        }
    }
}