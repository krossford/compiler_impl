package nfa;

import java.util.*;

public class NFAState {

    public Map<String, Set<NFAState>> transMap = new HashMap<>();

    /**
     * 标记自己是否是一个接受状态
     * */
    public boolean isAccept = false;

    public NFAState() {

    }

    public void addTrans(String input, NFAState toState) {
        Set<NFAState> stateSet = transMap.getOrDefault(input, new HashSet<>());
        stateSet.add(toState);
        transMap.put(input, stateSet);
    }

    /**
     * 是否只有一条空边
     * */
    public boolean hasEmptySingleTrans() {
        Set<NFAState> sets = transMap.get("");
        if (sets != null && sets.size() == 1) {
            return true;
        }
        return false;
    }

    public NFAState getEmptySingleTransState() {
        Set<NFAState> sets = transMap.get("");
        if (sets == null || sets.isEmpty()) {
            System.out.println("fuck");
            return null;
        } else {
            return sets.stream().findFirst().get();
        }
    }

    /**
     * 获取所有可以转移到的状态
     * */
    public Set<NFAState> getAllTransState() {
        Set<NFAState> all = new HashSet<>();
        Iterator<Map.Entry<String, Set<NFAState>>> it = transMap.entrySet().iterator();
        while (it.hasNext()) {
            all.addAll(it.next().getValue());
        }
        return all;
    }

    /**
     * 获取所有输入的列表
     * */
    public Set<String> getAllTransInput() {
        Set<String> all = new HashSet<>();
        Iterator<Map.Entry<String, Set<NFAState>>> it = transMap.entrySet().iterator();
        while (it.hasNext()) {
            all.add(it.next().getKey());
        }
        return all;
    }
}
