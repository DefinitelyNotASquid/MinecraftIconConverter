package sample;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.stream.Stream;

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;


/**
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this
 * software and associated documentation files (the "Software"), to deal in the Software
 * without restriction, including without limitation the rights to use, copy, modify,
 * merge, publish, distribute, sublicense, and/or sell copies of the Software, and to
 * permit persons to whom the Software is furnished to do so, subject to the following
 * conditions:
 * <p>
 * The above copyright notice and this permission notice shall be included in all copies
 * or substantial portions of the Software.
 * <p>
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED,
 * INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A
 * PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT
 * HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF
 * CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE
 * OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
public class Main extends Application {

    public void start(Stage primaryStage) throws Exception {
        Stage window = primaryStage;

        window.setTitle("Mine-Craft Icon Mapper");

        GroupBox SpritesGroup = new GroupBox();
        GroupBox OptionsGroup = new GroupBox();

        SpritesGroup.setText("Sprite Sheet Selector");
        OptionsGroup.setText("Options");

        GridPane spritesGridPane = new GridPane();
        GridPane optionsGridPane = new GridPane();
        GridPane MainBorderPane = new GridPane();
        ColumnConstraints cc = new ColumnConstraints();

        cc.setMinWidth(350);

        SpritesGroup.setContent(spritesGridPane);
        OptionsGroup.setContent(optionsGridPane);

        spritesGridPane.getColumnConstraints().add(cc);
        optionsGridPane.getColumnConstraints().add(cc);

        TabPane tabPane = new TabPane();
        Tab tab1 = new Tab();
        tab1.setText("General");
        tabPane.getTabs().add(tab1);
        tab1.setClosable(false);
        tab1.setContent(MainBorderPane);


        MainBorderPane.add(SpritesGroup, 0, 0);
        MainBorderPane.add(OptionsGroup, 0, 1);


        RadioButton biome = new RadioButton("biome");
        RadioButton block = new RadioButton("block");
        RadioButton effect = new RadioButton("effects");
        RadioButton entity = new RadioButton("entity");
        RadioButton env = new RadioButton("env");
        RadioButton item = new RadioButton("item");
        RadioButton inventory = new RadioButton("inv");

        spritesGridPane.add(biome, 0, 0);
        spritesGridPane.add(block, 0, 1);
        spritesGridPane.add(effect, 0, 2);
        spritesGridPane.add(entity, 0, 3);
        spritesGridPane.add(env, 0, 4);
        spritesGridPane.add(item, 0, 5);
        spritesGridPane.add(inventory, 0, 6);


        biome.setSelected(true);
        block.setSelected(true);
        effect.setSelected(true);
        entity.setSelected(true);
        env.setSelected(true);
        item.setSelected(true);
        inventory.setSelected(true);

        CheckBox removeOldTextures = new CheckBox("Remove all Old Textures");
        CheckBox removeOldRevisions = new CheckBox("Remove all Old Revisions");

        optionsGridPane.add(removeOldTextures, 0, 0);
        optionsGridPane.add(removeOldRevisions, 0, 1);

        Button convertMappings = new Button("Convert Mappings");
        Button create_icons = new Button("Create Icons");
        Button exportImageSet = new Button("Create Data Pack");

        exportImageSet.setMinWidth(60);
        convertMappings.setMinWidth(60);
        create_icons.setMinWidth(60);

        ToolBar toolbarhbox = new ToolBar();
        toolbarhbox.setStyle("-fx-background-insets: 0, 0 0 0 0;");


        toolbarhbox.getItems().addAll(convertMappings, new Separator(), create_icons, new Separator(), exportImageSet);
        Region region2 = new Region();
        HBox.setHgrow(region2, Priority.ALWAYS);

        ToolBar toolBar = new ToolBar(

                region2, toolbarhbox


        );

        exportImageSet.setOnAction(e -> {

            File DataPack = new File(System.getProperty("user.dir") + "/DataPack");
            File assets = new File(System.getProperty("user.dir") + "/DataPack/assets");
            File minecraft = new File(System.getProperty("user.dir") + "/DataPack/assets/minecraft");
            File textures = new File(System.getProperty("user.dir") + "/DataPack/assets/minecraft/textures");
            File font = new File(System.getProperty("user.dir") + "/DataPack/assets/minecraft/font");
            File textures_font = new File(System.getProperty("user.dir") + "/DataPack/assets/minecraft/textures/font/");

            if (!DataPack.exists()) {
                DataPack.mkdir();
            }
            if (!assets.exists()) {
                assets.mkdir();
            }
            if (!minecraft.exists()) {
                minecraft.mkdir();
            }
            if (!textures.exists()) {
                textures.mkdir();
            }
            if (!font.exists()) {
                font.mkdir();
            }
            if (!textures_font.exists()) {
                textures_font.mkdir();
            }



            List<String> sl = new ArrayList<>();
            if (biome.isSelected()) {
                sl.add("biome");
            }
            if (block.isSelected()) {
                sl.add("block");
            }
            if (effect.isSelected()) {
                sl.add("effects");
            }
            if (entity.isSelected()) {
                sl.add("entity");
            }
            if (env.isSelected()) {
                sl.add("env");
            }
            if (item.isSelected()) {
                sl.add("item");
            }
            if (inventory.isSelected()) {
                sl.add("inv");
            }

        });

        convertMappings.setOnAction(e -> {

            try {
                renameEmojis();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }

            String BiomePath = System.getProperty("user.dir") + "/Unmapped/" + "biome.txt";
            String BlockPath = System.getProperty("user.dir") + "/Unmapped/" + "block.txt";
            String EffectsPath = System.getProperty("user.dir") + "/Unmapped/" + "effects.txt";
            String EntityPath = System.getProperty("user.dir") + "/Unmapped/" + "entity.txt";
            String EnvPath = System.getProperty("user.dir") + "/Unmapped/" + "env.txt";
            String ItemPath = System.getProperty("user.dir") + "/Unmapped/" + "item.txt";
            String InvPath = System.getProperty("user.dir") + "/Unmapped/" + "inv.txt";

            try {
                if (biome.isSelected()) {
                    removeMappingAndWriteFile(BiomePath, "biome", removeOldRevisions.isSelected(), removeOldTextures.isSelected());
                }
                if (block.isSelected()) {
                    removeMappingAndWriteFile(BlockPath, "block", removeOldRevisions.isSelected(), removeOldTextures.isSelected());
                }
                if (effect.isSelected()) {
                    removeMappingAndWriteFile(EffectsPath, "effects", removeOldRevisions.isSelected(), removeOldTextures.isSelected());
                }
                if (entity.isSelected()) {
                    removeMappingAndWriteFile(EntityPath, "entity", removeOldRevisions.isSelected(), removeOldTextures.isSelected());
                }
                if (env.isSelected()) {
                    removeMappingAndWriteFile(EnvPath, "env", removeOldRevisions.isSelected(), removeOldTextures.isSelected());
                }
                if (item.isSelected()) {
                    removeMappingAndWriteFile(ItemPath, "item", removeOldRevisions.isSelected(), removeOldTextures.isSelected());
                }
                if (inventory.isSelected()) {
                    removeMappingAndWriteFile(InvPath, "inv", removeOldRevisions.isSelected(), removeOldTextures.isSelected());
                }

            } catch (IOException e1) {
                e1.printStackTrace();
            }
            System.out.println("Trying to update");
        });


        create_icons.setOnAction(e -> {

            List<String> sl = new ArrayList<>();
            if (biome.isSelected()) {
                sl.add("biome");
            }
            if (block.isSelected()) {
                sl.add("block");
            }
            if (effect.isSelected()) {
                sl.add("effects");
            }
            if (entity.isSelected()) {
                sl.add("entity");
            }
            if (env.isSelected()) {
                sl.add("env");
            }
            if (item.isSelected()) {
                sl.add("item");
            }
            if (inventory.isSelected()) {
                sl.add("inv");
            }

            StringBuilder fontJson = new StringBuilder();

            fontJson.append("{\n" +
                    "\t\"providers\": [\n" +
                    "\t\t{");
            fontJson.append("\n" +
                    "\t\t\t\"type\": \"bitmap\",\n" +
                    "\t\t\t\"file\": \"minecraft:font/minecraft_0.png\",\n" +
                    "\t\t\t\"width\": 8,\n" +
                    "\t\t\t\"height\": 8,\n" +
                    "\t\t\t\"ascent\": 7,\n" +
                    "\t\t\t\"chars\": [\n\t\t\t\t");

            List<FinalMap> minecraftMap = new ArrayList<>();
            try {
                minecraftMap = arrange(sl);
            } catch (IOException e1) {
                e1.printStackTrace();
            }

            int codePoint = 983040;
            int codePointEmoji = 993040;

            fontJson = writeSection(codePoint, "minecraft", minecraftMap, fontJson);

            fontJson.append("]\n" +
                    "\t\t}," +
                    "\n\t\t{\n" +
                    "\t\t\t\"type\": \"bitmap\",\n" +
                    "\t\t\t\"file\": \"minecraft:font/emoji_0.png\",\n" +
                    "\t\t\t\"width\": 8,\n" +
                    "\t\t\t\"height\": 8,\n" +
                    "\t\t\t\"ascent\": 7,\n" +
                    "\t\t\t\"chars\": [\n\t\t\t\t");

            try {
                List<FinalMap> mapList = getEmojisAsImageMap();
                fontJson = writeSection(codePointEmoji, "emoji", mapList, fontJson);
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }

            fontJson.append("]\n" +
                    "\t\t}\n" +
                    "\t]\n" +
                    "}");

            try (BufferedWriter bw = new BufferedWriter(new FileWriter(System.getProperty("user.dir") + "/DataPack/assets/minecraft/font/default.json"))) {
                final int aLength = fontJson.length();
                final int aChunk = 1024;// 1 kb buffer to read data from
                final char[] aChars = new char[aChunk];

                for (int aPosStart = 0; aPosStart < aLength; aPosStart += aChunk) {
                    final int aPosEnd = Math.min(aPosStart + aChunk, aLength);
                    fontJson.getChars(aPosStart, aPosEnd, aChars, 0); // Create no new buffer
                    bw.write(aChars, 0, aPosEnd - aPosStart);// This is faster than just copying one byte at the time
                }
                bw.flush();
            } catch (IOException ex) {
                ex.printStackTrace();
            }

            File destinationfile = new File(System.getProperty("user.dir") + "/DataPack/assets/minecraft/textures/font/");
            Path destination = Paths.get(System.getProperty("user.dir") + "/DataPack/assets/minecraft/textures/font/");
            Path icon_path = Paths.get(System.getProperty("user.dir") + "/Icons/");
            try {
                deleteDir(destinationfile);
                copyFolder(icon_path, destination);
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }

            System.out.println("Finished Creating Data Pack");

        });

        MainBorderPane.add(toolBar, 0, 2);

        Scene MainScene = new Scene(tabPane, 640, 480);

        window.setScene(MainScene);
        window.setMaximized(true);
        window.show();

        window.setOnCloseRequest(e -> {
            e.consume();
            ConfirmBox.handleClose(window, "Exit", "Sure you want to exit?");
        });
    }

    public StringBuilder writeSection(int codePoint, String sheetType, List<FinalMap> fullMap, StringBuilder fontJson){

        StringBuilder typeMappingsFile = new StringBuilder();
        int sheetName = 0;
        int count = 0;
        BufferedImage bi = new BufferedImage(72,18432, BufferedImage.TYPE_INT_ARGB);

        int subVal = 0;
        int amount = 1;

        for (FinalMap y : fullMap) {



            Graphics g = bi.getGraphics();
            g.drawImage(y.getIcon(), 0, 72*subVal, null);
            g.dispose();
            if(count != 0 && count%256==0){

                fontJson.append("]\n" +
                        "\t\t}," +
                        "\n\t\t{\n" +
                        "\t\t\t\"type\": \"bitmap\",\n" +
                        "\t\t\t\"file\": \"minecraft:font/"+sheetType+"_"+amount+".png\",\n" +
                        "\t\t\t\"width\": 8,\n" +
                        "\t\t\t\"height\": 8,\n" +
                        "\t\t\t\"ascent\": 7,\n" +
                        "\t\t\t\"chars\": [\n\t\t\t\t");
                amount++;


                subVal=0;
                try {

                    ImageIO.write(bi, "png", new File(System.getProperty("user.dir")+ "/Icons/" +sheetType+"_"+sheetName+ ".png"));
                    clearImage(bi);

                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                sheetName++;
            }


            char[] charPair = Character.toChars(codePoint + count);
            String symbol = new String(charPair);

            typeMappingsFile.append(symbol + ":" + y.name + "\n");

            fontJson.append("\""+symbol+"\""+"");

            if((count + 1)%256 != 0 && count != fullMap.size()-1){
                fontJson.append(",");
            }
            if((count + 1)%8 == 0){
                fontJson.append("\n\t\t\t\t");
            }


            subVal++;
            count++;
        }

        BufferedImage croppedImage = bi.getSubimage(
                0,
                0,
                72,
                fullMap.size()%256*72
        );

        try {
            ImageIO.write(croppedImage, "png", new File(System.getProperty("user.dir")+ "/Icons/" + sheetType+"_" +sheetName + ".png"));
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(System.getProperty("user.dir") + "/" + sheetType + ".txt"))) {
            final int aLength = typeMappingsFile.length();
            final int aChunk = 1024;// 1 kb buffer to read data from
            final char[] aChars = new char[aChunk];

            for (int aPosStart = 0; aPosStart < aLength; aPosStart += aChunk) {
                final int aPosEnd = Math.min(aPosStart + aChunk, aLength);
                typeMappingsFile.getChars(aPosStart, aPosEnd, aChars, 0); // Create no new buffer
                bw.write(aChars, 0, aPosEnd - aPosStart);// This is faster than just copying one byte at the time
            }
            bw.flush();
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        return fontJson;
    }

    public static void clearImage(BufferedImage bufferedImage) {
        Graphics2D g = bufferedImage.createGraphics();
        g.setBackground(new Color(0, 0, 0, 0));
        g.clearRect(0, 0, bufferedImage.getWidth(),
                bufferedImage.getHeight());
        g.dispose();// w  ww . j  a  v  a  2 s.  c  om
    }

    public void deleteDir(File file) {
        File[] contents = file.listFiles();
        if (contents != null) {
            for (File f : contents) {
                deleteDir(f);
            }
        }
        file.delete();
    }


    public void renameEmojis() throws IOException {

        InputStream is = new FileInputStream(System.getProperty("user.dir") + "/EmojiData/data/emojis.csv");
        BufferedReader buf = new BufferedReader(new InputStreamReader(is));

        String line = buf.readLine();

        while (line != null) {

            String[] arrOfStr = line.split(",");
            File f = new File(System.getProperty("user.dir") + "/EmojiData/icons/"+ arrOfStr[0] + ".png");
            if(f.exists()){
                boolean renameResult = f.renameTo(new File(System.getProperty("user.dir") + "/EmojiData/icons/"+ arrOfStr[1] + ".png"));
                if(renameResult){
                    System.out.println("Rename Success");
                }else{
                    System.out.println("Rename Failed");
                }
            }else{
                System.out.println("File Not Exist");
            }


            line = buf.readLine();
        }
    }

    public List<FinalMap> getEmojisAsImageMap() throws IOException {

        List<FinalMap> readmapping = new ArrayList<FinalMap>();
        InputStream is = new FileInputStream(System.getProperty("user.dir") + "/EmojiData/emojis.txt");
        BufferedReader buf = new BufferedReader(new InputStreamReader(is));

        String line = buf.readLine();

        while (line != null) {

            FinalMap finalMap = new FinalMap();
            BufferedImage buffImage = ImageIO.read(new File(System.getProperty("user.dir") + "/EmojiData/icons/" + line + ".png"));

            finalMap.setIcon(buffImage);
            finalMap.setName(line);
            finalMap.setType("Emojis");

            readmapping.add(finalMap);

            line = buf.readLine();
        }

        return readmapping;
    }

    public List<FinalMap> arrange(List<String> fileList) throws IOException {

        List<FinalMap> mainMapping = new ArrayList<>();

        for (String s : fileList) {

            List<YamlMap> readmapping = new ArrayList<YamlMap>();
            InputStream is = new FileInputStream(System.getProperty("user.dir") + "/Mapped/" + s + ".yaml");
            System.out.println("Reading file to parse int : " + s);
            BufferedReader buf = new BufferedReader(new InputStreamReader(is));

            String line = buf.readLine();

            while (line != null) {

                String[] arrOfStr = line.split(":");
                YamlMap yamlmap = new YamlMap(arrOfStr[0], Integer.parseInt(arrOfStr[1]));
                readmapping.add(yamlmap);
                line = buf.readLine();
            }

            Collections.sort(readmapping);

            BufferedImage bigImg = ImageIO.read(new File(System.getProperty("user.dir") + "/Sprites/" + s + ".png"));


            final int width = 72;
            final int height = 72;

            final int rows = bigImg.getHeight() / 72;
            final int cols = bigImg.getWidth() / 72;

            System.out.println("Image File Information: \n Width: " + width + " Height: " + height + " Rows: " + rows + " Cols: " + cols + " Name: " + s + ".png");

            int count = 1;
            for (int i = 0; i < rows; i++) {
                for (int j = 0; j < cols; j++) {
                    for (YamlMap ym : readmapping) {
                        if (ym.pos == count) {
                            System.out.println("Creating final map for : " + ym.name + " at position : " + ym.pos);
                            FinalMap fm = new FinalMap();
                            fm.setIcon(bigImg.getSubimage(
                                    j * width,
                                    i * height,
                                    width,
                                    height
                            ));
                            fm.name = ym.name;
                            fm.setType(s);
                            mainMapping.add(fm);
                        }
                    }
                    count++;
                }
            }

        }
        return mainMapping;
    }


    public void writeSingleSheet(String filename, int imageWidth, int imageHeight, List<FinalMap> finalMaps) throws IOException {

        BufferedImage result = new BufferedImage(imageWidth, imageHeight, BufferedImage.TYPE_INT_ARGB);
        Graphics g = result.getGraphics();

        int x = 0;
        int y = 0;
        int count = 1;
        for (FinalMap fm : finalMaps) {
            System.out.println("Writing Image Icon " + fm.name + " at X coord : " + x + " and Y coord: " + y);
            System.out.println("Final map local number:" + count);
            count++;
            BufferedImage bi = fm.icon;
            g.drawImage(bi, x, y, null);
            x += 16;
            if (x >= result.getWidth()) {
                x = 0;
                y += bi.getHeight();
            }
        }

        ImageIO.write(result, "png", new File(System.getProperty("user.dir") + "/DataPack/" + filename + ".png"));
    }


    public void removeMappingAndWriteFile(String filePath, String filename, boolean removeOldRevisions, boolean removeOldTextures) throws IOException {

        InputStream is = new FileInputStream(filePath);
        BufferedReader buf = new BufferedReader(new InputStreamReader(is));

        String line = buf.readLine();
        StringBuilder sb = new StringBuilder();

        while (line != null) {

            if (removeOldRevisions) {
                if (line.contains("revision")) {
                    line = "";
                }
            }
            if (removeOldTextures) {
                if (line.contains("pre-texture")) {
                    line = "";
                }
            }

            sb.append(line).append("\n");
            line = buf.readLine();
        }
        saveFile("/Mapped/", filename, ".yaml", yamlFormat(sb.toString()));
    }

    /***
     * Cheap way of removing all of the garbage left over from lua script
     * @param fileReadString
     * @return
     */
    public String yamlFormat(String fileReadString) {
        String pos = "\t\tpos ";
        String sheetsize = "\t\tsheetsize";
        String preformat1 = "return {" + "\n";
        String preformat2 = " = {";
        String preformat3 = " = '";
        String preformat4 = "{ name: ";
        String preformat5 = "['";
        String preformat6 = "']";
        String preformat51 = "[\"";
        String preformat61 = "\"]";
        String preformat7 = " = { pos = ";
        String preformat8 = "\t\t";
        String preformat9 = "\t";
        String preformat10 = "}";
        String preformat11 = "',";
        String preformat12 = " id = ";
        String preformat13 = " },";
        String preformat14 = "\n" + "},";
        String preformat15 = " = ";
        String preformat16 = ",";
        String preformat17 = "{";
        String preformat18 = "*";
        String preformat19 = "(";
        String preformat20 = ")";
        String preformat21 = "+";
        String preformat22 = "!";
        String preformat23 = "???";

        fileReadString = fileReadString.replace(pos, "pos ");
        fileReadString = fileReadString.replaceAll("\tids = .*", "");
        fileReadString = fileReadString.replaceAll("sheetsize = .*", "");
        fileReadString = fileReadString.replaceAll("align = .*", "");
        fileReadString = fileReadString.replaceAll("linkprefix = .*", "");
        fileReadString = fileReadString.replaceAll("\tsize = .*", "");
        fileReadString = fileReadString.replaceAll("classname = .*", "");
        fileReadString = fileReadString.replaceAll("image = .*", "");
        fileReadString = fileReadString.replaceAll("url = .*", "");
        fileReadString = fileReadString.replaceAll("name = .*", "");
        fileReadString = fileReadString.replaceAll("settings = .*", "");
        fileReadString = fileReadString.replaceAll("sections = .*", "");
        fileReadString = fileReadString.replaceAll("stylesheet = .*", "");
        fileReadString = fileReadString.replace(preformat7, ":");
        fileReadString = fileReadString.replace(preformat1, "");
        fileReadString = fileReadString.replace(preformat2, ":");
        fileReadString = fileReadString.replace(preformat3, ": ");
        fileReadString = fileReadString.replace(preformat4, "");
        fileReadString = fileReadString.replace(preformat5, "");
        fileReadString = fileReadString.replace(preformat6, "");
        fileReadString = fileReadString.replace(preformat51, "");
        fileReadString = fileReadString.replace(preformat61, "");
        fileReadString = fileReadString.replace(preformat8, "");
        fileReadString = fileReadString.replace(preformat9, "");
        fileReadString = fileReadString.replace(preformat10, "");
        fileReadString = fileReadString.replace(preformat11, "");
        fileReadString = fileReadString.replace(preformat12, ":");
        fileReadString = fileReadString.replace(preformat13, "");
        fileReadString = fileReadString.replace(preformat14, "");
        fileReadString = fileReadString.replaceAll(", section = .*", "");
        fileReadString = fileReadString.replaceAll("url = .*", "");
        fileReadString = fileReadString.replaceAll("^(sheetsize).*", "");
        fileReadString = fileReadString.replace(preformat15, ":");
        fileReadString = fileReadString.replace(preformat16, "");
        fileReadString = fileReadString.replace(preformat17, "");
        fileReadString = fileReadString.replace(preformat18, "");
        fileReadString = fileReadString.replace(preformat19, "");
        fileReadString = fileReadString.replace(preformat20, "");
        fileReadString = fileReadString.replace(preformat21, "-plus");
        fileReadString = fileReadString.replace(preformat22, "-exclamation-mark");
        fileReadString = fileReadString.replace(preformat23, "three-question-marks");
        fileReadString = fileReadString.replace("'", "");
        fileReadString = fileReadString.replaceAll("(?m)^[ \t]*\r?\n", "");
        fileReadString = fileReadString.replace(" ", "-");
        fileReadString = fileReadString.replaceAll("pos:.*", "");
        fileReadString = fileReadString.replaceAll("(?m)^[ \t]*\r?\n", "");
        fileReadString = fileReadString.toLowerCase(Locale.ROOT);
        return fileReadString;
    }


    /***
     * Saves file given a folderpath, filename, fileExtension and String content
     * @param folderpath
     * @param fileName
     * @param fileExtension
     * @param content
     */
    private void saveFile(String folderpath, String fileName, String fileExtension, String content) {

        new File(System.getProperty("user.dir") + folderpath).mkdirs();
        try (Writer writer = new BufferedWriter(new OutputStreamWriter(

                new FileOutputStream(System.getProperty("user.dir") + folderpath + fileName.toLowerCase() + fileExtension), "utf-8"))) {

            writer.write(content);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    public void copyFolder(Path src, Path dest) throws IOException {
        try (Stream<Path> stream = Files.walk(src)) {
            stream.forEach(source -> copy(source, dest.resolve(src.relativize(source))));
        }
    }

    private void copy(Path source, Path dest) {
        try {
            Files.copy(source, dest, REPLACE_EXISTING);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }


}