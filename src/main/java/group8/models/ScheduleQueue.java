package group8.models;

import java.util.*;

/**
 * Sub class of Priority queue used specially to handle schedules for the A* algorithm
 */
public class ScheduleQueue extends TreeSet<Schedule> {
    private List<List<Schedule>> _closedStates = new ArrayList<>();

    /**
     * Constructor to handle comparator argument
     * @param comparator
     */
    public ScheduleQueue(Comparator<? super Schedule> comparator){
        super(comparator);
    }



    @Override
    public boolean add(Schedule schedule){

//        for
//
//        Map<String, int[]> m1 =  s1.getTasks();
//        Map<String, int[]> m2 =  s2.getTasks();
//
//        boolean isSame = true;
//
//        for(int i = 0 ; i < schedule.getProcessors().length ; i++){
//            Map<String, Integer> same = new HashMap<>();
//            for(Map.Entry<String, int[]> me1 : m1.entrySet()){
//                if(me1.getValue()[1]==i){
//                    same.put(me1.getKey(),me1.getValue()[0]);
//                }
//            }
//
//            int processor = -1;
//            for(Map.Entry<String, Integer> node : same.entrySet()){
//                if(!m2.containsKey(node.getKey())){
//                    isSame = false;
//                    break;
//                }
//
//                int newProcessor = m2.get(node.getKey())[1];
//                if(m2.get(node.getKey())[0]!=node.getValue().intValue()){
//                    isSame = false;
//                    break;
//                }
//
//                if(processor==-1){
//                    processor=newProcessor;
//                }else if(processor!=newProcessor){
//                    isSame = false;
//                    break;
//                }
//
//            }
//
//            if(!isSame){
//                break;
//            }
//
//        }





        return super.add(schedule);
    }


    /**
     * Method for adding unoptimal schedules to the CLOSED list
     * @param state
     */
    public void addClosedState(Schedule state){

        if(_closedStates.size()>state.getTasks().size()){
            _closedStates.get(state.getTasks().size()-1).add(state);
        }else{
            _closedStates.add(new ArrayList<Schedule>());
            _closedStates.get(state.getTasks().size()).add(state);
        }
    }
}
