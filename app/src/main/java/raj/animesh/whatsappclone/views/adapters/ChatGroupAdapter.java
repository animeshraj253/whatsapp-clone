package raj.animesh.whatsappclone.views.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

import raj.animesh.whatsappclone.ChatActivity;
import raj.animesh.whatsappclone.model.ChatGroup;
import raj.animesh.whatsappclone.R;
import raj.animesh.whatsappclone.databinding.GroupsItemCardBinding;

public class ChatGroupAdapter extends RecyclerView.Adapter<ChatGroupAdapter.MyViewHolder> {

    public static ArrayList<ChatGroup> chatGroupArrayList;
    public Context context;

    public ChatGroupAdapter(ArrayList<ChatGroup> chatGroupArrayList, Context context) {
        this.chatGroupArrayList = chatGroupArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        GroupsItemCardBinding b = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.groups_item_card,parent,false);
        return new MyViewHolder(b);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        ChatGroup chatGroup = chatGroupArrayList.get(position);
        holder.binding.showGrpName.setText(chatGroup.getGroupName());
    }

    @Override
    public int getItemCount() {
        return chatGroupArrayList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        GroupsItemCardBinding binding;
        public MyViewHolder (GroupsItemCardBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

            binding.getRoot().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int n = getAdapterPosition();

                    ChatGroup chatGroup = chatGroupArrayList.get(n);
                    Intent i = new Intent(v.getContext(), ChatActivity.class);
                    i.putExtra("GROUP_NAME",chatGroup.getGroupName());


                    v.getContext().startActivity(i);
                    Toast.makeText(v.getContext(), "Clicked on " + binding.showGrpName.getText().toString(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
