package assignment.config;

import assignment.dao.PlayerRank;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Configuration
public class BeanConfig {

    @Bean
    public Map<String, PlayerRank> rankMap() throws FileNotFoundException {

        HashMap<String, PlayerRank> rankMap = new HashMap<String, PlayerRank>();
        String txtfilesPath = "src/main/resources/txtfiles/file1.txt";
        File file = new File(txtfilesPath);
        Scanner inputFile = new Scanner(file);
        beginScanFile(inputFile, rankMap);
        return rankMap;
    }


    public void beginScanFile(Scanner inputFile, Map<String, PlayerRank> rankMap) throws FileNotFoundException {
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
                updateRankMap(PlayerOne, PlayerTwo, rankMap);
            }else{
                updateRankMap(PlayerTwo, PlayerOne, rankMap);
            }
        }
    }

    public void updateRankMap(String winner, String loser, Map<String, PlayerRank> rankMap){
        if(rankMap.containsKey(winner)){
            PlayerRank winnerRank = rankMap.get(winner);
            winnerRank.setWin(winnerRank.getWin() + 1);
            rankMap.put(winner, winnerRank);
        }else{
            PlayerRank winnerRank = new PlayerRank(winner, 1, 0);
            rankMap.put(winner, winnerRank);
        }

        if(rankMap.containsKey(loser)){
            PlayerRank loserRank = rankMap.get(loser);
            loserRank.setLoss(loserRank.getLoss() + 1);
            rankMap.put(loser, loserRank);
        }else{
            PlayerRank loserRank = new PlayerRank(loser, 0, 1);
            rankMap.put(loser, loserRank);
        }
    }


}
