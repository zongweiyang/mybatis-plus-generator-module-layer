import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.config.TemplateConfig;
import com.baomidou.mybatisplus.generator.config.TemplateType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.VelocityTemplateEngine;

import java.util.Collections;

public class DbGeneratorTest {

    public static final String COMMON_MODEL = "common-model";
    public static final String COMMON_SERVICE = "common-service";
    public static final String COMMON_MAPPER = "common-mapper";
    public static final String DATASOURCE = "jdbc:mysql://localhost:3306/demo";
    public static final String TABLES = "demo";

    public static void main(String[] args) {
        generate(COMMON_MODEL, TemplateType.CONTROLLER, TemplateType.MAPPER, TemplateType.SERVICE, TemplateType.SERVICEIMPL);
        generate(COMMON_SERVICE, TemplateType.CONTROLLER, TemplateType.ENTITY, TemplateType.SERVICE, TemplateType.SERVICEIMPL);
        generate(COMMON_MAPPER, TemplateType.CONTROLLER, TemplateType.MAPPER, TemplateType.ENTITY);
        System.out.println("Success ...");
    }

    private static void generate(String moduleName, TemplateType... disableTypes) {
        FastAutoGenerator.create(DATASOURCE, "demo", "demo")
                .globalConfig(builder -> {
                    builder
//                          .enableSwagger() // 开启 swagger 模式
                            .disableOpenDir() //禁止打开输出目录
                            .outputDir(moduleName + "/src/main/java"); // 指定输出目录
                })
                .packageConfig(builder -> {
                    builder.parent("com.xt.demo") // 设置父包名
                            .moduleName("model") // 设置父包模块名
                            .entity("entity") //设置entity包名
                            .pathInfo(Collections.singletonMap(OutputFile.xml, null)); // 设置禁止mapper.xml的生成
                })
                .strategyConfig(builder -> {
                    builder.addInclude(TABLES.split(","))
                            .entityBuilder()
                            .enableTableFieldAnnotation()
                            .enableLombok()
                            .enableChainModel()
                            .enableActiveRecord()
                            .idType(IdType.ASSIGN_ID)
                            .naming(NamingStrategy.underline_to_camel)
                            .serviceBuilder()
                            .formatServiceFileName("%sService")
                            .formatServiceImplFileName("%sServiceImpl")
                            .mapperBuilder()
                            .formatMapperFileName("%sMapper");
                })
                .templateConfig(builder -> builder.disable(disableTypes))
                .templateEngine(new VelocityTemplateEngine())
                .execute();
    }
}
