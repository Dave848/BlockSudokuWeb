package pl.polsl.lab4.dawid.jeziorski.blocksudokuweb.model;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Class that holds information about piece templates.
 *
 * @author Dawid Jeziorski (dj300758@student.polsl.pl)
 * @version 1.1
 */
public class PieceTemplates {

    /**
     * Declaration of a templates field which is a 3D array of integers. It
     * holds different templates of pieces.
     */
    private List<List<List<Integer>>> templates;

    /**
     * 1 parameter constructor of a class that initializes templates field using
     * a file.
     *
     * @param filename path to file with templates
     * @throws java.io.FileNotFoundException when file of given file name does
     * not exist
     */
    public PieceTemplates(String filename) throws FileNotFoundException {
        templates = new ArrayList<>();
        List<List<Integer>> piece = new ArrayList<>();
        List<Integer> tempArray = new ArrayList<>();

        File file = new File(filename);
        try (Scanner scan = new Scanner(file)) {

            while (scan.hasNextLine()) {
                for (int i = 0; i < 3; i++) {
                    String[] temp = scan.nextLine().split(" ");
                    for (int j = 0; j < 3; j++) {
                        tempArray.add(Integer.valueOf(temp[j]));
                    }
                    piece.add(tempArray);
                    tempArray = new ArrayList<>();
                }
                templates.add(piece);
                piece = new ArrayList<>();

                if (scan.hasNextLine()) {
                    scan.nextLine();
                }
            }
        }
    }

    /**
     * Method that selects random template from templates field.
     *
     * @return 2D list that is a shape for a piece
     */
    public List<List<Integer>> getRandomTemplate() {
        int index = (int) (Math.random() * this.templates.size());
        return this.templates.get(index);
    }
}
