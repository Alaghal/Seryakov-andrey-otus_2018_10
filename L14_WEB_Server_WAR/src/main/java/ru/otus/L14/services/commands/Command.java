package ru.otus.L14.services.commands;

import java.util.concurrent.ExecutionException;

public interface Command<T> {
    public T exec () throws ExecutionException, InterruptedException;
}
