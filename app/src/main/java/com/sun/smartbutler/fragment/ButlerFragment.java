package com.sun.smartbutler.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SpeechSynthesizer;
import com.iflytek.cloud.SynthesizerListener;
import com.iflytek.cloud.util.ResourceUtil;
import com.sun.smartbutler.R;
import com.sun.smartbutler.adapter.ChatLiatAdapter;
import com.sun.smartbutler.entity.ChatListData;
import com.sun.smartbutler.ui.SettingActivity;
import com.sun.smartbutler.utils.ShareUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 项目名:   SmartButler
 * 包名:     com.sun.smartbutler.fragment
 * 文件名:   ButlerFragment
 * 创建者:   sun
 * 创建时间: 2019/6/2 0002 10:11
 * 描述:    智能机器人小管家
 */
public class ButlerFragment extends Fragment implements View.OnClickListener {

    private List<ChatListData> listDatas=new ArrayList<>();
    private ListView listView;
    private Button leftBtn,rightBtn;
    private ChatLiatAdapter adapter;

    //语音播报
    private SpeechSynthesizer mTts;

    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container,Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_butler,null);
        findView(view);
        return view;
    }

    //初始化
    private void findView(View view) {

        //1.创建SpeechSynthesizer对象, 第二个参数：本地合成时传InitListener
        mTts = SpeechSynthesizer.createSynthesizer(getContext(), null);
        //2.合成参数设置，详见《科大讯飞MSC API手册(Android)》SpeechSynthesizer 类
        mTts.setParameter(SpeechConstant.VOICE_NAME, "xiaoyan");//设置发音人
        mTts.setParameter(SpeechConstant.SPEED, "50");//设置语速
        mTts.setParameter(SpeechConstant.VOLUME, "80");//设置音量，范围0~100
        mTts.setParameter(SpeechConstant.ENGINE_TYPE, SpeechConstant.TYPE_CLOUD); //设置云端
        //设置合成音频保存位置（可自定义保存位置），保存在“./sdcard/iflytek.pcm”
        //保存在SD卡需要在AndroidManifest.xml添加写SD卡权限
        //如果不需要保存合成音频，注释该行代码
        //mTts.setParameter(SpeechConstant.TTS_AUDIO_PATH, "./sdcard/iflytek.pcm");

        leftBtn= (Button) view.findViewById(R.id.btn_left);
        rightBtn= (Button) view.findViewById(R.id.btn_right);
        leftBtn.setOnClickListener(this);
        rightBtn.setOnClickListener(this);
        listView= (ListView) view.findViewById(R.id.mListView);

        //设置适配器
        adapter = new ChatLiatAdapter(getContext(),listDatas);
        listView.setAdapter(adapter);
        //默认
         addLeftItem("你好,我是小管家");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_left:
                addLeftItem("你好");
                break;
            case R.id.btn_right:
                addRightItem("你也好");
                break;
        }
    }

    private void addRightItem(String text) {
        ChatListData chatListData=new ChatListData();
        chatListData.setType(ChatLiatAdapter.RIGHT_TYPE);
        chatListData.setText(text);
        listDatas.add(chatListData);
        //刷新
        adapter.notifyDataSetChanged();
        //滚动到底部
        listView.setSelection(listView.getBottom());
    }

    private void addLeftItem(String text) {

        //获取状态
        boolean saveSwitch = ShareUtils.getBoolean(getContext(), "save_switch", false);
        if (saveSwitch){
            startSpeak(text);
        }
        ChatListData chatListData=new ChatListData();
        chatListData.setType(ChatLiatAdapter.LEFT_TYPE);
        chatListData.setText(text);
        listDatas.add(chatListData);
        //刷新
        adapter.notifyDataSetChanged();
        //滚动到底部
        listView.setSelection(listView.getBottom());
    }

    //开始说话
    private void startSpeak(String text) {
        //3.开始合成
        mTts.startSpeaking(text, mSynListener);
    }
    //合成监听器
    private SynthesizerListener mSynListener = new SynthesizerListener() {
        //会话结束回调接口，没有错误时，error为null
        public void onCompleted(SpeechError error) {
        }

        //缓冲进度回调
        //percent为缓冲进度0~100，beginPos为缓冲音频在文本中开始位置，endPos表示缓冲音频在文本中结束位置，info为附加信息。
        public void onBufferProgress(int percent, int beginPos, int endPos, String info) {
        }

        //开始播放
        public void onSpeakBegin() {
        }

        //暂停播放
        public void onSpeakPaused() {
        }

        //播放进度回调
        //percent为播放进度0~100,beginPos为播放音频在文本中开始位置，endPos表示播放音频在文本中结束位置.
        public void onSpeakProgress(int percent, int beginPos, int endPos) {
        }

        //恢复播放回调接口
        public void onSpeakResumed() {
        }

        //会话事件回调接口
        public void onEvent(int arg0, int arg1, int arg2, Bundle arg3) {
        }
    };

}
