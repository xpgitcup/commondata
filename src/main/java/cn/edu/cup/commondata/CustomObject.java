package cn.edu.cup.commondata;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;

import java.io.File;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import static cn.edu.cup.commondata.Tools.readFromFile;
import static cn.edu.cup.commondata.Tools.writeToFile;
import org.apache.commons.math3.analysis.polynomials.PolynomialFunction;

public abstract class CustomObject {

    private String dataPath;
    private final RawData rawData;
    private Map<String, String> propertySettings;
    private final Map<String, PolynomialFunction> propertyPolies;
    // 记录日志
    public static final Logger LOG = Logger.getLogger(CustomObject.class.getName());

    /**
     * 构造函数
     */
    public CustomObject() {
        rawData = new RawData();
        setupRawData();
        propertySettings = new HashMap<>();
        propertyPolies = new HashMap<>();

        String configFileName = "config/" + this.getClass().getSimpleName() + ".json";
        File file = new File(configFileName);
//        System.out.println("构造函数：");
//        System.out.println(file.getAbsolutePath());
        if (file.exists()) {
            String s = readFromFile(file);
            propertySettings = JSON.parseObject(s, HashMap.class);
        } else {
            for (Field e : this.getClass().getDeclaredFields()) {
                propertySettings.put(e.getName(), "");
            }
            writeToFile(file, JSON.toJSONString(propertySettings, SerializerFeature.PrettyFormat));
        }
    }

    protected void setupRawData() {
        rawData.newItem("名称", DataValueType.scalar, "", "通用对象");
        rawData.newItem("型号", DataValueType.scalar, "", "");
    }

    @Override
    public String toString() {
        return getName() + ":" + getModel();
    }

    /*
     * 关键函数定义
     * --具体数据的导入，导出
     * --属性数据的赋值
     * */

 /*
     * 将属性的数据更新到字符串中
     * */
    private void exportToRawData() {
        for (Field f : this.getClass().getDeclaredFields()) {
            try {
                System.out.println(f.getName());
                String fname = f.getName();
                String key = propertySettings.get(fname);
                f.setAccessible(true);
                if (!key.isEmpty()) {
                    getRawData().setValue(key, JSON.toJSONString(f.get(this)));
                }
            } catch (IllegalArgumentException | IllegalAccessException ex) {
                Logger.getLogger(CustomObject.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    /*
     * 更新属性数据
     * */
    private void updateProperties() {
        System.out.println("开始赋值：");
        try {
            for (Field f : this.getClass().getDeclaredFields()) {
                System.out.println(f.getName());
                String fname = f.getName();
                String key = propertySettings.get(fname);
                if ((key != null) && (!key.isEmpty())) {
                    System.out.println("赋值....");
                    DataItem item = getRawData().getDataItems().get(key);
                    f.setAccessible(true);
                    switch (item.getDataValueType()) {
                        case scalar:
                            f.setDouble(this, getKeyDouble(key));
                            break;
                        case vector:
                            double[] x = getKeyVector(key);
                            f.set(this, x);
                            if (propertyPolies.get(fname) == null) {
                                propertyPolies.put(fname, new PolynomialFunction(x));
                            }
                            break;
                        case vector2D:
                            f.set(this, getKeyVector2D(key));
                            break;
                        case object:
                            break;
                        default:
                            throw new IllegalStateException("Unexpected value: " + item.getDataValueType());
                    }
                }
            }
        } catch (IllegalAccessException | IllegalArgumentException | IllegalStateException | SecurityException e) {
            LOG.log(Level.INFO, e.getMessage());
        }
    }

    /*
     * 输出到字符串
     * */
    public String exportToString() {
        exportToRawData();
        return JSON.toJSONString(rawData, SerializerFeature.PrettyFormat);
    }

    /*
     * 从字符串获取
     * */
    public void importFromString(String s) {
        RawData items = JSON.parseObject(s, RawData.class);
        rawData.getDataItems().putAll(items.getDataItems());
        updateProperties();
    }

    /*
     * 输出到文件
     * */
    public void exportToFile() {
        exportToRawData();
        String fileName = getDataFileName();
        LOG.log(Level.INFO, "export to file: {0}...", fileName);
        File file = new File(fileName);
        if (file.exists()) {
            file.delete();
        }
        writeToFile(file, exportToString());
    }

    /*
     * 从文件获取
     * */
    public void importFromFile() {
        String fileName = getDataFileName();
        LOG.log(Level.INFO, "import from file: {0}...", fileName);

        File file = new File(fileName);
        if (file.exists()) {
            LOG.log(Level.INFO, "发现文件: {0}...", fileName);
            String tmp = readFromFile(file);
            LOG.log(Level.INFO, "数据：{0}", tmp);
            importFromString(tmp);
            // 检查关键字
            RawData items = JSON.parseObject(tmp, RawData.class);
            if (!items.getDataItems().keySet().equals(rawData.getDataItems().keySet())) {
                LOG.log(Level.INFO, "不一样啊...{0}", this.getClass().getSimpleName());
                file.delete();
                writeToFile(file, exportToString());
            }
        } else {
            LOG.log(Level.INFO, "创建示例文件: {0}...", fileName);
            writeToFile(file, exportToString());
        }
    }

    //--------------------------------------------------------------------------
    //--------------------------------------------------------------------------
    public abstract String getDataFileName();

    public double getKeyDouble(String key) {
        return Double.parseDouble(rawData.getDataItems().get(key).getValueString());
    }

    public double[] getKeyVector(String key) {
        String s = rawData.getDataItems().get(key).getValueString();
        System.out.println("转换。。。");
        System.out.println(s);
        return JSON.parseObject(s, double[].class);
    }

    public double[][] getKeyVector2D(String key) {
        return JSON.parseObject(rawData.getDataItems().get(key).getValueString(), double[][].class);
    }

    public void setKeyValue(String key, String value) {
        rawData.setValue(key, value);
    }

    public void setName(String s) {
        rawData.getDataItems().get("名称").setValueString(s);
    }

    public void setModel(String s) {
        rawData.getDataItems().get("型号").setValueString(s);
    }

    public String getName() {
        return rawData.getDataItems().get("名称").getValueString();
    }

    public String getModel() {
        return rawData.getDataItems().get("型号").getValueString();
    }

    public String getDataPath() {
        return dataPath;
    }

    public void setDataPath(String dataPath) {
        this.dataPath = dataPath;
    }

    public RawData getRawData() {
        return rawData;
    }

    public Map<String, PolynomialFunction> getPropertyPolies() {
        return propertyPolies;
    }
    
}
