import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        List<String> fileNames;
        fileNames = openZip("C:/Users/tepla/Games/savegames/Zip_saved.zip",
                "C:/Users/tepla/Games/savegames/");
        for (String file:fileNames){
            GameProgress gp=openProgress(file);
            System.out.println(gp.toString());
        }
    }

    public static GameProgress openProgress(String fullName){
        GameProgress gp = null;
        try(FileInputStream fis = new FileInputStream(fullName);
            ObjectInputStream ois = new ObjectInputStream(fis)){
            gp = (GameProgress) ois.readObject();
            return gp;
        } catch (Exception ex){
            System.out.println(ex.getMessage());
        }
        System.out.println(gp);
        return gp;

    }

    public static List<String> openZip(String source, String target){
        List<String> fileNames = new ArrayList<>();
        try(ZipInputStream zin = new ZipInputStream(new FileInputStream(source))){
            ZipEntry entry;
            String name;
            while ((entry=zin.getNextEntry())!=null){
                name=entry.getName();
                fileNames.add(target+name);
                FileOutputStream fos = new FileOutputStream(target+name);
                for(int c=zin.read();c!=-1;c= zin.read()){
                    fos.write(c);
                }
                fos.flush();
                zin.closeEntry();
                fos.close();
            }
        }
        catch (Exception ex){
            System.out.println(ex.getMessage());
        }
        return fileNames;
    }

}