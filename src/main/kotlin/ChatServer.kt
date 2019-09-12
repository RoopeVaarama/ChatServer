import java.net.ServerSocket

class ChatServer{
    fun serve() {
        try {
            val serverSocket = ServerSocket(30001, 3)

            while(true) {
                val s = serverSocket.accept()
                println("new connection " + s.inetAddress.hostAddress + " " + s.port)

                val chatConnector = ChatConnector(s)

                val t = Thread(chatConnector)
                t.start()
            }
        } catch (e: Exception) {
            println("Got exception: ${e.message}")
        } finally {
            println("Server opened")
        }
    }
}