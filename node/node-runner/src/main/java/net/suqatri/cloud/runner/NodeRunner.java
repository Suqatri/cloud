package net.suqatri.cloud.runner;

import net.suqatri.cloud.runner.dependency.DependencyLoader;

import java.lang.reflect.InvocationTargetException;
import net.suqatri.cloud.commons.file.Files;

import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.List;
import java.util.stream.Collectors;

public class NodeRunner {

    public static void main(String[] args) {

        DependencyLoader dependencyLoader = new DependencyLoader(Files.LIBS_FOLDER.getFile(), Files.LIBS_REPO_FOLDER.getFile(), Files.LIBS_INFO_FOLDER.getFile(), Files.LIBS_BLACKLIST_FOLDER.getFile());

        List<URL> urls = dependencyLoader.getInstalledDependencies().parallelStream().map(advancedDependency -> {
            try {
                return advancedDependency.getDownloadedFile().getParentFile().toURI().toURL();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            return null;
        }).collect(Collectors.toList());

        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        URLClassLoader urlClassLoader = new URLClassLoader(urls.toArray(new URL[urls.size()]), classLoader);
        Thread.currentThread().setContextClassLoader(urlClassLoader);
        try {
            Class<?> clazz = urlClassLoader.loadClass("net.suqatri.cloud.node.NodeLauncherMain");
            Method method = clazz.getMethod("main", String[].class);
            method.invoke(null, args);
        } catch (ClassNotFoundException | NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            e.printStackTrace();
        }

    }

}
