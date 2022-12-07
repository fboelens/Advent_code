import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day7 {
    static Integer totalSize=0;
    static Integer totalDiskSpace = 70000000;
    static Integer unUsedSpace = 30000000;
    static Integer bestFolderSize = 0;

    public static void execute() {
        File file = new File("resources/day7.txt");

        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String st;
            String folderName;

            Folder topFolder = new Folder(null,"top");
            Folder currentFolder = topFolder;

            while ((st = br.readLine()) != null) {
                // toplevel
                if (st.startsWith("$ cd /")) {
                    currentFolder = topFolder;
                }
                // one level up
                else {
                    if (st.startsWith("$ cd ..")) {
                        currentFolder = currentFolder.getParent();
                    }
                    else {
                        if (st.startsWith("$ cd ")) {
                            folderName = st.substring(st.lastIndexOf(' ') + 1).trim();
                            Folder f = new Folder(currentFolder,folderName);
                            currentFolder.addFolder(f);
                            currentFolder = f;
                        }

                    }
                }
                if (st.startsWith("dir")) {
                    folderName = st.substring(st.lastIndexOf(' ') + 1).trim();
                    Folder f = new Folder(currentFolder,folderName);
                    currentFolder.addFolder(f);
                }
                // bestandsnaam
                if (Character.isDigit(st.charAt(0))) {
                    Pattern p = Pattern.compile("\\d+");
                    Matcher m = p.matcher(st);
                    currentFolder.changeSize(Integer.valueOf(m.group()));
                }


            }
            showFolderTree(topFolder);

            Integer currentDiskSpace = totalDiskSpace - topFolder.size;
            int spaceNeeded = unUsedSpace - currentDiskSpace;
            bestFolderSize = currentDiskSpace;
            findBestFolder(topFolder,spaceNeeded);
            System.out.println("Day 7 - Question 1: " + totalSize);
            System.out.println("Day 7 - Question 2: " + bestFolderSize);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static void showFolderTree(Folder f) {
        if (f.size<=100000 && f.size!=0) {
            totalSize+=f.size;
        }
        for (Folder folders: f.subFolders) {
            showFolderTree(folders);
        }
    }

    public static void findBestFolder(Folder f, int spaceNeeded ) {
        int spaceLeft = spaceNeeded - f.size;
        if (spaceLeft<0 && f.size<bestFolderSize) {
            bestFolderSize = f.size;
        }

        for (Folder folders: f.subFolders) {
            findBestFolder(folders,spaceNeeded);
        }
    }


}


class Folder {
    String name;
    Integer size;
    Folder parent;
    ArrayList<Folder> subFolders = new ArrayList<>();

    public Folder(Folder parent, String name) {
        this.name = name;
        this.size = 0;
        this.parent = parent;

    }

    public void addFolder(Folder f) {
        subFolders.add(f);
    }

    public void changeSize(Integer size) {
        this.size += size;
        if (this.parent!=null) {
            this.parent.changeSize(size);
        }
    }

    public Folder getParent() {
        return this.parent;
    }
}