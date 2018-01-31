package com.fanwe.shortvideo.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.fanwe.baimei.appview.BMRoomSendGiftView;
import com.fanwe.hybrid.fragment.BaseFragment;
import com.fanwe.hybrid.umeng.UmengSocialManager;
import com.fanwe.library.listener.SDViewVisibilityCallback;
import com.fanwe.library.utils.SDViewUtil;
import com.fanwe.library.view.CircleImageView;
import com.fanwe.live.R;
import com.fanwe.live.activity.LiveUserHomeActivity;
import com.fanwe.live.appview.room.RoomGiftGifView;
import com.fanwe.live.utils.GlideUtil;
import com.fanwe.shortvideo.model.ShortVideoDetailModel;

/**
 * @author wxy
 * Created by wxy on 2018/1/20.
 */

public class VideoDetailContainerFragment extends BaseFragment implements View.OnClickListener {
    private CircleImageView iv_head_image;
    private TextView tv_user_name;
    private TextView tv_follow;
    private RelativeLayout rel_bottom_view;
    private TextView tv_msg;
    private TextView tv_praise_num;
    private TextView tv_gift_num;
    private ImageView img_share;
    private ImageView img_close;
    private FrameLayout fl_msg;
    private FrameLayout fl_gift_play;
    private FrameLayout fl_gift;
    private RoomGiftGifView mRoomGiftGifView;
    private BMRoomSendGiftView mRoomSendGiftView;

    private ShortVideoDetailModel.VideoDetail detailModel;

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_head_image:
                Intent intent = new Intent(getActivity(), LiveUserHomeActivity.class);
                intent.putExtra(LiveUserHomeActivity.EXTRA_USER_ID, detailModel.user_id);
                getActivity().startActivity(intent);
                getActivity().finish();
                break;
            case R.id.tv_follow:
                openShare();
                break;
            case R.id.tv_praise_num:
                openShare();
                break;
            case R.id.tv_gift_num:
                addRoomSendGiftView();
                mRoomSendGiftView.getVisibilityHandler().setVisible(true);
                break;
            case R.id.img_share:
                openShare();
                break;
            case R.id.img_close:
                getActivity().finish();
                break;
            default:
                break;
        }

    }

    private void openShare() {
        UmengSocialManager.openShare("车到山前必有路，来到映秀刹不住", detailModel.sv_content, detailModel.sv_img,
                detailModel.sv_url, getActivity(), null);
    }


    public static VideoDetailContainerFragment newInstance() {
        return new VideoDetailContainerFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_video_detail_container, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        iv_head_image = (CircleImageView) view.findViewById(R.id.iv_head_image);
        tv_user_name = (TextView) view.findViewById(R.id.tv_user_name);
        tv_follow = (TextView) view.findViewById(R.id.tv_follow);
        rel_bottom_view = (RelativeLayout) view.findViewById(R.id.rel_bottom_view);
        tv_msg = (TextView) view.findViewById(R.id.tv_msg);
        tv_praise_num = (TextView) view.findViewById(R.id.tv_praise_num);
        tv_gift_num = (TextView) view.findViewById(R.id.tv_gift_num);
        img_share = (ImageView) view.findViewById(R.id.img_share);
        img_close = (ImageView) view.findViewById(R.id.img_close);
        fl_msg = (FrameLayout) view.findViewById(R.id.fl_msg);
        fl_gift_play = (FrameLayout) view.findViewById(R.id.fl_gift_play);
        fl_gift = (FrameLayout) view.findViewById(R.id.fl_gift);
        initListener();
    }

    private void initListener() {
        iv_head_image.setOnClickListener(this);
        tv_follow.setOnClickListener(this);
        tv_praise_num.setOnClickListener(this);
        tv_gift_num.setOnClickListener(this);
        img_share.setOnClickListener(this);
        img_close.setOnClickListener(this);

    }

    /**
     * 进入提示
     */
    protected void addMsgView() {
//        if (mRoomViewerJoinRoomView == null) {
//            mRoomViewerJoinRoomView = new RoomViewerJoinRoomView(this);
//            SDViewUtil.replaceView(R.id.fl_msg, child);
//        }
    }
    /**
     * 送礼物
     */
    protected void addRoomSendGiftView() {
        if (mRoomSendGiftView == null) {
            mRoomSendGiftView = new BMRoomSendGiftView(getActivity());
            SDViewUtil.setInvisible(mRoomSendGiftView);
            mRoomSendGiftView.getVisibilityHandler().addVisibilityCallback(new SDViewVisibilityCallback() {
                @Override
                public void onViewVisibilityChanged(View view, int visibility) {
                    if (View.VISIBLE != visibility) {
                        SDViewUtil.removeView(mRoomSendGiftView);
                        rel_bottom_view.setVisibility(View.VISIBLE);
                    }else {
                        rel_bottom_view.setVisibility(View.GONE);
                    }
                }
            });
        }
        mRoomSendGiftView.bindData();
        replaceView(R.id.fl_gift, mRoomSendGiftView);
    }

    /**
     * 送礼物动画效果
     */
    protected void addRoomGiftGifView() {
        if (mRoomGiftGifView == null) {
            mRoomGiftGifView = new RoomGiftGifView(getActivity());
            replaceView(R.id.fl_gift,mRoomGiftGifView);
        }
    }

    public void updataData(ShortVideoDetailModel.VideoDetail detail) {
        detailModel = detail;
        GlideUtil.load(detail.head_image).into(iv_head_image);
        tv_user_name.setText(detail.nick_name);
        tv_msg.setText(detail.count_comment);
        tv_praise_num.setText(detail.count_praise);
        //TODO 礼物个数字段
        tv_gift_num.setText(detail.count_praise);

    }

    private void replaceView(int parentId, View child){
        SDViewUtil.replaceView((ViewGroup) findViewById(parentId), child);
    }

}