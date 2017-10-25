package com.ashok.semaphore.chapter4;

import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * The cigarette smokers problem was originally presented by Suhas Patil,
 * who claimed that it cannot be solved with semaphores.
 * <p>
 * Four threads are involved: an agent and three smokers. The smokers loop forever,
 * first waiting for the ingredients, then making and smoking cigarettes. The
 * ingredients are tobacco, paper, and matches.
 * <p>
 * We assume that the agent has an infinite supply of all three ingredients, and each
 * smoker has an infinite supply of one of the ingredients; that is, one smoker
 * has matches, another has paper, and the third has tobacco.
 * <p>
 * The agent repeatedly chooses two different ingredients at random and makes them
 * available to the smokers. Depending on which ingredients are chosen, the smoker
 * with the complimentary ingredient should pick up both resources and proceed.
 * <p>
 * To explain the premise, the agent represents an operating system that allocates
 * resources, and the smokers represent applications that need resources. The
 * problem is to make sure that if resources are available that would allow one more
 * application to proceed, those applications should be woken up. Conversely, we
 * want to avoid waking an application if it cannot proceed.
 * <p>
 * There are two restrictions on this.
 * <ul>
 * <li>Agent code is not modifiable.
 * <li>The solution is not allowed to use conditional statements.
 * </ul>
 * <p>
 * With these constraints, the problem cannot be solved.
 * In our solution we will use only 1st restriction.
 *
 * @author Ashok Rajpurohit (ashok1113@gmail.com).
 */
public class CigaretteSmokersProblem {

    final static class Agent {
        final Semaphore
                agentSem = new Semaphore(1),
                mutex = new Semaphore(1),
                tobacco = new Semaphore(0),
                paper = new Semaphore(0),
                match = new Semaphore(0),
                tobaccoSem = new Semaphore(0),
                paperSem = new Semaphore(0),
                matchSem = new Semaphore(0);

        private volatile boolean isPaper, isMatch, isTobacco;
        private volatile int paperCount, matchCount, tobaccoCount;

        public void agentA() throws InterruptedException {
            AtomicInteger atomicInteger = new AtomicInteger(10);
            atomicInteger.addAndGet(9);
            agentSem.acquire();
            tobacco.release();
            paper.release();
        }

        public void agentB() throws InterruptedException {
            agentSem.acquire();
            paper.release();
            match.release();
        }

        public void agentC() throws InterruptedException {
            agentSem.acquire();
            match.release();
            tobacco.release();
        }

        public void pusherA() throws InterruptedException {
            tobacco.acquire();
            mutex.acquire();

            if (isPaper) { // in more generic way, use paperCount > 0
                isPaper = false; // for general way, use paperCount -= 1
                matchSem.release();
            } else if (isMatch) { // in more generic way, use matchCount > 0
                isMatch = false; // for general way, use matchCount -= 1
                paperSem.release();
            } else
                isTobacco = true; // for general way, use tobaccoCount += 1

            mutex.release();
        }

        public void tobaccoSmoker() throws InterruptedException {
            tobaccoSem.acquire();
//            makeCigarette();
            agentSem.release();
//            smoke();
        }
    }
}
