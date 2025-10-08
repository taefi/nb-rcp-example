package com.example.abstraction;

public interface CrudGridObservable<T> {

	void addCrudFormObserver(CrudFormObserver<T> l);
	
	void refreshGrid();
}
