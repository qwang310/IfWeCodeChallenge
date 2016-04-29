package assignment.service;

import assignment.dao.PlayerRank;

import java.util.Comparator;

/**
 * Created by qu2k8o6 on 4/29/16.
 */
public class PlayerRankComparator implements Comparator<PlayerRank> {

    @Override
    public int compare(PlayerRank o1, PlayerRank o2) {
        if(o1.getWin() > o2.getWin()){
            return -1;
        }
        if(o1.getWin() < o2.getWin()){
            return 1;
        }
        if(o1.getLoss() < o2.getLoss()){
            return -1;
        }
        if(o1.getLoss() > o2.getLoss()){
            return 1;
        }

        return o1.getName().compareTo(o2.getName());
    }
}