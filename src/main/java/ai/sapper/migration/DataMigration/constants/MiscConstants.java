package ai.sapper.migration.DataMigration.constants;

import java.util.regex.Pattern;


public class MiscConstants {
    public static final String FILE_EXT_PDF=".pdf";
    public static final String FILE_EXT_HTML=".html";
    public static final String FILE_EXT_HTM=".htm";
    public static final String FILE_EXT_CSV=".csv";
    public static final String FILE_EXT_XLS=".xls";
    public static final String FILE_EXT_JSON=".json";
    public static final String FILE_EXT_XLSX=".xlsx";
    public static final String FILE_EXT_ZIP=".zip";
    public static final String INPUT_DIR="input";
    public static final String OUTPUT_DIR="output";
    public static final String INPUT_DIR_IMAGE="images";
    public static final String COMPRESS_DIR="zipped";
    public final static String EOL="";
    public static final String TEMP="temp";

    public static final String UNDERSCORE = "_";
    public static final String IMP_TAG = "Important";
    public static final String OTHER_TAG = "O";

    public static final String CLS_TOKEN_STR = "[CLS]";
    public static final String SEP_TOKEN_STR = "[SEP]";
    public static final String PAD_TOKEN_STR = "[PAD]";

    public static final String STR_TEMPLATE = "Template";
    public static final String TAGMAP_KEY_JOINER = "______";

    public static final Pattern rowTagPattern = Pattern.compile("(TABLE##)(.*)(##R)(\\d+)");
}

