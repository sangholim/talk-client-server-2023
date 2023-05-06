const chat = {
    message : {
        viewId: "message-view",
        templateId: "message-template",
        messageSendFormId: "message-send-form",
        sendForm: {
            elements: () => {
                let instance;
                function init() {
                    let messageForm = document.getElementById(chat.message.messageSendFormId);
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
                let messageView = document.querySelector(`#${chat.message.viewId} .col`);
                let message = document.getElementById(chat.message.templateId).cloneNode(true);
                message.querySelector(".message-name > .col").textContent = data.name;
                message.querySelector(".message-content > .col").textContent = data.text;
                message.classList.remove("d-none");
                messageView.insertBefore(message, null);
            }
        },
        client: {
            getInstance : () => {
                let instance;
                function init() {
                    const rsocketClient = new rsocketCore.RSocketClient({
                        transport: new rsocketWebSocketClient(
                            {
                                url: 'ws://resource-service:5002/message-service',
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
                var endpoint = "stream.chats.a.message";
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
                            data: Buffer.from(JSON.stringify({name: "a" , text: content})),
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
                        var endpoint = "stream.chats.a.message";
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