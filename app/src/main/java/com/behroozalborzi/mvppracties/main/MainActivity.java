package com.behroozalborzi.mvppracties.main;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.behroozalborzi.mvppracties.databinding.ActivityMainBinding;
import com.behroozalborzi.mvppracties.detail.TaskDetailActivity;
import com.behroozalborzi.mvppracties.model.AppDatabase;
import com.behroozalborzi.mvppracties.model.Task;
import com.behroozalborzi.mvppracties.model.TaskAdapter;

import java.util.List;


public class MainActivity extends AppCompatActivity implements MainContract.View, TaskAdapter.TaskItemEventListener {
    private static final int REQUEST_CODE = 1810;
    public static final String EXTRA_ADD_TASK ="task";
    public static final int RESULT_CODE_ADD_TASK =1201;
    public static final int RESULT_CODE_UPDATE_TASK =1202;
    public static final int RESULT_CODE_DELETE_TASK =1203;
    private ActivityMainBinding binding;
    private MainContract.Presenter presenter;
    private TaskAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        presenter = new MainPresenter(AppDatabase.getAppDatabase(this).getTaskDao());
        adapter = new TaskAdapter(this,this);

        binding.addNewTaskBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                presenter.saveTaskClicked();
                Intent intent = new Intent(MainActivity.this, TaskDetailActivity.class);
                startActivityForResult(intent,REQUEST_CODE);

            }
        });

        binding.taskListRv.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        binding.taskListRv.setAdapter(adapter);
        presenter.onAttach(this);
        binding.searchEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                presenter.search(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        binding.deleteAllBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.deleteAllButtonClicked();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == MainActivity.REQUEST_CODE){

            if (resultCode==MainActivity.RESULT_CODE_ADD_TASK || resultCode==MainActivity.RESULT_CODE_UPDATE_TASK|| resultCode==MainActivity.RESULT_CODE_DELETE_TASK && data != null){

                Task task = data.getParcelableExtra(MainActivity.EXTRA_ADD_TASK);
                if (task!= null){

                    if (resultCode==MainActivity.RESULT_CODE_ADD_TASK){
                        addTask(task);
                    }else if (resultCode==MainActivity.RESULT_CODE_UPDATE_TASK){
                        updateTask(task);
                    }else {
                        deleteTask(task);
                    }

                    setEmptyStateVisibility(adapter.getItemCount()==0);

                }


            }

        }


    }

    @Override
    public void showTasks(List<Task> taskList) {
        adapter.setTasks(taskList);
    }

    @Override
    public void clearAllTask() {
        adapter.clearItems();
    }

    @Override
    public void addTask(Task task) {
        adapter.addItem(task);
    }

    @Override
    public void updateTask(Task task) {
        adapter.updateItem(task);
    }

    @Override
    public void deleteTask(Task task) {
        adapter.deleteItem(task);
    }

    @Override
    public void setEmptyStateVisibility(boolean visible) {
        binding.emptyState.setVisibility(visible?View.VISIBLE:View.GONE);
    }

    @Override
    public void onClick(Task task) {
        presenter.onItemClicked(task);
    }

    @Override
    public void onLongClick(Task task) {
        Intent intent = new Intent(MainActivity.this,TaskDetailActivity.class);
        intent.putExtra(MainActivity.EXTRA_ADD_TASK,task);
        startActivityForResult(intent,MainActivity.REQUEST_CODE);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.onDetach();
    }
}
