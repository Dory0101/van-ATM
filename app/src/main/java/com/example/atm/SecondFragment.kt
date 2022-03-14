package com.example.atm

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.TextureView
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.atm.databinding.FragmentSecondBinding
import com.example.atm.databinding.RowChatroomBinding
import com.google.gson.Gson
import okhttp3.*
import okio.ByteString
import org.w3c.dom.Text
import java.util.concurrent.TimeUnit

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class SecondFragment : Fragment() {
    //design draft

    val chatRooms = listOf<ChatRoom>(
        ChatRoom("101101", "Miau", "Welcome!"),
        ChatRoom("101102", "Miau2", "Welcome!2"),
        ChatRoom("101103", "Miau3", "Welcome!3"),
        ChatRoom("101104", "Miau4", "Welcome!4")
    )
    private var _binding: FragmentSecondBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.

    private val binding get() = _binding!!
    lateinit var websocket: WebSocket
    private val TAG = SecondFragment::class.java.simpleName
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentSecondBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.buttonSecond.setOnClickListener {
            findNavController().navigate(R.id.action_SecondFragment_to_FirstFragment)
        }

        //Web socket
        val client = OkHttpClient.Builder()
            .readTimeout(3, TimeUnit.SECONDS)
            .build()
        val request = Request.Builder()
            .url("wss://lott-dev.lottcube.asia/ws/chat/chat:app_test?nickname=doreen")
            .build()
        websocket = client.newWebSocket(request, object : WebSocketListener() {
            override fun onClosed(webSocket: WebSocket, code: Int, reason: String) {
                super.onClosed(webSocket, code, reason)
                Log.d(TAG, "onClosed: ")
            }

            override fun onClosing(webSocket: WebSocket, code: Int, reason: String) {
                super.onClosing(webSocket, code, reason)
                Log.d(TAG, "onClosing: ")
            }

            override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
                super.onFailure(webSocket, t, response)
                Log.d(TAG, "onFailure: ")
            }

            override fun onMessage(webSocket: WebSocket, text: String) {
                super.onMessage(webSocket, text)
                Log.d(TAG, "onMessage: $text")
            }

            override fun onMessage(webSocket: WebSocket, bytes: ByteString) {
                super.onMessage(webSocket, bytes)
                Log.d(TAG, "onMessage: ${bytes.hex()}")
            }

            override fun onOpen(webSocket: WebSocket, response: Response) {
                super.onOpen(webSocket, response)
                Log.d(TAG, "onOpen: connection!")
            }
        })
        binding.bSend.setOnClickListener {
            val message = binding.edMessage.text.toString()
//            val json = "{ \"action\": \"N\", \"content\": \"$message\" }"
//            websocket.send(json)
            websocket.send(Gson().toJson(Message("N", message)))
        }

        //Recycler's Adapter
        binding.recycler.hasFixedSize() //recycler size fixed
        binding.recycler.layoutManager = LinearLayoutManager(requireContext())
        binding.recycler.adapter = ChatRoomAdapter()

    }

    inner class ChatRoomAdapter : RecyclerView.Adapter<BindingViewHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BindingViewHolder {
//            val view = layoutInflater.inflate(
//                R.layout.row_chatroom, parent, false) //attachToRoot show immediately or not
//            return ChatRoomViewHolder(view)

            val binding = RowChatroomBinding.inflate(layoutInflater, parent, false)
            return BindingViewHolder(binding)

        }

        override fun onBindViewHolder(holder: BindingViewHolder, position: Int) {
            val room = chatRooms[position]
            holder.host.setText(room.hostName)
            holder.title.setText(room.title)
        }

        override fun getItemCount(): Int {
            return chatRooms.size
        }
    }

    inner class BindingViewHolder(val binding: RowChatroomBinding):
        RecyclerView.ViewHolder(binding.root) {
        val host = binding.chatoomHostName
        val title = binding.chatroomTitle
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

data class Message(val action: String, val content :String)