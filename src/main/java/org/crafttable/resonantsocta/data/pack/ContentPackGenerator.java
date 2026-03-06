package org.crafttable.resonantsocta.data.pack;

import com.mojang.logging.LogUtils;
import net.minecraft.server.packs.PackType;
import org.crafttable.resonantsocta.Config;
import org.crafttable.resonantsocta.ResonantsOcta;
import org.slf4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Collections;

public class ContentPackGenerator {
    private static final Logger LOGGER = LogUtils.getLogger();
    public static final String PACK_DIR_NAME = "ResonantOcta";
    public static final String DEFAULT_PACK_NAME = "default_pack";

    // JAR内（または開発環境の resources/）からコピーするデータパック/リソースパックのソースルート
    // src/main/resources 直下をコピー元とみなす。
    private static final String[] RESOURCES_TO_COPY = {
            "data/resonants_octa",
            "assets/resonants_octa",
            "pack.mcmeta"
    };

    /**
     * ResonantOctaフォルダおよび、default_packの生成・リセットを行う。
     * この処理は AddPackFindersEvent などでPackがロードされるよりも「前」に完了している必要があるため、
     * FMLCommonSetupEventより早い、あるいは直近でフックして呼び出す。
     */
    public static void initializeContentPacks(Path gameDir) {
        Path roDir = gameDir.resolve(PACK_DIR_NAME);
        Path defaultPackDir = roDir.resolve(DEFAULT_PACK_NAME);

        try {
            if (!Files.exists(roDir)) {
                Files.createDirectories(roDir);
                LOGGER.info("Created Resonant Octa packs directory: {}", roDir);
            }

            // configでリセットが有効なら、既存のdefault_packを丸ごと削除する
            if (Config.resetDefaultPackOnStart && Files.exists(defaultPackDir)) {
                LOGGER.info("Config 'resetDefaultPackOnStart' is true. Deleting existing default_pack...");
                deleteDirectory(defaultPackDir);
            }

            // default_pack が存在しない場合は、Jar/リソースからコピーして生成する
            if (!Files.exists(defaultPackDir)) {
                Files.createDirectories(defaultPackDir);
                LOGGER.info("Generating default_pack at: {}", defaultPackDir);
                copyDefaultResources(defaultPackDir);
            } else {
                LOGGER.info("default_pack already exists. Skipping generation.");
            }

        } catch (IOException e) {
            LOGGER.error("Failed to initialize Resonant Octa content packs.", e);
        }
    }

    private static void copyDefaultResources(Path targetDir) {
        // ClassLoaderを用いて、現在のクラスパス（Jar内部 または binフォルダ等）からリソースを取得・コピー
        try {
            for (String res : RESOURCES_TO_COPY) {
                // IDE実行時はファイルシステム、Jar実行時はJarFileSystemとなるケースへの対応
                java.net.URL url = ContentPackGenerator.class.getClassLoader().getResource(res);
                if (url == null) {
                    LOGGER.warn("Default resource not found in classpath: {}. Skipping...", res);
                    continue;
                }

                java.net.URI uri = url.toURI();
                if ("jar".equals(uri.getScheme())) {
                    try (FileSystem fileSystem = FileSystems.newFileSystem(uri, Collections.emptyMap())) {
                        Path sourcePath = fileSystem.getPath(res);
                        copyDirectory(sourcePath, targetDir.resolve(res));
                    }
                } else {
                    Path sourcePath = Paths.get(uri);
                    if (Files.isDirectory(sourcePath)) {
                        copyDirectory(sourcePath, targetDir.resolve(res));
                    } else if (Files.isRegularFile(sourcePath)) {
                        Path dest = targetDir.resolve(res);
                        Files.createDirectories(dest.getParent());
                        Files.copy(sourcePath, dest, StandardCopyOption.REPLACE_EXISTING);
                    }
                }
            }
        } catch (Exception e) {
            LOGGER.error("Error while copying default resources to {}", targetDir, e);
        }
    }

    private static void copyDirectory(Path source, Path target) throws IOException {
        Files.walkFileTree(source, new SimpleFileVisitor<Path>() {
            @Override
            public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
                Path relative = source.relativize(dir);
                Path destDir = target.resolve(relative.toString());
                if (!Files.exists(destDir)) {
                    Files.createDirectories(destDir);
                }
                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                Path relative = source.relativize(file);
                Path destFile = target.resolve(relative.toString());
                Files.copy(file, destFile, StandardCopyOption.REPLACE_EXISTING);
                return FileVisitResult.CONTINUE;
            }
        });
    }

    private static void deleteDirectory(Path directoryToBeDeleted) throws IOException {
        Files.walkFileTree(directoryToBeDeleted, new SimpleFileVisitor<Path>() {
            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                Files.delete(file);
                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
                Files.delete(dir);
                return FileVisitResult.CONTINUE;
            }
        });
    }
}
