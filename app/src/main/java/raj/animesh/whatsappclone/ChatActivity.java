package raj.animesh.whatsappclone;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import raj.animesh.whatsappclone.databinding.ActivityChatBinding;
import raj.animesh.whatsappclone.model.ChatMessage;
import raj.animesh.whatsappclone.viewmodel.MyViewModel;
import raj.animesh.whatsappclone.views.adapters.ChatMessagezAdapter;

public class ChatActivity extends AppCompatActivity {

    private ActivityChatBinding binding;
    private ChatMessagezAdapter chatMessagezAdapter;
    private MyViewModel myViewModel;

    private ArrayList<ChatMessage> arrayList;

    private RecyclerView recyclerView;
    private FloatingActionButton messageSendBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        // binding
        binding = DataBindingUtil.setContentView(this,R.layout.activity_chat);

        //view model
        myViewModel = new ViewModelProvider(this).get(MyViewModel.class);

        // views
        recyclerView = binding.messageRecyclerView;
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        messageSendBtn = binding.messageSendBtn;

        // getting group name
        String grpname = getIntent().getStringExtra("GROUP_NAME");

        // live observer
        myViewModel.getMessageLiveData(grpname).observe(this, new Observer<List<ChatMessage>>() {
            @Override
            public void onChanged(List<ChatMessage> chatMessages) {
                arrayList = new ArrayList<>();
                arrayList.addAll(chatMessages);
                chatMessagezAdapter = new ChatMessagezAdapter(arrayList,getApplicationContext());
                recyclerView.setAdapter(chatMessagezAdapter);
                chatMessagezAdapter.notifyDataSetChanged();

                // force recycler view to scroll to last message added
                int lastPosition = chatMessagezAdapter.getItemCount() - 1;
                if(lastPosition > 0){
                    recyclerView.smoothScrollToPosition(lastPosition);
                }

            }
        });

        messageSendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message = binding.messageInput.getText().toString();

                myViewModel.sendMessage(message,grpname);

                binding.messageInput.setText("");
            }
        });



    }
}