package humazed.github.com.smartbaking_java.ui.step_details;

import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.media.session.MediaSessionCompat;
import android.support.v4.media.session.PlaybackStateCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.PlaybackParameters;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import humazed.github.com.smartbaking_java.R;
import humazed.github.com.smartbaking_java.model.Step;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;
import static humazed.github.com.smartbaking_java.utils.SystemUtils.hideSystemUI;

public class StepDetailFragment extends Fragment implements ExoPlayer.EventListener {
    private static final String TAG = StepDetailFragment.class.getSimpleName();

    public static final String ARG_STEPS = "StepDetailFragment:mSteps";
    public static final String ARG_POSITION = "StepDetailFragment:mPosition";

    @BindView(R.id.exoPlayerView) SimpleExoPlayerView mExoPlayerView;
    @BindView(R.id.stepDescriptionTextView) TextView mStepDescriptionTextView;
    @BindView(R.id.nextFab) FloatingActionButton mNextFab;
    @BindView(R.id.previousFab) FloatingActionButton mPreviousFab;

    Unbinder unbinder;
    int mPosition;
    private ArrayList<Step> mSteps;

    SimpleExoPlayer mExoPlayer = null;
    MediaSessionCompat mMediaSession = null;
    PlaybackStateCompat.Builder mStateBuilder = null;


    public static StepDetailFragment newInstance(ArrayList<Step> steps, int position) {
        Bundle args = new Bundle();
        args.putParcelableArrayList(ARG_STEPS, steps);
        args.putInt(ARG_POSITION, position);
        StepDetailFragment fragment = new StepDetailFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public static void start(FragmentActivity activity, ArrayList<Step> steps, int position) {
        activity.getSupportFragmentManager().beginTransaction()
                .add(R.id.recipe_detail_container, newInstance(steps, position), TAG)
                .commit();
    }

    public static void switchTo(FragmentActivity activity, ArrayList<Step> steps, int position) {
        activity.getSupportFragmentManager().beginTransaction()
                .replace(R.id.recipe_detail_container, newInstance(steps, position), TAG)
                .commit();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mPosition = getArguments().getInt(ARG_POSITION);
        mSteps = getArguments().getParcelableArrayList(ARG_STEPS);
        (((AppCompatActivity) getActivity())).getSupportActionBar()
                .setTitle(mSteps.get(mPosition).shortDescription());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = getLayoutInflater().inflate(R.layout.fragment_step_detail, container, false);
        unbinder = ButterKnife.bind(this, view);

        Step step = mSteps.get(mPosition);

        mStepDescriptionTextView.setText(step.description());

        if (mPosition == 0) mPreviousFab.setVisibility(View.GONE);
        else if (mPosition == mSteps.size() - 1) mNextFab.setVisibility(View.GONE);

        mNextFab.setOnClickListener(v -> switchTo(getActivity(), mSteps, step.id() + 1));
        mPreviousFab.setOnClickListener(v -> switchTo(getActivity(), mSteps, step.id() - 1));


        if (!TextUtils.isEmpty(step.videoURL())) {
            initializeMediaSession();
            initializePlayer(Uri.parse(step.videoURL()));
            if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE
                    && !getResources().getBoolean(R.bool.isTablet)) {
                hideSystemUI(((AppCompatActivity) getActivity()));
                mStepDescriptionTextView.setVisibility(View.GONE);
                mExoPlayerView.getLayoutParams().height = MATCH_PARENT;
                mExoPlayerView.getLayoutParams().width = MATCH_PARENT;
            } else mStepDescriptionTextView.setText(step.description());
        } else mExoPlayerView.setVisibility(View.GONE);

        return view;
    }


    private void initializePlayer(Uri mediaUri) {
        if (mExoPlayer == null) {
            // Create an instance of the ExoPlayer.
            mExoPlayer = ExoPlayerFactory.newSimpleInstance(getActivity(), new DefaultTrackSelector());
            mExoPlayerView.setPlayer(mExoPlayer);
            // Prepare the MediaSource.
            ExtractorMediaSource mediaSource = new ExtractorMediaSource(mediaUri, new DefaultDataSourceFactory(
                    getActivity(), Util.getUserAgent(getActivity(), getString(R.string.app_name))),
                    new DefaultExtractorsFactory(), null, null);
            mExoPlayer.addListener(this);
            mExoPlayer.prepare(mediaSource);
            mExoPlayer.setPlayWhenReady(true);
        }
    }

    private void initializeMediaSession() {
        mMediaSession = new MediaSessionCompat(getContext(), TAG);
        mMediaSession.setFlags(
                MediaSessionCompat.FLAG_HANDLES_MEDIA_BUTTONS |
                        MediaSessionCompat.FLAG_HANDLES_TRANSPORT_CONTROLS);

        mMediaSession.setMediaButtonReceiver(null);
        mStateBuilder = new PlaybackStateCompat.Builder()
                .setActions(
                        PlaybackStateCompat.ACTION_PLAY |
                                PlaybackStateCompat.ACTION_PAUSE |
                                PlaybackStateCompat.ACTION_SKIP_TO_PREVIOUS |
                                PlaybackStateCompat.ACTION_PLAY_PAUSE);

        mMediaSession.setPlaybackState(mStateBuilder.build());
        mMediaSession.setCallback(new MySessionCallback());
        mMediaSession.setActive(true);
    }

    @Override
    public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
        if ((playbackState == ExoPlayer.STATE_READY) && playWhenReady) {
            mStateBuilder.setState(PlaybackStateCompat.STATE_PLAYING,
                    mExoPlayer.getCurrentPosition(), 1f);
        } else if ((playbackState == ExoPlayer.STATE_READY)) {
            mStateBuilder.setState(PlaybackStateCompat.STATE_PAUSED,
                    mExoPlayer.getCurrentPosition(), 1f);
        }
        mMediaSession.setPlaybackState(mStateBuilder.build());
    }

    @Override
    public void onTimelineChanged(Timeline timeline, Object manifest) {}

    @Override
    public void onTracksChanged(TrackGroupArray trackGroups, TrackSelectionArray trackSelections) {}

    @Override
    public void onLoadingChanged(boolean isLoading) {}

    @Override
    public void onPlayerError(ExoPlaybackException error) {}

    @Override
    public void onPositionDiscontinuity() {}

    @Override
    public void onPlaybackParametersChanged(PlaybackParameters playbackParameters) { }

    @Override
    public void onPause() {
        super.onPause();
        if (mExoPlayer != null) {
            mExoPlayer.stop();
            mExoPlayer.release();
            mExoPlayer = null;
        }
    }

    private class MySessionCallback extends MediaSessionCompat.Callback {
        @Override
        public void onPlay() {
            mExoPlayer.setPlayWhenReady(true);
        }

        @Override
        public void onPause() {
            mExoPlayer.setPlayWhenReady(false);
        }

        @Override
        public void onSkipToPrevious() {
            mExoPlayer.seekTo(0);
        }

    }
}