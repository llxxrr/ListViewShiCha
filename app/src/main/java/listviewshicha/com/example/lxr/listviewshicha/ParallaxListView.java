package listviewshicha.com.example.lxr.listviewshicha;


import android.animation.ValueAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ViewTreeObserver;
import android.view.animation.OvershootInterpolator;
import android.widget.ImageView;
import android.widget.ListView;

/**
 * Created by lxr on 2018/2/6.
 */

public class ParallaxListView extends ListView {
    private ImageView mimage;
    private int height;
    private int intrinsicHeight;

    public ParallaxListView(Context context) {
        this(context,null);
    }

    public ParallaxListView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public ParallaxListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setImage(final ImageView image, final ViewTreeObserver.OnGlobalLayoutListener onGlobalLayoutListener) {
        this.mimage=image;
        //图片的原始高度
        intrinsicHeight= image.getDrawable().getIntrinsicHeight();
        //imageview的原始高度
        height= mimage.getHeight();
    }
    @Override
    protected boolean overScrollBy(int deltaX, int deltaY, int scrollX, int scrollY, int scrollRangeX, int scrollRangeY, int maxOverScrollX, int maxOverScrollY, boolean isTouchEvent) {
       // Log.d("ParallaxListView==", "overScrollBy");
          //判断是手动向下滑
        if (deltaY<0&&isTouchEvent){
            int showheight= Math.abs(deltaY) + mimage.getHeight();
            if (showheight<=intrinsicHeight){
                mimage.getLayoutParams().height=showheight;
                mimage.requestLayout();
            }
        }
        return super.overScrollBy(deltaX, deltaY, scrollX, scrollY, scrollRangeX, scrollRangeY, maxOverScrollX, maxOverScrollY, isTouchEvent);
    }

   @Override
    public boolean onTouchEvent(MotionEvent ev) {
        int action= ev.getAction();
        switch (action){
            case MotionEvent.ACTION_UP:
                int currentheight= mimage.getHeight();
                ValueAnimator animator = ValueAnimator.ofInt(currentheight,height);
                //添加动画更新的监听
               animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animator) {
                        //获取的动画的值
                        Integer animatorValue = (Integer) animator.getAnimatedValue();
                        Log.d("ParallaxListView", "animatorValue:" + animatorValue);
                        mimage.getLayoutParams().height = animatorValue;
                        mimage.requestLayout();//使布局生效
                    }
                });
                animator.setInterpolator(new OvershootInterpolator(3));//弹性差值器
                animator.setDuration(350);
                animator.start();
                break;
        }
        return super.onTouchEvent(ev);
    }
}
