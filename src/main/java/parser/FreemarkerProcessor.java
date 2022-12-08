package parser;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.apache.commons.lang3.StringUtils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.StringWriter;
import java.nio.charset.Charset;

import static java.nio.charset.StandardCharsets.UTF_8;

public class FreemarkerProcessor {

    private final Configuration configuration;

    public FreemarkerProcessor(Class<?> resourceLoaderClass) {
        this(resourceLoaderClass, StringUtils.EMPTY);
    }

    /**
     * Configuration initialization
     * @param resourceLoaderClass class for loading resources
     * @param templatePath template path
     */
    public FreemarkerProcessor(Class<?> resourceLoaderClass, String templatePath) {
        configuration = new Configuration(Configuration.DEFAULT_INCOMPATIBLE_IMPROVEMENTS);
        configuration.setClassForTemplateLoading(resourceLoaderClass, templatePath);
        configuration.setDefaultEncoding(UTF_8.name());
    }

    public String process(String templateName, Object dataModel) throws IOException, TemplateException {
        Template template = configuration.getTemplate(templateName);
        StringWriter writer = new StringWriter();
        template.process(dataModel, writer);
        return writer.toString();
    }

    public String process(String templateName, Object dataModel, Charset charset) throws IOException, TemplateException {
        String data = process(templateName, dataModel);
        return new String(data.getBytes(UTF_8), charset);
    }
}
