package com.example.eata;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    EditText editText;
    ImageView imageView;
    ArrayList<Chatsmodal> chatsmodalArrayList;
    ChatAdapter chatAdapter;
    private  final String USER_KEY = "user";
    private  final String BOT_KEY = "bot";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.mainBackground));

        recyclerView = findViewById(R.id.chat_recycler);
        editText = findViewById(R.id.edt_msg);
        imageView = findViewById(R.id.send_btn);
        chatsmodalArrayList = new ArrayList<>();
        chatAdapter = new ChatAdapter(chatsmodalArrayList,this);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(chatAdapter);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(editText.getText().toString().isEmpty()){
                    Toast.makeText(MainActivity.this,"Please enter your message",Toast.LENGTH_SHORT).show();
                    return;
                }
                getResponse(editText.getText().toString());
                editText.setText("");
            }
        });

    }

            public void emj(View v) {
                Intent intent = new Intent(MainActivity.this,about_eata.class);
                startActivity(intent);
            }

    private void getResponse(String message) {
        chatsmodalArrayList.add(new Chatsmodal(message,USER_KEY));
        chatAdapter.notifyDataSetChanged();
        String url = "http://api.brainshop.ai/get?bid=166629&key=p5j26OYuH2X3RR28&uid=[uid]&msg=[msg]"+message;
        String BASE_URL = "http://api.brainshop.ai/";
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        RetroFitApi retroFitApi = retrofit.create(RetroFitApi.class);
        Call<MsgModal> call = retroFitApi.getMessage(url);
        call.enqueue(new Callback<MsgModal>() {
            @Override
            public void onResponse(Call<MsgModal> call, Response<MsgModal> response) {
                if(response.isSuccessful()){
                    MsgModal msgModal = response.body();
                    chatsmodalArrayList.add(new Chatsmodal(msgModal.getCnt(),BOT_KEY));
                    chatAdapter.notifyDataSetChanged();
                    recyclerView.scrollToPosition(chatsmodalArrayList.size()-1);
                }
            }

            @Override
            public void onFailure(Call<MsgModal> call, Throwable t) {
                chatsmodalArrayList.add(new Chatsmodal("no internet connection!",BOT_KEY));
                chatAdapter.notifyDataSetChanged();
            }
        });
    }
}