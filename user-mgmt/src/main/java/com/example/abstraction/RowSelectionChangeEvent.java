package com.example.abstraction;

public record RowSelectionChangeEvent<T>(T oldSelected, T newSelected) {
}
