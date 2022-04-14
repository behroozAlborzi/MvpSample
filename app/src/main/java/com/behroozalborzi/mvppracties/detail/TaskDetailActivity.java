package com.behroozalborzi.mvppracties.detail;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.behroozalborzi.mvppracties.R;
import com.behroozalborzi.mvppracties.databinding.ActivityEditTaskBinding;
import com.behroozalborzi.mvppracties.main.MainActivity;
import com.behroozalborzi.mvppracties.model.AppDatabase;
import com.behroozalborzi.mvppracties.model.Task;
import com.google.android.material.snackbar.Snackbar;


public class TaskDetailActivity extends AppCompatActivity implements TaskDetailContract.View{
    private int selectedImportance = Task.IMPORTANCE_NORMAL;
    private ImageView lastSelectedImportanceIv;
    private ActivityEditTaskBinding binding;
    private TaskDetailContract.Presenter presenter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityEditTaskBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        presenter = new TaskDetailPresenter(AppDatabase.getAppDatabase(this).getTaskDao(),getIntent().getParcelableExtra(MainActivity.EXTRA_ADD_TASK));


        binding.saveChangesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               presenter.saveButtonClicked(selectedImportance,binding.taskEt.getText().toString());
            }
        });

        binding.backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        lastSelectedImportanceIv = binding.normalImportanceBtn.findViewById(R.id.normalImportanceCheckIv);


        binding.highImportanceBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedImportance != Task.IMPORTANCE_HIGH) {
                    lastSelectedImportanceIv.setImageResource(0);
                    ImageView imageView = v.findViewById(R.id.highImportanceCheckIv);
                    imageView.setImageResource(R.drawable.ic_check_white_24dp);
                    selectedImportance = Task.IMPORTANCE_HIGH;

                    lastSelectedImportanceIv = imageView;
                }
            }
        });
        View lowImportanceBtn = findViewById(R.id.lowImportanceBtn);
        lowImportanceBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedImportance != Task.IMPORTANCE_LOW) {
                    lastSelectedImportanceIv.setImageResource(0);
                    ImageView imageView = v.findViewById(R.id.lowImportanceCheckIv);
                    imageView.setImageResource(R.drawable.ic_check_white_24dp);
                    selectedImportance = Task.IMPORTANCE_LOW;

                    lastSelectedImportanceIv = imageView;
                }
            }
        });

        binding.normalImportanceBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedImportance != Task.IMPORTANCE_NORMAL) {
                    lastSelectedImportanceIv.setImageResource(0);
                    ImageView imageView = v.findViewById(R.id.normalImportanceCheckIv);
                    imageView.setImageResource(R.drawable.ic_check_white_24dp);
                    selectedImportance = Task.IMPORTANCE_NORMAL;

                    lastSelectedImportanceIv = imageView;
                }
            }
        });

        presenter.onAttach(this);

        binding.deleteTaskBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.deleteButtonClicked();
                binding.taskEt.setText("");
            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.onDetach();
    }


    @Override
    public void editTask(Task task) {
        binding.taskEt.setText(task.getTitle());
        switch (task.getImportance()){
            case Task.IMPORTANCE_HIGH:
                binding.highImportanceBtn.performClick();
                break;
            case Task.IMPORTANCE_NORMAL:
                binding.normalImportanceBtn.performClick();
                break;
            case Task.IMPORTANCE_LOW:
                binding.lowImportanceBtn.performClick();
                break;
        }
    }

    @Override
    public void showError(String messageError) {
        Snackbar.make(binding.rootLayout,messageError,Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void setDeleteButtonStateVisibility(boolean visible) {
        binding.deleteTaskBtn.setVisibility(visible?View.VISIBLE:View.GONE);
    }

    @Override
    public void setTextTitleStateVisibility(boolean visible) {
        if (visible){
            binding.toolbarTitleTv.setText("Edit Task");
        }else {
            binding.toolbarTitleTv.setText("Add Task");
        }
    }

    @Override
    public void returnResult(int resultCode, Task task) {
        Intent intent = new Intent();
        intent.putExtra(MainActivity.EXTRA_ADD_TASK,task);
        setResult(resultCode,intent);
        finish();
    }
}
