package Configuration;

import Grade.MetricFactor;
import Grade.MetricGradeCalculations.RefExceedMetricGradeStrategy;
import Grade.MetricGradeCalculations.MetricGradeCalculator;
import Grade.MetricGradeCalculations.MetricGradeStrategy;
import Grade.MetricGradeCalculations.RawValueMetricGradeStrategy;
import Grade.Property;
import Report.MetricText;
import Util.FileHelper;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.FileInputStream;
import java.math.BigDecimal;
import java.math.MathContext;
import java.util.*;


public class ConfigurationManager {

    private final HashMap<String, Property> propertyMap;
    private final HashMap<String, MetricGradeCalculator> calculatorMap;
    private final HashMap<String, MetricGradeStrategy> strategyNameMap;
    private final HashMap<String, MetricText> metricTextMap;
    private final HashMap<String, Object> settingsMap;

    private static ConfigurationManager instance = null;


    private static final String RESOURCES_DIR = "/resources";

    private static final String CONFIG_DIR = RESOURCES_DIR + "/config";
    private static final String CONFIG_METRICS_DIR = CONFIG_DIR + "/metrics";
    private static final String CONFIG_METRICS_REF_VALUES = CONFIG_METRICS_DIR + "/ref_values.json";
    private static final String CONFIG_PROPS_DIR = CONFIG_DIR + "/props";

    private static final String TEXT_DIR = RESOURCES_DIR + "/text";
    private static final String TEXT_METRICS_DIR = TEXT_DIR + "/metrics";
    private static final String TEXT_HTML_DIR = TEXT_METRICS_DIR + "/html";
    private static final String TEXT_REPORT_TEMPLATES_DIR = TEXT_DIR + "/report";

    private static final String SETTINGS_FILE = CONFIG_DIR + "/settings.json";


    private static final String HTML_DESCRIPTION_KEY = "@html";

    public static final String SETTING_LOGGER_ENABLED = "logging_enabled";
    public static final String SETTING_MAX_GRADE = "max_grade";

    private JSONObject loadJsonFromFile(String filePath) {
        try {
            JSONTokener jsonTokener = new JSONTokener(new FileInputStream(filePath));

            return new JSONObject(jsonTokener);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    private void loadMetricRefValues() {
        try {
            JSONObject jsonObject = loadJsonFromFile(FileHelper.getLocalFilePath(CONFIG_METRICS_REF_VALUES));
            Set<String> keys = jsonObject.keySet();
            for (String metricId : keys) {
                JSONObject metricJsonObject = jsonObject.getJSONObject(metricId);
                double refValue = metricJsonObject.getDouble("reference");
                String strategy = metricJsonObject.getString("strategy");
                calculatorMap.put(metricId, new MetricGradeCalculator(strategyNameMap.get(strategy), refValue));
            }
        }
        catch (Exception e) {
            throw new RuntimeException("Unable to load metric reference value configuration!");
        }
    }

    private void loadProperties() {
        try {
            String currentDir = FileHelper.getWorkingDir();
            List<String> propertyFileNames = FileHelper.findFilesInDirectory(currentDir + CONFIG_PROPS_DIR);

            for (String propFilePath : propertyFileNames) {

                Map<String, MetricFactor> metricFactorMap = new HashMap<>();
                JSONObject propJsonObject = loadJsonFromFile(propFilePath);
                JSONArray factorArray = propJsonObject.getJSONArray("factors");

                BigDecimal checksum = new BigDecimal(0);

                for (int i = 0; i < factorArray.length(); i++) {
                    JSONObject factorJsonObject = factorArray.getJSONObject(i);
                    String metricId = factorJsonObject.getString("metric");
                    double value = factorJsonObject.getDouble("factor");

                    metricFactorMap.put(metricId, new MetricFactor(metricId, value));

                    checksum = checksum.add(new BigDecimal(value));
                }

                if (checksum.round(new MathContext(2)).compareTo(new BigDecimal(1.0)) != 0) {
                    throw new RuntimeException("Checksum failure, actual value = " + checksum);
                }

                String propId = FileHelper.getFileNameFromPath(propFilePath);
                String name = propJsonObject.getString("title");

                propertyMap.put(propId, new Property(propId, name, metricFactorMap));
            }
        }
        catch (Exception ex) {
            throw new RuntimeException("Unable to load property definitions!");
        }
    }

    private void loadMetricTextMap() {
        try {
            String textFilesPath = FileHelper.getLocalFilePath(TEXT_METRICS_DIR);
            List<String> allFiles = FileHelper.findFilesInDirectory(textFilesPath);
            for (String filePath : allFiles) {
                String metricId = FileHelper.getFileNameFromPath(filePath, false);
                JSONObject jsonObject = loadJsonFromFile(filePath);

                String title = jsonObject.getString("title");
                String description = jsonObject.getString("desc");

                if (HTML_DESCRIPTION_KEY.equals(description)) {
                    String htmlDescFilePath = TEXT_HTML_DIR + "/" + metricId + ".html";
                    description = FileHelper.loadTextFile(FileHelper.getLocalFilePath(htmlDescFilePath));
                }

                metricTextMap.put(metricId, new MetricText(metricId, title, description));
            }
        }
        catch (Exception e) {
            throw new RuntimeException("Unable to load metric text descriptions!");
        }
    }

    private void loadSettings() {
        try {
            JSONObject jsonSettings = loadJsonFromFile(FileHelper.getLocalFilePath(SETTINGS_FILE));
            for (String key : jsonSettings.keySet()) {
                settingsMap.put(key, jsonSettings.get(key));
            }
        }
        catch (Exception e) {
            throw new RuntimeException("Unable to load settings!");
        }
    }

    private ConfigurationManager() {

        strategyNameMap = new HashMap<>();
        metricTextMap = new HashMap<>();
        propertyMap = new HashMap<>();
        calculatorMap = new HashMap<>();
        settingsMap = new HashMap<>();
        strategyNameMap.put(MetricGradeStrategy.REF_EXCEED, new RefExceedMetricGradeStrategy());
        strategyNameMap.put(MetricGradeStrategy.RAW_VALUE, new RawValueMetricGradeStrategy());

        loadMetricRefValues();
        loadProperties();
        loadMetricTextMap();
        loadSettings();
    }

    public static ConfigurationManager getInstance() {
        if (instance == null) {
            instance = new ConfigurationManager();
        }

        return instance;
    }

    public static void reset() {
        instance = null;
    }

    public Map<String, MetricGradeCalculator> getDefaultCalculatorMap() {
        return (Map<String, MetricGradeCalculator>)calculatorMap.clone();
    }

    public Map<String, Property> getDefaultProperties() {
        return (Map<String, Property>) propertyMap.clone();
    }

    public MetricText getMetricText(String metricId) {
        return metricTextMap.get(metricId);
    }

    public Object getSetting(String key) {
        return settingsMap.get(key);
    }

    public void setSetting(String key, Object value) {
        settingsMap.put(key, value);
    }

    public String getReportTemplatesPath() {
        return TEXT_REPORT_TEMPLATES_DIR;
    }
}
