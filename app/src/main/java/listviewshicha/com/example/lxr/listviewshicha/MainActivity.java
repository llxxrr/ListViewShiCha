package listviewshicha.com.example.lxr.listviewshicha;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    private ParallaxListView par;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        final View mHeadView= View.inflate(this, R.layout.head_layout, null);
        par.addHeaderView(mHeadView);
        final ImageView imageView= (ImageView) mHeadView.findViewById(R.id.image);
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        mHeadView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener()      {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onGlobalLayout() {
                par.setImage(imageView,this);
                mHeadView.getViewTreeObserver().removeOnGlobalLayoutListener(this);  //取消当前的观察者
            }
        });
        par.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,Cheeses.NAMES));
    }

    private void initView() {
        par = (ParallaxListView) findViewById(R.id.par);

    }
}
