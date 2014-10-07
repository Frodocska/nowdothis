package com.makeramen.nowdothis;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import javax.inject.Inject;

public class TodoFragment extends Fragment {

  @Inject PreferenceHelper preferenceHelper;
  @InjectView(R.id.itemtext) TextView itemText;
  @InjectView(R.id.btn_editlist) TextView editListBtn;
  @InjectView(R.id.btn_done) Button doneButton;

  @Override public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_todo, container, false);
    ButterKnife.inject(this, view);
    return view;
  }

  @Override public void onActivityCreated(@Nullable Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);
    NowDoThisApp.inject(this, getActivity());
  }

  @Override public void onResume() {
    super.onResume();

    String[] todos = preferenceHelper.getTodos();
    updateUI(todos.length > 0 ? todos[0] : null);
  }

  void updateUI(@Nullable String todo) {
    if (todo != null) {
      if (!todo.endsWith(".")) { todo = todo + "."; }
      itemText.setText(todo);
      itemText.setTextColor(getResources().getColor(R.color.black));
    } else {
      itemText.setText(R.string.all_done);
      itemText.setTextColor(getResources().getColor(R.color.green));
      doneButton.setVisibility(View.INVISIBLE);
    }
  }

  @OnClick(R.id.btn_done) void doneClick() {
    updateUI(preferenceHelper.popList());
  }

  @OnClick(R.id.btn_editlist) void editList() {
    getFragmentManager().beginTransaction()
        .replace(getId(), new EditListFragment())
        .commit();
  }
}
