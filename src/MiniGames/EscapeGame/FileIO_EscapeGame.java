package MiniGames.EscapeGame;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

/**
 * FileIo
 */
public class FileIO_EscapeGame {
    public File file;

    public FileIO_EscapeGame(String file) throws IOException {
        this.file = new File(file);
        if (!this.file.exists())
            this.file.createNewFile();
    }

    public void clear() throws IOException {
        this.file.delete();
        this.file.createNewFile();
    }

    public int size() throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(this.file));
        int n = 0;
        while (br.readLine() != null)
            n++;
        br.close();
        return n;
    }

    public <T> int lineOf(T elem) throws Exception {
        ArrayList<String> arr = new ArrayList<>(read());
        int index = arr.indexOf(elem.toString());
        return index == -1 ? -1 : index + 1;
    }

    public <T> ArrayList<Integer> lineOfAll(T elem) throws Exception {
        ArrayList<String> arr = new ArrayList<>(read());
        ArrayList<Integer> index = new ArrayList<>();
        int indexOfElem = arr.indexOf(elem.toString());
        while (indexOfElem != -1) {
            index.add(indexOfElem + 1);
            arr.set(indexOfElem, null);
            indexOfElem = arr.indexOf(elem.toString());
        }
        return index;
    }

    public String getPath() {
        return this.file.getPath();
    }

    public String getAbsolutePath() {
        return this.file.getAbsolutePath();
    }

    public ArrayList<String> read() throws IOException {
        ArrayList<String> contenutoFile = new ArrayList<>();
        BufferedReader br = new BufferedReader(new FileReader(this.file));
        String st;
        while ((st = br.readLine()) != null)
            contenutoFile.add(st);
        br.close();
        return contenutoFile;
    }

    public <T> void write(T elem) throws IOException {
        BufferedWriter bw = new BufferedWriter(new FileWriter(this.file));
        bw.write(elem.toString() + "\n");
        bw.close();
    }

    public <T> void append(T elem) throws IOException {
        BufferedWriter bw = new BufferedWriter(new FileWriter(this.file, true));
        bw.write(elem.toString() + "\n");
        bw.close();
    }

    public <T> void writeCollection(Collection<T> arr) throws IOException {
        BufferedWriter bw = new BufferedWriter(new FileWriter(this.file));
        for (T elem : arr)
            bw.write(elem.toString() + "\n");
        bw.close();
    }

    public <T> void appendCollection(Collection<T> arr) throws IOException {
        BufferedWriter bw = new BufferedWriter(new FileWriter(this.file, true));
        for (T elem : arr)
            bw.write(elem.toString() + "\n");
        bw.close();
    }

    public <T> boolean remove(T elem) throws IOException {
        ArrayList<String> arr = new ArrayList<>(read());
        boolean rm = arr.remove(elem.toString());
        writeCollection(arr);
        return rm;
    }

    public <T> void removeAllElem(T elem) throws IOException {
        ArrayList<String> arr = new ArrayList<>(read());
        while (arr.remove(elem.toString()))
            ;
        writeCollection(arr);
    }

    @Override
    public String toString() {
        return "File= " + getAbsolutePath();
    }
}