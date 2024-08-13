package raj.animesh.whatsappclone.viewmodel;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

import raj.animesh.whatsappclone.model.ChatGroup;
import raj.animesh.whatsappclone.model.ChatMessage;
import raj.animesh.whatsappclone.repositoryfile.Repository;

public class MyViewModel extends AndroidViewModel {

    Repository repository;

    public MyViewModel(@NonNull Application application) {
        super(application);
        repository = new Repository();
    }

    // auth
    public void signupAnonymousUser(){
        Context c = this.getApplication();
        repository.firebaseAnonymousLogin(c);
    }

    // getting current user
    public String getCurrentUserID(){
        return repository.getCurrentUserID();
    }

    // signout
    public void signOutUser(){
        repository.singnOutUser();
    }

    // getting chats groups
    public MutableLiveData<List<ChatGroup>> getGroupsList(){
        return repository.getGroupsMutableLiveData();
    }

    // creating group
    public void createGroup(String grpName){
        repository.createGroup(grpName);
    }

    // messages
    public MutableLiveData<List<ChatMessage>> getMessageLiveData(String grpName){
        return repository.getMessageLiveData(grpName);
    }

    // sending message to firebase database
    public void sendMessage(String message, String grpName){
        repository.sendMessage(message,grpName);
    }


}
