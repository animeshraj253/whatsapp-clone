package raj.animesh.whatsappclone.views.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.library.baseAdapters.BR;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

import raj.animesh.whatsappclone.model.ChatMessage;
import raj.animesh.whatsappclone.R;
import raj.animesh.whatsappclone.databinding.RowChatsLayoutBinding;

public class ChatMessagezAdapter extends RecyclerView.Adapter<ChatMessagezAdapter.MyViewHolder> {

    List<ChatMessage> chatMessageList;
    Context context;

    public ChatMessagezAdapter(List<ChatMessage> chatMessageList, Context context) {
        this.chatMessageList = chatMessageList;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RowChatsLayoutBinding b = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.row_chats_layout,parent,false);
        return new MyViewHolder(b);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.getBinding().setVariable(BR.chatMessages,chatMessageList.get(position));
        holder.getBinding().executePendingBindings();
    }

    @Override
    public int getItemCount() {
        return chatMessageList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        RowChatsLayoutBinding binding;
        public MyViewHolder(RowChatsLayoutBinding binding) {
            super(binding.getRoot());
            setBinding(binding);
        }

        public RowChatsLayoutBinding getBinding() {
            return binding;
        }

        public void setBinding(RowChatsLayoutBinding binding) {
            this.binding = binding;
        }
    }

}
