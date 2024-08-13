package raj.animesh.whatsappclone.repositoryfile;

import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import raj.animesh.whatsappclone.model.ChatGroup;
import raj.animesh.whatsappclone.model.ChatMessage;
import raj.animesh.whatsappclone.views.GroupsActivity;
// it act as a bridge between the view model and the data source
public class Repository {

    MutableLiveData<List<ChatGroup>> groupsMutableLiveData;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference ,messageDbReference;
    MutableLiveData<List<ChatMessage>> messageLiveData;


    public Repository(){
        groupsMutableLiveData = new MutableLiveData<>();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
        messageLiveData = new MutableLiveData<>();
    }

    // firebase auth
    public void firebaseAnonymousLogin(Context context) {
        FirebaseAuth.getInstance().signInAnonymously()
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Intent i = new Intent(context, GroupsActivity.class);
                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(i);
                }
            }
        });
    }

    // getting current user id
    public String getCurrentUserID(){
        return FirebaseAuth.getInstance().getUid();
    }

    // signout user from app
    public void singnOutUser(){
        FirebaseAuth.getInstance().signOut();
    }

    // receiving all groups from firebase
    public MutableLiveData<List<ChatGroup>> getGroupsMutableLiveData() {
        List<ChatGroup> groupsList = new ArrayList<>();
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                groupsList.clear();
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    // change it
                    ChatGroup cg = new ChatGroup(dataSnapshot.getKey());

                    groupsList.add(cg);
                }
                groupsMutableLiveData.postValue(groupsList);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d("TAG","Error is : " + error.getMessage());
            }
        });
        return groupsMutableLiveData;
    }

    // creating group
    public void createGroup(String groupName){
        databaseReference.child(groupName).setValue(groupName);
    }

    public MutableLiveData<List<ChatMessage>> getMessageLiveData(String grpName) {
        List<ChatMessage> messageList = new ArrayList<>();
        messageDbReference = firebaseDatabase.getReference().child(grpName);

        messageDbReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                messageList.clear();
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    ChatMessage chatMessage = dataSnapshot.getValue(ChatMessage.class);
                    messageList.add(chatMessage);
                }
                messageLiveData.postValue(messageList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(firebaseDatabase.getApp().getApplicationContext(),
                        "Error : "+error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        return messageLiveData;
    }

    public void sendMessage (String messageText, String grpName){
        DatabaseReference ref = firebaseDatabase.getReference(grpName);

        if (!messageText.trim().equals("")){
            ChatMessage chatMessage = new ChatMessage(
                    FirebaseAuth.getInstance().getCurrentUser().getUid(),
                    messageText,
                    System.currentTimeMillis()
            );

            String randomID = ref.push().getKey();
            ref.child(randomID).setValue(chatMessage);
        }
    }
}
