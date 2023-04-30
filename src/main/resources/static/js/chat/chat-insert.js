const chat = {
    insert : {
        popupButtonId: "chat-insert-popup-button",
        popupFriendContainer: "chat-insert-popup-friend-container",
        popupFriendTemplate: "chat-insert-popup-friend-template",
        popupButtonTemplate: "chat-insert-popup-button-template",
        cancelButton: "chat-friends-cancel-button",
        addEvents: function() {
            var btn = document.getElementById(chat.insert.popupButtonId);
            chat.insert.popover(btn);
        },
        popover: function(element) {
            // Create a popover instance
            var popover = new bootstrap.Popover(element, {
                title: '<h5 class="custom-title"><i class="bi-info-circle-fill"></i> 친구 찾기</h5>',
                content: chat.insert.loadFriends,
                html: true
            });
        },
        closePopup: function() {
            var btn = document.getElementById(chat.insert.popupButtonId);
            var popover = bootstrap.Popover.getInstance(btn);
            popover.hide();
        },
        loadFriends: function() {
            var xhr = new XMLHttpRequest();
            xhr.addEventListener('loadstart', chat.insert.loading);
            xhr.open('GET', '/api/friends', false);
            xhr.setRequestHeader('Content-type', 'application/json');
            xhr.send();
            spinner.hide();
            if (xhr.status != 200) {
                return "처리중 예상치 못한 에러가 발견되었습니다";
            }
            var friends = JSON.parse(xhr.responseText);
            return chat.insert.createFriendsView(friends);
        },
        createFriendsView: function(friends) {
            let container = document.getElementById(chat.insert.popupFriendContainer).cloneNode(true);
            friends.map(friend => {
                let friendView = document.getElementById(chat.insert.popupFriendTemplate).cloneNode(true);
                friendView.querySelector("#friend-name").textContent = friend.name;
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

            return container;
        },
        loading: function() {
            spinner.show();
        }
    }
}