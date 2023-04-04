import java.io.File;
import java.io.IOException;
import java.util.*;

public class FileNavigator {

    private Map<String, List<FileData>> fileMap = new LinkedHashMap<>();
    private File file;

    public void add(String path, FileData fileData) {
        if (!fileData.getFilePath().equals(path))
            throw new IllegalArgumentException("File path " + path + " not equals fileData path: " + fileData.getFilePath());

        if (fileMap.containsKey(path))
            fileMap.get(path).add(fileData);
        else {
            List<FileData> fileDataList = new ArrayList<>();
            fileDataList.add(fileData);
            fileMap.put(path, fileDataList);
        }
    }

    public List<FileData> find(String path) {
        File file = new File(path).getAbsoluteFile();
        return fileMap.get(file.getAbsolutePath());
    }

    public List<FileData> filterBySize(int size) {
        List<FileData> fileDataList = new ArrayList<>();

        for (Map.Entry<String, List<FileData>> entry : fileMap.entrySet()) {
            for (FileData fileData : entry.getValue()) {
                if (fileData.getFileSize() >= size)
                    fileDataList.add(fileData);
            }
        }

        return fileDataList;
    }

    public void remove(String path) {
        File file = new File(path).getAbsoluteFile();
        fileMap.remove(file.getAbsolutePath());
    }

    public List<FileData> sortBySize() {
        List<FileData> fileDataList = new ArrayList<>();

        for (Map.Entry<String, List<FileData>> entry : fileMap.entrySet()) {
            fileDataList.addAll(entry.getValue());
        }
        fileDataList.sort(Comparator.comparingLong(FileData::getFileSize));

        return fileDataList;
    }

    public void add(String path) {
        try {
            File file = createFile(path);

            FileData fileData = new FileData(file.getName(), (int) file.length(), file.getParent());

            if (!fileData.getFilePath().equals(file.getParent()))
                throw new IllegalArgumentException("File path " + path + " not equals fileData path: " + fileData.getFilePath());

            if (fileMap.containsKey(file.getParent()))
                fileMap.get(file.getParent()).add(fileData);
            else {
                List<FileData> fileDataList = new ArrayList<>();
                fileDataList.add(fileData);
                fileMap.put(file.getParent(), fileDataList);
            }
        } catch (FileIsException e) {
            e.printStackTrace();
        }
    }

    private File createFile(String path) throws FileIsException {
        File file = new File(path).getAbsoluteFile();
        File directory = new File(file.getAbsoluteFile().getParent());

        if (!directory.isDirectory()) {
            directory.mkdirs();
        }

        if (file.isDirectory())
            throw new FileIsException("Was created a directory instead of file.");

        if (file.exists()) {
            return file;
        }
        try {
            file.createNewFile();
            return file;
        } catch (IOException e) {
            throw new RuntimeException("Create file is failed!!!", e);
        }
    }
}
