package com.example.tourmatefirebase.viewmodels;

import android.app.Application;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginViewModel extends AndroidViewModel {
    

   public MutableLiveData<Authentication> authenticationMutableLiveData;
   public MutableLiveData<String> errMsg;

    private FirebaseAuth auth;
    public LoginViewModel(@NonNull Application application) {
        super(application);
        auth=FirebaseAuth.getInstance();
        authenticationMutableLiveData=new MutableLiveData<>();
        errMsg=new MutableLiveData<>();
        if (auth.getCurrentUser()==null)
        {
            authenticationMutableLiveData.postValue(Authentication.UNAUTHENTICATED);



        }
        else
        {
            authenticationMutableLiveData.postValue(Authentication.AUTHENTICATED);

        }

    }

    public void login(String email,String pass)
    {
        auth.signInWithEmailAndPassword(email,pass).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {

                authenticationMutableLiveData.postValue(Authentication.AUTHENTICATED);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                errMsg.postValue(e.getLocalizedMessage());
            }
        });
    }

    public void register(String email,String pass)
    {
        auth.createUserWithEmailAndPassword(email,pass).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {

                authenticationMutableLiveData.postValue(Authentication.AUTHENTICATED);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                errMsg.postValue(e.getLocalizedMessage());
            }
        });
    }

    public void logout()
    {
        if (auth.getCurrentUser() !=null)
        {
            auth.signOut();
            authenticationMutableLiveData.postValue(Authentication.UNAUTHENTICATED);
        }
    }

    public enum Authentication
    {
        AUTHENTICATED,UNAUTHENTICATED
    }


}
