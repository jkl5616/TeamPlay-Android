package skku.teamplay.adapter;

import android.os.CountDownTimer;
import android.view.View;

import skku.teamplay.widget.AnimationUtil;
import skku.teamplay.widget.CircleCountdownView;

public class CountDownAdapter {
    CircleCountdownView[] mProgressView;//day, hour, min, secs
    long[] remainTime; //day, hour, min, secs
    private CountDownTimer[] countTimer = new CountDownTimer[4];
    private final int formDays = (1000 * 60 * 60 * 24);
    private int[] endTime = new int[4];
    private int timerIdx;

    public CountDownAdapter(CircleCountdownView[] view, long[] remainTime) {
        this.mProgressView= view;
        this.remainTime = remainTime;

        endTime[0] = 30;
        endTime[1] = 24;
        endTime[2] = endTime[3] = 60;
    }

    public CountDownAdapter(long[] remainTime) {
        this.remainTime= remainTime;

        endTime[0] = 30;
        endTime[1] = 24;
        endTime[2] = endTime[3] = 60;
    }

    public void setProgressView(CircleCountdownView[] views){
        this.mProgressView = views;
    }

    public void start(){
        showProgressBars();

        //Day
        mProgressView[0].setEndTime(30);
        mProgressView[0].setInitTime(remainTime[0]);
        mProgressView[0].setListener(new CircleCountdownView.OnFinishCycleProgressBar() {
            @Override
            public void onFinish() {
                if (countTimer[0] != null) countTimer[0].onFinish();
            }
        });

        //Hr
        mProgressView[1].setEndTime(24);
        mProgressView[1].setInitTime(remainTime[1]);
        mProgressView[1].setListener(new CircleCountdownView.OnFinishCycleProgressBar() {
            @Override
            public void onFinish() {
                if (countTimer[1] != null) countTimer[1].onFinish();
            }
        });

        //Min
        mProgressView[2].setEndTime(60);
        mProgressView[2].setInitTime(remainTime[2]);
        mProgressView[2].setListener(new CircleCountdownView.OnFinishCycleProgressBar() {
            @Override
            public void onFinish() {
                if (countTimer[2] != null) countTimer[2].onFinish();
            }
        });

        //Secs
        mProgressView[3].setEndTime(60);
        mProgressView[3].setInitTime(remainTime[3]);
        mProgressView[3].setListener(new CircleCountdownView.OnFinishCycleProgressBar() {
            @Override
            public void onFinish() {
                if (countTimer[3] != null) countTimer[3].onFinish();
            }
        });


        startCountDownTimerDay();
        startCountDownTimerHour();
        startCountDownTimerMin();
        startCountDownTimerSecond();
    }

    public void startCountDownTimerSecond() {
        //Why the final time is to long?
        //In Upcoming when a countdown depends on another countdown(the countdown minute needs to wait for the second countdown to finish to be able to count)
        // it is better to work with listener. So the final time is long only so that onFinish()
        // is called only on the listener's return, not when it actually arrives at the end of the final time

        countTimer[3] = new CountDownTimer(mProgressView[3].getEndTime() * formDays /*final time**/, 1000 /*interval**/) {
            @Override
            public void onTick(long millisUntilFinished) {
                mProgressView[3].onTick(mProgressView[3]);
            }

            @Override
            public void onFinish() {
                if (countTimer[2] != null) {
                    countTimer[2].cancel();
                }
                startCountDownTimerMin();
            }
        };
        countTimer[3].start();
    }


    public void startCountDownTimerMin() {
        countTimer[2] = new CountDownTimer(mProgressView[2].getEndTime() * formDays /*final time**/, formDays /*interval**/) {
            @Override
            public void onTick(long millisUntilFinished) {
                mProgressView[2].onTick(mProgressView[2]);
            }

            @Override
            public void onFinish() {
                if (countTimer[1] != null) {
                    countTimer[1].cancel();
                }
                startCountDownTimerHour();
            }
        };
        countTimer[2].start();
    }


    public void startCountDownTimerHour() {
        countTimer[1]= new CountDownTimer(mProgressView[1].getEndTime() * formDays /*final time**/, formDays /*interval**/) {
            @Override
            public void onTick(long millisUntilFinished) {
                mProgressView[1].onTick(mProgressView[1]);
            }

            @Override
            public void onFinish() {
                if (countTimer[0] != null) {
                    countTimer[0].cancel();
                }
                startCountDownTimerDay();
            }
        };
        countTimer[1].start();
    }


    public void startCountDownTimerDay() {
        countTimer[0] = new CountDownTimer(mProgressView[0].getEndTime() * formDays /*final time**/, formDays /*interval**/) {
            @Override
            public void onTick(long millisUntilFinished) {
                mProgressView[0].onTick(mProgressView[0]);
            }

            @Override
            public void onFinish() {
                this.start();
            }
        };
        countTimer[0].start();
    }



    private void showProgressBars() {
        //only for fade in
        AnimationUtil.with(mProgressView[3]).duration(300).animation(AnimationUtil.FADE_IN).visibility(View.VISIBLE).ready();
        AnimationUtil.with(mProgressView[2]).duration(300).animation(AnimationUtil.FADE_IN).visibility(View.VISIBLE).ready();
        AnimationUtil.with(mProgressView[1]).duration(300).animation(AnimationUtil.FADE_IN).visibility(View.VISIBLE).ready();
        AnimationUtil.with(mProgressView[0]).duration(300).animation(AnimationUtil.FADE_IN).visibility(View.VISIBLE).ready();
    }

}

