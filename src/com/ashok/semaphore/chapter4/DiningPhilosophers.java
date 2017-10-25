package com.ashok.semaphore.chapter4;

import java.util.concurrent.Semaphore;

/**
 * {@code The Dining Philosophers Problem} was proposed by Dijkstra in 1965.
 * The standard problem is:
 * There is a table with five plates, five forks (or chopsticks) and
 * a big bowl of spaghetti. Five philosophers, who represent interacting threads,
 * come to the table and execute the following loop:
 * <pre> {@code
 * 	while (true) {
 * 		think();
 * 		getForks();
 * 		eat();
 * 		putForks();
 * 	}}</pre>
 * <p>
 * The forks represent resources that the threads have to hold exclusively in
 * order to make progress. Philosophers need <i>two</i> forks to eat, so a hungry
 * philosopher might have to wait for a neighbor to put down a fork.
 * <p>
 * Assume that the philosophers have a local variable i that identifies each
 * philosopher with a value in [0..4]. Similarly, the forks are numbered from 0 to
 * 4, so that Philosopher <i>i</i> has fork <i>i</i> on the right and fork <i>i</i> + 1
 * on the left.
 * <ul>
 * <li>Only one philosopher can hold a fork at a time.
 * <li>It must be impossible for a deadlock to occur.
 * <li>It must be impossible for a philosopher to starve waiting for a fork.
 * <li>It must be possible for more than one philosopher to eat at the same time.
 * </ul>
 * Assuming that the philosphers know how to <b>think</b>
 *
 * @author Ashok Rajpurohit (ashok1113@gmail.com).
 */
public class DiningPhilosophers {
    final int size;
    final PhilosopherFactory philosopherFactory;
    final Semaphore footman;

    DiningPhilosophers(int tableSize) {
        size = tableSize;
        philosopherFactory = new PhilosopherFactory(size);
        footman = new Semaphore(size - 1);
    }

    /**
     * In this code, {@code philosopher} can acquire one fork but wait forever for the other fork.
     *
     * @param philosopher
     * @throws InterruptedException
     */
    private void wrongSolution(Philosopher philosopher) throws InterruptedException {
        // wrong code for getForks() method.
        philosopher.leftFork.acquire();
        philosopher.rightFork.acquire();

        // there is nothing wrong in this code for putForks() method.
        philosopher.leftFork.release();
        philosopher.rightFork.release();
    }

    /**
     * If there is atleast one leftie and one rightie, then it's impossible for the deadlock to
     * occur. Prove it.
     * The deadlock can occur when all the philosopher's have one fork and waiting for the other.
     * If there is atleast one leftie and one rightie than the deadlock situation is impossible.
     *
     * @param philosopher
     * @throws InterruptedException
     */
    private void correctSolution(Philosopher philosopher) throws InterruptedException {
    }

    /**
     * The Tanenbaum's solution is erroneous. It does block deadlock but A thread can starve.
     * Imagine that we are trying to starve Philosopher 0. Initially, 2 and 4 are at the
     * table and 1 and 3 are hungry. Imagine that 2 gets up and 1 sit down; then 4 gets up
     * and 3 sits down. Now we are in the mirror image of the starting position.
     * <p>
     * If 3 gets up and 4 sits down, and then 1 gets up and 2 sits down, we are back where we
     * started. We could repeat the cycle indefinitely and Philosopher 0 would starve.
     * <p>
     * So, Tanenbaum's solution doesn't satisfy all the requirements.
     */
    private void TanenbaumSolution() {
        /**
         * These are the methods for the solution.
         *
         * getForksTanenbaum(philosopher)
         * putForksTanenbaum(philosopher)
         * test(philosopher)
         */
    }

    private void getForksTanenbaum(Philosopher philosopher) throws InterruptedException {
        footman.acquire();
        philosopher.state = PhilosopherState.HUNGRY;
        test(philosopher);
        footman.release();
        philosopher.acquire(); // wait to start eating.
    }

    private void putForksTanenbaum(Philosopher philosopher) throws InterruptedException {
        footman.acquire();
        philosopher.state = PhilosopherState.THINKING;
        test(right(philosopher)); // let right side philosopher know, if he can start eating.
        test(left(philosopher)); // just copy the above comment.
        footman.release();
    }

    private void test(Philosopher philosopher) {
        if (philosopher.state == PhilosopherState.HUNGRY &&
                left(philosopher).state != PhilosopherState.EATING &&
                right(philosopher).state != PhilosopherState.EATING) {
            philosopher.state = PhilosopherState.EATING;
            philosopher.release(); // let philosopher know, he can eat.
        }
    }

    private Philosopher left(Philosopher philosopher) {
        int id = philosopher.id - 1;
        return philosopherFactory.getPhilosopher(id == -1 ? size - 1 : id);
    }

    private Philosopher right(Philosopher philosopher) {
        int id = philosopher.id + 1;
        return philosopherFactory.getPhilosopher(id == size ? 0 : id);
    }

    private void getForks(Philosopher philosopher) throws InterruptedException {
        footman.acquire();
        philosopher.leftFork.acquire();
        philosopher.rightFork.acquire();
    }

    private void putForks(Philosopher philosopher) {
        philosopher.leftFork.release();
        philosopher.rightFork.release();
        footman.release();
    }

    final static class PhilosopherFactory {
        final int size;
        final Philosopher[] philosophers;
        final ForkFactory forkFactory;

        PhilosopherFactory(int size) {
            this.size = size;
            forkFactory = new ForkFactory(size);
            philosophers = new Philosopher[size];
        }

        public synchronized Philosopher getPhilosopher(int id) {
            if (philosophers[id] == null)
                philosophers[id] = new Philosopher(id, forkFactory.getFork(id), forkFactory.getFork(id + 1));

            return philosophers[id];
        }
    }

    final static class ForkFactory {
        final int size;
        final Fork[] forks;

        ForkFactory(int size) {
            this.size = size;
            forks = new Fork[size];
        }

        public synchronized Fork getFork(int id) {
            if (id >= size)
                return getFork(id % size);

            if (forks[id] == null)
                forks[id] = new Fork(id);

            return forks[id];
        }
    }

    final static class Philosopher extends Semaphore {
        final int id;
        final Fork leftFork, rightFork; // forks he can use to eat, if available.
        PhilosopherState state = PhilosopherState.THINKING; // for Tanenbaum's solution.

        Philosopher(int id, Fork leftFork, Fork rightFork) {
            super(0); // only for Tanenbaum's solution.
            this.id = id;
            this.leftFork = leftFork;
            this.rightFork = rightFork;
        }

        public String toString() {
            return "[Philosopher: " + id + "]";
        }
    }

    final static class Fork extends Semaphore {

        final int id;

        Fork(int id) {
            super(1);
            this.id = id;
        }

        public String toString() {
            return "[Fork: " + id + "]";
        }

    }

    enum PhilosopherState {
        HUNGRY, THINKING, EATING
    }
}
