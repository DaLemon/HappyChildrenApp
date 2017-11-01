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

public class subtraction2 extends AppCompatActivity implements View.OnDragListener, View.OnLongClickListener{

    private static final String TAG = AdditionSubtractActivity.class.getSimpleName();
    //private TextView textView;
    //private Button button;
    //private ImageView nb1;
    private ImageView leaf1,leaf2,leaf3,leaf4,leaf5,leaf6,leaf7,leaf8,leaf9;

    private static final String IMAGE_VIEW_TAG = "LAUNCHER LOGO";
    //private static final String TEXT_VIEW_TAG = "DRAG TEXT";
    //private static final String BUTTON_VIEW_TAG = "DRAG BUTTON";
    DisplayMetrics metrics = new DisplayMetrics();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subtraction2);
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
        leaf1 = (ImageView) findViewById(R.id.leaf1);
        leaf1.setTag(IMAGE_VIEW_TAG);
        leaf2 = (ImageView) findViewById(R.id.leaf2);
        leaf2.setTag(IMAGE_VIEW_TAG);
        leaf3 = (ImageView) findViewById(R.id.leaf3);
        leaf3.setTag(IMAGE_VIEW_TAG);
        leaf4 = (ImageView) findViewById(R.id.leaf4);
        leaf4.setTag(IMAGE_VIEW_TAG);
        leaf5 = (ImageView) findViewById(R.id.leaf5);
        leaf5.setTag(IMAGE_VIEW_TAG);
        leaf6 = (ImageView) findViewById(R.id.leaf6);
        leaf6.setTag(IMAGE_VIEW_TAG);
        leaf7 = (ImageView) findViewById(R.id.leaf7);
        leaf7.setTag(IMAGE_VIEW_TAG);
        leaf8 = (ImageView) findViewById(R.id.leaf8);
        leaf8.setTag(IMAGE_VIEW_TAG);
        leaf9 = (ImageView) findViewById(R.id.leaf9);
        leaf9.setTag(IMAGE_VIEW_TAG);

        //button = (Button) findViewById(R.id.button);
        //button.setTag(BUTTON_VIEW_TAG) ;
    }

    private void implementEvents() {
        //之後再加各個小圖示
        //textView.setOnLongClickListener(this);
        //nb1.setOnLongClickListener(this);
        leaf1.setOnLongClickListener(this);
        leaf2.setOnLongClickListener(this);
        leaf3.setOnLongClickListener(this);
        leaf4.setOnLongClickListener(this);
        leaf5.setOnLongClickListener(this);
        leaf6.setOnLongClickListener(this);
        leaf7.setOnLongClickListener(this);
        leaf8.setOnLongClickListener(this);
        leaf9.setOnLongClickListener(this);
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

