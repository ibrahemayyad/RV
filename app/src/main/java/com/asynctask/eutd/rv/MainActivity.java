package com.asynctask.eutd.rv;

import android.animation.Animator;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.transition.Explode;
import android.transition.Fade;
import android.transition.Slide;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.amsen.par.searchview.prediction.OnPredictionClickListener;
import com.irozon.sneaker.Sneaker;
import com.miguelcatalan.materialsearchview.MaterialSearchView;

import com.amsen.par.searchview.AutoCompleteSearchView;
import com.amsen.par.searchview.prediction.Prediction;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity   {
    RecyclerView recyclerView ;
    static DataAdapter dataAdapter;
    private CoordinatorLayout coordinatorLayout;
    private static EditText et_item;
    private static int edit_position;
    private static View view;
    MaterialSearchView searchView;
    private boolean add = false;
    private static AlertDialog.Builder alertDialog;
    ItemTouchHelper androidItemTouchHelper;
    Uri mImageUri = Uri.EMPTY;
    String picturePath ;
    int getGallery =0;
    private AutoCompleteSearchView searchView1;

    SQL_DB db = null  ;
    private CollapsingToolbarLayout collapsingToolbarLayout;
//    private MockApi api;
    private TimerTask fakeNetworkCall;
    private Timer fakeNetworkThread;

    private View callToActionView;
    private TextView andSearch;
    private View searchedTitle;
    private TextView predictionResultView;
  public  static   String url_img = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordinator_layout);
        db = new SQL_DB(this);
        recyclerView = (RecyclerView) findViewById(R.id.rv);
        setupWindowAnimations();
        fakeNetworkThread = new Timer();

       // callToActionView = findViewById(R.id.clickSearch);
       // andSearch = (TextView) findViewById(R.id.andSearch);
        searchedTitle = findViewById(R.id.title);
      //  predictionResultView = (TextView) findViewById(R.id.selectedPrediction);

       // andSearch.setText(getString(R.string.and_search_ARG1, "\uD83E\uDD13")); //set nerd
        GlobalData.natureModelsList = NatureModel.getObjectList();

         db.getAllRecord();
        Log.e("TAG : ", "onCreate " + GlobalData.CheckDB + " " + db.getAllRecord().size());
        if (GlobalData.CheckDB == true){
            for (NatureModel natureModel : GlobalData.natureModelsList){

                db.insertData(natureModel);
            }
            GlobalData.CheckDB = false;
            GlobalData.natureModelsList = db.getAllRecord();
        }else {
            GlobalData.natureModelsList = db.getAllRecord();

        }
        GlobalData.natureModelsList = db.getAllRecord();

        for (int i = 0 ; i < GlobalData.natureModelsList.size(); i++){

            Log.e("TAG For: ", "onCreate " + GlobalData.natureModelsList.get(i).getTitle() +" " + GlobalData.natureModelsList.get(i).getImageID());
        }

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        recyclerView.setLayoutManager(layoutManager);


        recyclerView.setItemAnimator(new DefaultItemAnimator());
        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_container);
        collapsingToolbarLayout.setExpandedTitleColor(getResources().getColor(android.R.color.transparent));
        collapsingToolbarLayout.setContentScrimColor(getResources().getColor(R.color.error2));
        collapsingToolbarLayout.setStatusBarScrimColor(getResources().getColor(R.color.cardview_shadow_end_color));

        final Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Ibrahem Ayyad :)");

        toolbar.setTitleTextColor(Color.parseColor("#FFFFFF"));

//        searchView = (MaterialSearchView)findViewById(R.id.search_view);
//        searchView.setVisibility(View.GONE);
//        searchView.setOnSearchViewListener(new MaterialSearchView.SearchViewListener() {
//            @Override
//            public void onSearchViewShown() {
//                searchView.setVisibility(View.VISIBLE);
//              // toolbar.setBackgroundResource(R.color.colorPrimaryWhite);
//
//            }
//
//            @Override
//            public void onSearchViewClosed() {
//                searchView.setVisibility(View.GONE);
//
//            }
//        });
//
//        searchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
//            @Override
//            public boolean onQueryTextSubmit(String query) {
//                return false;
//            }
//
//            @Override
//            public boolean onQueryTextChange(String newText) {
//                onQuery(newText);
//                return true;
//            }
//
//        });


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(ContextCompat.getColor(this, R.color.colorPrimaryDark));
        }

        initDialog();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);

        Animation animation = AnimationUtils.loadAnimation(this, R.anim.slide_up);

        fab.setAnimation(animation);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                removeView();

                GlobalData.natureModels = null;
                add = true;
                DialogItem dialog = new DialogItem(view.getContext() );



                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                dialog.getWindow().setWindowAnimations(R.style.DialogSlide_up_dawn);


                dialog.show();
            }
        });

        DynamicEventsHelper.DynamicEventsCallback callback = new DynamicEventsHelper.DynamicEventsCallback() {


           @Override
           public void onSwiped(final RecyclerView.ViewHolder viewHolder, int direction, int position) {
               if (viewHolder instanceof DataAdapter.ViewHolder) {
                   // get the removed item name to display it in snack bar
                   String name = GlobalData.natureModelsList.get(viewHolder.getAdapterPosition()).getTitle();
                   Log.e("TAG position" , viewHolder.getAdapterPosition() + "");

                   // backup of removed item for undo purpose
                   final NatureModel natureModel = GlobalData.natureModelsList.get(viewHolder.getAdapterPosition());
                   final int deletedIndex = viewHolder.getAdapterPosition();

                   if (direction == ItemTouchHelper.LEFT) {
                       dataAdapter.itemRemve(deletedIndex);
                       // remove the item from recycler view
//                       dataAdapter.removeItem(viewHolder.getAdapterPosition());
//
//                       // showing snack bar with Undo option
//                       Snackbar snackbar = Snackbar
//                               .make(coordinatorLayout, name + " removed from cart!", Snackbar.LENGTH_LONG);
//                       snackbar.setAction("UNDO", new View.OnClickListener() {
//                           @Override
//                           public void onClick(View view) {
//
//                               // undo is selected, restore the deleted item
//                               dataAdapter.restoreItem(natureModel,  viewHolder.getAdapterPosition());
//                           }
//                       });
//                       snackbar.setActionTextColor(Color.YELLOW);
//                       snackbar.show();

                   }  else{
                       editItem(position);

                   }
               }
           }

           @Override
            public void onItemMove(int initialPosition, int finalPosition) {
                dataAdapter.onItemMove(initialPosition, finalPosition);



           }

           @Override
           public void removeItem(int position) {
               dataAdapter.removeItem(position);
               dataAdapter.notifyDataSetChanged();
           }


       };

         androidItemTouchHelper = new ItemTouchHelper(new DynamicEventsHelper(callback, this) );
        androidItemTouchHelper.attachToRecyclerView(recyclerView);


        /*
        /*
        /*
        /*
        /*
         */

    /*    ItemTouchHelper.SimpleCallback itemTouchHelperCallback1 = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT | ItemTouchHelper.UP) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {

            }

            @Override
            public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
            }

        };

        // attaching the touch helper to recycler view
        new ItemTouchHelper(itemTouchHelperCallback1).attachToRecyclerView(recyclerView);

*/

        dataAdapter = new DataAdapter(this ,coordinatorLayout,GlobalData.natureModelsList ,androidItemTouchHelper );
        recyclerView.setAdapter(dataAdapter);

    }



    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

/*
    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction, int position) {

        if (viewHolder instanceof DataAdapter.ViewHolder) {
            // get the removed item name to display it in snack bar
            String name = natureModelsList.get(viewHolder.getAdapterPosition()).getTitle();

            // backup of removed item for undo purpose
            final NatureModel deletedItem = natureModelsList.get(viewHolder.getAdapterPosition());
            final int deletedIndex = viewHolder.getAdapterPosition();

            // remove the item from recycler view
            dataAdapter.removeItem(viewHolder.getAdapterPosition());

            // showing snack bar with Undo option
            Snackbar snackbar = Snackbar
                    .make(coordinatorLayout, name + " removed from cart!", Snackbar.LENGTH_LONG);
            snackbar.setAction("UNDO", new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    // undo is selected, restore the deleted item
                    dataAdapter.restoreItem(deletedItem, deletedIndex);
                }
            });
            snackbar.setActionTextColor(Color.YELLOW);
            snackbar.show();
        }
    }
*/
private static void removeView(){
    if(view.getParent()!=null) {
        ((ViewGroup) view.getParent()).removeView(view);
    }
}
private void initDialog(){
    alertDialog = new AlertDialog.Builder(this);
    view = getLayoutInflater().inflate(R.layout.dialog_layout,null);
    alertDialog.setView(view);
    alertDialog.setPositiveButton("Save", new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            if(add){

                add =false;
             //   dataAdapter.addItem(et_country.getText().toString());
                dialog.dismiss();
                Sneaker.with(MainActivity.this)
                        .setTitle("Success!!")
                        .setMessage("This is the success message")
                        .sneakSuccess();
            } else {
                GlobalData.natureModelsList.get(edit_position).setTitle(et_item.getText().toString());
                dataAdapter.notifyDataSetChanged();
                dialog.dismiss();
                Sneaker.with(MainActivity.this)
                        .setTitle("Modified!!")
                        .setMessage("This is a Modified Success")
                        .sneakSuccess();
            }

        }
    });
    et_item = (EditText)view.findViewById(R.id.editText_item);
}
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        getGallery = requestCode;

        if (resultCode == RESULT_OK) {

             if (requestCode == 2) {


                mImageUri = data.getData();
                String[] filePath = { MediaStore.Images.Media.DATA };
                Cursor c = getContentResolver().query(mImageUri,filePath, null, null, null);
                c.moveToFirst();
                int columnIndex = c.getColumnIndex(filePath[0]);
                picturePath = c.getString(columnIndex);
                c.close();
              //  Bitmap thumbnail = (BitmapFactory.decodeFile(picturePath));
             //   signup_img.setImageBitmap(thumbnail);
                 url_img = picturePath;
                 DialogItem.imgView(url_img);
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_item,menu);
        final MenuItem item = menu.findItem(R.id.action_search);
//        searchView.setMenuItem(item);
        searchView1 = (AutoCompleteSearchView) item.getActionView();
        searchView1.setUseDefaultProgressBar(true);


        searchView1.setOnPredictionClickListener(new OnPredictionClickListener() {
                                                     @Override
                                                     public void onClick(int i, Prediction prediction) {
                                                         item.collapseActionView();

                                                     }
                                                 });

                searchView1.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                    @Override
                    public boolean onQueryTextSubmit(String query) {
                        return true;
                    }

                    @Override
                    public boolean onQueryTextChange(String newText) {
                        onQuery(newText);
                        return true;
                    }

                });


        return true;
    }

    public  static  void setAdabter(List<NatureModel> natureModelList){

        Log.e("TAG Dilog : ", "notifyDataSetChanged ");

        dataAdapter.notifyDataSetChanged();

    }

    public  static  void editItem(int position){
         DialogItem.postion = position;
        GlobalData.natureModels = GlobalData.natureModelsList.get(position);
        DialogItem dialog = new DialogItem(view.getContext() );

        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.getWindow().setWindowAnimations(R.style.DialogSlide_up_dawn);
        dialog.show();
        dataAdapter.notifyDataSetChanged();
//        removeView();
//        edit_position = position;
//        alertDialog.setTitle("Edit Country");
//        et_item.setText(GlobalData.natureModelsList.get(position).getTitle());
//        dataAdapter.notifyDataSetChanged();
//        alertDialog.show();
//        Log.e("TAG position" , edit_position + "");
    }

    private void onQuery(final String query) {
        if(query != null && !query.isEmpty()){
            searchView1.showProgressBar();
            List<NatureModel> lstFound = new ArrayList<NatureModel>();
            for(NatureModel item:GlobalData.natureModelsList){
                if(item.getTitle().contains(query))
                    lstFound.add(item);
            }

            dataAdapter = new DataAdapter(MainActivity.this ,coordinatorLayout,lstFound , androidItemTouchHelper );
            recyclerView.setAdapter(dataAdapter);
            searchView1.hideProgressBar();
        }
        else{
            //if search text is null
            //return default
            dataAdapter = new DataAdapter(MainActivity.this ,coordinatorLayout,GlobalData.natureModelsList , androidItemTouchHelper );
            recyclerView.setAdapter(dataAdapter);
        }
    }

    private void setupWindowAnimations() {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

               Fade fade = new Fade();
                fade.setDuration(900);
                getWindow().setReenterTransition(fade);


                Slide slide = new Slide();
                slide.setDuration(800);
                slide.setSlideEdge(Gravity.LEFT);
                getWindow().setReenterTransition(slide);
        }

    }
}
