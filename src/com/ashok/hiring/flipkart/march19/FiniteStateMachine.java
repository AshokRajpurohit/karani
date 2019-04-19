package com.ashok.hiring.flipkart.march19;

import java.util.*;
import java.util.concurrent.*;

/**
 * @author Ashok Rajpurohit (ashok1113@gmail.com).
 */
public class FiniteStateMachine {
    private final BlockingDeque<Action> producerActions = new LinkedBlockingDeque<>();
    private ConcurrentMap<StateTransition, ConcurrentMap<Subscriber, Queue<Object>>> transitionSubscribersMap = new ConcurrentHashMap<>();
    private volatile MachineState currentState;
    private final Set<MachineState> terminalStates = new HashSet<>();
    private final Map<MachineState, Map<Action, MachineState>> nextStateMap = new ConcurrentHashMap<>();
    private final Map<Subscriber, BlockingQueue<Object>> subscriberQueueMap = new ConcurrentHashMap<>();

    public FiniteStateMachine(Map<MachineState, Map<Action, MachineState>> stateMap, Collection<MachineState> terminalStates, MachineState startState) {
        if (terminalStates.isEmpty())
            throw new RuntimeException("There should be atleast one terminal state");

        for (Map.Entry<MachineState, Map<Action, MachineState>> entry : stateMap.entrySet()) {
            nextStateMap.put(entry.getKey(), new ConcurrentHashMap<>());
            Map map = nextStateMap.get(entry.getKey());
            map.putAll(stateMap.get(entry.getKey()));
        }

        this.terminalStates.addAll(terminalStates);
        currentState = startState;
        new Thread(() -> consumerActions()).start();
    }

    public void subscribe(Subscriber subscriber, StateTransition transition) {
        addTransition(transition);
        addSubscriber(subscriber);
        transitionSubscribersMap.get(transition).putIfAbsent(subscriber, subscriberQueueMap.get(subscriber));
    }

    private void addSubscriber(Subscriber subscriber) {
        subscriber.producer = this;
        subscriberQueueMap.putIfAbsent(subscriber, new LinkedBlockingQueue<>());
    }

    private void addTransition(StateTransition transition) {
        if (transitionSubscribersMap.containsKey(transition))
            return;

        transitionSubscribersMap.putIfAbsent(transition, new ConcurrentHashMap<>());
    }

    public void putAction(Action action) {
        producerActions.add(action);
    }

    private void consumerActions() {
        while (true) {
            MachineState previousState = currentState;
            Action action = null;
            try {
                action = producerActions.take();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            boolean validAction = applyAction(action);
            StateTransition transition = new StateTransition(previousState, currentState);
            publishMessage(transition, action, validAction);
        }
    }

    private boolean applyAction(Action action) {
        boolean isActionValid = validateAction(action);
        if (!isActionValid) return false;
        currentState = nextStateMap.get(currentState).get(action);
        return true;
    }

    public Object get(Subscriber subscriber) {
        try {
            return subscriberQueueMap.get(subscriber).take();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    private boolean validateAction(Action action) {
        if (terminalStates.contains(currentState)) return false;
        return nextStateMap.containsKey(currentState)
                && nextStateMap.get(currentState).containsKey(action);
    }

    private void publishMessage(StateTransition transition, Action action, boolean validity) {
        String message;
        if (!validity) {
            message = "Action: " + action + " can not be applied for state: " + transition.from;
        } else {
            message = "Action: " + action + " applied and state is changed from: " + transition.from + " to: " + transition.to;
        }

        if (transitionSubscribersMap.containsKey(transition))
            transitionSubscribersMap.get(transition).values().forEach(t -> t.add(message));

        if (transitionSubscribersMap.containsKey(StateTransition.ALL_TRANSITIONS))
            transitionSubscribersMap.get(StateTransition.ALL_TRANSITIONS).values().forEach(t -> t.add(message));
        System.out.println("publishing message: " + message);
    }
}
