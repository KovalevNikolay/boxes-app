package ru.kovalev.boxesapp.interfaces;

public interface Mapper <I, O> {
    O mapFrom(I input);
}