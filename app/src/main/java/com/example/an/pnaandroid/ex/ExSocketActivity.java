package com.example.an.pnaandroid.ex;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;

import com.example.an.pnaandroid.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URISyntaxException;
import java.util.ArrayList;

import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;

public class ExSocketActivity extends AppCompatActivity {
    private Socket socket;
    ListView listViewUser, listViewChat;
    EditText editTextContent;
    ImageButton btnAdd, btnSend;
    ArrayList<String> arrayList, arrayChat;
    ArrayAdapter arrayAdapter, arrayAdapterChat;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ex_socket);
        btnAdd = findViewById(R.id.btnAdd);
        btnSend = findViewById(R.id.btnSend);
        editTextContent = findViewById(R.id.editTextContent);
        listViewUser = findViewById(R.id.listViewUser);
        listViewChat = findViewById(R.id.listViewChat);
        arrayList = new ArrayList<>();
        arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, arrayList);
        listViewUser.setAdapter(arrayAdapter);

        arrayChat = new ArrayList<>();
        arrayAdapterChat = new ArrayAdapter(this, android.R.layout.simple_list_item_1, arrayChat);
        listViewChat.setAdapter(arrayAdapterChat);

        try {
            socket = IO.socket("http://10.183.2.52:3000/");
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        socket.connect();
        socket.on("server send result", onRetrieveData);
        socket.on("server send user", onUserList);
        socket.on("server send chat", onChatList);

        //socket.emit("client send data","android programming");
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(editTextContent.getText().toString().length()>0){
                    socket.emit("client register user", editTextContent.getText().toString());
                }

            }
        });
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(editTextContent.getText().toString().length()>0){
                    socket.emit("client send chat", editTextContent.getText().toString());
                }

            }
        });
    }
    private Emitter.Listener onRetrieveData =new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    JSONObject jsonObject = (JSONObject) args[0];
                    try {
                        Boolean exits = jsonObject.getBoolean("result");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    };
    private Emitter.Listener onUserList =new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    JSONObject jsonObject = (JSONObject) args[0];
                    try {
                        JSONArray jsonArray = jsonObject.getJSONArray("userlist");
                        arrayList.clear();
                        for (int i=0; i<jsonArray.length(); i++){
                            String name = jsonArray.getString(i);
                            arrayList.add(name);
                        }
                        arrayAdapter.notifyDataSetChanged();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    };
    private Emitter.Listener onChatList =new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    JSONObject jsonObject = (JSONObject) args[0];
                    try {
                        String  chatcontent = jsonObject.getString("chatcontent");
                        arrayChat.add(chatcontent);
                        arrayAdapterChat.notifyDataSetChanged();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    };
}