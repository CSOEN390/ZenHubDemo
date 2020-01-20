package ca.concordia.encs.conquerdia.controller.command;

import ca.concordia.encs.conquerdia.Starter;
import ca.concordia.encs.conquerdia.model.PhaseModel;
import ca.concordia.encs.conquerdia.model.PlayersModel;
import org.junit.Assert;
import org.junit.Test;

import java.io.*;
import java.util.Scanner;

/**
 * Starter Test
 */
public class TournamentTest {




    /**
     * tournament Test
     */
    @Test
    public void tournamentTest() {
        try {
            PlayersModel.clear();
            PhaseModel.clear();
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            PrintStream ps = new PrintStream(os);
            StringBuilder input = new StringBuilder();
            input.append("tournament -M risk Alberta Montreal -P aggressive random -G 5 -D 50").append(System.getProperty("line.separator")).append("exit");
            InputStream in = new ByteArrayInputStream(input.toString().getBytes());
            new Starter().start(new Scanner(in), ps);
            String output = os.toString("UTF8");
            Assert.assertTrue(output.contains("Tournament Finished Successfully."));
        } catch (UnsupportedEncodingException e) {

        }
    }
}
