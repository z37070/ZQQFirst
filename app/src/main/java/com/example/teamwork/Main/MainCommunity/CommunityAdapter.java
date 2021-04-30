package com.example.teamwork.Main.MainCommunity;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import java.util.List;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;


import com.example.teamwork.Main.MainActivity;
import com.example.teamwork.Main.MainBlueDialog;
import com.example.teamwork.Order;
import com.example.teamwork.R;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;

public class CommunityAdapter extends XRecyclerView.Adapter<CommunityAdapter.CommunityViewHolder> {
    private Context context_community_xr;
    private List<CommunityBean> communityList;
    String id;
    public CommunityAdapter(Context context_community_xr,List<CommunityBean> communityList) {
        this.context_community_xr = context_community_xr;
        this.communityList=communityList;
    }



    @NonNull
    @Override
    public CommunityViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Bmob.initialize(context_community_xr,"22859591a0207200359b133208256c5a");
        return new CommunityViewHolder(LayoutInflater.from(context_community_xr).inflate(R.layout.item_main_community,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull final CommunityViewHolder holder, int position) {
        holder.imageview_item_community.setImageResource(R.drawable.image_item_community);
        SharedPreferences sharedPreferences=context_community_xr.getSharedPreferences("user_info", Activity.MODE_PRIVATE);
        id=sharedPreferences.getString("id","");
        //将获取的数据显示到对应的控件上
        final CommunityBean info=communityList.get(position);
        //禁止自己接自己的单
       if(info.getExpressNumber().startsWith(id)) {
            holder.button_item_community1.setEnabled(false);
        }
        CommunityViewHolder viewHolder=(CommunityViewHolder) holder;
        viewHolder.textview_item_community5.setText(info.getExpressNumber());
        viewHolder.textview_item_community6.setText(info.getFacility());
        viewHolder.textview_item_community7.setText(info.getAddress());
        holder.button_item_community1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.dialog_community.show();
                holder.dialog_community.cancel_blue.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        holder.dialog_community.dismiss();
                        Animation shake= AnimationUtils.loadAnimation(context_community_xr,R.anim.layout_animator_buttonclock);
                        holder.button_item_community1.startAnimation(shake);
                    }
                });
                holder.dialog_community.sure_blue.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        holder.dialog_community.dismiss();


                        //判断状态是否为“未接单”，存在未刷新界面造成的接单失败。
                        if(info.getState().equals("未接单")) {
                            //接单成功，向订单表中补充接单人信息，并更改状态。

                            SharedPreferences sharedPreferences=context_community_xr .getSharedPreferences("user_info",Context.MODE_PRIVATE);
                            String id=sharedPreferences.getString("id","");
                            String phone=sharedPreferences.getString("phone","");
                            Order order=new Order();
                            order.setSubstituteID(id);
                            order.setSubstitutePhone(phone);
                            order.setState("进行中");
                            order.update(info.getObjectId(), new UpdateListener() {
                                @Override
                                public void done(BmobException e) {
                                    if(e==null){
                                        notifyItemRemoved(position+1);
                                        communityList.remove(position);
                                        notifyItemRangeChanged(position+1,getItemCount());

                                        Toast.makeText(context_community_xr,"接单成功,请移步我的接单查看",Toast.LENGTH_LONG).show();
                                    }
                                }
                            });
                        }
                        else Toast.makeText(context_community_xr, "接单失败，请重新刷新", Toast.LENGTH_LONG).show();
                    }
                });
            }
        });



    }


    @Override
    public int getItemCount() {
        return  communityList.size();
    }



    class CommunityViewHolder extends XRecyclerView.ViewHolder {

        private TextView textview_item_community5;
        private TextView textview_item_community6;
        private TextView textview_item_community7;
        private ImageView imageview_item_community;
        private Button button_item_community1;
        private MainBlueDialog dialog_community;

        public CommunityViewHolder(@NonNull View itemView) {
            super(itemView);
            imageview_item_community=itemView.findViewById(R.id.imageview_item_community);
            textview_item_community5 = itemView.findViewById(R.id.textview_item_community5);
            textview_item_community6 = itemView.findViewById(R.id.textview_item_community6);
            textview_item_community7 = itemView.findViewById(R.id.textview_item_community7);
            button_item_community1=itemView.findViewById(R.id.button_item_community1);
            dialog_community=new MainBlueDialog(itemView.getContext(),R.layout.dialog_main_blue,"确认接单?");

        }
    }
}
