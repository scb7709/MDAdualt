package com.headlth.management.fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.headlth.management.R;
import com.headlth.management.entity.anlyseCallBack;
import com.headlth.management.utils.ImageUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;


public class AnalizeClFragment extends BaseFragment {


    @InjectView(R.id.AvgCal)
    TextView AvgCal;
    @InjectView(R.id.TotalCal)
    TextView TotalCal;
    /*  @InjectView(R.id.TotalDays)
      TextView TotalDays;*/
    @InjectView(R.id.MaxTotalTime)
    TextView MaxTotalTime;
    @InjectView(R.id.midleTime)
    TextView midleTime;
    @InjectView(R.id.zhouyiall)
    Button clzhouyi;
    @InjectView(R.id.zhouerall)
    Button clzhouer;
    @InjectView(R.id.zhousanall)
    Button clzhousan;
    @InjectView(R.id.zhousiall)
    Button clzhousi;
    @InjectView(R.id.zhouwuall)
    Button clzhouwu;
    @InjectView(R.id.zhouliuall)
    Button clzhouliu;
    @InjectView(R.id.zhouriall)
    Button clzhouri;

    @InjectView(R.id.zhu)
    LinearLayout zhu;
    RelativeLayout relativeLayout;
    @InjectView(R.id.t1)
    TextView t1;
    @InjectView(R.id.t2)
    TextView t2;
    @InjectView(R.id.t3)
    TextView t3;
    @InjectView(R.id.t4)
    TextView t4;
    @InjectView(R.id.t5)
    TextView t5;
    @InjectView(R.id.t6)
    TextView t6;
    @InjectView(R.id.t7)
    TextView t7;
    private TextView botomLin;
    View view;
    List<Button> bts = null;
    List<TextView> ts = null;
    @InjectView(R.id.pingji)
    TextView pingji;
    private int screenWidth;
    private Activity activity;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_layout_cl, null);
        ButterKnife.inject(this, view);

        botomLin = (TextView) view.findViewById(R.id.t1);

        relativeLayout = (RelativeLayout) view.findViewById(R.id.view);

        WindowManager wm = getActivity().getWindowManager();
        screenWidth = wm.getDefaultDisplay().getWidth();

        activity = getActivity();
        return view;
    }

    private anlyseCallBack anlyse;

    @SuppressLint("ValidFragment")
    public AnalizeClFragment(anlyseCallBack anlyse) {
        this.anlyse = anlyse;
    }

    public AnalizeClFragment() {
    }

    String avgTal;
    String totalCal;
    int daohangHigh;

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        bts = new ArrayList<>();
        bts.add(clzhouyi);
        bts.add(clzhouer);
        bts.add(clzhousan);
        bts.add(clzhousi);
        bts.add(clzhouwu);
        bts.add(clzhouliu);
        bts.add(clzhouri);
        ts = new ArrayList<>();
        ts.add(t1);
        ts.add(t2);
        ts.add(t3);
        ts.add(t4);
        ts.add(t5);
        ts.add(t6);
        ts.add(t7);


        if (anlyse != null) {
            if (anlyse.getStatus() != 0) {
                for (int ShowPossition = 0; ShowPossition < 7; ShowPossition++) {
                    setOnClickListener(ShowPossition);
                }
                totalCal = "" + (Integer.parseInt(anlyse.getData().getSummary().get(0).getTotalCal()));
                TotalCal.setText(totalCal);
                avgTal = "" + (Integer.parseInt(anlyse.getData().getSummary().get(0).getAvgCal()));
                AvgCal.setText(avgTal);
         /*   TotalDays.setText("共运动：" + anlyse.getData().getSummary().get(0).getTotalDays() + "天");*/
                MaxTotalTime.setText((Integer.parseInt(anlyse.getData().getSummary().get(0).getMaxCalory())) + "千卡");
                midleTime.setText("" + (Integer.parseInt(anlyse.getData().getSummary().get(0).getMaxCalory())) / 2);
                pingji.setText(anlyse.getData().getSummary().get(0).getCaloryRate());


           /* mRoundProgressBar4 = (RelativeLayout) getActivity().findViewById(R.id.roundProgressBar4);
            mRoundProgressBar4.addView(new MyTestView(getActivity()));*/

                h.sendEmptyMessageDelayed(1, 1);
            }
        }

    }


    private void setOnClickListener(final int ShowPossition) {
        final Button show = bts.get(ShowPossition);
        show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int[] location55 = new int[2];
                show.getLocationOnScreen(location55);
                showdraw(anlyse.getData().getDetail().get(ShowPossition).getCalory(), location55[0], location55[1]);
            }
        });
    }

    private void showdraw(String EffectTime, int x, int y) {
        if (d != null) {
            relativeLayout.removeView(d);
            d = null;
        }
        d = new draw(getContext(), EffectTime, x, (y - daohangHigh));
        relativeLayout.addView(d);

    }

    public class draw extends View {
        int x = 0;
        int y = 0;
        String data = "";

        public draw(Context context, String data, int x, int y) {
            super(context);
            setWillNotDraw(false);
            this.data = data;
            this.x = x;
            this.y = y;
        }

        @Override
        public void onDraw(Canvas canvas) {
            super.onDraw(canvas);
            // Log.e("0000", x + "开始画了---22222" + y + "开始画了");
            Paint paint = new Paint();
            paint.setStyle(Paint.Style.FILL);
            paint.setAntiAlias(true);
            paint.setColor(Color.parseColor("#ffcc33"));
            paint.setStrokeWidth((float) 1.0);
            if (screenWidth < 1080) {
                paint.setTextSize(22);
            } else {
                paint.setTextSize(32);
            }

            int width = x + (clzhouyi.getWidth() - getTextWidth(data, paint)) / 2;//要居中的话 柱状图的宽度减去文字的宽度的一半 加上X 就等于文字的起始坐标
            Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.icon_triangle_orange);
            canvas.drawText(data, width, y - ImageUtil.dp2px(activity, 15), paint);
            canvas.drawBitmap(bitmap, x + (clzhouyi.getWidth() - bitmap.getWidth()) / 2, y - ImageUtil.dp2px(activity, 12), paint);

        }

    }

    public static int getTextWidth(String content, Paint paint) {
        int width = 0;
        if (content != null && content.length() > 0) {
            int length = content.length();
            float[] widths = new float[length];
            paint.getTextWidths(content, widths);
            for (int i = 0; i < length; i++) {
                width += (int) Math.ceil(widths[i]);
            }
        }
        return width;
    }


    int gap;

    public Handler h = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 1) {
                try {
                    if (relativeLayout.getWidth() != 0 && relativeLayout.getHeight() != 0 && zhu.getWidth() != 0 && zhu.getHeight() != 0) {
                        int[] location666 = new int[2];
                        relativeLayout.getLocationOnScreen(location666);
                        daohangHigh = location666[1];
                        int[] location = new int[2];
                        zhu.getLocationOnScreen(location);
                        gap = zhu.getHeight();//(botomLin.getTop() - zhu.getTop());
                        List<anlyseCallBack.DataBean.DetailBean> detailBean = anlyse.getData().getDetail();
                        int temp = Integer.parseInt(anlyse.getData().getSummary().get(0).getMaxCalory());
                        if (temp != 0) {
                            for (int i = 0; i < anlyse.getData().getDetail().size(); i++) {
                                RelativeLayout.LayoutParams linearParams = (RelativeLayout.LayoutParams) bts.get(i).getLayoutParams();
                                // 取控件aaa当前的布局参数
                                linearParams.height = gap * Integer.parseInt(detailBean.get(i).getCalory()) / (temp); //
                                bts.get(i).setLayoutParams(linearParams); // 使设置好的布局参数应用到控件aaa
                                ts.get(i).setText(anlyse.getData().getDetail().get(i).getDay());
                            }
                        }
                    } else {
                        h.sendEmptyMessageDelayed(1, 1);
                    }
                } catch (Exception e) {
                    h.sendEmptyMessageDelayed(1, 1);
                }
            }

        }
    };

    draw d;

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }

}
