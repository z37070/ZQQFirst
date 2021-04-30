package com.example.teamwork.Main.MainMine.Mainmineout;

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

public class MainMineoutFragment extends Fragment {
    private Button button_main_mineout1;
    private XRecyclerView xrecyclerview_main_mineout;
    private List<MineoutBean> mineoutList;
    private MineoutAdapter mineoutAdapter;
    private SoundPool soundpool_main_mineout;
    private int music_main_mineout;
    private int times=0;


    private MainMineoutViewModel mViewModel;



    public static MainMineoutFragment newInstance() {
        return new MainMineoutFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        Bmob.initialize(this.getContext(),"22859591a0207200359b133208256c5a");
        return inflater.inflate(R.layout.fragment_main_mineout, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(MainMineoutViewModel.class);
        // TODO: Use the ViewModel
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mineoutList=new ArrayList<MineoutBean>();
        xrecyclerview_main_mineout=view.findViewById(R.id.xrecyclerview_main_mineout);
        MainActivity.back=false;
        soundpool_main_mineout =new SoundPool(10, AudioManager.STREAM_SYSTEM, 5);
        music_main_mineout = soundpool_main_mineout.load(this.getContext(),R.raw.sound1,1);//卡通爆裂声 许可:CC0 作者:病尉迟 来源:耳聆网 https://www.ear0.com/sound/11846
        button_main_mineout1=view.findViewById(R.id.button_main_mineout1);
        ((MainActivity)getActivity()).getMydrawerlayout().setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        xrecyclerview_main_mineout.setArrowImageView(R.drawable.image_refresh_mineinout);
        xrecyclerview_main_mineout.setRefreshProgressStyle(16);
        xrecyclerview_main_mineout.setLoadingMoreEnabled(false);
        button_main_mineout1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavController navcontroller_outback= Navigation.findNavController(v);
                navcontroller_outback.navigateUp();

            }
        });

        SharedPreferences sharedPreferences= getActivity().getSharedPreferences("user_info", Activity.MODE_PRIVATE);
        String id=sharedPreferences.getString("id","");
        xrecyclerview_main_mineout.setHasFixedSize(true);
        times=0;
        if(mineoutList!=null){
            mineoutList.clear();
        }
        mineoutList=new ArrayList<MineoutBean>();
        BmobQuery<Order> query=new BmobQuery<Order>();
        query.addWhereEqualTo("receiveID",id);
        query.setLimit(50);
        query.order("-createdAt").findObjects(new FindListener<Order>() {
            @Override
            public void done(List<Order> list, BmobException e) {
                if(e==null){
                    for(Order object:list){
                        MineoutBean info=new MineoutBean();
                        info.setExpressNumber(object.getExpressNumber());
                        info.setSubstituteID(object.getSubstituteID());
                        info.setSubstitutePhone(object.getSubstitutePhone());
                        info.setState(object.getState());
                        info.setObjectId(object.getObjectId());
                        info.setReceiveID(object.getReceiveID());
                        mineoutList.add(info);
                    }
                    xrecyclerview_main_mineout.setLayoutManager(new LinearLayoutManager(getContext()));
                    LayoutAnimationController controller =
                            AnimationUtils.loadLayoutAnimation(getContext(), R.anim.layout_animator_mineinout);
                    xrecyclerview_main_mineout.setLayoutAnimation(controller);
                    mineoutAdapter=new MineoutAdapter(getContext(),mineoutList);
                    xrecyclerview_main_mineout.setAdapter(mineoutAdapter);
                }
            }
        });


        xrecyclerview_main_mineout.setLoadingListener(new XRecyclerView.LoadingListener() {
            @TargetApi(Build.VERSION_CODES.P)
            @Override
            public void onRefresh() {
                times=0;
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if(mineoutList!=null){
                            mineoutList.clear();
                        }
                        mineoutList=new ArrayList<MineoutBean>();
                        BmobQuery<Order> query=new BmobQuery<Order>();
                        //query.addWhereEqualTo("ReceiveName",id);
                        query.addWhereEqualTo("receiveID",id);
                        query.setLimit(50);
                        query.order("-createdAt").findObjects(new FindListener<Order>() {
                            @Override
                            public void done(List<Order> list, BmobException e) {
                                if(e==null){
                                    for(Order object:list){
                                        MineoutBean info=new MineoutBean();
                                        info.setExpressNumber(object.getExpressNumber());
                                        info.setSubstituteID(object.getSubstituteID());
                                        info.setSubstitutePhone(object.getSubstitutePhone());
                                        info.setState(object.getState());
                                        info.setObjectId(object.getObjectId());
                                        mineoutList.add(info);
                                    }
                                }
                                xrecyclerview_main_mineout.setLayoutManager(new LinearLayoutManager(getContext()));
                                LayoutAnimationController controller =
                                        AnimationUtils.loadLayoutAnimation(getContext(), R.anim.layout_animator_refresh);
                                xrecyclerview_main_mineout.setLayoutAnimation(controller);
                                mineoutAdapter=new MineoutAdapter(getContext(),mineoutList);
                                xrecyclerview_main_mineout.setAdapter(mineoutAdapter);
                            }
                        });
                        soundpool_main_mineout.play(music_main_mineout,1,1,0,0,1);
                        xrecyclerview_main_mineout.refreshComplete();
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
