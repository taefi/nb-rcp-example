package com.example.abstraction;

public interface CrudFormObserver<T> {

	void selectedRowChanged(RowSelectionChangeEvent<T> event);
	
	void setGridObservable(CrudGridObservable<T> gridObservable);
}
