package raj.animesh.whatsappclone.views;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import raj.animesh.whatsappclone.model.ChatGroup;
import raj.animesh.whatsappclone.R;
import raj.animesh.whatsappclone.databinding.ActivityGroupsBinding;
import raj.animesh.whatsappclone.viewmodel.MyViewModel;
import raj.animesh.whatsappclone.views.adapters.ChatGroupAdapter;

public class GroupsActivity extends AppCompatActivity {

    private MyViewModel myViewModel;
    private ActivityGroupsBinding binding;
    private RecyclerView recyclerView;
    private ChatGroupAdapter groupAdapter;
    private ArrayList<ChatGroup> chatGroupArrayList;

    private Dialog chatGroupDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_groups);

        // binding
        binding = DataBindingUtil.setContentView(this,R.layout.activity_groups);

        // my view holder
        myViewModel = new ViewModelProvider(this).get(MyViewModel.class);

        // recycler view
        recyclerView = binding.recyclerViewGroup;
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // observer
        myViewModel.getGroupsList().observe(this, new Observer<List<ChatGroup>>() {
            @Override
            public void onChanged(List<ChatGroup> chatGroups) {
                chatGroupArrayList = new ArrayList<>();
                chatGroupArrayList.addAll(chatGroups);

                groupAdapter = new ChatGroupAdapter(chatGroupArrayList,getApplicationContext());
                recyclerView.setAdapter(groupAdapter);
                groupAdapter.notifyDataSetChanged();
            }
        });

        binding.fabBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showGroupDialog();
            }
        });
    }
    public void showGroupDialog(){

        chatGroupDialog = new Dialog(this);
        chatGroupDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);


        View view = LayoutInflater.from(this).inflate(R.layout.dialog_layout,null);

        chatGroupDialog.setContentView(view);

        chatGroupDialog.show();

        Button btn = view.findViewById(R.id.createGrpBtn);
        EditText editText = view.findViewById(R.id.grpNameInput);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String grpname = editText.getText().toString();

                if(!(grpname.isEmpty())){
                    myViewModel.createGroup(grpname);
                    chatGroupDialog.dismiss();

                    Toast.makeText(GroupsActivity.this, "Group Created "+grpname, Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(GroupsActivity.this, "Please enter group name", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}