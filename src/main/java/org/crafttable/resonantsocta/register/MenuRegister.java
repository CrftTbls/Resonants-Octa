package org.crafttable.resonantsocta.register;

import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.registries.RegistryObject;
import org.crafttable.resonantsocta.inventory.BoxMenu;

import java.util.HashMap;
import java.util.Map;

import static org.crafttable.resonantsocta.core.RORegistries.MENU_TYPES;

public class MenuRegister {
    public static final Map<String, RegistryObject<MenuType<BoxMenu>>> MENUS = new HashMap<>();

    public static void setUp() {
        MENUS.put("box", MENU_TYPES.register("box",
                () -> IForgeMenuType.create(BoxMenu::new)));
    }
}
