package com.asynctask.eutd.rv;

import android.Manifest;
import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.transition.Explode;
import android.transition.Fade;
import android.transition.Slide;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.AnticipateOvershootInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import javax.microedition.khronos.opengles.GL;

import static com.asynctask.eutd.rv.DialogItem.READ_EXTERNAL_STORAGE;

public class Details extends AppCompatActivity {

    public static final String MODLE_TEXT = "text";
    public static final String MODLE_IMAGE = "image";
   private CardView card;
    private String mText = GlobalData.Gtitle;
    private int mImage = GlobalData.Gimage;
    private String sImage = GlobalData.GimageStr;
    TextView title;
    private CollapsingToolbarLayout collapsingToolbarLayout;
    FloatingActionButton flat, flat1, flat2;

    TextView email,NumberPhone;
    Animation animation;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        mText = GlobalData.Gtitle;


        mImage = GlobalData.Gimage;

        email = (TextView) findViewById(R.id.email);
        NumberPhone = (TextView) findViewById(R.id.NumberPhone);

        if (GlobalData.natureModelsDeils.getEmail() != null || GlobalData.natureModelsDeils.getEmail() != null) {
            email.setText(GlobalData.natureModelsDeils.getEmail());
        }else{
            email.setText("No Added");

        }
        if (GlobalData.natureModelsDeils.getNumberPhone() != null || GlobalData.natureModelsDeils.getNumberPhone() != null) {
            NumberPhone.setText(GlobalData.natureModelsDeils.getNumberPhone());

        } else{

            NumberPhone.setText("No Added");

        }
        Log.e("haint", mText + "----" + mImage);

//        ===================================
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    finishAfterTransition();
                }            }
        });
        flat = (FloatingActionButton) findViewById(R.id.fab);
        flat1 = (FloatingActionButton) findViewById(R.id.fab1);
        flat2 = (FloatingActionButton) findViewById(R.id.fab2);
        card = (CardView) findViewById(R.id.card);

        animation = AnimationUtils.loadAnimation(this, R.anim.slide_up);
         card.setAnimation(animation);

        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbarLayout.setTitle(mText);
        collapsingToolbarLayout.setExpandedTitleColor(getResources().getColor(android.R.color.transparent));

        final ImageView image = (ImageView) findViewById(R.id.image);

        if (GlobalData.Gimage != 2) {
            image.setBackgroundResource(GlobalData.Gimage);

          Bitmap bitmap = BitmapFactory.decodeResource(getResources(),
                    GlobalData.Gimage);
            Palette.from(bitmap).generate(new Palette.PaletteAsyncListener() {
                public void onGenerated(Palette palette) {
                    applyPalette(palette);
                }
            });
        } else {

            Bitmap thumbnail = (BitmapFactory.decodeFile(GlobalData.GimageStr));
            image.setImageBitmap(thumbnail);

            Palette.from(thumbnail).generate(new Palette.PaletteAsyncListener() {
                public void onGenerated(Palette palette) {
                    applyPalette(palette);
                }
            });

        }
        title = (TextView) findViewById(R.id.title);
        title.setText(mText);

        anmation();

        animation = AnimationUtils.loadAnimation(this, R.anim.slide_up);
        flat.setAnimation(animation);
        flat1.setAnimation(animation);
        flat2.setAnimation(animation);

        flat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent shareIntent = new Intent(Intent.ACTION_SEND);
                shareIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                shareIntent.setType("text/plain");
                shareIntent.putExtra(android.content.Intent.EXTRA_TEXT, "Hey, download this app");
                startActivity(shareIntent);
            }
        });
        flat1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (GlobalData.natureModelsDeils.getNumberPhone() != null || GlobalData.natureModelsDeils.getNumberPhone() != null) {

                    Uri smsUri = Uri.parse("tel:" + GlobalData.natureModelsDeils.getNumberPhone());
                    Intent intent = new Intent(Intent.ACTION_CALL);
                    intent.setData(smsUri);
                    if (ActivityCompat.checkSelfPermission(Details.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                        // TODO: Consider calling
                        //    ActivityCompat#requestPermissions
                        // here to request the missing permissions, and then overriding
                        //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                        //                                          int[] grantResults)
                        // to handle the case where the user grants the permission. See the documentation
                        // for ActivityCompat#requestPermissions for more details.
                        ActivityCompat.requestPermissions(Details.this,
                                new String[]{Manifest.permission.CALL_PHONE}, 0);
                        return;
                    }
                    startActivity(intent);
                }else {

                    Toast.makeText(Details.this, "No Number Phone ", Toast.LENGTH_SHORT).show();

                }
            }
        });
        flat2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri uri = Uri.parse("smsto:"+GlobalData.natureModelsDeils.getNumberPhone());
                Intent it = new Intent(Intent.ACTION_SENDTO, uri);
                it.putExtra("sms_body", "Walcom ... my "+GlobalData.natureModelsDeils.getTitle());
                startActivity(it);
            }
        });
    }

    private void anmation() {
        int random = (int )(Math.random() * 4 + 1);
        Log.e("random : ", " ---- " + random);

        switch (random){

            case 1 :


                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                    Fade fade = new Fade();
                    fade.setDuration(700);
                    getWindow().setEnterTransition(fade);

                    Slide enterTransition = null;
                    enterTransition = new Slide();
                    enterTransition.setSlideEdge(Gravity.TOP);
                    enterTransition.setDuration(700);
                    enterTransition.setInterpolator(new AnticipateOvershootInterpolator());
                    getWindow().setEnterTransition(enterTransition);


                }

                ;break;

            case 2:
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {

                    Explode enterTransition = new Explode();
                    enterTransition.setDuration(500);
                    getWindow().setEnterTransition(enterTransition);
                }
                ;break;


            case 3:
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {

                    Transition enterTansition = TransitionInflater.from(this).inflateTransition(R.transition.slide);
                    getWindow().setEnterTransition(enterTansition);
                }
                ;break;
            case 4 :


                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                    Slide enterTransition = null;
                    enterTransition = new Slide();
                    enterTransition.setSlideEdge(Gravity.LEFT);
                    enterTransition.setDuration(900);
                    enterTransition.setInterpolator(new AnticipateOvershootInterpolator());
                    getWindow().setEnterTransition(enterTransition);


                }

                ;break;
            case 5 :


                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {

                    Fade fade = new Fade();
                    fade.setDuration(1000);
                    getWindow().setEnterTransition(fade);

                    Slide slide = new Slide();
                    slide.setDuration(1000);
                    getWindow().setReturnTransition(slide);
                }

                ;break;


        }



       /* Fade enterTransition = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            enterTransition = new Fade();

        enterTransition.setDuration(1500);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                getWindow().setEnterTransition(enterTransition);
            }
        }*/
    }

    private void applyPalette(Palette palette) {
        int primaryDark = getResources().getColor(R.color.colorPrimary);
        int primary = getResources().getColor(R.color.colorPrimary);
        collapsingToolbarLayout.setContentScrimColor(palette.getVibrantColor(primary));
        collapsingToolbarLayout.setStatusBarScrimColor(palette.getVibrantColor(primaryDark));
        title.setTextColor(palette.getVibrantColor(primaryDark));
        updateBackground(flat, palette);
        updateBackground(flat1, palette);
        updateBackground(flat2, palette);
        supportStartPostponedEnterTransition();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(palette.getDarkVibrantColor(primary));
        }
    }

    private void updateBackground(FloatingActionButton fab, Palette palette) {
        int lightVibrantColor = palette.getLightVibrantColor(getResources().getColor(android.R.color.white));
        int vibrantColor = palette.getVibrantColor(getResources().getColor(R.color.accent));

        fab.setRippleColor(lightVibrantColor);
        fab.setBackgroundTintList(ColorStateList.valueOf(vibrantColor));
    }

    private  void shoDailgAnimation(int type , String massag){

        AlertDialog dialg = new AlertDialog.Builder(this).create();
        dialg.setTitle("Ibrahem Ayyad");
        dialg.setMessage(massag);
        dialg.getWindow().getAttributes().windowAnimations = type;
        dialg.show();



    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return super.onKeyDown(keyCode, event);


    }

    @Override
    public boolean onSupportNavigateUp() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            finishAfterTransition();
        }
        return true;
    }
   /* public void open(View view)
    {
        final Dialog dialog= new Dialog(this);
        dialog.setContentView(R.layout.dialog);
        dialog.setTitle("Dialog");
        Button btnDismiss=(Button)dialog.findViewById(R.id.btn_dialog_no);
        Button btnOkay=(Button)dialog.findViewById(R.id.btn_dialog_yes);
        btnOkay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(Details.this,"Dialog     Successful",Toast.LENGTH_LONG).show();
                dialog.dismiss();
            }
        });
        btnDismiss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog.getWindow().setWindowAnimations(R.style.DialogSlide_up_dawn);
        dialog.show();
    }

    private void showDiag() {

        final View dialogView = View.inflate(this,R.layout.dialog,null);

        final Dialog dialog = new Dialog(this,R.style.MyAlertDialogStyle);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(dialogView);
        Button btnDismiss=(Button)dialog.findViewById(R.id.btn_dialog_no);
        Button btnOkay=(Button)dialog.findViewById(R.id.btn_dialog_yes);
        btnOkay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                revealShow(dialogView, false, dialog);
            }
        });

        dialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialogInterface) {
                revealShow(dialogView, true, null);
            }
        });

        dialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialogInterface, int i, KeyEvent keyEvent) {
                if (i == KeyEvent.KEYCODE_BACK){

                    revealShow(dialogView, false, dialog);
                    return true;
                }

                return false;
            }
        });



        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        dialog.show();
    }

    private void revealShow(View dialogView, boolean b, final Dialog dialog) {

        final View view = dialogView.findViewById(R.id.dialog);

        int w = view.getWidth();
        int h = view.getHeight();

        int endRadius = (int) Math.hypot(w, h);

        int cx = (int) (flat.getX() + (flat.getWidth()/2));
        int cy = (int) (flat.getY())+ flat.getHeight() + 56;


        if(b){
            Animator revealAnimator = null;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                revealAnimator = ViewAnimationUtils.createCircularReveal(view, cx,cy, 0, endRadius);
            }

            view.setVisibility(View.VISIBLE);
            revealAnimator.setDuration(700);
            revealAnimator.start();

        } else {

            Animator anim =
                    null;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                anim = ViewAnimationUtils.createCircularReveal(view, cx, cy, endRadius, 0);
            }

            anim.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                    dialog.dismiss();
                    view.setVisibility(View.INVISIBLE);

                }
            });
            anim.setDuration(700);
            anim.start();
        }

    }
*/
}
