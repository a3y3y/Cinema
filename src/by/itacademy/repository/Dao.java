package by.itacademy.repository;

import by.itacademy.exception.OperationException;

import java.util.List;

public interface Dao<T> {
    boolean create(T t) throws OperationException;

    T update(String param, T t) throws OperationException;

    T read(int id) throws OperationException;

    boolean delete(T t) throws OperationException;

    List<T> readAll() throws OperationException;
}
