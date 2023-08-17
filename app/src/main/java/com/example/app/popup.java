package com.example.app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

public class popup extends Activity {
//사용 안내 팝업

    @Override
    protected void onCreate (Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        //타이틀바 없애기
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.popup);

        TextView text = findViewById(R.id.Text);

        ImageView popupImage = findViewById(R.id.popupimage);


        String popupType = getIntent().getStringExtra("popupType");

        if ("triangle".equals(popupType)) {
            popupImage.setImageResource(R.drawable.arm); // Replace with your triangle image resource
            text.setText("팔꿈치 충격 발생"); // Set the popup text
        } else if ("square".equals(popupType)) {
            popupImage.setImageResource(R.drawable.head); // Replace with your square image resource
            text.setText("머리 충격 발생"); // Set the popup text
        } else if ("circle".equals(popupType)) {
            popupImage.setImageResource(R.drawable.leg); // Replace with your circle image resource
            text.setText("무릎 충격 발생"); // Set the popup text
        }


    }

    public void mOnClose(View v){
        Intent intent = new Intent();
        intent.putExtra("result","Close Popup");
        setResult(RESULT_OK, intent);
        MainActivity.isPopupDisplayed = false; // Reset the flag when the popup is closed

        finish();
    }
    @Override
    public boolean onTouchEvent (MotionEvent event){
        //바깥레이어 클릭시 안닫히게
        if (event.getAction() == MotionEvent.ACTION_OUTSIDE) {
            return false;
        }
        return true;
    }


    @Override
    public void onBackPressed () {
        //안드로이드 백버튼 막기
        MainActivity.isPopupDisplayed = false; // Reset the flag when the popup is closed

        return;
    }



}
