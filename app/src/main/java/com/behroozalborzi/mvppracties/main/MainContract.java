package com.behroozalborzi.mvppracties.main;

import com.behroozalborzi.mvppracties.BasePresenter;
import com.behroozalborzi.mvppracties.BaseView;
import com.behroozalborzi.mvppracties.model.Task;

import java.util.List;

public interface MainContract {

    interface View extends BaseView {

        void showTasks(List<Task> taskList);

        void clearAllTask();

        void addTask(Task task);

        void updateTask(Task task);

        void deleteTask(Task task);

        void setEmptyStateVisibility(boolean visible);

    }

    interface Presenter extends BasePresenter<View> {


        void deleteAllButtonClicked();

        void search(String query);

        void onItemClicked(Task task);

//        void saveTaskClicked();

    }


}
