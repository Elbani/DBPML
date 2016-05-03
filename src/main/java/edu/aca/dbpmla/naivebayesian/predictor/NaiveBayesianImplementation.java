package edu.aca.dbpmla.naivebayesian.predictor;

import edu.aca.dbpmla.naivebayesian.entity.NaiveBayesianStructure;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Taulant on 5/2/2016.
 */
public class NaiveBayesianImplementation {
    /**
     * if we have a history table of n bits, the History Counter table will have n + 1 Counters
     * index[0] is the counter which increments for branch outcome 1 and decrements for branch outcome 0
     * index[i>0] are the counters for each history bit.
     *
     * @return
     */
    public Map<Integer, Integer> generateCounters(int size) {

        Map<Integer, Integer> tempHashMap = new HashMap<>();

        for (int i = 0; i < size; i++) {
            tempHashMap.put(i, 0);
        }

        return tempHashMap;
    }

    /**
     * Predict incoming branch
     * @param map
     * @param index
     * @return
     */
    public Boolean predictBranch(HashMap<Integer, NaiveBayesianStructure> map, int index) {
        NaiveBayesianStructure predictor = map.get(index);
        int countP0 = 0, countP1 = 0;

        int size = predictor.getHistoryCountersP1().size();
        int counterLength = (int) Math.pow(2, predictor.getBitCounterLength() - 1);

        for (int i = 0; i < size; i++) {

            Map<Integer, Integer> mp = predictor.getHistoryCountersP0();
            /**
             * counter 0
             */
            countP0 += mp.get(i);
            countP0 += counterLength - mp.get(i);
            countP0 += counterLength - predictor.getP1();
//            if (mp.get(i) >= counterLength ) {
//                countP0++;
//            }
//
//            if((counterLength - mp.get(i)) >= counterLength ){
//                countP0++;
//            }
//
//            if((counterLength - predictor.getP1()) >= counterLength){
//                countP0++;
//            }


            /**
             * counter 1
             */

            mp = predictor.getHistoryCountersP1();

            countP1 += mp.get(i);
            countP1 += counterLength - mp.get(i);
            countP1 += predictor.getP1();
//            if(mp.get(i) >= counterLength){
//                countP1++;
//            }
//            if((counterLength - (mp.get(i)) >= counterLength )){
//                countP1++;
//            }
//
//            if(predictor.getP1() >= counterLength){
//                countP1++;
//            }
        }

        if (countP0 > countP1) return false;
        else return true;
    }

    /**
     *
     * @param branchOutCome
     * @param map
     * @param index
     */
    public void trainNaiveBayesian(int branchOutCome, HashMap<Integer, NaiveBayesianStructure> map, int index) {
        NaiveBayesianStructure naiveBayesianStructure = map.get(index);

        if (branchOutCome == 1) {
            if (naiveBayesianStructure.getP1() < Math.pow(2, naiveBayesianStructure.getBitCounterLength())) {
                naiveBayesianStructure.setP1(naiveBayesianStructure.getP1() + 1);
            }
        } else {
            if (naiveBayesianStructure.getP1() > 0) {
                naiveBayesianStructure.setP1(naiveBayesianStructure.getP1() - 1);
            }
        }


        /**
         * Shift left for the new Branch outcome
         */
        naiveBayesianStructure.getHistoryTable().remove(0);
        naiveBayesianStructure.getHistoryTable().add(branchOutCome);

        updateCounters(naiveBayesianStructure, branchOutCome);
    }

    /**
     * Update counters - Increment counter if it is 1 in the history table, decrement if it is 0
     * @param naiveBayesianStructure
     * @param branchOutCome
     * @return
     */
    private Map<Integer, Integer> updateCounters(NaiveBayesianStructure naiveBayesianStructure, int branchOutCome) {
        Map<Integer, Integer> mp;

        if (branchOutCome == 0) {
            mp = naiveBayesianStructure.getHistoryCountersP0();
        } else {
            mp = naiveBayesianStructure.getHistoryCountersP1();
        }

        for (Map.Entry<Integer, Integer> entry : mp.entrySet()) {
            if (naiveBayesianStructure.getHistoryTable().get(entry.getKey()) == 0) {

                if (entry.getValue() > 0) {
                    entry.setValue(entry.getValue() - 1);
                }
            } else { // else 1

                if (entry.getValue() < Math.pow(2, naiveBayesianStructure.getBitCounterLength()) - 1) {
                    entry.setValue(entry.getValue() + 1);
                }
            }
        }

        return mp;
    }
}
