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
    private ConcurrentHashMap<String, PlayerRank> rankMap;

    @Setter
    private String txtfilesPath = "src/main/resources/txtfiles/";

    private List<PlayerRank> rankList = new ArrayList<>();


    public List<PlayerRank> findRank() throws FileNotFoundException {

        beginScanFolder();
        rankList.addAll(rankMap.values());
        Collections.sort(rankList, new PlayerRankComparator());

        return rankList;
    }

    /*
    Scan every file in the path
     */
    public void beginScanFolder() throws FileNotFoundException {

        File folder = new File(txtfilesPath);
        File[] listOfFiles = folder.listFiles();

        for (int i = 0; i < listOfFiles.length; i++) {
            File file = listOfFiles[i];
            if (file.isFile() && file.getName().endsWith(".txt")) {
                Scanner inputFile = new Scanner(file);
                if(inputFile != null) {
                    beginScanFile(inputFile);
                }
            }
        }

    }


    public void beginScanFile(Scanner inputFile) throws FileNotFoundException {
        List<String> lines = new ArrayList<>();

        inputFile.nextLine();
        while(inputFile.hasNextLine()) {
            String input = inputFile.nextLine();
            lines.add(input);
        }

        for(String line : lines){
            String[] stringList = line.split(",");
            if(stringList == null || stringList.length != 4){
                continue;
            }
            String PlayerOne = stringList[0];
            String PlayerTwo = stringList[2];
            int PlayerOneScore = Integer.valueOf(stringList[1]);
            int PlayerTwoScore = Integer.valueOf(stringList[3]);

            if(PlayerOneScore > PlayerTwoScore){
                updateRankMap(PlayerOne, PlayerTwo);
            }else{
                updateRankMap(PlayerTwo, PlayerOne);
            }
        }
    }

    public void updateRankMap(String winner, String loser){
        if(rankMap.containsKey(winner)){
            PlayerRank winnerRank = rankMap.get(winner);
            winnerRank.setWin(winnerRank.getWin() + 1);
            rankMap.put(winner, winnerRank);
        }else{
            PlayerRank winnerRank = new PlayerRank(winner, 1, 0, 0);
            rankMap.put(winner, winnerRank);
        }

        if(rankMap.containsKey(loser)){
            PlayerRank loserRank = rankMap.get(loser);
            loserRank.setLoss(loserRank.getLoss() + 1);
            rankMap.put(loser, loserRank);
        }else{
            PlayerRank loserRank = new PlayerRank(loser, 0, 0, 1);
            rankMap.put(loser, loserRank);
        }
    }



}

class PlayerRankComparator implements Comparator<PlayerRank>{

    @Override
    public int compare(PlayerRank o1, PlayerRank o2) {
        if(o1.getWin() > o2.getWin()){
            return -1;
        }
        if(o1.getWin() < o2.getWin()){
            return 1;
        }
        if(o1.getTie() > o2.getTie()){
            return -1;
        }
        if(o1.getTie() < o2.getTie()){
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
