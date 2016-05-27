package com.stride.android.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import butterknife.ButterKnife;
import com.stride.android.R;

/**
 * Created by connormcfadden on 27/05/16.
 */
public class TrophyFragment extends BaseFragment {

  @Override public void onActivityCreated(@Nullable Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);
    setHasOptionsMenu(true);
  }

  @Override public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    View fragmentView = inflater.inflate(R.layout.fragment_trophy, container, false);
    ButterKnife.bind(this, fragmentView);
    return fragmentView;
  }
}
