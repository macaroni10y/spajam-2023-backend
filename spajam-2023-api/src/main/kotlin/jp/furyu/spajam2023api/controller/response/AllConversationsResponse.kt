package jp.furyu.spajam2023api.controller.response

class AllConversationsResponse(val conversations: List<Conversation>)

class Conversation(val speaker : String, val text : String)
