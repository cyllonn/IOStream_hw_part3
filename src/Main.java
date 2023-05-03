import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

;


public class Main {
    public static String PATH = "D://IO_Games/savegames/";

    public static void main(String[] args) {
        String zipPath = PATH + "save.zip";
        openZip(zipPath, PATH);
        for (int i = 1; i < 4; i++) {
            System.out.println(openProgress(PATH + "game" + i + ".dat"));
        }
    }

    public static void openZip(String pathToZip, String pathToOutput) {
        try (ZipInputStream zin = new ZipInputStream(new FileInputStream(pathToZip))) {
            ZipEntry entry;
            String name;
            while ((entry = zin.getNextEntry()) != null) {
                name = pathToOutput + entry.getName();
                try (FileOutputStream fos = new FileOutputStream(name)) {
                    for (int i = zin.read(); i != -1; i = zin.read()) {
                        fos.write(i);
                    }
                    fos.flush();
                    zin.closeEntry();
                }
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    public static GameProgress openProgress(String pathToSaveFile) {
        GameProgress gameProgress = null;
        try (FileInputStream fis = new FileInputStream(pathToSaveFile);
             ObjectInputStream ois = new ObjectInputStream(fis)) {
            gameProgress = (GameProgress) ois.readObject();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        return gameProgress;
    }
}
