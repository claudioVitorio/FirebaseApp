package com.claudiovitorio.firebaseapp.util;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.claudiovitorio.firebaseapp.R;
import com.claudiovitorio.firebaseapp.model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import static com.claudiovitorio.firebaseapp.util.App.CHANNEL_1;

public class NotificationService extends Service {
private ValueEventListener listener;
private DatabaseReference receiveRef;
    @Override
    public void onCreate() {
        super.onCreate();

    }
    public void showNotify(User user){
        // criando a notificação
        Notification notification = new NotificationCompat
                .Builder(getApplicationContext(), CHANNEL_1)
                .setSmallIcon(R.drawable.ic_account_circle_black_24dp)
                .setContentTitle("Alteração!!!")
                .setContentText(user.getNome())
                .setPriority(Notification.PRIORITY_HIGH)
                .build();

        //enviando para o chennel
        NotificationManager nm = (NotificationManager) getApplicationContext()
                .getSystemService(Context.NOTIFICATION_SERVICE);
        nm.notify(1,notification);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        //é executado quando o serviço é chamado
        receiveRef  = FirebaseDatabase
                .getInstance()
                .getReference("users")
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid());
        listener = receiveRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User user = snapshot.getValue(User.class);
                showNotify(user);
                //finaliza a service

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        return super.onStartCommand(intent, flags, startId);

    }

    @Override
    public void onRebind(Intent intent) {
        super.onRebind(intent);
        Log.i("SERVICE","ok");
        receiveRef.removeEventListener(listener);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
