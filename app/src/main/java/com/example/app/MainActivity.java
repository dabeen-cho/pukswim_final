package com.example.app;

import android.Manifest;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.AssetFileDescriptor;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import org.tensorflow.lite.Interpreter;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

import app.akexorcist.bluetotohspp.library.BluetoothSPP;
import app.akexorcist.bluetotohspp.library.BluetoothState;
import app.akexorcist.bluetotohspp.library.DeviceList;


public class MainActivity extends AppCompatActivity {

    private BluetoothSPP bt;
    int size;
    boolean blockDetected = false;
    boolean wallDetected = false;
    boolean downhillDetected = false;
    private SharedPreferences UserInfo;

    public static boolean isPopupDisplayed = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        TextView phoneNumberTextView = findViewById(R.id.phoneNumberEditText);
        TextView heightTextView = findViewById(R.id.heightEditText);
        TextView weightTextView = findViewById(R.id.weightEditText);
        TextView bloodTextView = findViewById(R.id.bloodEditText);
        TextView ageTextView = findViewById(R.id.ageEditText);
        TextView nameTextView =findViewById(R.id.nameEditText);

        String phoneNumber = getIntent().getStringExtra("phone_number");
        String height = getIntent().getStringExtra("height");
        String weight = getIntent().getStringExtra("weight");
        String blood = getIntent().getStringExtra("blood");
        String age = getIntent().getStringExtra("age");
        String name = getIntent().getStringExtra("name");


        phoneNumberTextView.setText("보호자 번호: " + phoneNumber);
        heightTextView.setText("키: " + height+"cm");
        weightTextView.setText("몸무게: " + weight+"kg");
        bloodTextView.setText("혈액형: " + blood+"형");
        ageTextView.setText("나이: "+ age+"살");
        nameTextView.setText("이름 : "+ name);








        bt = new BluetoothSPP(this); //Initializing


        if (!bt.isBluetoothAvailable()) { //블루투스 사용 불가
            Toast.makeText(getApplicationContext()
                    , "Bluetooth is not available"
                    , Toast.LENGTH_SHORT).show();
            finish();
        }

        bt.setOnDataReceivedListener(new BluetoothSPP.OnDataReceivedListener() {
            TextView distance11 = findViewById(R.id.distance1);
            TextView distance22 = findViewById(R.id.distance2);
            TextView distance33 = findViewById(R.id.distance3);
            TextView distance44 = findViewById(R.id.distance4);
            TextView distance55 = findViewById(R.id.distance5);
            TextView distance66 = findViewById(R.id.distance6);
            TextView distance77 = findViewById(R.id.distance7);
            TextView distance88 = findViewById(R.id.distance8);
            TextView distance99 = findViewById(R.id.distance9);
            TextView distance1010 = findViewById(R.id.distance10);
            TextView distance1111 = findViewById(R.id.distance11);
            TextView distance1212 = findViewById(R.id.distance12);

            TextView resultt = findViewById(R.id.result);


            public void onDataReceived(byte[] data, String message) { //데이터 수신용 코드 추가


                String[] array = message.split(",");

                distance11.setText(array[0]);
                distance22.setText(array[1]);
                distance33.setText(array[2]);
                distance44.setText(array[3]);
                distance55.setText(array[4]);
                distance66.setText(array[5]);
                distance77.setText(array[6]);
                distance88.setText(array[7]);
                distance99.setText(array[8]);
                distance1010.setText(array[9]);
                distance1111.setText(array[10]);
                distance1212.setText(array[11]);

                double distance1 = Double.parseDouble(array[0]);
                double distance2 = Double.parseDouble(array[1]);
                double distance3 = Double.parseDouble(array[2]);
                double distance4 = Double.parseDouble(array[3]);
                double distance5 = Double.parseDouble(array[4]);
                double distance6 = Double.parseDouble(array[5]);
                double distance7 = Double.parseDouble(array[6]);
                double distance8 = Double.parseDouble(array[7]);
                double distance9 = Double.parseDouble(array[8]);
                double distance10 = Double.parseDouble(array[9]);
                double distance11 = Double.parseDouble(array[10]);
                double distance12 = Double.parseDouble(array[11]);

                float[][] input = new float[][]{
                        {(float) distance1, (float) distance2, (float) distance3, (float) distance4,
                                (float) distance5, (float) distance6, (float) distance7, (float) distance8,
                                (float) distance9, (float) distance10, (float) distance11, (float) distance12}
                };
                float[][] output = new float[1][1];

                Interpreter tflite = getTfliteInterpreter("model.tflite");
                tflite.run(input, output);

                double prediction = output[0][0]; // Get the predicted value

                ImageView imageViewcircle = findViewById(R.id.circle);
                ImageView imageViewsquare = findViewById(R.id.square);
                ImageView imageViewtri = findViewById(R.id.tri);

                if (prediction>=0&&prediction<0.5) {
                    imageViewtri.setImageResource(R.drawable.normal);
                    resultt.setText(String.valueOf("정상상태"));
                    }else if (prediction<=1.5) {
                    if (!isPopupDisplayed) {
                        Intent popupIntent = new Intent(MainActivity.this, popup.class);
                        isPopupDisplayed = true; // Set the flag to true to indicate a popup is displayed
                        popupIntent.putExtra("popupType", "triangle"); // Pass the popup type to the popup activity
                        startActivity(popupIntent);
                        sendMessage(phoneNumber, "환자의 팔꿈치에 충격이 있습니다");
                    }
                } else if (prediction<=2.5) {
                    if (!isPopupDisplayed) {
                        Intent popupIntent = new Intent(MainActivity.this, popup.class);
                        isPopupDisplayed = true; // Set the flag to true to indicate a popup is displayed
                        popupIntent.putExtra("popupType", "square"); // Pass the popup type to the popup activity
                        startActivity(popupIntent);
                        sendMessage(phoneNumber, "환자의 머리에 충격이 있습니다");
                    }
                } else if (prediction==3) {
                        if (!isPopupDisplayed) {
                            Intent popupIntent = new Intent(MainActivity.this, popup.class);
                            isPopupDisplayed = true; // Set the flag to true to indicate a popup is displayed
                            popupIntent.putExtra("popupType", "circle"); // Pass the popup type to the popup activity
                            startActivity(popupIntent);
                            sendMessage(phoneNumber, "환자의 무릎에 충격이 있습니다");
                        }
                    }
                else{
                    imageViewtri.setImageResource(R.drawable.normal);
                    resultt.setText(String.valueOf("정상상태"));
                }
            }
        }


        );


        bt.setBluetoothConnectionListener(new BluetoothSPP.BluetoothConnectionListener() { //연결됐을 때

            public void onDeviceConnected(String name, String address) {
                Toast.makeText(getApplicationContext()
                        , "Connected to " + name + "\n" + address
                        , Toast.LENGTH_SHORT).show();
            }

            public void onDeviceDisconnected() { //연결해제
                Toast.makeText(getApplicationContext()
                        , "Connection lost", Toast.LENGTH_SHORT).show();
            }

            public void onDeviceConnectionFailed() { //연결실패
                Toast.makeText(getApplicationContext()
                        , "Unable to connect", Toast.LENGTH_SHORT).show();
            }
        });

        Button btnConnect = findViewById(R.id.btnConnect); //연결시도
        btnConnect.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (bt.getServiceState() == BluetoothState.STATE_CONNECTED) {
                    bt.disconnect();
                } else {
                    Intent intent = new Intent(getApplicationContext(), DeviceList.class);
                    startActivityForResult(intent, BluetoothState.REQUEST_CONNECT_DEVICE);
                }
            }
        });




    }
    private void sendMessage(String phoneNumber, String message) {
                try {
                    SmsManager smsManager = SmsManager.getDefault();
                    smsManager.sendTextMessage(phoneNumber, null, message, null, null);
                } catch (Exception e) {
                    e.printStackTrace();
                }
    }
    private Interpreter getTfliteInterpreter(String modelpath) {
        try {
            return new Interpreter(loadModelFile(MainActivity.this, modelpath));
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    private MappedByteBuffer loadModelFile(Activity activity, String modelPath) throws IOException {
        AssetFileDescriptor fileDescriptor = activity.getAssets().openFd(modelPath);
        FileInputStream inputStream = new FileInputStream(fileDescriptor.getFileDescriptor());
        FileChannel fileChannel = inputStream.getChannel();
        long startOffset = fileDescriptor.getStartOffset();
        long declaredLength = fileDescriptor.getDeclaredLength();
        return fileChannel.map(FileChannel.MapMode.READ_ONLY, startOffset, declaredLength);
    }


    public void onDestroy() {
        super.onDestroy();
        bt.stopService(); //블루투스 중지
    }

    public void onStart() {
        super.onStart();
        if (!bt.isBluetoothEnabled()) { //
            Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            startActivityForResult(intent, BluetoothState.REQUEST_ENABLE_BT);
        } else {
            if (!bt.isServiceAvailable()) {
                bt.setupService();
                bt.startService(BluetoothState.DEVICE_OTHER); //DEVICE_ANDROID는 안드로이드 기기 끼리
//                setup();
            }
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == BluetoothState.REQUEST_CONNECT_DEVICE) {
            if (resultCode == Activity.RESULT_OK)
                bt.connect(data);
        } else if (requestCode == BluetoothState.REQUEST_ENABLE_BT) {
            if (resultCode == Activity.RESULT_OK) {
                bt.setupService();
                bt.startService(BluetoothState.DEVICE_OTHER);
//                setup();
            } else {
                Toast.makeText(getApplicationContext()
                        , "Bluetooth was not enabled."
                        , Toast.LENGTH_SHORT).show();
                finish();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }




}
