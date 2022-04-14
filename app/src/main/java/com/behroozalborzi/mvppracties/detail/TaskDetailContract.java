package com.behroozalborzi.mvppracties.detail;

import com.behroozalborzi.mvppracties.BasePresenter;
import com.behroozalborzi.mvppracties.BaseView;
import com.behroozalborzi.mvppracties.model.Task;

public interface TaskDetailContract {

    interface View extends BaseView {

        void editTask(Task task);

        void showError(String messageError);

        void setDeleteButtonStateVisibility(boolean visible);

        void setTextTitleStateVisibility(boolean visible);

        void returnResult(int resultCode,Task task);

    }

    interface Presenter extends BasePresenter<View>{

        void deleteButtonClicked();

        void saveButtonClicked(int important,String title);

    }

}
