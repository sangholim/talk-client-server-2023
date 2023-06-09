const chat = {
    participant: {
        _items: [],
      set items(chatParticipants) {
          if(!chatParticipants) return false;
          chat.participant._items = chatParticipants;
      },
      get: (chatParticipantId) => {
        return chat.participant._items.find(x => x.id == chatParticipantId)
      },
    },
    message : {
        viewId: "message-view",
        templateId: "message-template",
        senderTemplateId: "message-sender-template",
        sendFormId: "message-send-form",
        sendForm: {
            elements: () => {
                let instance;
                function init() {
                    let messageForm = document.getElementById(chat.message.sendFormId);
                    return {
                        textArea: messageForm.querySelector("textarea"),
                        button: messageForm.querySelector("button")
                    }
                }
                if(!instance) {
                    instance = init();
                }
                return instance;
            },
            textAreaClear: () => {
                var elements = chat.message.sendForm.elements();
                elements.textArea.value = '';
            }
        },
        viewForm: {
            insert: (data) => {
                let chatParticipant = chat.participant.get(data.chatParticipantId);
                let messageTemplateId = (chatParticipant.id === chat.message.client._sender)? chat.message.senderTemplateId : chat.message.templateId;
                let messageView = document.querySelector(`#${chat.message.viewId} .col`);
                let message = document.getElementById(messageTemplateId).cloneNode(true);
                message.querySelector(".message-name > .col > div").textContent = chatParticipant.name;
                message.querySelector(".message-content > .col > div").textContent = data.content;
                message.classList.remove("d-none");
                message.classList.add(`${chatParticipant.id}`);
                messageView.insertBefore(message, null);
            }
        },
        client: {
            _url: 'ws://resource-service:5002/message-service',
            _endpoint: '',
            _sender: '',
            set sender(chatParticipantId) {
                            if(!chatParticipantId) return false;
                            chat.message.client._sender = chatParticipantId;
            },
            set endpoint(chatId) {
                if(!chatId) return false;
                chat.message.client._endpoint = `stream.chats.${chatId}.message`;
            },
            getInstance : () => {
                let instance;
                function init() {
                    const rsocketClient = new rsocketCore.RSocketClient({
                        transport: new rsocketWebSocketClient(
                            {
                                url: chat.message.client._url,
                            },
                            rsocketCore.BufferEncoders,
                        ),
                        setup: {
                            dataMimeType: 'application/json',
                            metadataMimeType: rsocketCore.MESSAGE_RSOCKET_COMPOSITE_METADATA.string,
                            keepAlive: 5000,
                            lifetime: 60000,
                        }
                    });
                    return rsocketClient;
                }
                if(!instance) {
                    instance = init();
                }
                return instance;
            },
            requestChannelPayload: () => {
                var endpoint = chat.message.client._endpoint;
                var payload = new rsocketFlowable.Flowable(source => {
                    source.onSubscribe({
                        cancel: () => {},
                        request: (n) => {}
                    });
                    let sendForm = chat.message.sendForm;
                    let elements = sendForm.elements();
                    elements.button.addEventListener("click", function() {
                        let content = elements.textArea.value;
                        if(content == '') {
                            return
                        }
                        source.onNext({
                            data: Buffer.from(JSON.stringify({chatParticipantId: chat.message.client._sender , content: content})),
                            metadata: rsocketCore.encodeAndAddWellKnownMetadata(
                                Buffer.alloc(0),
                                rsocketCore.MESSAGE_RSOCKET_ROUTING,
                                Buffer.from(String.fromCharCode(endpoint.length) + endpoint)
                            )
                        });
                        sendForm.textAreaClear();
                    });
                });
                return payload;
            },
            requestStreamOnNext: (e) => {
                function parseData(data) {
                    try {
                        return JSON.parse(data);
                    } catch (err) {
                        return false;
                    }
                }
                var view = parseData(e.data);
                if(!view) {
                    return false;
                }
                chat.message.viewForm.insert(view);
            },
            connect : function() {
                const rsocketClient = chat.message.client.getInstance();

                rsocketClient.connect()
                    .then(rsocket => {
                        var endpoint = chat.message.client._endpoint;
                        var payload = chat.message.client.requestChannelPayload();

                        rsocket.requestChannel(payload)
                            .subscribe({
                                onSubscribe: (s) => {
                                    s.request(100);
                                },
                                onError: (e) => {
                                    console.log(`request-channel: ${e}`);
                                }
                            });

                        rsocket.requestStream({
                            metadata: rsocketCore.encodeAndAddWellKnownMetadata(
                                Buffer.alloc(0),
                                rsocketCore.MESSAGE_RSOCKET_ROUTING,
                                Buffer.from(String.fromCharCode(endpoint.length) + endpoint)
                            )
                        })
                        .subscribe({
                            onSubscribe: (s) => {
                                s.request(100)
                            },
                            onNext: chat.message.client.requestStreamOnNext,
                            onError: (e) => {
                             console.log(`request-stream: ${e}`);
                            }
                        });
                    });
            }
        }
    },
}