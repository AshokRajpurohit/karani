package com.ashok.semaphore.chapter6;

import com.ashok.lang.dsa.RandomObjects;
import com.ashok.semaphore.chapter4.LightSwitch;

import java.util.LinkedList;
import java.util.Random;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Problem statement can be found in the e-book.
 *
 * @author Ashok Rajpurohit (ashok1113@gmail.com).
 */
public class SearchInsertDelete<T> {
    private final LinkedList<T> list = new LinkedList<>();
    private final LightSwitch insertSwitch = new LightSwitch(), searchSwitch = new LightSwitch();

    private final Semaphore
            noInserter = new Semaphore(1),
            noSearcher = new Semaphore(1),
            insertMutex = new Semaphore(1);

    public int search(T t) throws InterruptedException {
        searchSwitch.lock(noSearcher);
        System.out.println("search request: list size: " + list.size());

        int res = list.indexOf(t);
        System.out.println("Searching item " + t + " found at " + res);
        searchSwitch.unlock(noSearcher);
        return res;
    }

    public void insert(int index, T t) throws InterruptedException {
        insertSwitch.lock(noInserter);
        System.out.println("insert request: list size: " + list.size());
        // insert operation.
        index %= (list.size() + 1); // new insert position.
        list.add(index, t);
        System.out.println("Inserted item: " + t + " at index " + index);

        insertSwitch.unlock(noInserter);
    }

    public void delete(T t) throws InterruptedException {
        noInserter.acquire();
        noSearcher.acquire();
        System.out.println("delete request: list size: " + list.size());

        // delete operation
        boolean res = list.remove(t);
        System.out.println("Deleting item " + t + " " + res);

        noSearcher.release();
        noInserter.release();
    }

    public Runnable getSearchTask(int period, RandomObjects<T> randomizer) {
        return new SearchTask(period, randomizer);
    }

    public Runnable getInsertTask(int period, RandomObjects<T> randomizer) {
        return new InsertTask(period, randomizer);
    }

    public Runnable getDeleteTask(int period, RandomObjects<T> randomizer) {
        return new DeleteTask(period, randomizer);
    }

    private AtomicInteger
            searchIdSequence = new AtomicInteger(),
            insertIdSequence = new AtomicInteger(),
            deleteIdSequence = new AtomicInteger();

    private final class SearchTask extends Task {
        final int periodLength;
        final RandomObjects<T> randomObjects;

        SearchTask(int periodLength, RandomObjects<T> randomObjects) {
            super(searchIdSequence.incrementAndGet());
            this.periodLength = periodLength;
            this.randomObjects = randomObjects;
        }

        @Override
        public void run() {
            while (true) {
                try {
                    search(randomObjects.next());
                    Thread.sleep(periodLength);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

        public String toString() {
            return "Search task " + id;
        }
    }

    private final class InsertTask extends Task {
        final int periodLength;
        final RandomObjects<T> randomObjects;
        final Random random = new Random();

        InsertTask(int periodLength, RandomObjects<T> randomObjects) {
            super(insertIdSequence.incrementAndGet());
            this.periodLength = periodLength;
            this.randomObjects = randomObjects;
        }

        @Override
        public void run() {
            while (true) {
                try {
                    insert(random.nextInt(Integer.MAX_VALUE), randomObjects.next());
                    Thread.sleep(periodLength);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

        public String toString() {
            return "Insert task " + id;
        }
    }

    private final class DeleteTask extends Task {
        final int periodLength;
        final RandomObjects<T> randomObjects;

        DeleteTask(int periodLength, RandomObjects<T> randomObjects) {
            super(deleteIdSequence.incrementAndGet());
            this.periodLength = periodLength;
            this.randomObjects = randomObjects;
        }

        @Override
        public void run() {
            while (true) {
                try {
                    delete(randomObjects.next());
                    Thread.sleep(periodLength);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

        public String toString() {
            return "Delete task " + id;
        }
    }

    private abstract class Task implements Runnable {
        final int id;

        Task(int id) {
            this.id = id;
        }
    }
}
