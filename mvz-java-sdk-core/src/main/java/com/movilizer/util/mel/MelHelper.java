package com.movilizer.util.mel;

import com.movilizer.util.resource.IResourceTextLoader;
import com.movilizer.util.resource.ResourceTextLoader;

import java.io.*;
import java.util.Map;

import static com.movilizer.util.dbc.Ensure.ensureNotNull;
import static com.movilizer.util.dbc.Ensure.ensureNotNullOrEmpty;
import static java.lang.String.format;

/**
 * @author Peter.Grigoriev@movilizer.com
 */
public class MelHelper {

    // Device-specific, move to MEL
    public static String getLocalFilePath(String resourceName) {
        return format("concat($local:webViewDirectory, \"/\", \"%s\")", resourceName);
    }

    public static String saveOnDevice(String fileName, Reader text) throws IOException {
        ensureNotNullOrEmpty(fileName, "file name");
        ensureNotNull(text, "text");
        String result = getMelTemplateToCreateFile().replace("%FILE_NAME%", fileName);
        String lines = toMelWriteTextLines("connectionId", text);
        result = result.replace("%WRITE_TEXT_LINES%", lines);
        return result;
    }

    public static String toMelWriteTextLines(String connectionId, String source) throws IOException {
        return toMelWriteTextLines(connectionId, new StringReader(source));
    }

    public static String toMelWriteTextLines(String connectionId, Reader reader) throws IOException {
        ensureNotNullOrEmpty(connectionId, "connectionId");
        ensureNotNull(reader, "reader");

        LineNumberReader lineNumberReader = new LineNumberReader(reader);
        StringWriter res = new StringWriter();
        PrintWriter writer = new PrintWriter(res, true);


        String line;
        while ((line = lineNumberReader.readLine()) != null) {
            line = line.trim();
            if (!line.isEmpty()) {
                writer.println(toMelWriteTextLine(connectionId, line));
            }
        }

        return res.toString();
    }

    private static String toMelWriteTextLine(String connectionId, String line) {
        //String resultLine = escapeXml(line);
        line = line.trim();
        String resultLine = escapeBadCharactersWith222Code(line);


        if (resultLine.contains("#222")) {
            return format("writeTextLine(%s, strReplace(\"%s\", \"#222\", '\"'));", connectionId, resultLine);
        }
        return format("writeTextLine(%s, \"%s\");", connectionId, resultLine);
    }

    private static String escapeBadCharactersWith222Code(String line) {
        String resultLine = line.replace("\"", "#222");
        resultLine = resultLine.replace("&quot;", "#222");
        resultLine = resultLine.replace("&gt;", ">");
        resultLine = resultLine.replace("\\", "\\\\");
        return resultLine;
    }

    public static String getMelTemplateToCreateFile() {
        return getMelTemplate("writeFile.mel");
    }

    private static IResourceTextLoader resourceTextLoader = ResourceTextLoader.forClass(MelHelper.class);

    public static String getMelTemplate(String templateName) {
        return resourceTextLoader.getResourceAsString(templateName);
    }

    public static String toMelWriteTextLineWithReplacements(String connectionId, String text, Map<String, String> replacements) {
        text = text.trim();
        String resultLine = escapeBadCharactersWith222Code(text);


        String mel = "tmpLine = " + "\"" + resultLine + "\";\n";
        for (String toReplace : replacements.keySet()) {
            mel += format("tmpLine = strReplace(tmpLine, \"%s\", %s);\n", toReplace, replacements.get(toReplace));
        }

        mel += format("tmpLine = strReplace(tmpLine, \"#222\", '\"');\n");
        mel += format("writeTextLine(%s, tmpLine);\n", connectionId);

        return mel;
    }
}