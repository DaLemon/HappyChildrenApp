package com.example.vrml.happychildapp.HYC_Code.AdditionAndSubtraction;

import android.content.ClipData;
import android.content.ClipDescription;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.DragEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.vrml.happychildapp.R;

public class addition2 extends AppCompatActivity implements View.OnDragListener, View.OnLongClickListener{

    private static final String TAG = AdditionSubtractActivity.class.getSimpleName();
    //private TextView textView;
    //private Button button;
    //private ImageView nb1;
    private ImageView mp1;
    private ImageView mp2;
    private ImageView mp3;
    private ImageView mp4;
    private ImageView mp5;
    private ImageView mp6;
    private ImageView mm_10,mm2,mm3,mm4;
    private static final String IMAGE_VIEW_TAG = "LAUNCHER LOGO";
    DisplayMetrics metrics = new DisplayMetrics();
    //private static final String TEXT_VIEW_TAG = "DRAG TEXT";
    //private static final String BUTTON_VIEW_TAG = "DRAG BUTTON";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addition2);

        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        DisplayMetrics metrics = getResources().getDisplayMetrics();

        int DeviceTotalWidth = metrics.widthPixels;
        int DeviceTotalHeight = metrics.heightPixels;
        TextView product = (TextView)findViewById(R.id.textView) ;
        TextView product_2 = (TextView)findViewById(R.id.formula) ;
        product.setTextSize(DeviceTotalWidth/70);
        product_2.setTextSize(DeviceTotalWidth/70);

        findViews();
        implementEvents();
    }

    public void onClick(View v){
        Intent a = new Intent(this,AdditionSubtractActivity.class);
        startActivity(a);
    } //和主選單連接

    private void findViews() {
        //textView = (TextView) findViewById(R.id.label);
        //textView.setTag(TEXT_VIEW_TAG);
        //nb1 = (ImageView) findViewById(R.id.nb1);
        //nb1.setTag(IMAGE_VIEW_TAG);
        mp1 = (ImageView) findViewById(R.id.mp1);
        mp1.setTag(IMAGE_VIEW_TAG);
        mp2 = (ImageView) findViewById(R.id.mp2);
        mp2.setTag(IMAGE_VIEW_TAG);
        mp3 = (ImageView) findViewById(R.id.mp3);
        mp3.setTag(IMAGE_VIEW_TAG);
        mp4 = (ImageView) findViewById(R.id.mp4);
        mp4.setTag(IMAGE_VIEW_TAG);
        mp5 = (ImageView) findViewById(R.id.mp5);
        mp5.setTag(IMAGE_VIEW_TAG);
        mp6 = (ImageView) findViewById(R.id.mp6);
        mp6.setTag(IMAGE_VIEW_TAG);

        mm_10 = (ImageView) findViewById(R.id.mm_10);
        mm_10.setTag(IMAGE_VIEW_TAG);
        mm2 = (ImageView) findViewById(R.id.mm2);
        mm2.setTag(IMAGE_VIEW_TAG);
        mm3 = (ImageView) findViewById(R.id.mm3);
        mm3.setTag(IMAGE_VIEW_TAG);
        mm4 = (ImageView) findViewById(R.id.mm4);
        mm4.setTag(IMAGE_VIEW_TAG);
        //button = (Button) findViewById(R.id.button);
        //button.setTag(BUTTON_VIEW_TAG) ;
    }

    private void implementEvents() {
        //之後再加各個小圖示
        //textView.setOnLongClickListener(this);
        //nb1.setOnLongClickListener(this);
        mp1.setOnLongClickListener(this);
        mp2.setOnLongClickListener(this);
        mp3.setOnLongClickListener(this);
        mp4.setOnLongClickListener(this);
        mp5.setOnLongClickListener(this);
        mp6.setOnLongClickListener(this);

        mm_10.setOnLongClickListener(this);
        mm2.setOnLongClickListener(this);
        mm3.setOnLongClickListener(this);
        mm3.setOnLongClickListener(this);

        //button.setOnLongClickListener(this);

        //上面加這邊也要
        //findViewById(R.id.nb2).setOnDragListener(this);
        //findViewById(R.id.nb3).setOnDragListener(this);
        //findViewById(R.id.nb4).setOnDragListener(this);
        //findViewById(R.id.nb5).setOnDragListener(this);
        // findViewById(R.id.nb6).setOnDragListener(this);
        findViewById(R.id.top_layout).setOnDragListener(this);
        findViewById(R.id.left_layout).setOnDragListener(this);
        //findViewById(R.id.right_layout).setOnDragListener(this);
    }
    @Override
    public boolean onLongClick(View view) {

        ClipData.Item item = new ClipData.Item((CharSequence) view.getTag());
        String[] mimeTypes = {ClipDescription.MIMETYPE_TEXT_PLAIN};
        ClipData data = new ClipData(view.getTag().toString(), mimeTypes, item);

        // Instantiates the drag shadow builder.
        View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(view);

        // Starts the drag
        view.startDrag(data//data to be dragged
                , shadowBuilder //drag shadow
                , view//local data about the drag and drop operation
                , 0//no needed flags
        );

        //Set view visibility to INVISIBLE as we are going to drag the view
        view.setVisibility(View.INVISIBLE);
        return true;
    }

    // This is the method that the system calls when it dispatches a drag event to the
    // listener.
    @Override
    public boolean onDrag(View view, DragEvent event) {
        // Defines a variable to store the action type for the incoming event
        int action = event.getAction();
        // Handles each of the expected events
        switch (action) {
            case DragEvent.ACTION_DRAG_STARTED:
                // Determines if this View can accept the dragged data
                if (event.getClipDescription().hasMimeType(ClipDescription.MIMETYPE_TEXT_PLAIN)) {

                    return true;

                }

                return false;

            case DragEvent.ACTION_DRAG_ENTERED:
                view.getBackground().setColorFilter(Color.YELLOW, PorterDuff.Mode.SRC_IN);

                // Invalidate the view to force a redraw in the new tint
                view.invalidate();

                return true;
            case DragEvent.ACTION_DRAG_LOCATION:
                // Ignore the event
                return true;
            case DragEvent.ACTION_DRAG_EXITED:
                view.getBackground().clearColorFilter();
                view.invalidate();

                return true;
            case DragEvent.ACTION_DROP:
                // Gets the item containing the dragged data
                ClipData.Item item = event.getClipData().getItemAt(0);
                String dragData = item.getText().toString();

                view.getBackground().clearColorFilter();
                view.invalidate();

                View v = (View) event.getLocalState();
                ViewGroup owner = (ViewGroup) v.getParent();
                owner.removeView(v);//remove the dragged view
                LinearLayout container = (LinearLayout) view;//caste the view into LinearLayout as our drag acceptable layout is LinearLayout
                container.addView(v);//Add the dragged view
                v.setVisibility(View.VISIBLE);//finally set Visibility to VISIBLE
                return true;
            case DragEvent.ACTION_DRAG_ENDED:
                view.getBackground().clearColorFilter();
                view.invalidate();
                return true;
            default:
                Log.e("DragDrop Example", "Unknown action type received by OnDragListener.");
                break;
        }
        return false;
    }
}
