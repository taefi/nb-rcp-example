package com.example.model;

public record City(
	Integer id,
	String name
){
	@Override
	public final String toString() {
		return name;
	}
}
