package org.crafttable.resonantsocta.data.pack;

import net.minecraft.network.chat.Component;
import net.minecraft.server.packs.PackResources;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.PathPackResources;
import net.minecraft.server.packs.repository.Pack;
import net.minecraft.server.packs.repository.PackSource;
import net.minecraft.world.level.validation.DirectoryValidator;
import net.minecraftforge.event.AddPackFindersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.loading.FMLPaths;
import org.crafttable.resonantsocta.ResonantsOcta;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;

@Mod.EventBusSubscriber(modid = ResonantsOcta.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ContentPackHandler {

    /**
     * Modバスの AddPackFindersEvent で発火する。
     * リソースマネージャおよびデータパックマネージャの両方に対して、"ResonantOcta"ディレクトリ内のフォルダをPackとして登録する。
     */
    @SubscribeEvent
    public static void onAddPackFinders(AddPackFindersEvent event) {
        // パックの生成はここが呼ばれるタイミングで行うのが最も安全で確実 (FMLのGameDirが確定しているため)
        Path gameDir = FMLPaths.GAMEDIR.get();
        ContentPackGenerator.initializeContentPacks(gameDir);

        Path packsDir = gameDir.resolve(ContentPackGenerator.PACK_DIR_NAME);
        if (!Files.exists(packsDir))
            return;

        try (DirectoryStream<Path> stream = Files.newDirectoryStream(packsDir)) {
            for (Path packPath : stream) {
                if (Files.isDirectory(packPath)) {
                    String packId = ResonantsOcta.MODID + ":" + packPath.getFileName().toString();

                    // Minecraft 1.20.1 準拠のPack登録
                    // 引数: id, description, required, packResourceSupplier, packInfo, location,
                    // isHidden, source

                    Pack pack = Pack.readMetaAndCreate(
                            packId,
                            Component.literal("Resonant Octa Content Pack: " + packPath.getFileName().toString()),
                            true, // required: 必ずロードさせる
                            (s) -> new PathPackResources(s, packPath, false), // isBuiltin = false
                            event.getPackType(), // (CLIENT_RESOURCES or SERVER_DATA) イベントが持つTypeをそのまま使う
                            Pack.Position.TOP, // プライオリティ: 最高
                            PackSource.BUILT_IN // 出所を組み込みとして扱う（ユーザーが意図せず無効化できないようにするため。場合によっては PACKSOURCE.DEFAULT
                                                // 等に変える）
                    );

                    if (pack != null) {
                        event.addRepositorySource((packConsumer) -> packConsumer.accept(pack));
                        ResonantsOcta.LOGGER.info("Registered RO Content Pack '{}' for type {}", packId,
                                event.getPackType());
                    } else {
                        ResonantsOcta.LOGGER.warn(
                                "Failed to create Pack instance for directory: {}. Is pack.mcmeta missing?", packPath);
                    }
                }
            }
        } catch (IOException e) {
            ResonantsOcta.LOGGER.error("Failed to read ResonantOcta packs directory.", e);
        }
    }
}
