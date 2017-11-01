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

public class subtraction1 extends AppCompatActivity implements View.OnDragListener, View.OnLongClickListener{

    private static final String TAG = AdditionSubtractActivity.class.getSimpleName();
    //private TextView textView;
    //private Button button;
    //private ImageView nb1;
    private ImageView nbk1,nbk2,nbk3,nbk4,nbk5,nbk6,nbk7,nbk8,nbk9,nbk10,nbk11,nbk12;

    private static final String IMAGE_VIEW_TAG = "LAUNCHER LOGO";
    //private static final String TEXT_VIEW_TAG = "DRAG TEXT";
    //private static final String BUTTON_VIEW_TAG = "DRAG BUTTON";
    DisplayMetrics metrics = new DisplayMetrics();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subtraction1);
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
        nbk1 = (ImageView) findViewById(R.id.nbk1);
        nbk1.setTag(IMAGE_VIEW_TAG);
        nbk2 = (ImageView) findViewById(R.id.nbk2);
        nbk2.setTag(IMAGE_VIEW_TAG);
        nbk3 = (ImageView) findViewById(R.id.nbk3);
        nbk3.setTag(IMAGE_VIEW_TAG);
        nbk4 = (ImageView) findViewById(R.id.nbk4);
        nbk4.setTag(IMAGE_VIEW_TAG);
        nbk5 = (ImageView) findViewById(R.id.nbk5);
        nbk5.setTag(IMAGE_VIEW_TAG);
        nbk6 = (ImageView) findViewById(R.id.nbk6);
        nbk6.setTag(IMAGE_VIEW_TAG);
        nbk7 = (ImageView) findViewById(R.id.nbk7);
        nbk7.setTag(IMAGE_VIEW_TAG);
        nbk8 = (ImageView) findViewById(R.id.nbk8);
        nbk8.setTag(IMAGE_VIEW_TAG);
        nbk9 = (ImageView) findViewById(R.id.nbk9);
        nbk9.setTag(IMAGE_VIEW_TAG);
        nbk10 = (ImageView) findViewById(R.id.nbk10);
        nbk10.setTag(IMAGE_VIEW_TAG);
        nbk11 = (ImageView) findViewById(R.id.nbk11);
        nbk11.setTag(IMAGE_VIEW_TAG);
        nbk12 = (ImageView) findViewById(R.id.nbk12);
        nbk12.setTag(IMAGE_VIEW_TAG);
        //button = (Button) findViewById(R.id.button);
        //button.setTag(BUTTON_VIEW_TAG) ;
    }

    private void implementEvents() {
        //之後再加各個小圖示
        //textView.setOnLongClickListener(this);
        //nb1.setOnLongClickListener(this);
        nbk1.setOnLongClickListener(this);
        nbk2.setOnLongClickListener(this);
        nbk3.setOnLongClickListener(this);
        nbk4.setOnLongClickListener(this);
        nbk5.setOnLongClickListener(this);
        nbk6.setOnLongClickListener(this);
        nbk7.setOnLongClickListener(this);
        nbk8.setOnLongClickListener(this);
        nbk9.setOnLongClickListener(this);
        nbk10.setOnLongClickListener(this);
        nbk11.setOnLongClickListener(this);
        nbk12.setOnLongClickListener(this);
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

