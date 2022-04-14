package com.behroozalborzi.mvppracties.detail;

import com.behroozalborzi.mvppracties.main.MainActivity;
import com.behroozalborzi.mvppracties.model.Task;
import com.behroozalborzi.mvppracties.model.TaskDao;

public class TaskDetailPresenter implements TaskDetailContract.Presenter{

    private TaskDao taskDao;
    private TaskDetailContract.View view;
    private Task task;

    public TaskDetailPresenter(TaskDao taskDao,Task task) {
        this.taskDao = taskDao;
        this.task = task;
    }

    @Override
    public void onAttach(TaskDetailContract.View view) {
        this.view = view;
        if (task!= null){
            view.setDeleteButtonStateVisibility(true);
            view.setTextTitleStateVisibility(true);
            view.editTask(task);

        }else {
            view.setDeleteButtonStateVisibility(false);
            view.setTextTitleStateVisibility(false);
        }
    }

    @Override
    public void onDetach() {

    }

    @Override
    public void deleteButtonClicked() {

        if (task!=null){

           int result = taskDao.delete(task);

           if (result>0){

               view.returnResult(MainActivity.RESULT_CODE_DELETE_TASK,task);

           }

        }



    }

    @Override
    public void saveButtonClicked(int important, String title) {
        if (title.isEmpty()){

            view.showError("Enter title Task ...");
            return;

        }

        if (task== null){

            Task task = new Task();
            task.setTitle(title);
            task.setImportance(important);
            long id = taskDao.add(task);

            task.setId(id);

            view.returnResult(MainActivity.RESULT_CODE_ADD_TASK,task);

        }else {
            task.setTitle(title);
            task.setImportance(important);
           int result = taskDao.update(task);

           if (result>0){

               view.returnResult(MainActivity.RESULT_CODE_UPDATE_TASK,task);
           }

        }
    }
}
