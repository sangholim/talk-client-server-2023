const friend = {
    insert: {
        modalId: 'friend-insert-modal',
        addEvent : function() {
            var modal = document.getElementById(`${this.modalId}`);
            modal.addEventListener('hide.bs.modal', function () {
                const nodes = document.querySelectorAll(`#${modal.id} .modal-body input`);
                console.log(this.modalId);
                nodes.forEach(x=> {
                    x.value = '';
                });
            })
        }
    }
}