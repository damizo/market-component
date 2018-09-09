package com.popielarski.market.common.domain;

import com.popielarski.market.common.utils.RandomGenerator;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;


//TODO: to improve
public class InMemoryRepository<E> implements JpaRepository<E, Long> {
    protected ConcurrentHashMap<Long, E> map = new ConcurrentHashMap<>();

    @Override
    public <S extends E> S save(S s) {
        Long id = RandomGenerator
                .generateDecimal(1, 25000)
                .longValue();
        map.put(id, s);
        return (S) map.get(id);
    }

    public void save(Long identifier, E e) {
        map.put(identifier, e);
    }

    @Override
    public Optional<E> findById(Long id) {
        return Optional.ofNullable(
                map.get(id)
        );
    }

    @Override
    public boolean existsById(Long id) {
        return map.entrySet()
                .stream()
                .anyMatch((entry) -> entry.getKey().equals(id));
    }

    @Override
    public List<E> findAll() {
        return map.entrySet()
                .stream()
                .map(Map.Entry::getValue)
                .collect(Collectors.toList());
    }

    @Override
    public List<E> findAll(Sort sort) {
        return null;
    }

    @Override
    public Page<E> findAll(Pageable pageable) {
        List<E> all = findAll();
        return new PageImpl<>(all, pageable, all.size());
    }

    @Override
    public List<E> findAllById(Iterable<Long> iterable) {
        return null;
    }

    @Override
    public long count() {
        return 0;
    }

    @Override
    public void deleteById(Long id) {
        map.remove(id);
    }

    @Override
    public void delete(E e) {

    }

    @Override
    public void deleteAll(Iterable<? extends E> iterable) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void deleteAll() {
        throw new UnsupportedOperationException();
    }


    @Override
    public void flush() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void deleteInBatch(Iterable<E> iterable) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void deleteAllInBatch() {
        throw new UnsupportedOperationException();
    }

    @Override
    public E getOne(Long id) {
        return null;
    }

    @Override
    public <S extends E> List<S> findAll(Example<S> example, Sort sort) {
        throw new UnsupportedOperationException();
    }

    @Override
    public <S extends E> List<S> findAll(Example<S> example) {
        throw new UnsupportedOperationException();
    }

    @Override
    public <S extends E> S saveAndFlush(S s) {
        throw new UnsupportedOperationException();
    }

    @Override
    public <S extends E> List<S> saveAll(Iterable<S> iterable) {
        throw new UnsupportedOperationException();
    }

    @Override
    public <S extends E> Optional<S> findOne(Example<S> example) {
        return Optional.empty();
    }

    @Override
    public <S extends E> Page<S> findAll(Example<S> example, Pageable pageable) {
        throw new UnsupportedOperationException();
    }

    @Override
    public <S extends E> long count(Example<S> example) {
        throw new UnsupportedOperationException();
    }

    @Override
    public <S extends E> boolean exists(Example<S> example) {
        throw new UnsupportedOperationException();
    }
}
