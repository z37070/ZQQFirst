package com.example.teamwork.Main.MainCommunity;

import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.ViewModelProviders;

import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.Toast;

import com.example.teamwork.Main.MainActivity;
import com.example.teamwork.Order;
import com.example.teamwork.R;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.util.List;
import java.util.ArrayList;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

public class MainCommunityFragment extends Fragment {
    private XRecyclerView xrecyclerview_main_community;
    private int times=0;
    private List<CommunityBean> communityList;
    private CommunityAdapter communityAdapter;
    private MainCommunityViewModel mViewModel;
    private SoundPool soundpool_main_community;
    private int music_main_community;

    public static MainCommunityFragment newInstance() {
        return new MainCommunityFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        Bmob.initialize(this.getContext(),"22859591a0207200359b133208256c5a");
        return inflater.inflate(R.layout.fragment_main_community, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(MainCommunityViewModel.class);
        // TODO: Use the ViewModel
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        MainActivity.back=false;
        ((MainActivity)getActivity()).getMydrawerlayout().setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        communityList=new ArrayList<CommunityBean>();
        xrecyclerview_main_community =view.findViewById(R.id.xrecyclerview_main_community);
        soundpool_main_community=new SoundPool(10, AudioManager.STREAM_SYSTEM, 5);
        music_main_community=soundpool_main_community.load(this.getContext(),R.raw.sound1,1);//卡通爆裂声 许可:CC0 作者:病尉迟 来源:耳聆网 https://www.ear0.com/sound/11846
        xrecyclerview_main_community.setLoadingMoreEnabled(false);
        xrecyclerview_main_community.setArrowImageView(R.drawable.image_refresh_community);
        xrecyclerview_main_community.setRefreshProgressStyle(25);
        xrecyclerview_main_community.setHasFixedSize(true);
        times=0;
        if(communityList!=null){
            communityList.clear();
        }
        communityList=new ArrayList<CommunityBean>();
        BmobQuery<Order> query = new BmobQuery<Order>();
        query.addWhereEqualTo("state", "未接单");//查询所有状态为 未接单的订单。
        query.setLimit(50);
        query.order("-createdAt").findObjects(new FindListener<Order>() {
            @Override
            public void done(List<Order> object, BmobException e) {
                if(e==null){
                    for (Order order : object) {
                        CommunityBean info=new CommunityBean();
                        info.setExpressNumber(order.getExpressNumber());
                        info.setFacility(order.getFacility());
                        info.setAddress(order.getReceiveAddress());
                        info.setState(order.getState());
                        info.setObjectId(order.getObjectId());
                        communityList.add(info);
                    }
                    xrecyclerview_main_community.setLayoutManager(new LinearLayoutManager(getContext()));
                    LayoutAnimationController controller =
                            AnimationUtils.loadLayoutAnimation(getContext(), R.anim.layout_animator_community);
                    xrecyclerview_main_community.setLayoutAnimation(controller);
                    communityAdapter=new CommunityAdapter(getContext(),communityList);
                    xrecyclerview_main_community.setAdapter(communityAdapter);
                }
            }
        });






        xrecyclerview_main_community.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                //通过下拉刷新来显示所有订单状态为未接单的订单。
                times=0;
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if(communityList!=null){
                            communityList.clear();
                        }
                        communityList=new ArrayList<CommunityBean>();
                        BmobQuery<Order> query = new BmobQuery<Order>();
                        query.addWhereEqualTo("state", "未接单");//查询所有状态为 未接单的订单。
                        query.setLimit(50);
                        query.order("-createdAt").findObjects(new FindListener<Order>() {
                            @Override
                            public void done(List<Order> object, BmobException e) {
                                if(e==null){
                                    for (Order order : object) {
                                        CommunityBean info=new CommunityBean();
                                        info.setExpressNumber(order.getExpressNumber());
                                        info.setFacility(order.getFacility());
                                        info.setAddress(order.getReceiveAddress());
                                        info.setState(order.getState());
                                        info.setObjectId(order.getObjectId());
                                        communityList.add(info);
                                    }
                                    xrecyclerview_main_community.setLayoutManager(new LinearLayoutManager(getContext()));
                                    LayoutAnimationController controller =
                                            AnimationUtils.loadLayoutAnimation(getContext(), R.anim.layout_animator_refresh);
                                    xrecyclerview_main_community.setLayoutAnimation(controller);
                                    communityAdapter=new CommunityAdapter(getContext(),communityList);
                                    xrecyclerview_main_community.setAdapter(communityAdapter);
                                }
                            }
                        });

                        soundpool_main_community.play(music_main_community,1,1,0,0,1);
                        xrecyclerview_main_community.refreshComplete();
                        Toast.makeText(getActivity(),"刷新成功",Toast.LENGTH_SHORT).show();
                    }

                },900);

            }

            @Override
            public void onLoadMore() {
            }
        });
    }
}
