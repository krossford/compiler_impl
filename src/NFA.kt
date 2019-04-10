

class NFA {

    var startState: StateNode? = null
    var endState: StateNode? = null


    companion object {

        /**
         * read re, convert to NFA
         * */
//        fun parse(re: String): NFA {
//            for ((index, c) in re.toCharArray().withIndex()) {
//                if ((c in 'a'..'z') || (c in 'A'..'Z')) {
//                    val nfa = NFA()
//                    val start = StateNode()
//                    val end = StateNode()
//                    start.stateMap[c] = end
//                    nfa.startState = start
//                    nfa.endState = end
//                }
//            }
//        }
    }
}