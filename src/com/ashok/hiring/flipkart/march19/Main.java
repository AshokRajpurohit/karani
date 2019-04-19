/*
 * Copyright (c) 2015, 2099, Ashok and/or its affiliates. All rights reserved.
 * ASHOK PROPRIETARY/CONFIDENTIAL. Use is subject to license terms, But you are free to use it :).
 *
 */
package com.ashok.hiring.flipkart.march19;

import com.ashok.lang.inputs.InputReader;
import com.ashok.lang.inputs.Output;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Problem Name:
 * Link:
 *
 * @author Ashok Rajpurohit (ashok1113@gmail.com)
 */
public class Main {
    private static Output out = new Output();
    private static InputReader in = new InputReader();

    public static void main(String[] args) throws IOException {
        try {
            solve();
        } finally {
            in.close();
            out.close();
        }
    }

    private static void solve() throws IOException {
        int n = in.readInt();
        Action[] actions = Arrays.stream(in.readStringArray(n)).map(s -> new Action(s)).toArray(t -> new Action[t]);
        MachineState[] states = Arrays.stream(in.readStringArray(in.readInt())).map(s -> new MachineState(s)).toArray(t -> new MachineState[t]);
        MachineState current = states[in.readInt()];
        Subscriber[] subscribers = Arrays.stream(in.readStringArray(in.readInt())).map(s -> new Subscriber(s)).toArray(t -> new Subscriber[t]);

        StateTransition[] transitions = new StateTransition[subscribers.length];
        for (int i = 0; i < transitions.length; i++) {
            int a = 0, b = 0;
            try {
                a = in.readInt();
                b = in.readInt();
            } catch (IOException e) {
                e.printStackTrace();
            }
            transitions[i] = new StateTransition(states[a], states[b]);
        }

        List<MachineState> terminalStates = Arrays.stream(in.readIntArray(in.readInt())).mapToObj(t -> states[t]).collect(Collectors.toList());
        n = in.readInt();
        out.println("enter the state machine action map. from-state-index, to-state-index, action-index");
        out.flush();
        int[][] stateMap = in.readIntTable(n, 3);
        Map<MachineState, Map<Action, MachineState>> machineStateMap = new HashMap<>();
        Arrays.stream(stateMap).forEach(t -> {
            machineStateMap.putIfAbsent(states[t[0]], new HashMap<>());
            machineStateMap.get(states[t[0]]).put(actions[t[2]], states[t[1]]);
        });

        FiniteStateMachine machine = new FiniteStateMachine(machineStateMap, terminalStates, current);
        IntStream.range(0, subscribers.length).forEach(i -> machine.subscribe(subscribers[i], transitions[i]));
        for (Subscriber subscriber : subscribers) {
            startSubscriber(subscriber);
        }
        while (true) {
            out.println("enter action: apply action 1, add subscriber from list, 2 and then it's index");
            out.flush();
            int action = in.readInt();
            if (action == 1) {
                Action a = actions[in.readInt()];
                machine.putAction(a);
            } else {
                Subscriber subscriber = new Subscriber(in.read());
                out.println("want to subscribe for all? press -1 or else enter indices of states");
                out.flush();

                int state = in.readInt();
                if (state == -1)
                    machine.subscribe(subscriber, StateTransition.ALL_TRANSITIONS);
                else
                    machine.subscribe(subscriber, new StateTransition(states[state], states[in.readInt()]));

                startSubscriber(subscriber);
            }
            out.flush();
        }
    }

    private static void startSubscriber(Subscriber subscriber) {
        new Thread(() -> {
            subscriber.start();
        }).start();
    }
}
