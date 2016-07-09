package clasedinamica;

import java.io.File;
import java.io.FileWriter;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Arrays;
import javax.tools.JavaCompiler;
import javax.tools.StandardJavaFileManager;
import javax.tools.StandardLocation;
import javax.tools.ToolProvider;

/**
 *
 * @author jberjano
 */
public class ClaseDinamica {

    public static void main(String[] args) throws Exception {
        // create the source

        String carpeta = System.getProperty("user.dir") + "/temp";
        File sourceFile = new File(carpeta + "/Hello.java");
        FileWriter writer = new FileWriter(sourceFile);

        writer.write(
                "public class Hello{ \n"
                + " public void doit() { \n"
                + "   System.out.println(\"Hello world\") ;\n"
                + " }\n"
                + "}"
        );
        writer.close();

        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        StandardJavaFileManager fileManager
                = compiler.getStandardFileManager(null, null, null);

        fileManager.setLocation(StandardLocation.CLASS_OUTPUT,
                Arrays.asList(new File(carpeta)));
        // Compile the file
        compiler.getTask(null,
                fileManager,
                null,
                null,
                null,
                fileManager.getJavaFileObjectsFromFiles(Arrays.asList(sourceFile)))
                .call();
        fileManager.close();

        // delete the source file
        // sourceFile.deleteOnExit();
        try {

            File directorio = new File(carpeta);
            URL[] urls = {directorio.toURI().toURL()};
            URLClassLoader classLoader = new URLClassLoader(urls);

            Class params[] = {};
            Object paramsObj[] = {};
            Class thisClass = Class.forName("Hello", true, classLoader);
            Object iClass = thisClass.newInstance();
            Method thisMethod = thisClass.getDeclaredMethod("doit", params);
            thisMethod.invoke(iClass, paramsObj);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
