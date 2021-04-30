package com.example.teamwork.Main.MainMine.MainMinein;

import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.ViewModelProviders;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.Button;
import android.widget.Toast;

import com.example.teamwork.Main.MainActivity;
import com.example.teamwork.Order;
import com.example.teamwork.R;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

public class MainMineinFragment extends Fragment {
    private Button button_main_minein1;
    private XRecyclerView xrecyclerview_main_minein;
    private List<MineinBean> mineinList;
    private MineinAdapter mineinAdapter;
    private SoundPool soundpool_main_minein;
    private int music_main_minein;
    private int times=0;

    private MainMineinViewModel mViewModel;

    public static MainMineinFragment newInstance() {
        return new MainMineinFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        Bmob.initialize(this.getContext(),"22859591a0207200359b133208256c5a");
        return inflater.inflate(R.layout.fragment_main_minein, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(MainMineinViewModel.class);
        // TODO: Use the ViewModel
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mineinList=new ArrayList<MineinBean>();
        xrecyclerview_main_minein=view.findViewById(R.id.xrecyclerview_main_minein);
        MainActivity.back=false;
        soundpool_main_minein =new SoundPool(10, AudioManager.STREAM_SYSTEM, 5);
        music_main_minein = soundpool_main_minein.load(this.getContext(),R.raw.sound1,1);//卡通爆裂声 许可:CC0 作者:病尉迟 来源:耳聆网 https://www.ear0.com/sound/11846
        button_main_minein1=view.findViewById(R.id.button_main_minein1);
        ((MainActivity)getActivity()).getMydrawerlayout().setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        xrecyclerview_main_minein.setArrowImageView(R.drawable.image_refresh_mineinout);
        xrecyclerview_main_minein.setRefreshProgressStyle(16);
        xrecyclerview_main_minein.setLoadingMoreEnabled(false);
        button_main_minein1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavController navcontroller_inback= Navigation.findNavController(v);
                navcontroller_inback.navigateUp();
                /////////////////////////////
                /////////////////////////////
                //////////////////////////////
                ///////////////////////////////
            }
        });

        SharedPreferences sharedPreferences=getActivity().getSharedPreferences("user_info", Activity.MODE_PRIVATE);
        String id=sharedPreferences.getString("id","");

        xrecyclerview_main_minein.setHasFixedSize(true);

        times=0;

        if(mineinList!=null){
            mineinList.clear();
        }
        mineinList=new ArrayList<MineinBean>();
        BmobQuery<Order> query=new BmobQuery<Order>();
        query.addWhereEqualTo("substituteID",id);
        query.setLimit(50);
        query.order("-createdAt").findObjects(new FindListener<Order>() {
            @Override
            public void done(List<Order> list, BmobException e) {
                if(e==null){
                    for(Order order:list){
                        MineinBean info=new MineinBean();
                        info.setExpressNumber(order.getExpressNumber());
                        info.setFacility(order.getFacility());
                        info.setPickupNumber(order.getPickupNumber());
                        info.setReceiveAddress(order.getReceiveAddress());
                        info.setReceiveName(order.getReceiveName());
                        info.setReceivePhone(order.getReceivePhone());
                        info.setObjectId(order.getObjectId());
                        info.setState(order.getState());
                        info.setSubstituteID(order.getSubstituteID());
                        mineinList.add(info);
                    }
                    xrecyclerview_main_minein.setLayoutManager(new LinearLayoutManager(getContext()));
                    LayoutAnimationController controller =
                            AnimationUtils.loadLayoutAnimation(getContext(), R.anim.layout_animator_mineinout);
                    xrecyclerview_main_minein.setLayoutAnimation(controller);
                    mineinAdapter=new MineinAdapter(getContext(),mineinList);
                    xrecyclerview_main_minein.setAdapter(mineinAdapter);
                }
            }
        });




        xrecyclerview_main_minein.setLoadingListener(new XRecyclerView.LoadingListener() {
            @TargetApi(Build.VERSION_CODES.P)
            @Override
            public void onRefresh() {

                times=0;
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if(mineinList!=null){
                            mineinList.clear();
                        }
                        mineinList=new ArrayList<MineinBean>();
                        BmobQuery<Order> query=new BmobQuery<Order>();
                        query.addWhereEqualTo("substituteID",id);
                        query.setLimit(50);
                        query.order("-createdAt").findObjects(new FindListener<Order>() {
                            @Override
                            public void done(List<Order> list, BmobException e) {
                                if(e==null){
                                    for(Order order:list){
                                        MineinBean info=new MineinBean();
                                        info.setExpressNumber(order.getExpressNumber());
                                        info.setFacility(order.getFacility());
                                        info.setPickupNumber(order.getPickupNumber());
                                        info.setReceiveAddress(order.getReceiveAddress());
                                        info.setReceiveName(order.getReceiveName());
                                        info.setReceivePhone(order.getReceivePhone());
                                        info.setObjectId(order.getObjectId());
                                        info.setState(order.getState());
                                        info.setSubstituteID(order.getSubstituteID());
                                        mineinList.add(info);
                                    }
                                    xrecyclerview_main_minein.setLayoutManager(new LinearLayoutManager(getContext()));
                                    LayoutAnimationController controller =
                                            AnimationUtils.loadLayoutAnimation(getContext(), R.anim.layout_animator_refresh);
                                    xrecyclerview_main_minein.setLayoutAnimation(controller);
                                    mineinAdapter=new MineinAdapter(getContext(),mineinList);
                                    xrecyclerview_main_minein.setAdapter(mineinAdapter);
                                }
                            }
                        });
                        soundpool_main_minein.play(music_main_minein,1,1,0,0,1);
                        xrecyclerview_main_minein.refreshComplete();
                        Toast.makeText(getActivity(),"刷新成功",Toast.LENGTH_SHORT).show();
                    }
                },800);

            }

            @Override
            public void onLoadMore() {
            }


        });




    }
}
