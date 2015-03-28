package com.example.rolltmdapp.Utils;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.Toast;

public class TouchImageView extends ImageView {


	public TouchImageView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		this.context=context;
	}
	public TouchImageView(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context=context;
	}
	public TouchImageView(Context context) {
		super(context);
		this.context=context;
	}
	private Context context;
	private long downtime;//按下时间
	private long uptime;//松开时间
	private float startx;//开始x
	private float starty;//开始Y
	private float endx;//结束x
	private float endy;//结束Y
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		switch(event.getAction()){
		case MotionEvent.ACTION_DOWN:
			System.out.println(	event.getX()+"="+event.getY()+"ACTION_DOWN");
			downtime=System.currentTimeMillis();
			startx=event.getX();
			starty=event.getY();
			clearAnimation();
			 break;
		case MotionEvent.ACTION_MOVE:
			System.out.println(	event.getX()+"="+event.getY()+"ACTION_MOVE");
			break;
		case MotionEvent.ACTION_UP:
			System.out.println(	event.getX()+"="+event.getY()+"ACTION_UP");
			uptime=System.currentTimeMillis();
			System.out.println(getMeasuredHeight()+"==="+getMeasuredWidth());
			endx=event.getX();
			endy=event.getY();
			//开始动画
			playAnimation();
			Toast.makeText(context, getSpeed()+" ", Toast.LENGTH_SHORT).show();
			break;
			
		}
		return true;
	}
	private void playAnimation() {
		RotateAnimation rotateAnimation=new RotateAnimation(0,(int)(getSpeed()*360),Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_SELF,0.5f);
		DecelerateInterpolator dec=new DecelerateInterpolator(5f);
		rotateAnimation.setInterpolator(dec);
		rotateAnimation.setDuration((int)(getSpeed()*1000));
		rotateAnimation.setFillAfter(true);
		startAnimation(rotateAnimation);
	}
	//判断是否为顺时针旋转
	private boolean isClockWise()
	{
		//需要获得图片中心的位置，然后与按下和放开两个点之间进行判断，来决定
		return false;
	}
	public    double   getLength()
	{
		double length = 0;
		if(startx!=0&&starty!=0&&endx!=0&&endy!=0&&downtime!=0&&uptime!=0)
			length =Math.sqrt((startx-endx)*(startx-endx)+(starty-endy)*(starty-endy));
		return length;
	}
	//获得你移动的速度，速速越快，速度越大
	public double getSpeed()
	{
		return getLength()/(uptime-downtime);
	}
}
