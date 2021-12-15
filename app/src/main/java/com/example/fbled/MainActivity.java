package com.example.fbled;

import androidx.appcompat.app.AppCompatActivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {
    Button onButton; // on버튼
    Button offButton; // off버튼
    TextView textView; // 텍스트 출력창
    ImageView oneImage; // 이미지뷰

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        onButton = findViewById(R.id.btn01); // 해당 버튼의 경로설정
        offButton = findViewById(R.id.btn02); // 해당 버튼의 경로설정
        textView = findViewById(R.id.textView); // 해당 뷰의 경로 설정
        oneImage = findViewById(R.id.oneimg); // 해당 이미지뷰의 경로 설정

        setTitle(("LED Remote Control")); // 제목


        // Write a message to the database
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("LED_STATUS");

        myRef.setValue("Hello, World!"); // 초기화면 출력 텍스트
        textView.setText(myRef.getKey());

        // read from the Database, addValue Event Listenning
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String ledState = (String) dataSnapshot.getValue();
                //String value = dataSnapshot.getValue(String.class);
                textView.setText("LED STATUS : " + ledState); // 버튼 클릭시 ledState값을 받아와 텍스트에 출력
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        onButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                textView.setBackgroundColor(Color.YELLOW); // 텍스트의 색깔은 노란색
                // write to the Database
                myRef.setValue("ON"); // on버튼 클릭시 ledState로 on 텍스트 전송
                oneImage.setImageResource(R.drawable.on); // 해당 버튼 클릭시 ON이라는 이미지 출력 및 경로 설정


            }
        });

        offButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                textView.setBackgroundColor(Color.GREEN); // 텍스트의 색깔은 초록색
                // write to the Database
                myRef.setValue("OFF"); // off버튼 클릭시 ledState로 off텍스트 전송
                oneImage.setImageResource(R.drawable.off); // 해당 버튼 클릭시 off라는 이미지출력 및 경로 설정


            }
        });
    }
}