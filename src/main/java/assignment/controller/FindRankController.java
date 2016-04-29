package assignment.controller;

import java.io.FileNotFoundException;
import java.util.List;

import assignment.dao.PlayerRank;
import assignment.service.ProcessingService;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.HttpStatus.OK;

@RestController
public class FindRankController {

    @Autowired
    @Setter
    private ProcessingService processingService;


    @RequestMapping(value="/findRank", method = RequestMethod.GET)
    public ResponseEntity<String> findRank() throws FileNotFoundException {

        List<PlayerRank> rankList = processingService.findRank();
        StringBuffer result = new StringBuffer();
        result.append("The following list shows the rank:  \n");

        for(PlayerRank playerRank : rankList){
            result.append("Player: " + playerRank.getName() + ", Win: " + playerRank.getWin() + ", Loss: " + playerRank.getLoss() + "\n" );
        }

        return new ResponseEntity<String>(result.toString(), OK);

    }

}
