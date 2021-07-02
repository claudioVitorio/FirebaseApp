package com.claudiovitorio.firebaseapp.fragment;

import android.app.Activity;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavDeepLinkBuilder;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.claudiovitorio.firebaseapp.LoginActivity;
import com.claudiovitorio.firebaseapp.NavigationActivity;
import com.claudiovitorio.firebaseapp.R;
import com.claudiovitorio.firebaseapp.util.App;

import static com.claudiovitorio.firebaseapp.util.App.CHANNEL_1;

/**
 * A simple {@link Fragment} subclass.
 */
public class NotificationFragment extends Fragment {
private NotificationManagerCompat notificationManagerCompat;

    public NotificationFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View layout = inflater.inflate(R.layout.fragment_notification, container, false);
        notificationManagerCompat = NotificationManagerCompat.from(getContext());

        EditText editTitle = layout.findViewById(R.id.frag_notification_title);
        EditText editMsg = layout.findViewById(R.id.frag_notification_msg);
        Button btnSend = layout.findViewById(R.id.frag_notification_btn_send);
        btnSend.setOnClickListener(v -> {
            String title = editTitle.getText().toString();
            String msg = editMsg.getText().toString();
            Intent intent = new Intent(getContext(), NavigationActivity.class);

            PendingIntent contentIntent = new NavDeepLinkBuilder(getContext())
                    .setComponentName(NavigationActivity.class)
                    .setGraph(R.navigation.nav_graph)
                    .setDestination(R.id.nav_menu_lista_imagens)
                    .createPendingIntent();

            /*PendingIntent contentIntent = PendingIntent.getActivity(
                    getContext(),
                    0,
                    intent,
                    0
            );*/

            //Criar a notificacao
            Notification notification = new NotificationCompat
                    .Builder(getContext(),CHANNEL_1)
                    .setSmallIcon(R.drawable.ic_account_circle_black_24dp)
                    .setContentTitle(title)
                    .setContentText(msg)
                    .setPriority(Notification.PRIORITY_HIGH)
                    .setContentIntent(contentIntent)
                    .addAction(R.drawable.ic_account_circle_black_24dp,
                            "Toast", actionIntent)
                    .build();
            notificationManagerCompat.notify(1, notification);

        });
        return layout;
    }
}
