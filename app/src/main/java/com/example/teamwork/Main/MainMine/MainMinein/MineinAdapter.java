package com.example.teamwork.Main.MainMine.MainMinein;

import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.SoundPool;
import android.net.Uri;
import android.os.Handler;
import android.os.Vibrator;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.teamwork.Main.MainBlueDialog;
import com.example.teamwork.Main.MainRedDialog;
import com.example.teamwork.Order;
import com.example.teamwork.R;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.util.List;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.QueryListener;
import cn.bmob.v3.listener.UpdateListener;

public class MineinAdapter extends XRecyclerView.Adapter<MineinAdapter.MineinViewHolder> {
    private Context context_minein_xr;
    private List<MineinBean> mineinList;

    MineinAdapter(Context context_minein_xr, List<MineinBean> mineinList) {
        this.context_minein_xr=context_minein_xr;
        this.mineinList=mineinList;
    }

    @NonNull
    @Override
    public MineinViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Bmob.initialize(context_minein_xr,"22859591a0207200359b133208256c5a");
        return new MineinViewHolder(LayoutInflater.from(context_minein_xr).inflate(R.layout.item_main_minein, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull final MineinViewHolder holder, int position) {
        holder.imageview_item_minein.setImageResource(R.drawable.image_item_minein);
        //将订单表中获取数据显示到相应的控件上。
        final MineinBean info=mineinList.get(position);
        MineinViewHolder viewHolder=holder;
        viewHolder.textview_item_minein8.setText(info.getExpressNumber());
        viewHolder.textview_item_minein9.setText(info.getFacility());
        viewHolder.textview_item_minein10.setText(info.getPickupNumber());
        viewHolder.textview_item_minein11.setText(info.getReceiveAddress());
        viewHolder.textview_item_minein12.setText(info.getReceiveName());
        viewHolder.textview_item_minein13.setText(info.getReceivePhone());
        viewHolder.textview_item_minein14.setText(info.getState());
        if (!holder.textview_item_minein14.getText().toString().equals("已完成")) {
            holder.button_item_minein2.setVisibility(View.INVISIBLE);
        }
        else {
            holder.button_item_minein2.setVisibility(View.VISIBLE);
        }
        if (!holder.textview_item_minein14.getText().toString().equals("进行中")) {
            holder.button_item_minein1.setEnabled(false);
        }
        else {
            holder.button_item_minein1.setEnabled(true);
        }

        holder.button_item_minein1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.dialog_minein1.show();
                holder.dialog_minein1.cancel_blue.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        holder.dialog_minein1.dismiss();
                        Animation shake= AnimationUtils.loadAnimation(context_minein_xr,R.anim.layout_animator_buttonclock);
                        holder.button_item_minein1.startAnimation(shake);

                    }
                });
                holder.dialog_minein1.sure_blue.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        holder.dialog_minein1.dismiss();
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                Animation shake= AnimationUtils.loadAnimation(context_minein_xr,R.anim.layout_animator_buttonx);
                                holder.imageview_item_minein.startAnimation(shake);

                            }
                        },100);
                        //告知送达后更改订单状态信息.
                        Order order=new Order();
                        order.setState("已送达等待确认");
                        order.update(info.getObjectId(), new UpdateListener() {
                            @Override
                            public void done(BmobException e) {
                                if(e==null){
                                    info.setState(order.getState());
                                    notifyDataSetChanged();
                                    Toast.makeText(context_minein_xr,"告知成功",Toast.LENGTH_LONG).show();

                                }
                            }
                        });
                        if (!holder.textview_item_minein14.getText().toString().equals("已完成")) {
                            holder.button_item_minein2.setVisibility(View.INVISIBLE);
                        }
                        else {
                            holder.button_item_minein2.setVisibility(View.VISIBLE);
                        }
                        if (!holder.textview_item_minein14.getText().toString().equals("进行中")) {
                            holder.button_item_minein1.setEnabled(false);
                        }
                        else {
                            holder.button_item_minein1.setEnabled(true);
                        }
                    }
                });
            }
        });

        holder.button_item_minein2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Vibrator vibrator = (Vibrator) context_minein_xr.getSystemService(Context.VIBRATOR_SERVICE);
                vibrator.vibrate(40);
                holder.dialog_minein2.show();
                holder.dialog_minein2.cancel_red.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        holder.dialog_minein2.dismiss();
                        Animation shake= AnimationUtils.loadAnimation(context_minein_xr,R.anim.layout_animator_buttony);
                        holder.button_item_minein2.startAnimation(shake);
                    }
                });
                holder.dialog_minein2.sure_red.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        holder.dialog_minein2.dismiss();
                        //在我的接单中删除订单信息，并更新。
                        Order order=new Order();
                        String str=info.getObjectId();
                        order.setSubstituteID(info.getSubstituteID()+" ");
                        order.update(info.getObjectId(),new UpdateListener() {
                            @Override
                            public void done(BmobException e) {
                                if(e==null){
                                    //更新后判断接单人ID和收件人姓名是否都为空，若是，则从订单信息表（数据库）中删除该订单。
                                    BmobQuery<Order> query=new BmobQuery<Order>();
                                    query.getObject(info.getObjectId(), new QueryListener<Order>() {
                                        @Override
                                        public void done(Order order, BmobException e) {
                                            if(e==null){
                                                if(order.getReceiveID().endsWith(" ")&&order.getSubstituteID().endsWith(" ")){
                                                    order.setObjectId(info.getObjectId());
                                                    order.delete(new UpdateListener() {
                                                        @Override
                                                        public void done(BmobException e) {
                                                        }
                                                    });
                                                }
                                            }
                                        }
                                    });
                                    holder.soundpool_item_minein.play(holder.music_item_minein,1,1,0,0,1);
                                    notifyItemRemoved(position+1);
                                    mineinList.remove(position);
                                    notifyItemRangeChanged(position+1,getItemCount());
                                    Toast.makeText(context_minein_xr,"删除成功",Toast.LENGTH_LONG).show();
                                }
                            }
                        });

                    }
                });
            }
        });

        holder.textview_item_minein13.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent_in=new Intent();
                intent_in.setAction(Intent.ACTION_DIAL);
                intent_in.setData(Uri.parse("tel:"+holder.textview_item_minein13.getText().toString()));
                context_minein_xr.startActivity(intent_in);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mineinList.size();
    }



     static class MineinViewHolder extends XRecyclerView.ViewHolder {
        private ImageView imageview_item_minein;
        private TextView textview_item_minein8;
        private TextView textview_item_minein9;
        private TextView textview_item_minein10;
        private TextView textview_item_minein11;
        private TextView textview_item_minein12;
        private TextView textview_item_minein13;
        private TextView textview_item_minein14;
        private Button button_item_minein1;
        private Button button_item_minein2;
        private MainBlueDialog dialog_minein1;
        private MainRedDialog dialog_minein2;
        private SoundPool soundpool_item_minein;
        private int music_item_minein;


        MineinViewHolder(@NonNull View itemView) {
            super(itemView);
            imageview_item_minein=itemView.findViewById(R.id.imageview_item_minein);
            textview_item_minein8=itemView.findViewById(R.id.textview_item_minein8);
            textview_item_minein9=itemView.findViewById(R.id.textview_item_minein9);
            textview_item_minein10=itemView.findViewById(R.id.textview_item_minein10);
            textview_item_minein11=itemView.findViewById(R.id.textview_item_minein11);
            textview_item_minein12=itemView.findViewById(R.id.textview_item_minein12);
            textview_item_minein13=itemView.findViewById(R.id.textview_item_minein13);
            textview_item_minein14=itemView.findViewById(R.id.textview_item_minein14);
            button_item_minein1=itemView.findViewById(R.id.button_item_minein1);
            button_item_minein2=itemView.findViewById(R.id.button_item_minein2);
            soundpool_item_minein =new SoundPool(10, AudioManager.STREAM_SYSTEM, 5);
            music_item_minein = soundpool_item_minein.load(itemView.getContext(),R.raw.sound3,1);//嗖一声 许可:CC-BY-NC 作者:LloydEvans09 来源:耳聆网 https://www.ear0.com/sound/10420
            dialog_minein1 =new MainBlueDialog(itemView.getContext(),R.layout.dialog_main_blue,"确认告知送达?");
            dialog_minein2=new MainRedDialog(itemView.getContext(),R.layout.dialog_main_red,"确认删除此接单记录?");



        }


    }
}
