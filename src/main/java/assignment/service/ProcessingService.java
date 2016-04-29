package assignment.service;


import assignment.dao.PlayerRank;
import lombok.Setter;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class ProcessingService {

    @Setter
    @Resource(name = "rankMap")
    private Map<String, PlayerRank> rankMap;

    private List<PlayerRank> rankList;


    public List<PlayerRank> findRank() throws FileNotFoundException {

        rankList = new ArrayList<>();
        rankList.addAll(rankMap.values());
        Collections.sort(rankList, new PlayerRankComparator());
        return rankList;
    }



}
