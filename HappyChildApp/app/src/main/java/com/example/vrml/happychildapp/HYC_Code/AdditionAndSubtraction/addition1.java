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

public class addition1 extends AppCompatActivity implements View.OnDragListener, View.OnLongClickListener{
    private static final String TAG = AdditionSubtractActivity.class.getSimpleName();
    private ImageView nb2;
    private ImageView nb3;
    private ImageView nb4;
    private ImageView nb5;
    private ImageView nb6;
    private ImageView bb_10,bb2,bb3,bb4,bb5,bb6,bb7;
    private static final String IMAGE_VIEW_TAG = "LAUNCHER LOGO";
    //private static final String TEXT_VIEW_TAG = "DRAG TEXT";
    //private static final String BUTTON_VIEW_TAG = "DRAG BUTTON";
    DisplayMetrics metrics = new DisplayMetrics();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addition1);
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
        Intent i = new Intent(this,AdditionSubtractActivity.class);
        startActivity(i);
    } //和主選單連接

    private void findViews() {
        //textView = (TextView) findViewById(R.id.label);
        //textView.setTag(TEXT_VIEW_TAG);
        //nb1 = (ImageView) findViewById(R.id.nb1);
        //nb1.setTag(IMAGE_VIEW_TAG);
        nb2 = (ImageView) findViewById(R.id.nb2);
        nb2.setTag(IMAGE_VIEW_TAG);
        nb3 = (ImageView) findViewById(R.id.nb3);
        nb3.setTag(IMAGE_VIEW_TAG);
        nb4 = (ImageView) findViewById(R.id.nb4);
        nb4.setTag(IMAGE_VIEW_TAG);
        nb5 = (ImageView) findViewById(R.id.nb5);
        nb5.setTag(IMAGE_VIEW_TAG);
        nb6 = (ImageView) findViewById(R.id.nb6);
        nb6.setTag(IMAGE_VIEW_TAG);

        bb_10 = (ImageView) findViewById(R.id.bb_10);
        bb_10.setTag(IMAGE_VIEW_TAG);
        bb2 = (ImageView) findViewById(R.id.bb2);
        bb2.setTag(IMAGE_VIEW_TAG);
        bb3 = (ImageView) findViewById(R.id.bb3);
        bb3.setTag(IMAGE_VIEW_TAG);
        bb4 = (ImageView) findViewById(R.id.bb4);
        bb4.setTag(IMAGE_VIEW_TAG);
        bb5 = (ImageView) findViewById(R.id.bb5);
        bb5.setTag(IMAGE_VIEW_TAG);
        bb6 = (ImageView) findViewById(R.id.bb6);
        bb6.setTag(IMAGE_VIEW_TAG);
        bb7 = (ImageView) findViewById(R.id.bb7);
        bb7.setTag(IMAGE_VIEW_TAG);
        //button = (Button) findViewById(R.id.button);
        //button.setTag(BUTTON_VIEW_TAG) ;
    }

    private void implementEvents() {
        //之後再加各個小圖示
        //textView.setOnLongClickListener(this);
        //nb1.setOnLongClickListener(this);
        nb2.setOnLongClickListener(this);
        nb3.setOnLongClickListener(this);
        nb4.setOnLongClickListener(this);
        nb5.setOnLongClickListener(this);
        nb6.setOnLongClickListener(this);

        bb_10.setOnLongClickListener(this);
        bb2.setOnLongClickListener(this);
        bb3.setOnLongClickListener(this);
        bb4.setOnLongClickListener(this);
        bb5.setOnLongClickListener(this);
        bb6.setOnLongClickListener(this);
        bb7.setOnLongClickListener(this);
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
                    // if you want to apply color when drag started to your view you can uncomment below lines
                    // to give any color tint to the View to indicate that it can accept
                    // data.

                    //  view.getBackground().setColorFilter(Color.BLUE, PorterDuff.Mode.SRC_IN);//set background color to your view

                    // Invalidate the view to force a redraw in the new tint
                    //  view.invalidate();

                    // returns true to indicate that the View can accept the dragged data.
                    return true;

                }

                // Returns false. During the current drag and drop operation, this View will
                // not receive events again until ACTION_DRAG_ENDED is sent.
                return false;

            case DragEvent.ACTION_DRAG_ENTERED:
                // Applies a YELLOW or any color tint to the View, when the dragged view entered into drag acceptable view
                // Return true; the return value is ignored.

                view.getBackground().setColorFilter(Color.YELLOW, PorterDuff.Mode.SRC_IN);

                // Invalidate the view to force a redraw in the new tint
                view.invalidate();

                return true;
            case DragEvent.ACTION_DRAG_LOCATION:
                // Ignore the event
                return true;
            case DragEvent.ACTION_DRAG_EXITED:
                // Re-sets the color tint to blue, if you had set the BLUE color or any color in ACTION_DRAG_STARTED. Returns true; the return value is ignored.

                //  view.getBackground().setColorFilter(Color.BLUE, PorterDuff.Mode.SRC_IN);

                //If u had not provided any color in ACTION_DRAG_STARTED then clear color filter.
                view.getBackground().clearColorFilter();
                // Invalidate the view to force a redraw in the new tint
                view.invalidate();

                return true;
            case DragEvent.ACTION_DROP:
                // Gets the item containing the dragged data
                ClipData.Item item = event.getClipData().getItemAt(0);

                // Gets the text data from the item.
                String dragData = item.getText().toString();

                // Displays a message containing the dragged data.
                //Toast.makeText(this, "Dragged data is " + dragData, Toast.LENGTH_SHORT).show();

                // Turns off any color tints
                view.getBackground().clearColorFilter();

                // Invalidates the view to force a redraw
                view.invalidate();

                View v = (View) event.getLocalState();
                ViewGroup owner = (ViewGroup) v.getParent();
                owner.removeView(v);//remove the dragged view
                LinearLayout container = (LinearLayout) view;//caste the view into LinearLayout as our drag acceptable layout is LinearLayout
                container.addView(v);//Add the dragged view
                v.setVisibility(View.VISIBLE);//finally set Visibility to VISIBLE

                // Returns true. DragEvent.getResult() will return true.
                return true;
            case DragEvent.ACTION_DRAG_ENDED:
                // Turns off any color tinting
                view.getBackground().clearColorFilter();

                // Invalidates the view to force a redraw
                view.invalidate();

                // Does a getResult(), and displays what happened.
                //if (event.getResult())
                //    Toast.makeText(this, "已經移動", Toast.LENGTH_SHORT).show();

                //else
                //    Toast.makeText(this, "登愣", Toast.LENGTH_SHORT).show();


                // returns true; the value is ignored.
                return true;

            // An unknown action type was received.
            default:
                Log.e("DragDrop Example", "Unknown action type received by OnDragListener.");
                break;
        }
        return false;
    }
}
