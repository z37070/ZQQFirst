package com.example.teamwork.Main.MainMine.Mainmineout;

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

public class MineoutAdapter extends XRecyclerView.Adapter<MineoutAdapter.MineoutViewHolder> {
    private Context context_mineout_xr;
    private List<MineoutBean> mineoutList;
    MineoutAdapter(Context context_mineout_xr,List<MineoutBean> mineoutList) {
        this.context_mineout_xr=context_mineout_xr;
        this.mineoutList=mineoutList;
    }

    @NonNull
    @Override
    public MineoutAdapter.MineoutViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Bmob.initialize(context_mineout_xr,"22859591a0207200359b133208256c5a");
        return new MineoutViewHolder(LayoutInflater.from(context_mineout_xr).inflate(R.layout.item_main_mainout, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull final MineoutAdapter.MineoutViewHolder holder, int position) {
        holder.imageview_item_mineout.setImageResource(R.drawable.image_item_mineout);
        //将订单中获取的数据显示到Adapter上。

        MineoutViewHolder viewHolder=holder;
        final MineoutBean info=mineoutList.get(position);
        viewHolder.textview_item_mineout5.setText(info.getExpressNumber());
        viewHolder.textview_item_mineout6.setText(info.getSubstituteID());
        viewHolder.textview_item_mineout7.setText(info.getSubstitutePhone());
        viewHolder.textview_item_mineout8.setText(info.getState());
        //

        if(!holder.textview_item_mineout8.getText().toString().equals("未接单")) {
            holder.button_item_mineout1.setEnabled(false);
        }
        else {
            holder.button_item_mineout1.setEnabled(true);
        }
        if(!holder.textview_item_mineout8.getText().toString().equals("已完成")) {
            holder.button_item_mineout3.setVisibility(View.INVISIBLE);
        }
        else {
            holder.button_item_mineout3.setVisibility(View.VISIBLE);
        }
        if (!holder.textview_item_mineout8.getText().toString().equals("已送达等待确认")) {
            holder.button_item_mineout2.setEnabled(false);
        }
        else {
            holder.button_item_mineout2.setEnabled(true);
        }

        holder.button_item_mineout1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Vibrator vibrator = (Vibrator) context_mineout_xr.getSystemService(Context.VIBRATOR_SERVICE);
                vibrator.vibrate(40);
                holder.dialog_mineout1.show();
                holder.dialog_mineout1.cancel_red.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        holder.dialog_mineout1.dismiss();
                        Animation shake= AnimationUtils.loadAnimation(context_mineout_xr,R.anim.layout_animator_buttonclock);
                        holder.button_item_mineout1.startAnimation(shake);
                    }
                });
                holder.dialog_mineout1.sure_red.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        holder.dialog_mineout1.dismiss();
                        //撤销发布，从订单信息表（数据库）中删除该订单。
                        Order order=new Order();
                        String i=info.getObjectId();
                        order.setObjectId(info.getObjectId());
                        order.delete(new UpdateListener() {
                            @Override
                            public void done(BmobException e) {
                                if(e==null){
                                    notifyItemRemoved(position+1);
                                    mineoutList.remove(position);
                                    notifyItemRangeChanged(position+1,getItemCount());
                                    Toast.makeText(context_mineout_xr,"撤销成功",Toast.LENGTH_LONG).show();
                                }
                            }
                        });

                    }
                });
            }
        });

        holder.button_item_mineout2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.dialog_mineout2.show();
                holder.dialog_mineout2.cancel_blue.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        holder.dialog_mineout2.dismiss();
                        Animation shake= AnimationUtils.loadAnimation(context_mineout_xr,R.anim.layout_animator_buttonclock);
                        holder.button_item_mineout2.startAnimation(shake);
                    }
                });
                holder.dialog_mineout2.sure_blue.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        holder.dialog_mineout2.dismiss();
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                Animation shake= AnimationUtils.loadAnimation(context_mineout_xr,R.anim.layout_animator_buttonx);
                                holder.imageview_item_mineout.startAnimation(shake);
                            }
                        },100);
                        //收货成功，更改订单状态信息。
                        Order order=new Order();
                        order.setState("已完成");
                        order.update(info.getObjectId(), new UpdateListener() {
                            @Override
                            public void done(BmobException e) {
                                if(e==null){
                                    info.setState(order.getState());
                                    notifyDataSetChanged();
                                    Toast.makeText(context_mineout_xr,"收货成功",Toast.LENGTH_LONG).show();
                                }
                            }
                        });
                        if(!holder.textview_item_mineout8.getText().toString().equals("未接单")) {
                            holder.button_item_mineout1.setEnabled(false);
                        }
                        else {
                            holder.button_item_mineout1.setEnabled(true);
                        }
                        if(!holder.textview_item_mineout8.getText().toString().equals("已完成")) {
                            holder.button_item_mineout3.setVisibility(View.INVISIBLE);
                        }
                        else {
                            holder.button_item_mineout3.setVisibility(View.VISIBLE);
                        }
                        if (!holder.textview_item_mineout8.getText().toString().equals("已送达等待确认")) {
                            holder.button_item_mineout2.setEnabled(false);
                        }
                        else {
                            holder.button_item_mineout2.setEnabled(true);
                        }
                    }
                });
            }
        });

        holder.button_item_mineout3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Vibrator vibrator = (Vibrator) context_mineout_xr.getSystemService(Context.VIBRATOR_SERVICE);
                vibrator.vibrate(40);
                holder.dialog_mineout3.show();
                holder.dialog_mineout3.cancel_red.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        holder.dialog_mineout3.dismiss();
                        Animation shake= AnimationUtils.loadAnimation(context_mineout_xr,R.anim.layout_animator_buttony);
                        holder.button_item_mineout3.startAnimation(shake);
                    }
                });
                holder.dialog_mineout3.sure_red.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        holder.dialog_mineout3.dismiss();
                        //在我的发布中删除订单，并更新.
                        Order order=new Order();
                        order.setReceiveID(info.getReceiveID()+" ");
                        order.update(info.getObjectId(),new UpdateListener() {
                            @Override
                            public void done(BmobException e) {
                                if(e==null){
                                    //更新后判断接单人ID和收件人姓名是否都为空，若是，则从订单信息表（数据库）中删除该订单。
                                    BmobQuery<Order> query=new BmobQuery<Order>();
                                    query.getObject(info.getObjectId(),new QueryListener<Order>() {
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
                                    holder.soundpool_item_mineout.play(holder.music_item_mineout,1,1,0,0,1);
                                    notifyItemRemoved(position+1);
                                    mineoutList.remove(position);
                                    notifyItemRangeChanged(position+1,getItemCount());
                                    Toast.makeText(context_mineout_xr,"删除成功",Toast.LENGTH_LONG).show();
                                }
                            }
                        });

                    }
                });
            }
        });

        holder.textview_item_mineout7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent_out=new Intent();
                intent_out.setAction(Intent.ACTION_DIAL);
                intent_out.setData(Uri.parse("tel:"+holder.textview_item_mineout7.getText().toString()));
                context_mineout_xr.startActivity(intent_out);
            }
        });

    }

    @Override
    public int getItemCount() {
        return mineoutList.size();
    }

    static class MineoutViewHolder extends XRecyclerView.ViewHolder {
        private ImageView imageview_item_mineout;
        private TextView textview_item_mineout5;
        private TextView textview_item_mineout6;
        private TextView textview_item_mineout7;
        private TextView textview_item_mineout8;
        private Button button_item_mineout1;
        private Button button_item_mineout2;
        private Button button_item_mineout3;
        private MainRedDialog dialog_mineout1;
        private MainBlueDialog dialog_mineout2;
        private MainRedDialog dialog_mineout3;
        private SoundPool soundpool_item_mineout;
        private int music_item_mineout;


        MineoutViewHolder(@NonNull View itemView) {
            super(itemView);
            imageview_item_mineout=itemView.findViewById(R.id.imageview_item_mineout);
            textview_item_mineout5=itemView.findViewById(R.id.textview_item_mineout5);
            textview_item_mineout6=itemView.findViewById(R.id.textview_item_mineout6);
            textview_item_mineout7=itemView.findViewById(R.id.textview_item_mineout7);
            textview_item_mineout8=itemView.findViewById(R.id.textview_item_mineout8);
            button_item_mineout1=itemView.findViewById(R.id.button_item_mineout1);
            button_item_mineout2=itemView.findViewById(R.id.button_item_mineout2);
            button_item_mineout3=itemView.findViewById(R.id.button_item_mineout3);
            soundpool_item_mineout =new SoundPool(10, AudioManager.STREAM_SYSTEM, 5);
            music_item_mineout = soundpool_item_mineout.load(itemView.getContext(),R.raw.sound3,1);//嗖一声 许可:CC-BY-NC 作者:LloydEvans09 来源:耳聆网 https://www.ear0.com/sound/10420
            dialog_mineout1=new MainRedDialog(itemView.getContext(),R.layout.dialog_main_red,"确认撤销发布?");
            dialog_mineout2=new MainBlueDialog(itemView.getContext(),R.layout.dialog_main_blue,"确认收到快递?");
            dialog_mineout3=new MainRedDialog(itemView.getContext(),R.layout.dialog_main_red,"确认删除此发布记录?");


        }
    }

}
