import javax.swing.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.StandardCopyOption;
import java.util.HashMap;
import java.nio.file.Files;

public class Main {

    private static HashMap<String, String> fileTypes = new HashMap<>();

    static {
        fileTypes.put("PDF", "25504446");
        fileTypes.put("PNG", "89504E47");
        fileTypes.put("GIF", "47494638");
        fileTypes.put("WAV", "52494646");
        fileTypes.put("MOV", "00000020");
        fileTypes.put("MKV", "1A45DFA3");
        fileTypes.put("ZIP", "504B0506");
        fileTypes.put("DOC", "D0CF11E0");
        fileTypes.put("TAR", "75737461");
        fileTypes.put("FLAC", "664C6143");
        fileTypes.put("XLSX", "504B0304");
        fileTypes.put("BMP", "424D");
        fileTypes.put("AI", "25504446");
        fileTypes.put("LIB", "213C6172");
        fileTypes.put("RGB", "01DA0101");
        fileTypes.put("DRW", "01FF0204");
        fileTypes.put("DSS", "02647373");
        fileTypes.put("INDD", "0606EDF5");
        fileTypes.put("DOCX", "0D444F43");
        fileTypes.put("WEBM", "1A45DFA3");
        fileTypes.put("IMG", "514649FB");
        fileTypes.put("AVI", "52494646");
        fileTypes.put("RAR", "52617221");
        fileTypes.put("EML", "52657475");
        fileTypes.put("JAR", "5F27A889");
        fileTypes.put("CSH", "63757368");
        fileTypes.put("TORRENT", "64383A61");
        fileTypes.put("TXT", "330D0A34");
        fileTypes.put("MSG", "52006F00");
        fileTypes.put("JPG", "FFD8FFE0");
        fileTypes.put("XLS", "D0CF11E0");
        fileTypes.put("PPT", "D0CF11E0");
        fileTypes.put("ISO", "43443030");
        fileTypes.put("MP3", "49443303");
        fileTypes.put("MP4", "00000020");
        fileTypes.put("EXE", "4D5A");
        fileTypes.put("BAT", "40656368");
        fileTypes.put("JAVA", "CAFEBABE");
        fileTypes.put("HTML", "3C68746D");
        fileTypes.put("CSS", "2F2A2054");
        fileTypes.put("JS", "2F2A204A");
        fileTypes.put("XML", "3C3F786D");
        fileTypes.put("CPP", "2320496E");
        fileTypes.put("PHP", "3C3F7068");
        fileTypes.put("PY", "23202049");
        fileTypes.put("SQL", "2D2D2053");
        fileTypes.put("LOG", "74696D65");
        fileTypes.put("TIFF", "49492A00");
        fileTypes.put("ICO", "00000100");
        fileTypes.put("RTF", "7B5C7274");

    }

    public static String identifyFileType(File file) throws IOException {
        String extension = null;
        byte[] magicNumbers = new byte[4];
        try (FileInputStream fileInputStream = new FileInputStream(file)) {
            if (fileInputStream.read(magicNumbers) != -1) {
                StringBuilder hex = new StringBuilder();
                for (byte b : magicNumbers) {
                    hex.append(String.format("%02X", b));
                }
                String magicNumber = hex.toString();
                extension = fileTypes.entrySet().stream()
                        .filter(entry -> magicNumber.startsWith(entry.getValue()))
                        .map(HashMap.Entry::getKey)
                        .findFirst()
                        .orElse(null);
            }
        }
        return extension;
    }

    public static void main(String[] args) {
        JFileChooser fileChooser = new JFileChooser();
        int result = fileChooser.showOpenDialog(null);
        File file = null;
        if (result == JFileChooser.APPROVE_OPTION) {
            file = fileChooser.getSelectedFile();
            String desktopPath = file.getParent();
            File desktopFile = new File(desktopPath, file.getName());
            try {
                Files.copy(file.toPath(), desktopFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
                file = desktopFile;
            } catch (IOException e) {
                e.printStackTrace();
            }

        String for_ext = file.getName();
        int tochka = for_ext.lastIndexOf('.');
        String ext = (tochka == -1) ? for_ext : for_ext.substring(tochka + 1, for_ext.length());
        try {
            String fileType = identifyFileType(file);
            if (fileType != null && ext.equals(fileType.toLowerCase())){
                JFrame frame = new JFrame("Восстановление расширения файла");
                JLabel label = new JLabel("Файл не поврежден. Расширение: " + fileType);
                JPanel panel = new JPanel();
                panel.add(label);
                frame.getContentPane().add(panel);
                frame.setSize(400, 400);
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setVisible(true);
            } else if (fileType != null) {
                JFrame frame = new JFrame("Восстановление расширения файла");
                JLabel label = new JLabel("Тип расширения файла: " + fileType);
                JPanel panel = new JPanel();
                panel.add(label);
                frame.getContentPane().add(panel);
                frame.setSize(400, 400);
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setVisible(true);
                // восстановление утерянного расширения
                String name = file.getName();
                int dot = name.lastIndexOf('.');
                String woext = (dot == -1) ? name : name.substring(0, dot);
                String restoredFilename = woext + '.' + fileType.toLowerCase();
                File restoredFile = new File(file.getParent(), restoredFilename);
                if (file.renameTo(restoredFile)) {
                    JLabel label1 = new JLabel("Тип расширения файла изменен на корректный: " + restoredFile.getName());
                    panel.add(label1);
                    frame.getContentPane().add(panel);
                } else {
                    JLabel label2 = new JLabel("Произошла ошибка в восстановлении расширения. Попробуйте снова!");
                    panel.add(label2);
                    frame.getContentPane().add(panel);
                }
            } else {
                JFrame frame = new JFrame("Восстановление расширения файла");
                JLabel label = new JLabel("Не удалось распознать тип расширения файла.");
                JPanel panel = new JPanel();
                panel.add(label);
                frame.getContentPane().add(panel);
                frame.setSize(400, 400);
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setVisible(true);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        } else {
            System.out.println("Файл не выбран. Попробуйте снова!");
        }
    }
}

