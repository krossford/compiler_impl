package nfa;

import util.Util;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class NFA {

    public NFAState startState = null;

    public NFAState endState = null;

    public static NFA link(NFA nfa1, NFA nfa2) {
        nfa1.endState.addTrans("", nfa2.startState);
        nfa1.endState = nfa2.endState;
        return nfa1;
    }

    public static NFA removeEmptyTrans(NFA nfa) {
        removeEmptyTrans(nfa.startState);
        return nfa;
    }

    public static void removeEmptyTrans(NFAState state) {
        Set<NFAState> all = state.getAllTransState();

        all.stream().forEach(new Consumer<NFAState>() {
            @Override
            public void accept(NFAState nfaState) {
                if (nfaState != state && nfaState.hasEmptySingleTrans()) {
                    nfaState.transMap = nfaState.getEmptySingleTransState().transMap;
                }
            }
        });

        all.stream().forEach(new Consumer<NFAState>() {
            @Override
            public void accept(NFAState nfaState) {
                if (nfaState != state) {
                    removeEmptyTrans(nfaState);
                }
            }
        });
    }

    static Map<Integer, NFAState> map_no_to_stateObj = new HashMap<>();
    static Map<NFAState, Integer> map_stateObj_to_no = new HashMap<>();
    static Set<NFAState> acceptStates = new HashSet<>();
    static Set<String> inputs = new HashSet<>();

    static int nextAvailableNo = 0;

    static int index = 0;

    @Override
    public String toString() {

        inputs.clear();
        acceptStates.clear();
        map_no_to_stateObj.clear();
        map_stateObj_to_no.clear();

        nextAvailableNo = 0;

        mapAllState(startState);

        StringBuilder sb = new StringBuilder();

        // -----------------
        // output all states
        // -----------------

        sb.append("states:{ ");
        List<Integer> stateNoSortedList = Util.asSortedList(map_stateObj_to_no.values());
        stateNoSortedList.forEach(new Consumer<Integer>() {
            @Override
            public void accept(Integer integer) {
                sb.append(integer).append(", ");
            }
        });
        sb.delete(sb.length() - 2, sb.length());
        sb.append(" }\n");

        // ------------------
        // output start state
        // ------------------

        sb.append("start:{ ").append(map_stateObj_to_no.get(startState)).append(" }\n");

        // --------------------
        // output accept states
        // --------------------
        sb.append("accept:{ ");
        acceptStates.forEach(new Consumer<NFAState>() {
            @Override
            public void accept(NFAState nfaState) {
                sb.append(map_stateObj_to_no.get(nfaState)).append(", ");
            }
        });
        sb.delete(sb.length() - 2, sb.length());
        sb.append(" }\n");

        sb.append("input:{ ");
        inputs.forEach(new Consumer<String>() {
            @Override
            public void accept(String s) {
                sb.append(s).append(", ");
            }
        });
        sb.delete(sb.length() - 2, sb.length());
        sb.append(" }\n");

        //                                       row                          col
        String[][] transTable = new String[map_stateObj_to_no.size() + 1][inputs.size() + 1];
        transTable[0][0] = "";

        index = 1;

        stateNoSortedList.forEach(new Consumer<Integer>() {
            @Override
            public void accept(Integer integer) {
                transTable[index][0] = integer.toString();
                index++;
            }
        });

        index = 1;

        inputs.forEach(new Consumer<String>() {
            @Override
            public void accept(String s) {
                transTable[0][index] = s;
                index++;
            }
        });

        // 保存每一列最大的字符宽度
        int[] eachColMaxWidth = new int[transTable[0].length];

        for (int row = 0; row < transTable.length; row++) {
            for (int col = 0; col < transTable[0].length; col++) {
                if (col == 0) {
                    eachColMaxWidth[col] = Math.max(transTable[row][0].length(), eachColMaxWidth[col]);
                }
                if (row != 0 && col != 0) {
                    String s = getStateByInput(Integer.valueOf(transTable[row][0]), transTable[0][col]);
                    transTable[row][col] = s;
                    eachColMaxWidth[col] = Math.max(s.length(), eachColMaxWidth[col]);
                }
            }
        }

        sb.append("table:\n");
        for (int row = 0; row < transTable.length; row++) {
            for (int col = 0; col < transTable[0].length; col++) {
                String s = transTable[row][col];
                sb.append(s);
                if (s.length() < eachColMaxWidth[col]) {
                    for (int i = 0; i < eachColMaxWidth[col] - s.length(); i++) {
                        sb.append(" ");
                    }
                }
                sb.append("|");
            }
            sb.append("\n");
        }
        sb.append("");

        return sb.toString();
    }

    private static void mapAllState(NFAState state) {

        if (map_stateObj_to_no.containsKey(state)) {
            return;
        }

        if (state.isAccept) {
            acceptStates.add(state);
        }

        map_no_to_stateObj.put(nextAvailableNo, state);
        map_stateObj_to_no.put(state, nextAvailableNo);
        nextAvailableNo++;

        state.getAllTransInput().forEach(new Consumer<String>() {
            @Override
            public void accept(String s) {
                inputs.add(s);
            }
        });

        state.getAllTransState().forEach(new Consumer<NFAState>() {
            @Override
            public void accept(NFAState nfaState) {
                mapAllState(nfaState);
            }
        });
    }

    private static String getStateByInput(int state, String input) {
        Set<NFAState> transResult = map_no_to_stateObj.get(state).transMap.getOrDefault(input, null);
        if (transResult == null || transResult.size() == 0) {
            return "";
        } else {
            StringBuilder sb = new StringBuilder();
            transResult.forEach(new Consumer<NFAState>() {
                @Override
                public void accept(NFAState nfaState) {
                    sb.append(map_stateObj_to_no.get(nfaState)).append(", ");
                }
            });
            sb.delete(sb.length() - 2, sb.length());
            return sb.toString();
        }
    }


}
