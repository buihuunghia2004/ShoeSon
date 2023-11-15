package com.example.shoeson.Activities.Intro;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.example.shoeson.R;
import com.example.shoeson.databinding.ActivityIntroBinding;

public class IntroActivity extends AppCompatActivity {
    ActivityIntroBinding binding;
    int count;
    int[] arrImage;
    String[] arrContent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityIntroBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        count=1;
        arrImage= new int[]{R.drawable.shoe1, R.drawable.shoe2, R.drawable.shoe3};
        arrContent=new String[]{"content1","content2","content3"};

    }

    @Override
    protected void onResume() {
        super.onResume();
        binding.btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (count==3){
                    //chuyển sang page home
                }else if (count == 2){
                    count+=1;
                    binding.imgIntro.setImageResource(arrImage[count-1]);
                    binding.tvContent.setText(arrContent[count-1]);
                    binding.tvCount.setText(String.valueOf(count)+"/3");
                    binding.btnNext.setText("Get started");
                }else{
                    count+=1;
                    binding.imgIntro.setImageResource(arrImage[count-1]);
                    binding.tvCount.setText(String.valueOf(count)+"/3");
                    binding.tvContent.setText(arrContent[count-1]);
                }
            }
        });
    }
}