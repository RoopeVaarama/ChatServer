import java.net.ServerSocket

/**
 * Created by Topias, Roope and Tiia
 */

/**
 * When the server is started, this class handles all new connection requests
 * and starts a new session with a new thread for every client
 */
class ChatServer{
    fun serve() {
        try {
            val serverSocket = ServerSocket(30001, 3)

            while(true) {
                val s = serverSocket.accept()
                println("new connection " + s.inetAddress.hostAddress + " " + s.port)


                val chatConnector = ChatConnector(s)
                ChatHistory.registerObserver(chatConnector)
                ChatHistory.registerObserver(TopChatter)
                ChatHistory.registerObserver(ChatConsole)

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