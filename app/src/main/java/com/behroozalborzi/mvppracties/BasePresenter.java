package com.behroozalborzi.mvppracties;

public interface BasePresenter<T extends BaseView> {


    void onAttach(T view);

    void onDetach();

}
