
on load {
    send "Hello Light" to console
    send "Hello Panda" to console; send "Hello Core" to console // :3

    loop 2b+10s times {
        invoke "ray"
    }

    send 5 > 10 to console
    send 10 > 5 to console
}

function ray {
    send "Hello Ray" to console
}