import java.io.BufferedReader
import java.io.PrintWriter
import java.net.Socket

fun main() {
    print("host=> ")
    val host = readLine()
    print("port=> ")
    val socket = Socket(host, readLine()!!.toInt())
    val socketInputChannel = BufferedReader(socket.getInputStream().reader())
    val socketOutputChannel = PrintWriter(socket.getOutputStream(), true)
    Thread {
        while (true) {
            if (socketInputChannel.ready())
                println(
                    socketInputChannel.readLine()
                )
        }
    }.start()
    Thread {
        while (true)
            socketOutputChannel.println(readLine())
    }.start()
}
