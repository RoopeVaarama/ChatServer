object ChatHistory {
    val listOfChatMessages = mutableListOf<ChatMessage>()

    fun insert(message: ChatMessage){
        listOfChatMessages.add(message)
    }

    override fun toString(): String {
        var chatHistory : String = ""
        for (chatMessage in listOfChatMessages) {
            chatHistory += (chatMessage.formattedTime) + (" ") + (chatMessage.message) + "\n"
        }
        return chatHistory
    }
}