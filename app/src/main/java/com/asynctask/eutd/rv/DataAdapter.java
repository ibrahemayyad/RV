package com.asynctask.eutd.rv;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Vibrator;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.ScaleAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.balysv.materialripple.MaterialRippleLayout;
import com.irozon.sneaker.Sneaker;
import com.nightonke.boommenu.BoomMenuButton;
import com.nightonke.boommenu.Types.BoomType;
import com.nightonke.boommenu.Types.ButtonType;
import com.nightonke.boommenu.Types.PlaceType;
import com.nightonke.boommenu.Util;

import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * Created by ibrahem on 8/28/2017.
 */

public class DataAdapter extends RecyclerView.Adapter<DataAdapter.ViewHolder> {
    List<NatureModel> natureModelList;
    Context context;
    private int lastPosition = -1;
    CoordinatorLayout coordinatorLayout;
    ItemTouchHelper androidItemTouchHelper;
    SQL_DB db ;

    private final static int FADE_DURATION = 2000; // in milliseconds
    private static String[] Colors = new String[]{
            "#fc4207",
            "#009688",
            "#2196F3" };
    public DataAdapter(Context context , CoordinatorLayout coordinatorLayout, List<NatureModel> natureModelList , ItemTouchHelper androidItemTouchHelper){

        this.natureModelList = natureModelList;
        this.context = context;
       this.coordinatorLayout =   coordinatorLayout;
         this.androidItemTouchHelper  = androidItemTouchHelper;
        db= new SQL_DB(context);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(context);

        View view =  inflater.inflate(R.layout.cart_list_item , parent , false);

        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.setData(natureModelList.get(position),position);

        /************
         *
         *
         * Animations
         *
         *
         * **********/
      //  setFadeAnimation(holder.itemView);
       //setScaleAnimation(holder.itemView);
        setAnimation(holder.itemView, position);
       //setAnimation2(holder.itemView, position);

//       final Animation animAnticipateOvershoot = AnimationUtils.loadAnimation(context, R.anim.slid_left);
//        holder.itemView.setAnimation(animAnticipateOvershoot);
//

        /************
         *
         *
         * Animations
         *
         *
         * **********/

       /* holder.btn_delet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                itemRemve(position);
            }
        });

        holder.btn_copy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.itemAdd(position,natureModelList.get(position));
            }
        });*/

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @TargetApi(Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View view) {
                Intent i = new Intent(context,Details.class);
                GlobalData.Gtitle =  natureModelList.get(position).getTitle();
                if( natureModelList.get(position).getImageID() ==2){
                    GlobalData.GimageStr   = natureModelList.get(position).getImageStr();
                    GlobalData.Gimage = natureModelList.get(position).getImageID();
                }else {
                    GlobalData.Gimage = natureModelList.get(position).getImageID();
                }
                GlobalData.natureModelsDeils =  natureModelList.get(position);


              /*  Pair[] pairs = new Pair[3];
                pairs[0] = new Pair<View,String>(holder.imgThumb,"ImgName");
                pairs[1] = new Pair<View,String>(holder.title,"TitleName");
                pairs[2] = new Pair<View,String>(holder.txt_email,"Email");

                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation((Activity) context, pairs);
*/
                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation((Activity) context);

                context.startActivity(i,options.toBundle());
            }
        });


    //    holder.btn_move.setVisibility(View.GONE);

       holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                Vibrator vibe = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
                vibe.vibrate(100);
                if (androidItemTouchHelper != null) androidItemTouchHelper.startDrag(holder);

                return true;
            }
        });




        final Drawable[] circleSubButtonDrawables = new Drawable[3];
        int[] drawablesResource = new int[]{
                R.drawable.delete,
                R.drawable.edit,
                R.drawable.copy
        };
        for (int i = 0; i < 3; i++)
            circleSubButtonDrawables[i]
                    = ContextCompat.getDrawable(context, drawablesResource[i]);


        final String[] circleSubButtonTexts = new String[]{
                "Delet",
                "Edit",
                "Copy"};

        final int[][] subButtonColors = new int[3][2];
           /* for (int i = 0; i < 3; i++) {
                subButtonColors[i][1] = GetRandomColor();
                subButtonColors[i][0] = Util.getInstance().getPressedColor(subButtonColors[i][1]);
            }*/
        subButtonColors[0][1] = Color.parseColor(Colors[0]);
        subButtonColors[1][1] = Color.parseColor(Colors[1]);
        subButtonColors[2][1] = Color.parseColor(Colors[2]);
        subButtonColors[0][0] = Util.getInstance().getPressedColor(subButtonColors[0][1]);
        subButtonColors[1][0] = Util.getInstance().getPressedColor(subButtonColors[1][1]);
        subButtonColors[2][0] = Util.getInstance().getPressedColor(subButtonColors[2][1]);



        holder.circleBoomMenuButton.postDelayed(new Runnable() {
            @Override
            public void run() {
                // Now with Builder, you can init BMB more convenient
                new BoomMenuButton.Builder()
                        .subButtons(circleSubButtonDrawables, subButtonColors, circleSubButtonTexts)
                        .button(ButtonType.CIRCLE)
                        .boom(BoomType.PARABOLA)
                        .place(PlaceType.CIRCLE_3_2)
                        .subButtonsShadow(Util.getInstance().dp2px(2), Util.getInstance().dp2px(2))
                        .onSubButtonClick(new BoomMenuButton.OnSubButtonClickListener() {
                            @Override
                            public void onClick(int buttonIndex) {
                                Toast.makeText(
                                        context,
                                        circleSubButtonTexts[buttonIndex],
                                        Toast.LENGTH_SHORT).show();

                                switch (circleSubButtonTexts[buttonIndex]){

                                    case "Delet":

                                        itemRemve(position)

                                        ;break;
                                    case "Edit":
                                           MainActivity.editItem(position);
                                           notifyDataSetChanged();
                                        ;break;
                                    case "Copy":
                                        holder.itemAdd(position,natureModelList.get(position));
                                        SQL_DB db = new SQL_DB(context);
                                       boolean result = db.insertData(natureModelList.get(position));
                                        Log.e("TAG Adabter: ", "insertData : "+result);

                                        notifyDataSetChanged();
                                        ;break;


                                }
                            }
                        })
                        .init(holder.circleBoomMenuButton);
            }
        }, 1);


/*
        holder.btn_move.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                holder.viewBackground.setVisibility(View.GONE);


                return true;
            }
        });*/

    }

    private void setScaleAnimation(View view) {
        ScaleAnimation anim = new ScaleAnimation(0.0f, 1.0f, 0.0f, 1.0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        anim.setDuration(FADE_DURATION);
        view.startAnimation(anim);
    }

    private void setFadeAnimation(View view) {
        //أختفاء العنصر
        AlphaAnimation anim = new AlphaAnimation(0.0f, 1.0f);
        anim.setDuration(FADE_DURATION);
        view.startAnimation(anim);
    }
    private void setAnimation(View viewToAnimate, int position)
    {
        //سحب العنصر من اليسار

        // If the bound view wasn't previously displayed on screen, it's animated
//        private int lastPosition = -1;
        if (position > lastPosition)
        {
            Animation animation = AnimationUtils.loadAnimation(context, R.anim.slid_left);
            viewToAnimate.startAnimation(animation);
            lastPosition = position;
        }
    }
    private void setAnimation2(View viewToAnimate, int position) {
        // If the bound view wasn't previously displayed on screen, it's animated
        if (position > lastPosition) {
            ScaleAnimation anim = new ScaleAnimation(0.0f, 1.0f, 0.0f, 1.0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
            anim.setDuration(new Random().nextInt(501));//to make duration random number between [0,501)
            viewToAnimate.startAnimation(anim);
            lastPosition = position;
        }
    }

    @Override
    public int getItemCount() {
        return natureModelList.size();
    }

    public void removeItem(int position) {
        natureModelList.remove(position);
        // notify the item removed by position
        // to perform recycler view delete animations
        // NOTE: don't call notifyDataSetChanged()
        notifyItemRemoved(position);
    }
    public void itemRemve(int position ){

        String name = natureModelList.get(position).getTitle() ;
        int id = natureModelList.get(position).getId() ;

        final NatureModel deletedItem = natureModelList.get(position);
        final int deletedIndex = position;
        Log.e("TAG position" , position + "");
        natureModelList.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, natureModelList.size());
        db.delet(id);
        notifyDataSetChanged();

        Sneaker.with((Activity) context)
                .setTitle("Removed!!")
                .setMessage("This is the success Removed")
                .sneakSuccess();
        Snackbar snackbar = Snackbar
                .make(coordinatorLayout, name + " removed from cart!", Snackbar.LENGTH_LONG);
        snackbar.setAction("UNDO", new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // undo is selected, restore the deleted item
                restoreItem(deletedItem, deletedIndex);
            }
        });
        snackbar.setActionTextColor(Color.YELLOW);
        snackbar.show();
    }
    public void restoreItem(NatureModel item, int position) {

        natureModelList.add(position, item);
        // notify item added by position
        SQL_DB db = new SQL_DB(context);
        boolean result = db.insertData(item);
        notifyItemInserted(position);
        Log.e("TAG Adabter: ", "insertData : "+result);

    }

    public void onItemMove(final int initialPosition, final int finalPosition) {


        if (initialPosition < natureModelList.size() && finalPosition < natureModelList.size()) {

            if (initialPosition < finalPosition) {

                for (int i = initialPosition; i < finalPosition; i++) {
                    Collections.swap(natureModelList, i, i + 1);
                }
            } else {
                for (int i = initialPosition; i > finalPosition; i--) {
                    Collections.swap(natureModelList, i, i - 1);
                }

            }

            notifyItemMoved( initialPosition, finalPosition);

        }




    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private cTextView title,txt_email;
        private ImageView imgThumb;
        private Button btn_delet, btn_copy ,btn_move;
        private int position;
        private NatureModel currentObject;
        MaterialRippleLayout m ;
        private BoomMenuButton circleBoomMenuButton;
        public RelativeLayout viewBackground, viewForeground;


        public ViewHolder(View itemView) {
            super(itemView);
            title       = (cTextView)  itemView.findViewById(R.id.txt_title);
            txt_email       = (cTextView)  itemView.findViewById(R.id.txt_email);
            imgThumb    = (ImageView) itemView.findViewById(R.id.img_title);
//            btn_delet   = (Button) itemView.findViewById(R.id.btn_remove);
//            btn_copy = (Button) itemView.findViewById(R.id.btn_copy);
         //   btn_move = (Button) itemView.findViewById(R.id.move);
            m = (MaterialRippleLayout) itemView.findViewById(R.id.ripple);
            viewBackground = itemView.findViewById(R.id.view_background);
            viewForeground = itemView.findViewById(R.id.view_foreground);
            circleBoomMenuButton = (BoomMenuButton) itemView.findViewById(R.id.boom_circle);

        }

        public void setData(NatureModel natureModel , int position){

            title.setText(natureModel.getTitle());
            txt_email.setText(natureModel.getEmail());

            if (natureModel.getImageID() == 2) {
                Bitmap thumbnail = (BitmapFactory.decodeFile(natureModel.getImageStr()));
                imgThumb.setImageBitmap(thumbnail);

            } else {
                imgThumb.setImageResource(natureModel.getImageID());
            }

                this.position = position;

        }




        public void itemAdd(int position , NatureModel natureModel ){

            natureModelList.add(position,natureModel);
            notifyItemInserted(position);

            notifyItemRangeChanged(position, natureModelList.size());

            notifyDataSetChanged();

        }



    }
}
