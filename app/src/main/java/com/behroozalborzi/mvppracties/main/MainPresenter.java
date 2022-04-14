package com.behroozalborzi.mvppracties.main;

import com.behroozalborzi.mvppracties.model.Task;
import com.behroozalborzi.mvppracties.model.TaskDao;

import java.util.List;

public class MainPresenter implements MainContract.Presenter {

    private TaskDao taskDao;
    private MainContract.View view;
    private List<Task> taskList;
    public MainPresenter(TaskDao taskDao) {
        this.taskDao = taskDao;
        this.taskList = taskDao.getAll();
    }

    @Override
    public void onAttach(MainContract.View view) {
        this.view = view;

        if (!taskList.isEmpty()){
            view.setEmptyStateVisibility(false);
            view.showTasks(taskList);
        }else{
            view.setEmptyStateVisibility(true);
        }

    }

    @Override
    public void onDetach() {

    }

    @Override
    public void deleteAllButtonClicked() {
        taskDao.deleteAll();
        view.clearAllTask();
        view.setEmptyStateVisibility(true);
    }

    @Override
    public void search(String query) {
       ;
        if (!query.isEmpty()){
            List<Task> taskList = taskDao.search(query);
            view.showTasks(taskList);
        }else {
            List<Task> taskList = taskDao.getAll();
            view.showTasks(taskList);
        }
    }

    @Override
    public void onItemClicked(Task task) {

        task.setCompleted(!task.isCompleted());

        int result = taskDao.update(task);
        if (result>0){
            view.updateTask(task);
        }


    }

//    @Override
//    public void saveTaskClicked() {
//
//    }
}
