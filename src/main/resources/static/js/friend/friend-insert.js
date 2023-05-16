const friend = {
    email: {
        nav: {
            get: () => {
                return document.querySelector('#friend-insert-modal .nav .email')
            },
            active: () => {
                let nav = friend.email.nav.get();
                nav.classList.add("active");
                nav.style.color='';
            },
            inActive: () => {
                let nav = friend.email.nav.get();
                nav.classList.remove("active");
                nav.style.color='grey';
            }
        },
        form: {
            get: () => {
                return document.querySelector('#friend-insert-modal .form .email')
            },
            active: () => {
                let form = friend.email.form.get();
                form.classList.add("active");
                form.classList.remove("d-none");
            },
            inActive: () => {
                let form = friend.email.form.get();
                form.classList.remove("active");
                form.classList.add("d-none");
            },
            fieldClear: () => {
                const nodes = friend.email.form.get().querySelectorAll(`.modal-body input[type='text']`);
                nodes.forEach(x=> {
                    x.value = '';
                });
                friend.email.form.message.modify();
            },
            message: {
                _default: '이메일을 입력하세요',
                get: () => {
                    return friend.email.form.get().querySelector('.message');
                },
                modify: (text) => {
                    friend.email.form.message.get().innerText = !text? friend.email.form.message._default : text;
                }
            }
        }
    },
    name: {
        nav: {
            get: () => {
                return document.querySelector('#friend-insert-modal .nav .name')
            },
            active: () => {
                let nav = friend.name.nav.get();
                nav.classList.add("active");
                nav.style.color='';
            },
            inActive: () => {
                let nav = friend.name.nav.get();
                nav.classList.remove("active");
                nav.style.color='grey';
            }
        },
        form: {
            get: () => {
                return document.querySelector('#friend-insert-modal .form .name')
            },
            active: () => {
                let form = friend.name.form.get();
                form.classList.add("active");
                form.classList.remove("d-none");
            },
            inActive: () => {
                let form = friend.name.form.get();
                form.classList.remove("active");
                form.classList.add("d-none");
            },
            fieldClear: () => {
                const nodes = friend.name.form.get().querySelectorAll(`.modal-body input[type='text']`);
                nodes.forEach(x=> {
                    x.value = '';
                });
                friend.name.form.message.modify();
            },
            message: {
                _default: '이름을 입력하세요',
                get: () => {
                    return friend.name.form.get().querySelector('.message');
                },
                modify: (text) => {
                    friend.name.form.message.get().innerText = !text? friend.name.form.message._default : text;
                }
            }
        }
    },
    insert: {
        addEvents : function() {
            friend.email.nav.get().addEventListener('click', function() {
                friend.name.form.inActive();
                friend.name.nav.inActive();
                friend.name.form.fieldClear();

                friend.email.nav.active();
                friend.email.form.active();
                friend.email.form.fieldClear();
            });
            friend.name.nav.get().addEventListener('click', function() {
                friend.email.form.inActive();
                friend.email.nav.inActive();
                friend.email.form.fieldClear();

                friend.name.nav.active();
                friend.name.form.active();
                friend.name.form.fieldClear();
            });
            var modal = document.getElementById('friend-insert-modal');
            modal.addEventListener('hide.bs.modal', function () {
                friend.name.form.inActive();
                friend.name.nav.inActive();
                friend.name.form.fieldClear();

                friend.email.nav.active();
                friend.email.form.active();
                friend.email.form.fieldClear();
            });
            var insertButton = document.getElementById('friend-insert-button');
            insertButton.addEventListener('click', function () {
                friend.insert.request(modal);
            });
        },
        activeForm: () => {
            return document.querySelector(`#friend-insert-modal .modal-body .form .active`);
        },
        request: function(modal) {
            const body = {};
            const activeForm = friend.insert.activeForm();
            const nodes = activeForm.querySelectorAll(`input`);
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
            const activeForm = friend.insert.activeForm();
            const message = activeForm.querySelector(`.message`);
            message.innerText = text;
        },
        loading: function() {
            spinner.show();
        }
    }
}