package com.asynctask.eutd.rv;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.graphics.Palette;
import android.transition.Transition;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.irozon.sneaker.Sneaker;
import com.transitionseverywhere.TransitionManager;
import com.transitionseverywhere.Recolor;
import java.io.File;

/**
 * Created by ibrahem on 9/24/2017.
 */

public class DialogItem extends Dialog{
    public static final int READ_EXTERNAL_STORAGE = 0,MULTIPLE_PERMISSIONS = 10;
    private static final int GALLERY_INTENT = 2;
    EditText Etxt,NumberPhone,Email;

    public static int postion = 0 ;
        static ImageView imageViewDialog;
    Activity activity;

    public static String UrlImge = " ";
    SQL_DB sql_db;
    NatureModel model;
    public DialogItem(final Context context) {
        super(context);

        activity = (Activity) context;

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setCancelable(false);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog);
        Etxt = (EditText) findViewById(R.id.txtname);
        NumberPhone = (EditText) findViewById(R.id.NumberPhone);
        Email = (EditText) findViewById(R.id.Email);
        Etxt.setText(" ");
        final ViewGroup transitionsContainer = (ViewGroup) findViewById(R.id.transitions_container);
        final Button okBtn = (Button) transitionsContainer.findViewById(R.id.okBtn);
        final TextView text = (TextView) transitionsContainer.findViewById(R.id.text);
        imageViewDialog = (ImageView) findViewById(R.id.signImage);

        if(GlobalData.natureModels != null) {
            Etxt.setText(GlobalData.natureModels.getTitle());
            Email.setText(GlobalData.natureModels.getEmail());
            NumberPhone.setText(GlobalData.natureModels.getNumberPhone());

            if (GlobalData.natureModels.getImageID() != 2) {
                imageViewDialog.setBackgroundResource(GlobalData.natureModels.getImageID());

            } else {

                Bitmap thumbnail = (BitmapFactory.decodeFile(GlobalData.natureModels.getImageStr()));
                imageViewDialog.setImageBitmap(thumbnail);

            }
        }
         sql_db = new SQL_DB(getContext());

        okBtn.setOnClickListener(new View.OnClickListener() {
            boolean visible;
            boolean mColorsInverted;
            @Override
            public void onClick(View view) {

                String txt = Etxt.getText().toString();
                if (!txt.equals(" ")){

                    Log.e("TAG Dilog: ", "data ");

                    model = new NatureModel();
                    if(GlobalData.natureModels == null) {
                        model.setId(GlobalData.natureModelsList.size() + 1);
                    }else {
                        model.setId(GlobalData.natureModels.getId());

                    }
                    model.setTitle(Etxt.getText().toString());
                    model.setNumberPhone(NumberPhone.getText().toString());
                    model.setEmail(Email.getText().toString());
                    if(!UrlImge.equals(" ")) {
                        model.setImageID(2);
                        model.setImageStr(UrlImge);
                        UrlImge = " ";
                    }else {
                        if(GlobalData.natureModels == null) {
                            model.setImageID(R.drawable.camera);
                        }else{
                                model.setImageID(GlobalData.natureModels.getImageID());
                                model.setImageStr(GlobalData.natureModels.getImageStr());
                            }

                    }




                    Sneaker.with(activity)
                            .setTitle("Added!!")
                            .setMessage("This is the success Added")
                            .sneakSuccess();
                    dismiss();

                }else {


                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                        TransitionManager.beginDelayedTransition(transitionsContainer);
                        TransitionManager.beginDelayedTransition(transitionsContainer,new Recolor());

                    }
                    visible = !visible;
                    text.setVisibility(visible ? View.VISIBLE : View.GONE);


                    mColorsInverted = !mColorsInverted;
                    okBtn.setTextColor(activity.getResources().getColor(!mColorsInverted ? R.color.error : R.color.colorBtuoon));
                    okBtn.setBackgroundDrawable(
                            new ColorDrawable(activity.getResources().getColor(!mColorsInverted ? R.color.colorBtuoon : R.color.error)));
                }


                if (!txt.equals(" ")) {
                    if (GlobalData.natureModels == null) {
                        Log.e("TAG Dilog : ", "insertData ");

                        boolean result = sql_db.insertData(model);
                        Log.e("TAG Adabter: ", "insertData : "+result);

                        GlobalData.natureModelsList.add(model);
                        MainActivity.setAdabter(GlobalData.natureModelsList);


                    } else {
                        sql_db.update(model);
                        Log.e("TAG Dilog : ", "update");
                        GlobalData.natureModelsList.remove(postion);
                        GlobalData.natureModelsList.add(postion,model);
                        MainActivity.setAdabter(GlobalData.natureModelsList);

                    }
                }



                MainActivity.setAdabter(GlobalData.natureModelsList);

            }
        });

        imageViewDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (ContextCompat.checkSelfPermission(activity, android.Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
                {
                    ActivityCompat.requestPermissions(activity,
                            new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE}, READ_EXTERNAL_STORAGE);
                }
                else
                {
                    callgalary();

                }


            }
        });
    }


    @Override
    public void onBackPressed() {

        dismiss();
        MainActivity.setAdabter(GlobalData.natureModelsList);

        super.onBackPressed();
    }

    @Override
    public boolean onKeyDown(int keyCode, @NonNull KeyEvent event) {
        return super.onKeyDown(keyCode, event);


    }

    private void callgalary() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
       activity.startActivityForResult(intent, GALLERY_INTENT);
    }

    public static void imgView(String url_img) {
        UrlImge = url_img;
        Bitmap thumbnail = (BitmapFactory.decodeFile(url_img));
        imageViewDialog.setImageBitmap(thumbnail);

    }


    @Override
    protected void onStart() {
        super.onStart();
        MainActivity.setAdabter(GlobalData.natureModelsList);

    }

    @Override
    protected void onStop() {
        super.onStop();
        MainActivity.setAdabter(GlobalData.natureModelsList);

    }
}
